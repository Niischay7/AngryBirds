package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class YellowBird extends Bird {

    public YellowBird(Texture texture, String color, String ability, float size, int hp,int damage ,Vector2 velocity) {
        super(texture, "yellow", "increase_momentum", size, hp,damage, velocity);

    }

    public void launch_bird() {
        increase_momentum();
        System.out.println("YellowBird has launched with increased momentum!");
    }


    public int min_damage() {
        return 5;  // change krlenge baadmein
    }

    public void increase_momentum() {
        Vector2 current_velocity = getVelocity();
      //  System.out.println("Momentum increased! New velocity: " + (this.velocity + current_velocity/2));

    }
}
