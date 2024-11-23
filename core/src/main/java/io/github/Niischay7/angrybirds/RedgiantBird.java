package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class RedgiantBird extends Bird{
    public RedgiantBird(Texture texture, String color, String ability, float size, int hp, int damage , Vector2 velocity) {
        super(texture, "yellow", "increase_momentum", size, hp,damage, velocity);

    }

    public void launch_bird() {
        System.out.println("redgiantbird");

    }


    public int min_damage() {
        return 5;  // change krlenge baadmein
    }


}
