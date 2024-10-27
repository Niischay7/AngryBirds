package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;

public class YellowBird extends Bird {

    public YellowBird(Texture texture, String color, String ability, float size, int hp,int damage ,float velocity) {
        super(texture, "yellow", "increase_momentum", size, hp,damage, velocity);

    }
    @Override
    public void launch_bird() {
        increase_momentum();
        System.out.println("YellowBird has launched with increased momentum!");
    }

    @Override
    public int min_damage() {
        return 5;  // change krlenge baadmein
    }

    public void increase_momentum() {
        float current_velocity = getVelocity();
        System.out.println("Momentum increased! New velocity: " + (this.velocity + current_velocity/2));//flash waali ability

    }
}
