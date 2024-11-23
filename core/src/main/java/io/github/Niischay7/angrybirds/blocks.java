package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Rectangle;

public abstract class blocks extends Actor {
    protected String material;
    public float size;
    public int hp;
    protected Texture texture;
    protected Rectangle bounds;
    protected Array<blocks> blocksAbove;
    protected Array<pig> pigsAbove;
    protected boolean isDestroyed;

    public blocks(String material, float size, int hp) {
        this.material = material;
        this.size = size;
        this.hp = hp;
        this.bounds = new Rectangle();
        this.blocksAbove = new Array<>();
        this.pigsAbove = new Array<>();
        this.isDestroyed = false;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        bounds.set(x, y, size, size);
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
        // Normalize speed (assuming max speed is 1200f)
        float normalizedSpeed = Math.min(impactSpeed / 1200f, 1.0f);

        // Calculate angle multiplier (perpendicular hits deal more damage)
        float angleMultiplier = Math.abs((float)Math.sin(Math.toRadians(impactAngle)));

        // Material-specific multipliers
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
        // Damage objects above when destroyed
        for (blocks block : blocksAbove) {
            block.takeDamage(new Bird(null, "", "", size, 1, 1, null), 90, 0); // Collapse damage
        }
        for (pig pig : pigsAbove) {
            pig.takeDamage(1, 90, 0); // Collapse damage to pigs
        }
    }
    private void triggerChainReaction() {
        if (isDestroyed) {
            for (blocks blockAbove : blocksAbove) {
                if (!blockAbove.isDestroyed) {
                    blockAbove.takeDamage(10, 90, 400); // Base damage for chain reactions
                }
            }

            for (pig pigAbove : pigsAbove) {
                if (!pigAbove.isDestroyed()) {
                    pigAbove.takeDamage(10, 90, 400); // Base damage for chain reactions
                }
            }
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
}
