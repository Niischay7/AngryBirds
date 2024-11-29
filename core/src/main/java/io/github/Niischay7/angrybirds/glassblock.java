package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;

public class glassblock extends blocks implements Serializable {
    private static final long serialVersionUID = 1L;

    // No-argument constructor for deserialization
    public glassblock() {
        super("glass", 60, 50);
    }

    public glassblock(float size, int hp) {
        super("glass", size, hp);
        this.texture = new Texture("glassblock-removebg-preview.png");
    }

    @Override
    public void reinitializeTransientFields(Texture texture) {
        super.reinitializeTransientFields(texture);
    }
}
