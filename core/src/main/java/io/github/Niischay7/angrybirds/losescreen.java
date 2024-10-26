package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class losescreen implements Screen {
    private Main game;
    private Stage stage;
    private Texture background;
    private Texture retryTexture;
    private final Screen previousScreen;
    private int currentLevel;

    public losescreen(Main game, Screen previousScreen) {
        this.game = game;
        this.previousScreen = previousScreen;

        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

        if (previousScreen instanceof thirdscreen) {
            this.currentLevel = 1; // Default to level 1 if not specified
        }
    }

    @Override
    public void show() {

        background = new Texture(Gdx.files.internal("losescreen.jpg"));
        Image backgroundImage = new Image(background);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);


        retryTexture = new Texture(Gdx.files.internal("blabla-removebg-preview.png"));
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(retryTexture);

        ImageButton retryButton = new ImageButton(buttonStyle);
        retryButton.setSize(200, 80);
        // Center the button horizontally and place it near the bottom
        retryButton.setPosition(
            (Gdx.graphics.getWidth() - retryButton.getWidth()) / 2,
            10
        );


        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("LoseScreen", "Retry button clicked!");

                Gdx.input.setInputProcessor(null);

                game.setScreen(new thirdscreen(game, currentLevel));
                Gdx.app.log("LoseScreen", "Screen changed to thirdscreen");
            }
        });

        stage.addActor(retryButton);


        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Update and draw the stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Update viewport when screen is resized
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {
        // Ensure input processor is set when resuming
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        // Remove input processor when screen is hidden
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        if (background != null) background.dispose();
        if (retryTexture != null) retryTexture.dispose();
        if (stage != null) stage.dispose();
    }
}
