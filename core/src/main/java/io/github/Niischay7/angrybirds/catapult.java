package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;

public class catapult {
    private float angle;
    private int strength;
    private Texture texture;
    public catapult(float angle, int strength, Texture texture) {
        this.angle = angle;
        this.strength = strength;
        this.texture = texture;
    }

}
