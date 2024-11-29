package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.io.Serializable;

public class pig extends Actor implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String color;
    protected String type;
    public float size;
    public int hp;
    protected transient Texture texture;
    protected Rectangle bounds;
    protected boolean isDestroyed;
    protected boolean isFalling;
    private static final float GRAVITY = -600f;
    private static final float DRAG = 0.99f;
    private float x;
    private float y;

    public pig(String color, String type, float size, int hp, Texture texture) {
        this.color = color;
        this.type = type;// type se hum king pig wagera define krlenge
        this.size = size;
        this.hp = hp;
        this.texture = texture;
        this.bounds = new Rectangle();
        this.isDestroyed = false;
        this.isFalling = false;
    }



    public void takeDamage(int damage, float impactAngle, float impactSpeed) {
        hp -= damage;
        if (hp <= 0) {
            isDestroyed = true;
            isFalling = true;
        }
    }

    public Rectangle getBounds() {

        bounds.setPosition(getX(), getY());
        return bounds;
    }
    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        this.x = x;
        this.y = y;
        bounds.set(x, y, size, size);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void reinitializeTransientFields(Texture pigTexture) {
        this.bounds = new Rectangle();
        this.bounds.setPosition(getX(), getY());
        this.bounds.setSize(size, size);
    }
    public boolean isDestroyed() {
        return isDestroyed;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void updateFalling(float delta) {
        if (isFalling) {

            float velocityY = getVelocity().y + GRAVITY * delta;
            float velocityX = getVelocity().x * DRAG;


            float newY = getY() + velocityY * delta;
            float newX = getX() + velocityX * delta;

            setPosition(newX, newY);


            setVelocity(new Vector2(velocityX, velocityY));


            if (getY() <= 0) {
                setY(0);
                setVelocity(new Vector2(0, 0));
                isFalling = false;
            }
        }
    }

    private Vector2 getVelocity() {

        return new Vector2(0, 0); // Placeholder, replace with actual velocity
    }

    private void setVelocity(Vector2 velocity) {

    }
}

//updated code !
