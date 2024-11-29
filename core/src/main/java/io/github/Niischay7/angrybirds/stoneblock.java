package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;

public class stoneblock extends blocks implements Serializable {
    private static final long serialVersionUID = 1L;


    public stoneblock() {
        super("stone", 60, 100);
    }

    public stoneblock(float size, int hp) {
        super("stone", size, hp);
        this.texture = new Texture("stoneblock-preview.png");
    }

    @Override
    public void reinitializeTransientFields(Texture texture) {
        super.reinitializeTransientFields(texture);
    }
}

