package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Texture;

public class RedgiantBird extends Bird{
    public RedgiantBird(Texture texture, String color, String ability, float size, int hp,int damage ,float velocity) {
        super(texture, "yellow", "increase_momentum", size, hp,damage, velocity);

    }
    @Override
    public void launch_bird() {
        System.out.println("redgiantbird");

    }

    @Override
    public int min_damage() {
        return 5;  // change krlenge baadmein
    }


}
