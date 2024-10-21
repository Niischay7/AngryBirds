package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;

public class BlueBird extends Bird {
    public BlueBird(Texture texture, String color, String ability, float size, int hp,int damage ,float velocity) {
        super(texture, "Blue", "split into three", size, hp,damage, velocity);

    }
    @Override
    public void launch_bird() {
        split_into_three();
        System.out.println("YellowBird has launched with increased momentum!");
    }

    @Override
    public int min_damage() {
        return 5;  // change krlenge baadmein
    }

    public void split_into_three() {
        float current_velocity = getVelocity();
        System.out.println("Momentum increased! New velocity: ");

    }
}
