package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class BirdDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    String color;
    private String ability;
    private float size;
    private int hp;
    private int damage;
    private Vector2 position;
    private boolean isLaunched;

    public BirdDTO(Bird bird) {
        this.color = bird.color;
        this.ability = bird.ability;
        this.size = bird.size;
        this.hp = bird.hp;
        this.damage = bird.damage;
        this.position = new Vector2(bird.getX(), bird.getY());
        this.isLaunched = bird.isLaunched();
    }

    public Bird toBird(Texture texture) {
        Bird bird = new Bird(texture, color, ability, size, hp, damage, new Vector2(0, 0));
        bird.setPosition(position.x, position.y);
        bird.setLaunched(isLaunched);
        return bird;
    }

    // Getters and setters
    public boolean isLaunched() {
        return isLaunched;
    }

    public void setLaunched(boolean launched) {
        isLaunched = launched;
    }
}
