package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class pig extends Actor {
    protected String color;
    protected String type;
    public float size;
    public int hp;
    protected Texture texture;
    protected Rectangle bounds;
    protected boolean isDestroyed;
    protected boolean isFalling;
    private static final float GRAVITY = -600f; // Gravity constant
    private static final float DRAG = 0.99f; // Air resistance

    public pig(String color, String type, float size, int hp, Texture texture) {
        this.color = color;
        this.type = type;
        this.size = size;
        this.hp = hp;
        this.texture = texture;
        this.bounds = new Rectangle();
        this.isDestroyed = false;
        this.isFalling = false;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        bounds.set(x, y, size, size);
    }

    public void takeDamage(int damage, float impactAngle, float impactSpeed) {
        hp -= damage;
        if (hp <= 0) {
            isDestroyed = true;
            isFalling = true;
        }
    }

    public Rectangle getBounds() {
        // Update bounds position to match current actor position
        bounds.setPosition(getX(), getY());
        return bounds;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void updateFalling(float delta) {
        if (isFalling) {
            // Update velocity with gravity
            float velocityY = getVelocity().y + GRAVITY * delta;
            float velocityX = getVelocity().x * DRAG;

            // Update position based on velocity
            float newY = getY() + velocityY * delta;
            float newX = getX() + velocityX * delta;

            setPosition(newX, newY);

            // Apply drag (air resistance)
            setVelocity(new Vector2(velocityX, velocityY));

            // Check for ground collision
            if (getY() <= 0) {
                setY(0);
                setVelocity(new Vector2(0, 0));
                isFalling = false;
            }
        }
    }

    private Vector2 getVelocity() {
        // Assuming you have a velocity vector in your pig class
        // Return the current velocity vector
        return new Vector2(0, 0); // Placeholder, replace with actual velocity
    }

    private void setVelocity(Vector2 velocity) {
        // Assuming you have a velocity vector in your pig class
        // Set the current velocity vector
    }
}
