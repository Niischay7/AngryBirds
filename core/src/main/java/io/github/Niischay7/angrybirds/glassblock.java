package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;

public class glassblock extends blocks{
    public glassblock(float size, int hp) {
        super("glass", size, hp);
        this.texture = new Texture("glassblock-removebg-preview.png");
    }
}
