//package io.github.Niischay7.angrybirds;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class thirdscreenTest {
//
//    @Test
//    public void testResetGameScreen() {
//        // Arrange
//        thirdscreen gameScreen = new thirdscreen(null, 1);
//        ArrayList<Bird> birds = new ArrayList<>();
//        ArrayList<blocks> allBlocks = new ArrayList<>();
//        ArrayList<pig> allPigs = new ArrayList<>();
//
//        // Act
//        gameScreen.resetGameScreen();
//
//        // Assert
//        assertEquals(0, gameScreen.getScore());
//        assertNotNull(gameScreen.birds);
//        assertNotNull(gameScreen.allBlocks);
//        assertNotNull(gameScreen.allPigs);
//    }
//
//    @Test
//    public void testCheckGameState() {
//        // Arrange
//        thirdscreen gameScreen = new thirdscreen(null, 1);
//        ArrayList<Bird> birds = new ArrayList<>();
//        ArrayList<blocks> allBlocks = new ArrayList<>();
//        ArrayList<pig> allPigs = new ArrayList<>();
//
//        // Act
//        gameScreen.checkGameState();
//
//        // Assert
//        assertTrue(gameScreen.levelCompleted);
//    }
//}
