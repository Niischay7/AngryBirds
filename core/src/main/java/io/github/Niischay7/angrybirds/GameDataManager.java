package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.io.*;

public class GameDataManager {
    private static final String SAVE_FILE = "game_save.dat";

    public static void saveGameData(thirdscreen gameScreen) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(gameScreen.birds);
            oos.writeObject(gameScreen.allBlocks);
            oos.writeObject(gameScreen.allPigs);
            oos.writeInt(gameScreen.levelNumber);
            oos.writeObject(gameScreen.slingPosition);
            oos.writeInt(gameScreen.getScore());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadGameData(thirdscreen gameScreen) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            gameScreen.birds = (Array<Bird>) ois.readObject();
            gameScreen.allBlocks = (Array<blocks>) ois.readObject();
            gameScreen.allPigs = (Array<pig>) ois.readObject();
            gameScreen.levelNumber = ois.readInt();
            gameScreen.slingPosition = (Vector2) ois.readObject();
            gameScreen.updateScore(ois.readInt());

            // Reset the game screen with the loaded data
            gameScreen.resetGameScreen();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
