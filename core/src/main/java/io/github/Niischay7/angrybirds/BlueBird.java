package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class BlueBird extends Bird implements Serializable {
    private static final long serialVersionUID = 1L;
    public BlueBird(Texture texture, String color, String ability, float size, int hp,int damage ,Vector2 velocity) {
        super(texture, "Blue", "split into three", size, hp,damage, velocity);

    }

    public void launch_bird() {
        split_into_three();
        System.out.println("YellowBird has launched with increased momentum!");
    }


    public int min_damage() {
        return 5;  // change krlenge isko baadmein 
    }

    public void split_into_three() {
        Vector2 current_velocity = getVelocity();
        System.out.println("Momentum increased! New velocity: ");

    }
}
