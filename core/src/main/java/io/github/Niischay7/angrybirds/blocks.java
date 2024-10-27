package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class blocks extends Actor {
    protected String material;//wood,stone,glass
    public float size;
    public int hp;
    protected Texture texture;

    public blocks(String material, float size, int hp) {
        this.material = material;
        this.size = size;
        this.hp = hp;
    }
}
