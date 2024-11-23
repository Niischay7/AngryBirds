package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.Array;

public class thirdscreen implements Screen {
    private Main game;
    private Stage stage;
    private Texture background;
    private Image backgroundImage;
    private Texture buttonUpTexture;
    private boolean birdsAdded = false;
    private Bird selectedBird;
    private Vector2 slingPosition;
    private Array<Bird> birds;
    private Vector2 dragStart;
    private Array<blocks> allBlocks;
    private Array<pig> allPigs;
    private static final float MAX_DRAG_DISTANCE = 150f; // Adjust this value as needed
    private static final float MIN_LAUNCH_POWER = 200f; // Minimum launch power
    private static final float MAX_LAUNCH_POWER = 800f;
    private collisionmanager collisionManager;
    public thirdscreen(Main game, int i) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.birds = new Array<>();
        this.slingPosition = new Vector2(70, 137); // Positioned near catapult
        this.dragStart = new Vector2();
        this.allBlocks = new Array<>();
        this.allPigs = new Array<>();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        if (background == null) {
            // Set up background
            background = new Texture("thirdscreenbg.jpg");
            backgroundImage = new Image(background);
            backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            stage.addActor(backgroundImage);

            // Set up catapult
            Texture catapult = new Texture("catapult-removebg-preview.png");
            catapult cata = new catapult(45.2F, 500, catapult);
            Image catapultImage = new Image(catapult);
            catapultImage.setSize(100, 100);
            catapultImage.setPosition(40, 68);
            stage.addActor(catapultImage);

            // Set up buttons
            setupButtons();
        }

        // Add birds and structure if not already added
        if (!birdsAdded) {
            addBirds();
            createStructure();
            collisionManager = new collisionmanager(stage, allBlocks, allPigs,game,this);
            birdsAdded = true;
        }
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update stage
        stage.act(delta);

        // Update birds physics and check collisions
        for (Bird bird : birds) {
            if (bird.isLaunched()) {
                bird.act(delta);
                handleCollisions(bird); // Add this line to check collisions

                // Optional: Remove destroyed blocks/pigs from stage
                for (blocks block : allBlocks) {
                    if (block.isDestroyed()) {
                        block.remove(); // Remove from stage if actor exists
                    }
                }

                for (pig pig : allPigs) {
                    if (pig.isDestroyed()) {
                        pig.remove(); // Remove from stage if actor exists
                    }
                }
            }
        }

        // Draw stage
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Update viewport when screen is resized
        stage.getViewport().update(width, height, true);

