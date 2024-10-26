package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;

public class stoneblock extends blocks {
    public stoneblock(float size, int hp) {
        super("stone", size, hp);
        this.texture = new Texture("stoneblock-preview.png");
    }
}
