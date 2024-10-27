package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class winscreen implements Screen {
    //to be displayed after level screen if the mission is successful.
    private Main game;
    private Stage stage;
    private Skin skin;
    private Texture winTexture;
    private Image winImage;

    public winscreen(Main game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
    }

    @Override
    public void show() {

        winTexture = new Texture(Gdx.files.internal("winscreen.jpg"));
        winImage = new Image(winTexture);
        winImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        winImage.setTouchable(null);



        TextButton backButton = new TextButton("Back", skin);
        backButton.setSize(100, 50);
        backButton.setPosition(Gdx.graphics.getWidth() / 2f - 50, 10);
//
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(null);
                game.setScreen(new secondscreen(game));
            }
        });
        stage.addActor(backButton);
        stage.addActor(winImage);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        winTexture.dispose();
        skin.dispose();
    }
}