        // Resize background if it exists
        if (backgroundImage != null) {
            backgroundImage.setSize(width, height);
        }
    }

    @Override
    public void pause() {
        // Implement pause logic if needed
    }

    @Override
    public void resume() {
        // Implement resume logic if needed
    }

    @Override
    public void hide() {
        // Called when this screen is no longer the current screen
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        // Dispose of all resources
        stage.dispose();
        if (background != null) {
            background.dispose();
        }
        if (buttonUpTexture != null) {
            buttonUpTexture.dispose();
        }
        // Dispose of bird textures
        for (Bird bird : birds) {
            if (bird.texture != null) {
                bird.texture.dispose();
            }
        }
    }

    private void setupButtons() {
        // Pause button
        buttonUpTexture = new Texture(Gdx.files.internal("bottom.png"));
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(buttonUpTexture);
        ImageButton imageButton = new ImageButton(buttonStyle);
        imageButton.setSize(90, 70);
        imageButton.setPosition(0, 409);
        imageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PauseScreen(game, thirdscreen.this));
            }
        });

        // Win/Lose buttons
        TextButton lose_game = new TextButton("Lose Game", new Skin(Gdx.files.internal("uiskin.json")));
        lose_game.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 60);
        lose_game.setSize(100, 60);
        lose_game.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new losescreen(game, thirdscreen.this));
            }
        });

        TextButton win_game = new TextButton("Win game", new Skin(Gdx.files.internal("uiskin.json")));
        win_game.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 150);
        win_game.setSize(100, 60);
        win_game.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new winscreen(game));
            }
        });

        stage.addActor(imageButton);
        stage.addActor(lose_game);
        stage.addActor(win_game);
    }
    private Array<blocks> getAllBlocks() {
        return allBlocks;
    }

    private Array<pig> getAllPigs() {
        return allPigs;
    }
    private void addBirds() {
        float birdX = slingPosition.x;
        float birdY = slingPosition.y;

        // Terence bird
        Texture terence1 = new Texture("terence-removebg-preview.png");
        Bird terence = createBird(terence1, "Red", birdX, birdY, 40);
        terence.getBirdImage().setName("terence");
        birds.add(terence);
        stage.addActor(terence.getBirdImage());

        // Blue bird
        Texture blueImage1 = new Texture("bluebird-removebg-preview.png");
        Bird blueBird = createBird(blueImage1, "Blue", birdX - 50, birdY, 40);
        birds.add(blueBird);

        blueBird.getBirdImage().setName("bluebird");

        stage.addActor(blueBird.getBirdImage());

        // Yellow bird
        Texture yellowImage1 = new Texture("yellow-removebg-preview.png");
        Bird yellowBird = createBird(yellowImage1, "Yellow", birdX - 100, birdY, 40);
        birds.add(yellowBird);
        stage.addActor(yellowBird.getBirdImage());
        yellowBird.getBirdImage().setName("yellow");
        // Add listeners to each bird
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
            private static final float MAX_DRAG_DISTANCE = 100f; // Reduced from 150f
            private static final float MIN_LAUNCH_POWER = 400f;  // Increased from 200f
            private static final float MAX_LAUNCH_POWER = 1000f; // Increased from 800f

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
                    selectedBird.getBirdImage().setName("launched");
                    float distance = launchVector.len();
                    // Adjusted power calculation for more intense launches
                    float powerRatio = Math.min(distance / MAX_DRAG_DISTANCE, 1.0f);
                    float power = MIN_LAUNCH_POWER + (powerRatio * (MAX_LAUNCH_POWER - MIN_LAUNCH_POWER));

                    launchVector.nor().scl(power);
                    selectedBird.setVelocity(launchVector);
                    selectedBird.launch();

                    System.out.println("Launch Power: " + power);
                    System.out.println("Launch Vector: " + launchVector);

                    isDragging = false;
                    selectedBird = null;
                }
            }
        });
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
       // checkGameState(); // Check if game is won/lost after collisions
    }
    private void processChainReaction(blocks startBlock, Array<blocks> processedBlocks,
                                      Array<pig> processedPigs, float angle, float speed, Bird originalBird) {
        if (processedBlocks.contains(startBlock, true)) return;

        // Process current block
        processedBlocks.add(startBlock);

        // Calculate chain reaction damage
        float chainReactionMultiplier = 0.7f; // Reduce damage for chain reactions
        int chainDamage = Math.round(originalBird.damage * chainReactionMultiplier);

        // Apply damage to the block using the original bird's properties
        startBlock.takeDamage(originalBird, angle, speed);

        if (startBlock.isDestroyed()) {
            // Process blocks above with diminishing damage
            for (blocks blockAbove : startBlock.blocksAbove) {
                if (!processedBlocks.contains(blockAbove, true)) {
                    processChainReaction(blockAbove, processedBlocks, processedPigs, 90, speed * 0.8f, originalBird);
                }
            }

            // Process pigs above with diminishing damage
            for (pig pigAbove : startBlock.pigsAbove) {
                if (!processedPigs.contains(pigAbove, true)) {
                    pigAbove.takeDamage(chainDamage, 90, speed * 0.8f);
                    processedPigs.add(pigAbove);
                }
            }

            // Check nearby blocks for additional collapses
            checkNearbyBlocks(startBlock, processedBlocks, processedPigs, angle, speed, originalBird);
        }
    }



    private void checkNearbyBlocks(blocks destroyedBlock, Array<blocks> processedBlocks,
                                   Array<pig> processedPigs, float angle, float speed, Bird originalBird) {
        float collapseRadius = destroyedBlock.size * 1.5f;
        float proximityDamageMultiplier = 0.5f; // Reduce damage for proximity effects

        for (blocks block : getAllBlocks()) {
            if (!processedBlocks.contains(block, true) && !block.isDestroyed()) {
                float dx = block.getX() - destroyedBlock.getX();
                float dy = block.getY() - destroyedBlock.getY();
                float distance = (float) Math.sqrt(dx * dx + dy * dy);

                if (distance <= collapseRadius) {
                    // Calculate diminished damage based on distance
                    float distanceRatio = 1 - (distance / collapseRadius);
                    float adjustedSpeed = speed * distanceRatio * proximityDamageMultiplier;

                    processChainReaction(block, processedBlocks, processedPigs, angle, adjustedSpeed, originalBird);
                }
            }
        }
    }

    private void checkGameState() {
        // Check if all pigs are destroyed
        boolean allPigsDestroyed = true;
        for (pig pig : getAllPigs()) {
            if (!pig.isDestroyed()) {
                allPigsDestroyed = false;
                break;
            }
        }

        if (allPigsDestroyed) {
            game.setScreen(new winscreen(game));
            return;
        }

        // Check if all birds are used
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

    // Add these methods to your thirdscreen class to set up block relationships
    private void setupBlockRelationships() {
        Array<blocks> allBlocks = getAllBlocks();
        Array<pig> allPigs = getAllPigs();

        // For each base block
        int size = allBlocks.size;
        for (int i = 0; i < size; i++) {
            blocks block = allBlocks.get(i);

            // Check all other blocks to find ones above it
            for (int j = 0; j < size; j++) {
                blocks other = allBlocks.get(j);
                // Skip checking against itself
                if (i != j && isDirectlyAbove(block, other)) {
                    block.addBlockAbove(other);
                }
            }

            // Check all pigs to find ones above this block
            for (int k = 0; k < allPigs.size; k++) {
                pig currentPig = allPigs.get(k);
                if (isDirectlyAbove(block, currentPig)) {
                    block.addPigAbove(currentPig);
                }
            }
        }
    }

    private boolean isDirectlyAbove(blocks base, blocks above) {
        float tolerance = 5f; // Adjust this value based on your needs
        return above.getY() > base.getY() &&
            Math.abs((base.getX() + base.size/2) - (above.getX() + above.size/2)) < tolerance;
    }

    private boolean isDirectlyAbove(blocks base, pig above) {
        float tolerance = 5f;
        return above.getY() > base.getY() &&
            Math.abs((base.getX() + base.size/2) - (above.getX() + above.size/2)) < tolerance;
    }
    private void createStructure() {
        // Clear existing arrays
        allBlocks.clear();
        allPigs.clear();

        // Create base layer - Stone blocks
        stoneblock stoneBase1 = new stoneblock(60, 100);
        stoneblock stoneBase2 = new stoneblock(60, 100);
        stoneblock stoneBase3 = new stoneblock(60, 100);

        // Middle layer - Wood blocks
        woodblock woodMiddle1 = new woodblock(60, 80);
        woodblock woodMiddle2 = new woodblock(60, 80);

        // Top layer - Glass block
        glassblock glassTop = new glassblock(60, 50);

        // Add all blocks to tracking array
        allBlocks.add(stoneBase1);
        allBlocks.add(stoneBase2);
        allBlocks.add(stoneBase3);
        allBlocks.add(woodMiddle1);
        allBlocks.add(woodMiddle2);
        allBlocks.add(glassTop);

        Texture pigTexture = new Texture("pigs-removebg-preview.png");
        pig smallPig = new pig("green", "basic", 40, 50, pigTexture);
        pig bigPig = new pig("green", "big", 50, 75, pigTexture);

        // Add pigs to tracking array
        allPigs.add(smallPig);
        allPigs.add(bigPig);

        // Position and add blocks to stage
        float baseX = 300;
        float baseY = 68;

        // Position blocks
        stoneBase1.setPosition(baseX, baseY);
        stoneBase2.setPosition(baseX + 65, baseY);
        stoneBase3.setPosition(baseX + 130, baseY);
        woodMiddle1.setPosition(baseX + 22f, baseY + 58);
        woodMiddle2.setPosition(baseX + 106f, baseY + 58);
        glassTop.setPosition(baseX + 61, baseY + 120);

        // Position pigs
        smallPig.setPosition(baseX + 75, baseY + 60);
        bigPig.setPosition(baseX + 65, baseY + 130);

        // Add blocks to stage
        addBlockToStage(stoneBase1, stoneBase1.getX(), stoneBase1.getY());
        addBlockToStage(stoneBase2, stoneBase2.getX(), stoneBase2.getY());
        addBlockToStage(stoneBase3, stoneBase3.getX(), stoneBase3.getY());
        addBlockToStage(woodMiddle1, woodMiddle1.getX(), woodMiddle1.getY());
        addBlockToStage(woodMiddle2, woodMiddle2.getX(), woodMiddle2.getY());
        addBlockToStage(glassTop, glassTop.getX(), glassTop.getY());

        // Add pigs to stage
        addPigToStage(smallPig, smallPig.getX(), smallPig.getY());
        addPigToStage(bigPig, bigPig.getX(), bigPig.getY());

        // Setup relationships between blocks and objects above them
        setupBlockRelationships();
    }

    // Add this method to clean up resources
    private void cleanupStructure() {
        // Dispose of textures when cleaning up
        for (blocks block : allBlocks) {
            if (block.texture != null) {
                block.texture.dispose();
            }
        }
        for (pig pig : allPigs) {
            if (pig.texture != null) {
                pig.texture.dispose();
            }
        }
        allBlocks.clear();
        allPigs.clear();
    }

}
