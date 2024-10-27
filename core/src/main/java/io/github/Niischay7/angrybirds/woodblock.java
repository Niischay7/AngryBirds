package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class woodblock extends blocks {
    public woodblock(float size, int hp) {
        super("wood", size, hp);
        this.texture = new Texture("woodie-removebg-preview.png");//only wood image for GUI
    }
}
