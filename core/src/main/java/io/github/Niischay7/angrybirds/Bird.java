package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import org.w3c.dom.Text;

public  class Bird extends Actor{
    protected String color;
    protected String ability;
    public float size;
    public int hp;
    protected int damage;//yeh shayad add krna padh jaaye reference ke liye likh diya maine
    protected float velocity;// 2-dimensional waala dekhlenge baadmein
    protected Texture texture;

    public Bird() {
      this.texture = texture;
      this.color = color;
      this.ability = ability;
      this.size = size;
      this.hp = hp;
        this.damage = damage;
        this.velocity = velocity;
    }

    public Bird(String color) {
        this.color = color;
    }

    public Bird(Texture texture, int hp, float velocity) {
        this.texture = texture;
        this.hp = hp;
        this.velocity = velocity;

    }

    public Bird(Texture texture, String yellow, String increaseMomentum, float size, int hp, int damage, float velocity) {
        this.texture = texture;
        this.color = color;
        this.ability = ability;
        this.size = size;
        this.hp = hp;
        this.damage = damage;
        this.velocity = velocity;
    }

    public float getVelocity() {
        return velocity;
    }
    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public void launch_bird() {

    }

    public int min_damage()
    {
        return 1;// change krlenge baadmein default ko
    }

    public Image getImage()
    {
        return new Image(new Texture(Gdx.files.internal(ability)));
    }
}
