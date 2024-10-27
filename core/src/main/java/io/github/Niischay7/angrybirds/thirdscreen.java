// thirdscreen.java
package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class thirdscreen implements Screen {
    private Main game;
    private Stage stage;
    private Texture background;
    private Image backgroundImage;
    private Texture buttonUpTexture;
    private boolean birdsAdded = false;

    public thirdscreen(Main game, int i) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        // Re-set the input processor when shown
        Gdx.input.setInputProcessor(stage);


        if (background == null) {  // Only create if not already created
            background = new Texture("thirdscreenbg.jpg");
            backgroundImage = new Image(background);
            backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            stage.addActor(backgroundImage);
            Texture catapult = new Texture("catapult-removebg-preview.png");
            catapult cata=new catapult(45.2F,67,catapult);

            Image catapultImage = new Image(catapult);
            catapultImage.setSize(100, 100);
            catapultImage.setPosition(40, 68);
            stage.addActor(catapultImage);

            buttonUpTexture = new Texture(Gdx.files.internal("bottom.png"));
            ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
            buttonStyle.up = new TextureRegionDrawable(buttonUpTexture);

            ImageButton imageButton = new ImageButton(buttonStyle);
            imageButton.setSize(90, 70);
            imageButton.setPosition(0, 409);

            TextButton lose_game= new TextButton("Lose Game",new Skin(Gdx.files.internal("uiskin.json")));
            lose_game.setPosition(Gdx.graphics.getWidth()-150, Gdx.graphics.getHeight()-60);
            lose_game.setSize(100,60);
            lose_game.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new losescreen(game,thirdscreen.this));
                }
            });
            TextButton win_game=new TextButton("Win game",new Skin(Gdx.files.internal("uiskin.json")));
            win_game.setPosition(Gdx.graphics.getWidth()-150,Gdx.graphics.getHeight()-150);
            win_game.setSize(100, 60);
            win_game.addListener(new ClickListener() {
               public void clicked(InputEvent event, float x, float y) {
                   game.setScreen(new winscreen(game));
               }
            });
            stage.addActor(lose_game);
            stage.addActor(win_game);
            imageButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    game.setScreen(new PauseScreen(game, thirdscreen.this));
                }
            });

            stage.addActor(imageButton);
        }

        // Add birds if not already added
        if (!birdsAdded) {
            addBirds();
            createStructure();
            birdsAdded = true;
        }
    }

    private void addBirds() {
        // Terence bird
        Texture terence1 = new Texture("terence-removebg-preview.png");
        Bird terence = new Bird(terence1, "Red", "null", 5, 100, 25, 50);
        game.birds.add(terence);
        Image terenceImage = new Image(terence.texture);
        terenceImage.setSize(40, 40);
        terenceImage.setPosition(70, 137);
        stage.addActor(terenceImage);

        // Blue bird
        Texture blueImage1 = new Texture("bluebird-removebg-preview.png");
        Bird Blue = new Bird(blueImage1, "Yellow", "increasemomentum", 5, 100, 25, 50);
        game.birds.add(Blue);
        Image blueImage = new Image(Blue.texture);
        blueImage.setSize(40, 40);
        blueImage.setPosition(45, 60);
        stage.addActor(blueImage);

        // Yellow bird
        Texture Yellowbird1 = new Texture("yellow-removebg-preview.png");
        Bird Yellow = new Bird(Yellowbird1, "Yellow", "increasemomentum", 5, 100, 25, 50);
        game.birds.add(Yellow);
        Image yellowImage = new Image(Yellow.texture);
        yellowImage.setSize(40, 40);
        yellowImage.setPosition(0, 65);
        stage.addActor(yellowImage);
    }
    private void createStructure() {
        // Create base layer - Stone blocks
        stoneblock stoneBase1 = new stoneblock(60, 100);
        stoneblock stoneBase2 = new stoneblock(60, 100);
        stoneblock stoneBase3 = new stoneblock(60, 100);

        // Middle layer - Wood blocks
        woodblock woodMiddle1 = new woodblock(60, 80);
        woodblock woodMiddle2 = new woodblock(60, 80);

        // Top layer - Glass block
        glassblock glassTop = new glassblock(60, 50);


        Texture pigTexture = new Texture("pigs-removebg-preview.png");
        pig smallPig = new pig("green", "basic", 40, 50, pigTexture);
        pig bigPig = new pig("green", "big", 50, 75, pigTexture);

        // Position and add blocks to stage
        float baseX = 300;  // Starting X position
        float baseY = 68;   // Base Y position


        Image stone1Image = new Image(stoneBase1.texture);
        stone1Image.setSize(stoneBase1.size, stoneBase1.size);
        stone1Image.setPosition(baseX, baseY);
        stage.addActor(stone1Image);

        Image stone2Image = new Image(stoneBase2.texture);
        stone2Image.setSize(stoneBase2.size, stoneBase2.size);
        stone2Image.setPosition(baseX + 65, baseY);
        stage.addActor(stone2Image);

        Image stone3Image = new Image(stoneBase3.texture);
        stone3Image.setSize(stoneBase3.size, stoneBase3.size);
        stone3Image.setPosition(baseX + 130, baseY);
        stage.addActor(stone3Image);

        // Add middle layer blocks
        Image wood1Image = new Image(woodMiddle1.texture);
        wood1Image.setSize(woodMiddle1.size, woodMiddle1.size);
        wood1Image.setPosition(baseX + 22f, baseY + 58);
        stage.addActor(wood1Image);

        Image wood2Image = new Image(woodMiddle2.texture);
        wood2Image.setSize(woodMiddle2.size, woodMiddle2.size);
        wood2Image.setPosition(baseX + 106f, baseY + 58);
        stage.addActor(wood2Image);

        // Add top glass block
        Image glassImage = new Image(glassTop.texture);
        glassImage.setSize(glassTop.size, glassTop.size);
        glassImage.setPosition(baseX + 61, baseY + 120);
        stage.addActor(glassImage);

        // Add pigs
        Image smallPigImage = new Image(smallPig.texture);
        smallPigImage.setSize(smallPig.size, smallPig.size);
        smallPigImage.setPosition(baseX + 75, baseY + 60);
        stage.addActor(smallPigImage);

        Image bigPigImage = new Image(bigPig.texture);
        bigPigImage.setSize(bigPig.size, bigPig.size);
        bigPigImage.setPosition(baseX + 65, baseY + 130);
        stage.addActor(bigPigImage);
    }
    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        if (background != null) background.dispose();
        if (buttonUpTexture != null) buttonUpTexture.dispose();
        if (stage != null) stage.dispose();
    }
}
