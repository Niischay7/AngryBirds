//package io.github.Niischay7.angrybirds;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.io.*;
//import java.util.ArrayList;
//
//public class GameDataManagerTest {
//
//    @Test
//    public void testSaveGameData() throws IOException, ClassNotFoundException {
//        // Arrange
//        thirdscreen gameScreen = new thirdscreen(null, 1);
//        ArrayList<Bird> birds = new ArrayList<>();
//        ArrayList<blocks> allBlocks = new ArrayList<>();
//        ArrayList<pig> allPigs = new ArrayList<>();
//
//        // Act
//        GameDataManager.saveGameData(gameScreen, 1);
//
//        // Assert
//        File file = new File("game_save.dat1");
//        assertTrue(file.exists());
//    }
//
//    @Test
//    public void testLoadGameData() throws IOException, ClassNotFoundException {
//        // Arrange
//        thirdscreen gameScreen = new thirdscreen(null, 1);
//        ArrayList<Bird> birds = new ArrayList<>();
//        ArrayList<blocks> allBlocks = new ArrayList<>();
//        ArrayList<pig> allPigs = new ArrayList<>();
//
//        // Act
//        GameDataManager.loadGameData(gameScreen, 1);
//
//        // Assert
//        assertEquals(1, gameScreen.levelNumber);
//        assertNotNull(gameScreen.birds);
//        assertNotNull(gameScreen.allBlocks);
//        assertNotNull(gameScreen.allPigs);
//    }
//}
