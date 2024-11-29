package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;

public class PigDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String color;
    private String type;
    private float size;
    private int hp;
    private float x;
    private float y;
    private boolean isDestroyed;
    private boolean isFalling;

    public PigDTO(pig pig) {
        this.color = pig.color;
        this.type = pig.type;
        this.size = pig.size;
        this.hp = pig.hp;
        this.x = pig.getX();
        this.y = pig.getY();
        this.isDestroyed = pig.isDestroyed();
        this.isFalling = pig.isFalling();
    }

    public pig toPig(Texture texture) {
        pig pig = new pig(color, type, size, hp, texture);
        pig.setPosition(x, y);
        pig.isDestroyed = isDestroyed;
        pig.isFalling = isFalling;
        return pig;
    }

    // Getters and setters
}
