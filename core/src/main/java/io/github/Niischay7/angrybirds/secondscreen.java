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
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class secondscreen implements Screen {
    private Stage stage;
    private Main game;
    private Texture backgroundTexture;
    private Image background;
    private Texture backButtonTexture;
    private Image backButton;

    public secondscreen(Main game) {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        float buttonWidth = 95;
        float buttonHeight = 85;
        float paddingX = 30;
        float paddingY = 23;

        int totalLevels = 15;
        int columns = 5;

        float startX = 0;
        float startY = Gdx.graphics.getHeight() - 245;

        for (int i = 1; i <= totalLevels; i++) {
            TextButton levelButton = new TextButton("Level " + i, skin);

            final int level = i;
            levelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("Level Selected", "You selected Level " + level);
                    game.setScreen(new thirdscreen(game, level));
                }
            });

            float yPos = 0;
            int j = i;
            paddingX += (i % 5) * 1;
            float xPos = startX + ((i - 1) % columns) * (buttonWidth + paddingX);
            if ((i - 1) % 5 == 0 && i != 2) {
                yPos = startY - ((float) (i - 1) / columns) * (buttonHeight + paddingY);
            } else {
                yPos = startY - (i - 1) / columns * (buttonHeight + paddingY);
            }

            levelButton.setSize(buttonWidth, buttonHeight);
            levelButton.setPosition(xPos, yPos);
            paddingX -= ((i - 1) % 5) * 1;

            stage.addActor(levelButton);
        }

        backgroundTexture = new Texture(Gdx.files.internal("levels.jpg"));
        background = new Image(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setTouchable(Touchable.disabled);
        stage.addActor(background);


        backButtonTexture = new Texture(Gdx.files.internal("backbuuton.png"));
        backButton = new Image(backButtonTexture);
        backButton.setSize(100, 100);
        backButton.setPosition(10, Gdx.graphics.getHeight() - 110);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new FirstScreen(game));
            }
        });
        stage.addActor(backButton);

        // Ensure input processor is set
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        backButtonTexture.dispose();
        stage.dispose();
    }
}
