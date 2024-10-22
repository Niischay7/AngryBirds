// thirdscreen.java
package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
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
            catapult cata=new catapult(45.2,67,catapult);

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

            imageButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("ImageButton Clicked", "Opening pause screen");
                    game.setScreen(new PauseScreen(game, thirdscreen.this));
                }
            });

            stage.addActor(imageButton);
        }

        // Add birds if not already added
        if (!birdsAdded) {
            addBirds();
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
        // Remove input processor when hidden
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        if (background != null) background.dispose();
        if (buttonUpTexture != null) buttonUpTexture.dispose();
        if (stage != null) stage.dispose();
    }
}
