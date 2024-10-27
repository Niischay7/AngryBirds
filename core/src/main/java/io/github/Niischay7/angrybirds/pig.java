package io.github.Niischay7.angrybirds;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import org.w3c.dom.Text;

public class pig extends Actor {
    protected String color;
    protected String type;
    public float size;
    public int hp;
    protected Texture texture;
    public pig(String color, String type, float size, int hp, Texture texture) {
        this.color = color;
        this.type = type;// type se hum king pig wagera define krlenge
        this.size = size;
        this.hp = hp;
        this.texture = texture;
    }
}

//updated code !
