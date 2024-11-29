package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.Game;

import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    public ArrayList<Bird> birds;
    @Override
    public void create()
    {
        birds=new ArrayList<>();
        setScreen(new FirstScreen(this));
    }
}
//code khtm.
