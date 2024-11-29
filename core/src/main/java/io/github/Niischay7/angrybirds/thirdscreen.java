package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class thirdscreen implements Screen {
    private Main game;
    private Stage stage;
    private transient Texture background;
    private transient Image backgroundImage;
    private transient Texture buttonUpTexture;
    private boolean birdsAdded = false;
    private Bird selectedBird;
    int levelNumber;
    Vector2 slingPosition;
    Array<Bird> birds;
    private Vector2 dragStart;
    Array<blocks> allBlocks;
    Array<pig> allPigs;
    private static final float MAX_DRAG_DISTANCE = 150f;
    private static final float MIN_LAUNCH_POWER = 200f;
    private static final float MAX_LAUNCH_POWER = 800f;
    private collisionmanager collisionManager;
    private int score = 0;
    private transient Label scoreLabel;
    private transient TrajectoryActor trajectoryActor;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private static final float TIME_STEP = 1 / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;
    boolean levelCompleted = false;

    public thirdscreen(Main game, int levelNumber) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.birds = new Array<>();
        this.slingPosition = new Vector2(70, 137);
        this.dragStart = new Vector2();
        this.allBlocks = new Array<>();
        this.allPigs = new Array<>();
        this.levelNumber = levelNumber;
        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();
    }

    private void createLevelStructure() {
        switch (levelNumber) {
            case 1:
                createFirstLevelStructure();
                break;
            case 2:
                createSecondLevelStructure();
                break;
            case 3:
                createThirdLevelStructure();
                break;
            default:
                createFirstLevelStructure();
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        trajectoryActor = new TrajectoryActor();
        stage.addActor(trajectoryActor);
        if (background == null) {
            background = new Texture("thirdscreenbg.jpg");
            backgroundImage = new Image(background);
            backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            stage.addActor(backgroundImage);

            Texture catapult = new Texture("catapult-removebg-preview.png");
            catapult cata = new catapult(45.2F, 500, catapult);
            Image catapultImage = new Image(catapult);
            catapultImage.setSize(100, 100);
            catapultImage.setPosition(40, 68);
            stage.addActor(catapultImage);

            setupButtons();
        }
        setupScoreboard();

        if (!birdsAdded) {
            addBirds();
            createLevelStructure();
            collisionManager = new collisionmanager(stage, allBlocks, allPigs, game, this);
            birdsAdded = true;
        }
    }

    private void setupScoreboard() {

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        scoreLabel = new Label("Score: 0", skin);
        scoreLabel.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 50);
        scoreLabel.setColor(Color.WHITE);

        stage.addActor(scoreLabel);
    }

    void updateScore(int points) {
        score += points;
        if (scoreLabel != null) {
            scoreLabel.setText("Score: " + score);
        }
    }

    public int getScore() {
        return score;
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ShapeRenderer trajectoryRenderer = new ShapeRenderer();

        stage.act(delta);

        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

        if (selectedBird != null && !selectedBird.isLaunched()) {
            try {

                float deltaX = slingPosition.x - selectedBird.getX();
                float deltaY = slingPosition.y - selectedBird.getY();
                Vector2 launchVector = new Vector2(deltaX, deltaY);

                float distance = launchVector.len();
                float powerRatio = Math.min(distance / MAX_DRAG_DISTANCE, 1.0f);
                float power = MIN_LAUNCH_POWER + (powerRatio * (MAX_LAUNCH_POWER - MIN_LAUNCH_POWER));

                launchVector.nor().scl(power);

                Vector2[] trajectoryPoints = selectedBird.calculateTrajectory(launchVector, 3f);

                trajectoryActor.setTrajectoryPoints(trajectoryPoints);
            } catch (Exception e) {
                System.err.println("Error drawing trajectory: " + e.getMessage());
            }
        } else {
            trajectoryActor.setTrajectoryPoints(null);
        }

        for (Bird bird : birds) {
            if (bird.isLaunched()) {
                bird.act(delta);
                handleCollisions(bird);

                for (blocks block : allBlocks) {
                    if (block.isDestroyed()) {
                        block.remove();
                    }
                }

                for (pig pig : allPigs) {
                    if (pig.isFalling()) {
                        pig.updateFalling(delta);
                    }
                }
            }
        }

        stage.draw();

        debugRenderer.render(world, stage.getCamera().combined);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        if (backgroundImage != null) {
            backgroundImage.setSize(width, height);
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        if (background != null) {
            background.dispose();
        }
        if (buttonUpTexture != null) {
            buttonUpTexture.dispose();
        }
        if (trajectoryActor != null) {
            trajectoryActor.dispose();
        }
        for (Bird bird : birds) {
            if (bird.texture != null) {
                bird.texture.dispose();
            }
        }
        world.dispose();
        debugRenderer.dispose();
    }

    private void setupButtons() {
        buttonUpTexture = new Texture(Gdx.files.internal("bottom.png"));
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(buttonUpTexture);
        ImageButton imageButton = new ImageButton(buttonStyle);
        imageButton.setSize(90, 70);
        imageButton.setPosition(0, 409);
        imageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PauseScreen(game, thirdscreen.this, levelNumber));
            }
        });

        TextButton lose_game = new TextButton("Lose Game", new Skin(Gdx.files.internal("uiskin.json")));
        lose_game.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 60);
        lose_game.setSize(100, 60);
        lose_game.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new losescreen(game, thirdscreen.this));
            }
        });

        TextButton win_game = new TextButton("", new Skin(Gdx.files.internal("uiskin.json")));
        win_game.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 150);
        win_game.setSize(100, 60);
        win_game.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new winscreen(game, score));
            }
        });
        TextButton nextLevelButton = new TextButton("Win game", new Skin(Gdx.files.internal("uiskin.json")));
        nextLevelButton.setPosition(Gdx.graphics.getWidth() - 120, 20);
        nextLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (levelCompleted) {
                    game.setScreen(new thirdscreen(game, levelNumber + 1));
                }
            }
        });
      //  stage.addActor(nextLevelButton);

        stage.addActor(imageButton);
    }

    private Array<blocks> getAllBlocks() {
        return allBlocks;
    }

    private Array<pig> getAllPigs() {
        return allPigs;
    }

    private void addBirds() {
        float birdX = 10;
        float birdY = 68;
        float spacing = 50;

        Texture terence1 = new Texture("terence-removebg-preview.png");
        Bird terence = createBird(terence1, "Red", birdX, birdY, 40);
        terence.getBirdImage().setName("terence");
        birds.add(terence);
        stage.addActor(terence.getBirdImage());

        Texture blueImage1 = new Texture("bluebird-removebg-preview.png");
        Bird blueBird = createBird(blueImage1, "Blue", birdX - spacing, birdY, 40);
        blueBird.getBirdImage().setName("bluebird");
        birds.add(blueBird);
        stage.addActor(blueBird.getBirdImage());

        Texture yellowImage1 = new Texture("yellow-removebg-preview.png");
        Bird yellowBird = createBird(yellowImage1, "Yellow", birdX - (2 * spacing), birdY, 40);
        yellowBird.getBirdImage().setName("yellow");
        birds.add(yellowBird);
        stage.addActor(yellowBird.getBirdImage());

        if (!birds.isEmpty()) {
            birds.get(0).setPosition(slingPosition.x, slingPosition.y);
        }

        for (Bird bird : birds) {
            addBirdListeners(bird);
        }
    }

    private Bird createBird(Texture texture, String color, float x, float y, float size) {
        Bird bird = new Bird(texture, color, "", size, 100, 25, new Vector2(0, 0));
        bird.setPosition(x, y);
        return bird;
    }

    private void addBirdListeners(final Bird bird) {
        bird.getBirdImage().addListener(new InputListener() {
            private boolean isDragging = false;
            private final Vector2 dragOffset = new Vector2();
            private final Vector2 currentDrag = new Vector2();
            private static final float MAX_DRAG_DISTANCE = 100f;
            private static final float MIN_LAUNCH_POWER = 400f;
            private static final float MAX_LAUNCH_POWER = 1000f;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!bird.isLaunched()) {
                    isDragging = true;
                    selectedBird = bird;
                    dragOffset.set(x, y);
                    return true;
                }
                return false;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (isDragging && selectedBird != null) {
                    float stageX = event.getStageX();
                    float stageY = event.getStageY();

                    currentDrag.set(stageX - slingPosition.x, stageY - slingPosition.y);

                    float distance = currentDrag.len();

                    if (distance > MAX_DRAG_DISTANCE) {
                        currentDrag.nor().scl(MAX_DRAG_DISTANCE);
                    }

                    float newX = slingPosition.x + currentDrag.x;
                    float newY = slingPosition.y + currentDrag.y;

                    selectedBird.setPosition(newX, newY);
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (isDragging && selectedBird != null) {
                    float deltaX = slingPosition.x - selectedBird.getX();
                    float deltaY = slingPosition.y - selectedBird.getY();

                    Vector2 launchVector = new Vector2(deltaX, deltaY);
                    selectedBird.getBirdImage().setName("launched-" + selectedBird.getBirdImage().getName());

                    float distance = launchVector.len();

                    float powerRatio = Math.min(distance / MAX_DRAG_DISTANCE, 1.0f);
                    float power = MIN_LAUNCH_POWER + (powerRatio * (MAX_LAUNCH_POWER - MIN_LAUNCH_POWER));

                    launchVector.nor().scl(power);

                    selectedBird.setVelocity(launchVector);
                    selectedBird.launch();

                    moveNextBirdToSling();

                    isDragging = false;
                    selectedBird = null;
                }
            }
        });
    }

    private void moveNextBirdToSling() {
        for (Bird nextBird : birds) {
            if (!nextBird.isLaunched()) {
                nextBird.setPosition(slingPosition.x, slingPosition.y);
                return;
            }
        }
    }

    private void addBlockToStage(blocks block, float x, float y) {
        Image blockImage = new Image(block.texture);
        blockImage.setSize(block.size, block.size);
        blockImage.setPosition(x, y);
        stage.addActor(blockImage);
    }

    private void addPigToStage(pig pig, float x, float y) {
        Image pigImage = new Image(pig.texture);
        pigImage.setSize(pig.size, pig.size);
        pigImage.setPosition(x, y);
        stage.addActor(pigImage);
    }

    private void handleCollisions(Bird bird) {
        collisionManager.handleCollisions(bird);
    }

    private void processChainReaction(blocks startBlock, Array<blocks> processedBlocks,
                                      Array<pig> processedPigs, float angle, float speed, Bird originalBird) {
        if (processedBlocks.contains(startBlock, true)) return;

        processedBlocks.add(startBlock);

        float chainReactionMultiplier = 0.7f;
        int chainDamage = Math.round(originalBird.damage * chainReactionMultiplier);

        startBlock.takeDamage(originalBird, angle, speed);

        if (startBlock.isDestroyed()) {
            for (blocks blockAbove : startBlock.blocksAbove) {
                if (!processedBlocks.contains(blockAbove, true)) {
                    processChainReaction(blockAbove, processedBlocks, processedPigs, 90, speed * 0.8f, originalBird);
                }
            }

            for (pig pigAbove : startBlock.pigsAbove) {
                if (!processedPigs.contains(pigAbove, true)) {
                    pigAbove.takeDamage(chainDamage, 90, speed * 0.8f);
                    processedPigs.add(pigAbove);
                }
            }

            checkNearbyBlocks(startBlock, processedBlocks, processedPigs, angle, speed, originalBird);
        }
    }

    private void checkNearbyBlocks(blocks destroyedBlock, Array<blocks> processedBlocks,
                                   Array<pig> processedPigs, float angle, float speed, Bird originalBird) {
        float collapseRadius = destroyedBlock.size * 1.5f;
        float proximityDamageMultiplier = 0.5f;

        for (blocks block : getAllBlocks()) {
            if (!processedBlocks.contains(block, true) && !block.isDestroyed()) {
                float dx = block.getX() - destroyedBlock.getX();
                float dy = block.getY() - destroyedBlock.getY();
                float distance = (float) Math.sqrt(dx * dx + dy * dy);

                if (distance <= collapseRadius) {
                    float distanceRatio = 1 - (distance / collapseRadius);
                    float adjustedSpeed = speed * distanceRatio * proximityDamageMultiplier;

                    processChainReaction(block, processedBlocks, processedPigs, angle, adjustedSpeed, originalBird);
                }
            }
        }
    }

    void checkGameState() {
        boolean allPigsDestroyed = true;
        for (pig pig : getAllPigs()) {
            if (!pig.isDestroyed()) {
                allPigsDestroyed = false;
                break;
            }
        }

        if (allPigsDestroyed) {
            game.setScreen(new winscreen(game, score));
            return;
        }

        boolean birdsRemaining = false;
        for (Bird bird : birds) {
            if (!bird.isLaunched()) {
                birdsRemaining = true;
                break;
            }
        }

        if (!birdsRemaining && !allPigsDestroyed) {
            game.setScreen(new losescreen(game, this));
        }
    }

    private void setupBlockRelationships() {
        Array<blocks> allBlocks = getAllBlocks();
        Array<pig> allPigs = getAllPigs();

        int size = allBlocks.size;
        for (int i = 0; i < size; i++) {
            blocks block = allBlocks.get(i);

            for (int j = 0; j < size; j++) {
                blocks other = allBlocks.get(j);
                if (i != j && isDirectlyAbove(block, other)) {
                    block.addBlockAbove(other);
                }
            }

            for (int k = 0; k < allPigs.size; k++) {
                pig currentPig = allPigs.get(k);
                if (isDirectlyAbove(block, currentPig)) {
                    block.addPigAbove(currentPig);
                }
            }
        }
    }

    private boolean isDirectlyAbove(blocks base, blocks above) {
        float tolerance = 5f;
        return above.getY() > base.getY() &&
            Math.abs((base.getX() + base.size / 2) - (above.getX() + above.size / 2)) < tolerance;
    }

    private boolean isDirectlyAbove(blocks base, pig above) {
        float tolerance = 5f;
        return above.getY() > base.getY() &&
            Math.abs((base.getX() + base.size / 2) - (above.getX() + above.size / 2)) < tolerance;
    }

    private void createFirstLevelStructure() {
        allBlocks.clear();
        allPigs.clear();

        stoneblock stoneBase1 = new stoneblock(60, 100);
        stoneblock stoneBase2 = new stoneblock(60, 100);
        stoneblock stoneBase3 = new stoneblock(60, 100);

        woodblock woodMiddle1 = new woodblock(60, 80);
        woodblock woodMiddle2 = new woodblock(60, 80);

        glassblock glassTop = new glassblock(60, 50);

        allBlocks.add(stoneBase1);
        allBlocks.add(stoneBase2);
        allBlocks.add(stoneBase3);
        allBlocks.add(woodMiddle1);
        allBlocks.add(woodMiddle2);
        allBlocks.add(glassTop);

        Texture pigTexture = new Texture("pigs-removebg-preview.png");
        pig smallPig = new pig("green", "basic", 40, 50, pigTexture);
        pig bigPig = new pig("green", "big", 50, 75, pigTexture);
        pig kingpig=new pig("green","king",75,100,pigTexture);

        allPigs.add(smallPig);
        allPigs.add(bigPig);

        float baseX = 300;
        float baseY = 68;

        stoneBase1.setPosition(baseX, baseY);
        stoneBase2.setPosition(baseX + 65, baseY);
        stoneBase3.setPosition(baseX + 130, baseY);
        woodMiddle1.setPosition(baseX + 22f, baseY + 58);
        woodMiddle2.setPosition(baseX + 106f, baseY + 58);
        glassTop.setPosition(baseX + 61, baseY + 120);

        smallPig.setPosition(baseX + 75, baseY + 60);
        bigPig.setPosition(baseX + 65, baseY + 130);

        addBlockToStage(stoneBase1, stoneBase1.getX(), stoneBase1.getY());
        addBlockToStage(stoneBase2, stoneBase2.getX(), stoneBase2.getY());
        addBlockToStage(stoneBase3, stoneBase3.getX(), stoneBase3.getY());
        addBlockToStage(woodMiddle1, woodMiddle1.getX(), woodMiddle1.getY());
        addBlockToStage(woodMiddle2, woodMiddle2.getX(), woodMiddle2.getY());
        addBlockToStage(glassTop, glassTop.getX(), glassTop.getY());

        addPigToStage(smallPig, smallPig.getX(), smallPig.getY());
        addPigToStage(bigPig, bigPig.getX(), bigPig.getY());

        setupBlockRelationships();
    }

    private void createSecondLevelStructure() {
        allBlocks.clear();
        allPigs.clear();

        stoneblock stoneBase1 = new stoneblock(60, 100);
        stoneblock stoneBase2 = new stoneblock(60, 100);
        stoneblock stoneBase3 = new stoneblock(60, 100);

        woodblock woodMiddle1 = new woodblock(60, 80);
        stoneblock stoneMiddle = new stoneblock(60, 80);

        glassblock glassTop1 = new glassblock(60, 50);
        woodblock woodTop = new woodblock(60, 50);

        allBlocks.add(stoneBase1);
        allBlocks.add(stoneBase2);
        allBlocks.add(stoneBase3);
        allBlocks.add(woodMiddle1);
        allBlocks.add(stoneMiddle);
        allBlocks.add(glassTop1);
        allBlocks.add(woodTop);

        Texture pigTexture = new Texture("pigs-removebg-preview.png");
        pig smallPig = new pig("green", "basic", 40, 50, pigTexture);
        pig mediumPig = new pig("green", "medium", 45, 60, pigTexture);

        allPigs.add(smallPig);
        allPigs.add(mediumPig);

        float baseX = 300;
        float baseY = 68;

        stoneBase1.setPosition(baseX, baseY);
        stoneBase2.setPosition(baseX + 65, baseY);
        stoneBase3.setPosition(baseX + 130, baseY);
        woodMiddle1.setPosition(baseX + 22f, baseY + 58);
        stoneMiddle.setPosition(baseX + 106f, baseY + 58);
        glassTop1.setPosition(baseX + 40, baseY + 120);
        woodTop.setPosition(baseX + 100, baseY + 120);

        smallPig.setPosition(baseX + 75, baseY + 60);
        mediumPig.setPosition(baseX + 85, baseY + 130);

        addBlockToStage(stoneBase1, stoneBase1.getX(), stoneBase1.getY());
        addBlockToStage(stoneBase2, stoneBase2.getX(), stoneBase2.getY());
        addBlockToStage(stoneBase3, stoneBase3.getX(), stoneBase3.getY());
        addBlockToStage(woodMiddle1, woodMiddle1.getX(), woodMiddle1.getY());
        addBlockToStage(stoneMiddle, stoneMiddle.getX(), stoneMiddle.getY());
        addBlockToStage(glassTop1, glassTop1.getX(), glassTop1.getY());
        addBlockToStage(woodTop, woodTop.getX(), woodTop.getY());

        addPigToStage(smallPig, smallPig.getX(), smallPig.getY());
        addPigToStage(mediumPig, mediumPig.getX(), mediumPig.getY());

        setupBlockRelationships();
    }

    private void createThirdLevelStructure() {
        allBlocks.clear();
        allPigs.clear();

        stoneblock stoneBase1 = new stoneblock(60, 100);
        stoneblock stoneBase2 = new stoneblock(60, 100);
        woodblock woodBase = new woodblock(60, 100);

        woodblock woodMiddle1 = new woodblock(60, 80);
        stoneblock stoneMiddle = new stoneblock(60, 80);
        glassblock glassMiddle = new glassblock(60, 80);

        glassblock glassTop1 = new glassblock(60, 50);
        woodblock woodTop = new woodblock(60, 50);

        allBlocks.add(stoneBase1);
        allBlocks.add(stoneBase2);
        allBlocks.add(woodBase);
        allBlocks.add(woodMiddle1);
        allBlocks.add(stoneMiddle);
        allBlocks.add(glassMiddle);
        allBlocks.add(glassTop1);
        allBlocks.add(woodTop);

        Texture pigTexture = new Texture("pigs-removebg-preview.png");
        pig smallPig = new pig("green", "basic", 40, 50, pigTexture);
        pig mediumPig = new pig("green", "medium", 45, 60, pigTexture);
        pig bigPig = new pig("green", "big", 50, 75, pigTexture);

        allPigs.add(smallPig);
        allPigs.add(mediumPig);
        allPigs.add(bigPig);

        float baseX = 300;
        float baseY = 68;

        stoneBase1.setPosition(baseX, baseY);
        stoneBase2.setPosition(baseX + 65, baseY);
        woodBase.setPosition(baseX + 130, baseY);
        woodMiddle1.setPosition(baseX + 22f, baseY + 58);
        stoneMiddle.setPosition(baseX + 86f, baseY + 58);
        glassMiddle.setPosition(baseX + 150f, baseY + 58);
        glassTop1.setPosition(baseX + 40, baseY + 120);
        woodTop.setPosition(baseX + 110, baseY + 120);

        smallPig.setPosition(baseX + 75, baseY + 60);
        mediumPig.setPosition(baseX + 125, baseY + 60);
        bigPig.setPosition(baseX + 85, baseY + 130);

        addBlockToStage(stoneBase1, stoneBase1.getX(), stoneBase1.getY());
        addBlockToStage(stoneBase2, stoneBase2.getX(), stoneBase2.getY());
        addBlockToStage(woodBase, woodBase.getX(), woodBase.getY());
        addBlockToStage(woodMiddle1, woodMiddle1.getX(), woodMiddle1.getY());
        addBlockToStage(stoneMiddle, stoneMiddle.getX(), stoneMiddle.getY());
        addBlockToStage(glassMiddle, glassMiddle.getX(), glassMiddle.getY());
        addBlockToStage(glassTop1, glassTop1.getX(), glassTop1.getY());
        addBlockToStage(woodTop, woodTop.getX(), woodTop.getY());

        addPigToStage(smallPig, smallPig.getX(), smallPig.getY());
        addPigToStage(mediumPig, mediumPig.getX(), mediumPig.getY());
        addPigToStage(bigPig, bigPig.getX(), bigPig.getY());

        setupBlockRelationships();
    }

    void resetGameScreen() {
        stage.clear();

        if (background == null) {
            background = new Texture("thirdscreenbg.jpg");
        }
        backgroundImage = new Image(background);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        Texture catapultTexture = new Texture("catapult-removebg-preview.png");
        Image catapultImage = new Image(catapultTexture);
        catapultImage.setSize(100, 100);
        catapultImage.setPosition(40, 68);
        stage.addActor(catapultImage);

        setupScoreboard();

        setupButtons();

        for (Bird bird : birds) {

            System.out.println(bird.color +bird.isLaunched());
            bird.reinitializeTransientFields(bird.texture);
            stage.addActor(bird.getBirdImage());
            System.out.println(bird.getBirdImage().getName());
            if (bird.color=="Blue")
            {
                bird.getBirdImage().setName("bluebird");
            } else if (bird.color=="Red") {
                bird.getBirdImage().setName("terence");

            } else if (bird.color=="Yellow") {
                bird.getBirdImage().setName("Yellow");
            }
            System.out.println(bird.getBirdImage().getName());
            if (!bird.isLaunched()) {
                bird.setPosition(slingPosition.x, slingPosition.y);
            }
            addBirdListeners(bird);
        }

        for (blocks block : allBlocks) {
            if (!block.isDestroyed()) {
                block.reinitializeTransientFields(block.texture);
                addBlockToStage(block, block.getX(), block.getY());
            }
        }

        for (pig pig : allPigs) {
            if (!pig.isDestroyed()) {
                pig.reinitializeTransientFields(pig.texture);
                addPigToStage(pig, pig.getX(), pig.getY());
            }
        }

        collisionManager = new collisionmanager(stage, allBlocks, allPigs, game, this);
        checkGameState();

        System.out.println("Game screen reset with loaded data.");
        for (blocks block : allBlocks) {
            System.out.println("Block: " + block.getX() + ", " + block.getY() + ", " + block.isDestroyed());
        }

        for (pig pig : allPigs) {
            System.out.println("Pig: " + pig.getX() + ", " + pig.getY() + ", " + pig.isDestroyed());
        }
    }

    public void setScore(int score) {
        this.score = score;
        if (scoreLabel != null) {
            scoreLabel.setText("Score: " + score);
        }
    }
}
