package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.io.*;
import java.util.ArrayList;

public class GameDataManager {
    private static final String SAVE_FILE_PREFIX = "game_save.dat";
    private static final String SAVE_FILE_EXTENSION = ".dat";

    public static void saveGameData(thirdscreen gameScreen, int levelNumber) {
        String saveFileName = SAVE_FILE_PREFIX + levelNumber + SAVE_FILE_EXTENSION;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFileName))) {
            // Convert Array to ArrayList
            ArrayList<BirdDTO> birdDTOs = new ArrayList<>();
            for (Bird bird : gameScreen.birds) {
                birdDTOs.add(new BirdDTO(bird));
                System.out.println("Saving bird: " + bird.color + ", " + bird.getX() + ", " + bird.getY() + ", " + bird.isLaunched());
            }

            ArrayList<BlockDTO> blockDTOs = new ArrayList<>();
            for (blocks block : gameScreen.allBlocks) {
                blockDTOs.add(new BlockDTO(block));
                System.out.println("Saving block: " + block.getX() + ", " + block.getY() + ", " + block.isDestroyed());
            }

            ArrayList<PigDTO> pigDTOs = new ArrayList<>();
            for (pig pig : gameScreen.allPigs) {
                pigDTOs.add(new PigDTO(pig));
                System.out.println("Saving pig: " + pig.getX() + ", " + pig.getY() + ", " + pig.isDestroyed());
            }

            int score = gameScreen.getScore();
            System.out.println("Saving score: " + score);

            oos.writeObject(birdDTOs);
            oos.writeObject(blockDTOs);
            oos.writeObject(pigDTOs);
            oos.writeInt(gameScreen.levelNumber);
            oos.writeObject(gameScreen.slingPosition);
            oos.writeInt(score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadGameData(thirdscreen gameScreen, int levelNumber) {
        String saveFileName = SAVE_FILE_PREFIX + levelNumber + SAVE_FILE_EXTENSION;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFileName))) {
            // Convert ArrayList back to libGDX Array
            ArrayList<BirdDTO> birdDTOs = (ArrayList<BirdDTO>) ois.readObject();
            ArrayList<BlockDTO> blockDTOs = (ArrayList<BlockDTO>) ois.readObject();
            ArrayList<PigDTO> pigDTOs = (ArrayList<PigDTO>) ois.readObject();

            // Initialize new Arrays and add items manually
            gameScreen.birds = new Array<>();
            for (BirdDTO birdDTO : birdDTOs) {
                Texture birdTexture = null;
                switch (birdDTO.color.toLowerCase()) {
                    case "red":
                        birdTexture = new Texture(Gdx.files.internal("terence.png"));
                        break;
                    case "blue":
                        birdTexture = new Texture(Gdx.files.internal("bluebird-removebg-preview.png"));
                        break;
                    case "yellow":
                        birdTexture = new Texture(Gdx.files.internal("yellow-removebg-preview.png"));
                        break;
                    // Add other bird types as needed
                }
                Bird bird = birdDTO.toBird(birdTexture);
                if (!bird.isLaunched()){
                gameScreen.birds.add(bird);
                System.out.println(bird.color);
                    System.out.println("gsdsdsg"+gameScreen.birds.size);}
                System.out.println("Loading bird: " + bird.color + ", " + bird.getX() + ", " + bird.getY() + ", " + bird.isLaunched());
            }

            gameScreen.allBlocks = new Array<>();
            for (BlockDTO blockDTO : blockDTOs) {
                blocks block = blockDTO.toBlock();
                gameScreen.allBlocks.add(block);
                System.out.println("Loading block: " + block.getX() + ", " + block.getY() + ", " + block.isDestroyed());
                // Reinitialize transient fields with the correct texture
                switch (block.material.toLowerCase()) {
                    case "stone":
                        block.reinitializeTransientFields(new Texture(Gdx.files.internal("stoneblock-preview.png")));
                        break;
                    case "wood":
                        block.reinitializeTransientFields(new Texture(Gdx.files.internal("woodie-removebg-preview.png")));
                        break;
                    case "glass":
                        block.reinitializeTransientFields(new Texture(Gdx.files.internal("glassblock-removebg-preview.png")));
                        break;
                }
            }

            gameScreen.allPigs = new Array<>();
            for (PigDTO pigDTO : pigDTOs) {
                pig pig = pigDTO.toPig(new Texture(Gdx.files.internal("pigs-removebg-preview.png")));
                gameScreen.allPigs.add(pig);

                System.out.println("Loading pig: " + pig.getX() + ", " + pig.getY() + ", " + pig.isDestroyed());
            }

            gameScreen.levelNumber = ois.readInt();
            gameScreen.slingPosition = (Vector2) ois.readObject();
            int score = ois.readInt();
            System.out.println("Loading score: " + score);
            gameScreen.setScore(score); // Use setScore to directly set the score

            // Reset the game screen with the loaded data
            gameScreen.resetGameScreen();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
