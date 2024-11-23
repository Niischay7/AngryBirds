package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.math.Rectangle;

public class pig extends Actor {
    protected String color;
    protected String type;
    public float size;
    public int hp;
    protected Texture texture;
    protected Rectangle bounds;
    protected boolean isDestroyed;

    public pig(String color, String type, float size, int hp, Texture texture) {
        this.color = color;
        this.type = type;
        this.size = size;
        this.hp = hp;
        this.texture = texture;
        this.bounds = new Rectangle();
        this.isDestroyed = false;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        bounds.set(x, y, size, size);
    }

    public void takeDamage(int damage, float impactAngle, float impactSpeed) {
        float damageMultiplier = calculateDamageMultiplier(impactAngle, impactSpeed);
        int actualDamage = Math.round(damage * damageMultiplier);

        hp -= actualDamage;

        if (hp <= 0 && !isDestroyed) {
            isDestroyed = true;
        }
    }

    private float calculateDamageMultiplier(float impactAngle, float impactSpeed) {
        // Normalize speed
        float normalizedSpeed = Math.min(impactSpeed / 1200f, 1.0f);

        // Calculate angle multiplier
        float angleMultiplier = Math.abs((float)Math.sin(Math.toRadians(impactAngle)));

        // Type-specific multipliers
        float typeMultiplier = type.toLowerCase().equals("big") ? 0.7f : 1.0f;

        return normalizedSpeed * angleMultiplier * typeMultiplier;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
