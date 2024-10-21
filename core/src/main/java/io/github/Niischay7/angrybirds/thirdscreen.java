package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class thirdscreen implements Screen {
    private Main game;
    private Stage stage;
    private Texture background;
    private Image backgroundImage;
    private Texture buttonUpTexture, buttonDownTexture;

    public thirdscreen(Main game, int i) {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        background = new Texture("thirdscreenbg.jpg");
        backgroundImage = new Image(background);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        Texture catapult=new Texture("catapult-removebg-preview.png");
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
                Gdx.app.log("ImageButton Clicked", "The button was clicked");

                game.setScreen(new FirstScreen(game));
            }
        });

        stage.addActor(imageButton);

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(v);
        stage.draw();
        Texture terence1=new Texture("terence-removebg-preview.png");
        Bird terence=new Bird(terence1,"Red","null",5,100,25,50);
        game.birds.add(terence);
        Image terenceImage = new Image(terence.texture);
        terenceImage.setSize(40, 40);
        terenceImage.setPosition(70, 137);
        stage.addActor(terenceImage);
        Texture blueImage1=new Texture("bluebird-removebg-preview.png");
        Bird Blue=new Bird(blueImage1,"Yellow","increasemomentum",5,100,25,50);
        game.birds.add(Blue);
        Image blueImage=new Image(Blue.texture);
        blueImage.setSize(40, 40);
        blueImage.setPosition(45, 60);
        stage.addActor(blueImage);
        Texture Yellowbird1=new Texture("yellow-removebg-preview.png");
        Bird Yellow=new Bird(Yellowbird1,"Yellow","increasemomentum",5,100,25,50);
        game.birds.add(Yellow);
        Image yellowImage = new Image(Yellow.texture);
        yellowImage.setSize(40, 40);
        yellowImage.setPosition(0, 65);
        stage.addActor(yellowImage);
    }

    @Override
    public void resize(int i, int i1) {
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

    }
}
