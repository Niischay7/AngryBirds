package io.github.Niischay7.angrybirds;
//we implemented all the libraries
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/** First screen of the application. Displayed after the application is created. */

// first screeen here is the main screen where the play button is gonna be displayed.
public class FirstScreen implements Screen {
    private Stage stage;
    private final Main game;
    private Texture backgroundTexture;
    private Image background;
    private Music backgroundMusic;

    public FirstScreen(Main game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("download.jpeg"));
        background = new Image(backgroundTexture);

        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Table root = new Table();
        root.setFillParent(true);

        root.add(background).expand().fill();

        stage.addActor(root);
    }

    @Override
    public void show() {
        Texture angrybirds = new Texture(Gdx.files.internal("Angry-Birds-Log.png"));
        Texture pigs = new Texture(Gdx.files.internal("pigs.jpeg"));
        Texture wood = new Texture(Gdx.files.internal("wood.jpeg"));
        Texture stone = new Texture(Gdx.files.internal("stone.jpeg"));
        Texture glass = new Texture(Gdx.files.internal("glass.jpeg"));
        Texture playbutton = new Texture(Gdx.files.internal("playbutton.jpg"));
        Texture exitbutton = new Texture(Gdx.files.internal("exitbutton.jpeg"));
        Texture eggs = new Texture(Gdx.files.internal("eggs.jpeg"));
        Texture birdandpigs = new Texture(Gdx.files.internal("birdandpig.jpeg"));
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("25. Main Theme.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
        Image angrybirdsImage = new Image(angrybirds);
        Image pigsImage = new Image(pigs);
        Image woodImage = new Image(wood);
        Image stoneImage = new Image(stone);
        Image glassImage = new Image(glass);
        Image playbuttonImage = new Image(playbutton);
        Image exitbuttonImage = new Image(exitbutton);
        Image eggsImage = new Image(eggs);
        Image birdandpigsImage = new Image(birdandpigs);

        float logoWidth = Gdx.graphics.getWidth() * 0.7f;
        float logoHeight = angrybirdsImage.getHeight() * (logoWidth / angrybirdsImage.getWidth());

        angrybirdsImage.setSize((logoWidth), logoHeight);
        playbuttonImage.setSize(logoWidth*((float) 3/5), logoHeight);
        exitbuttonImage.setSize(logoWidth*((float) 1/5), logoHeight*((float) 2/5));
        pigsImage.setSize(logoWidth*((float) 2/5), logoHeight*((float) 4/5));
        eggsImage.setSize(logoWidth*((float) 2/5), logoHeight*((float) 4/5));

        angrybirdsImage.setPosition((Gdx.graphics.getWidth() - angrybirdsImage.getWidth()) / 2,
            (Gdx.graphics.getHeight() - angrybirdsImage.getHeight()) / 2 + 130);
        playbuttonImage.setPosition((Gdx.graphics.getWidth() - playbuttonImage.getWidth()) / 2,
            (Gdx.graphics.getHeight() - playbuttonImage.getHeight()) / 2 -120);
        exitbuttonImage.setPosition(Gdx.graphics.getWidth() - exitbuttonImage.getWidth()-550,
            (Gdx.graphics.getHeight() - exitbuttonImage.getHeight()) / 2-217);
        pigsImage.setPosition((Gdx.graphics.getWidth() - pigsImage.getWidth()) / 2 +111,
            (Gdx.graphics.getHeight() - pigsImage.getHeight()) / 2 +30);
        eggsImage.setPosition((Gdx.graphics.getWidth() - eggsImage.getWidth()) / 2-70,
            (Gdx.graphics.getHeight() - eggsImage.getHeight()) / 2 +30);

//        stage.addActor(angrybirdsImage);
        stage.addActor(playbuttonImage);
        stage.addActor(exitbuttonImage);
//        stage.addActor(pigsImage);
//        stage.addActor(eggsImage);


        playbuttonImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                System.out.println("Play button clicked!");
                game.setScreen(new secondscreen(game));

    }});
        exitbuttonImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Exit button clicked!");
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {

            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            stage.act(delta);
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

    }
    @Override
    public void dispose() {

        stage.dispose();
        backgroundTexture.dispose();
        backgroundMusic.dispose();
    }
}
//updated code (background bug fixed)
