package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PauseScreen implements Screen {
    private Main game;
    private Stage stage;
    private Skin skin;
    private Screen previousScreen;
    private int levelNumber;

    public PauseScreen(Main game, Screen previousScreen, int levelNumber) {
        this.game = game;
        this.previousScreen = previousScreen;
        this.levelNumber = levelNumber;
        this.stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

        try {
            skin = new Skin(Gdx.files.internal("uiskin.json"));
        } catch (Exception e) {
            Gdx.app.error("PauseScreen", "Error loading skin: " + e.getMessage());
            return;
        }

        TextButton resume_game = new TextButton("Resume", skin);
        resume_game.setSize(200, 60);
        resume_game.setPosition(Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f + 120);

        TextButton exit_game = new TextButton("Exit", skin);
        exit_game.setSize(200, 60);
        exit_game.setPosition(Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f - 120);

        TextButton load_level = new TextButton("Load Level", skin);
        load_level.setSize(200, 60);
        load_level.setPosition(Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f - 40);

        TextButton save_level = new TextButton("Save Level", skin);
        save_level.setSize(200, 60);
        save_level.setPosition(Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f + 40);

        resume_game.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("PauseScreen", "Resume clicked");
                if (previousScreen != null) {
                    dispose();
                    game.setScreen(previousScreen);
                }
            }
        });

        exit_game.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                Screen newScreen = new secondscreen(game);
                // Set screen first
                game.setScreen(newScreen);
                // Then ensure show is called and input processor is set
                Gdx.app.postRunnable(() -> {
                    newScreen.show();
                });
            }
        });

        load_level.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("PauseScreen", "Load Level clicked");
                if (previousScreen instanceof thirdscreen) {
                    thirdscreen gameScreen = (thirdscreen) previousScreen;
                    GameDataManager.loadGameData(gameScreen, levelNumber);
                    dispose();
                    game.setScreen(gameScreen);
                }
            }
        });

        save_level.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("PauseScreen", "Save Level clicked");
                if (previousScreen instanceof thirdscreen) {
                    thirdscreen gameScreen = (thirdscreen) previousScreen;
                    GameDataManager.saveGameData(gameScreen, levelNumber);
                }
            }
        });

        stage.addActor(resume_game);
        stage.addActor(exit_game);
        stage.addActor(load_level);
        stage.addActor(save_level);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0.8f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        if (stage != null) {
            stage.dispose();
        }
        if (skin != null) {
            skin.dispose();
        }
    }
}
