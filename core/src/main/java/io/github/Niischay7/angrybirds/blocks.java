package io.github.Niischay7.angrybirds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public abstract class blocks extends Actor implements Serializable{
    private static final long serialVersionUID = 1L;
    protected String material;
    public float size;
    public int hp;
    protected transient Texture texture;
    protected transient Rectangle bounds;
    protected List<blocks> blocksAbove = new ArrayList<>();
    protected List<pig> pigsAbove = new ArrayList<>();
    protected boolean isDestroyed;
    private float x;
    private float y;

    public blocks(String material, float size, int hp) {
        this.material = material;
        this.size = size;
        this.hp = hp;
        this.bounds = new Rectangle();
        this.blocksAbove = new ArrayList<>();
        this.pigsAbove = new ArrayList<>();
        this.isDestroyed = false;
    }
    public blocks() {
    }


    public void addBlockAbove(blocks block) {
        blocksAbove.add(block);
    }

    public void addPigAbove(pig pig) {
        pigsAbove.add(pig);
    }

    public boolean isColliding(Rectangle other) {
        return bounds.overlaps(other);
    }

    public void takeDamage(int damage, float impactAngle, float impactSpeed) {
        float damageMultiplier = calculateDamageMultiplier(impactAngle, impactSpeed);
        int finalDamage = Math.round(damage * damageMultiplier);

        hp -= finalDamage;

        if (hp <= 0 && !isDestroyed) {
            isDestroyed = true;
            triggerChainReaction();
        }
    }

    public void takeDamage(Bird bird, float impactAngle, float impactSpeed) {
        if (bird != null) {
            takeDamage(bird.damage, impactAngle, impactSpeed);
        }
    }

    private float calculateDamageMultiplier(float impactAngle, float impactSpeed) {

        float normalizedSpeed = Math.min(impactSpeed / 1200f, 1.0f);

        float angleMultiplier = Math.abs((float)Math.sin(Math.toRadians(impactAngle)));


        float materialMultiplier;
        switch(material.toLowerCase()) {
            case "stone":
                materialMultiplier = 0.8f;
                break;
            case "wood":
                materialMultiplier = 1.0f;
                break;
            case "glass":
                materialMultiplier = 1.5f;
                break;
            default:
                materialMultiplier = 1.0f;
        }

        return normalizedSpeed * angleMultiplier * materialMultiplier;
    }

    protected void onDestroy() {

        for (blocks block : blocksAbove) {
            block.takeDamage(new Bird(null, "", "", size, 1, 1, null), 90, 0); // Collapse damage
        }
        for (pig pig : pigsAbove) {
            pig.takeDamage(1, 90, 0); // Collapse damage to pigs
        }
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

    public void reinitializeTransientFields(Texture texture) {
        this.bounds = new Rectangle();
        this.bounds.setPosition(getX(), getY());
        this.bounds.setSize(size, size);
    }

    public void triggerChainReaction() {
        if (isDestroyed) {

            for (blocks blockAbove : blocksAbove) {
                if (!blockAbove.isDestroyed) {
                    blockAbove.takeDamage(10, 90, 400);
                }
            }


            for (pig pigAbove : pigsAbove) {
                if (!pigAbove.isDestroyed()) {

                    pigAbove.takeDamage(100, 90, 400);
                }
            }
        }
    }

    public Rectangle getBounds() {

        bounds.setPosition(getX(), getY());
        return bounds;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
