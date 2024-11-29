package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.io.Serializable;

public class woodblock extends blocks implements Serializable {
    private static final long serialVersionUID = 1L;

    // No-argument constructor for deserialization
    public woodblock() {
        super("wood", 60, 80);
    }

    public woodblock(float size, int hp) {
        super("wood", size, hp);
        this.texture = new Texture("woodie-removebg-preview.png");
    }

    @Override
    public void reinitializeTransientFields(Texture texture) {
        super.reinitializeTransientFields(texture);
    }
}

