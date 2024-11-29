//package io.github.Niischay7.angrybirds;
//
//import io.github.Niischay7.angrybirds.pig;
////import org.junit.jupiter.api.Test;
////import static org.junit.jupiter.api.Assertions.*;
//
//import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;
//
//public class collisionmanagerTest {
//
//    @Test
//    public void testHandleCollisions() {
//        // Arrange
//        thirdscreen gameScreen = new thirdscreen(null, 1);
//        Bird bird = new Bird(null, "Red", "", 60, 100, 1, new Vector2(0, 0));
//        blocks block = new stoneblock(60, 100);
//        pig pig = new pig("green", "normal", 60, 100, null);
//
//        // Act
//        collisionmanager collisionManager = new collisionmanager(null, null, null, null, gameScreen);
//        collisionManager.handleCollisions(bird);
//
//        // Assert
//        assertTrue(bird.isLaunched());
//        assertTrue(block.isDestroyed() || pig.isDestroyed());
//    }
//
//    @Test
//    public void testCheckCollisions() {
//        // Arrange
//        thirdscreen gameScreen = new thirdscreen(null, 1);
//        Bird bird = new Bird(null, "Red", "", 60, 100, 1, new Vector2(0, 0));
//        blocks block = new stoneblock(60, 100);
//        pig pig = new pig("green", "normal", 60, 100, null);
//
//        // Act
//        collisionmanager collisionManager = new collisionmanager(null, null, null, null, gameScreen);
//        collisionManager.handleCollisions(bird);
//
//        // Assert
//        assertTrue(bird.isLaunched() || block.isDestroyed() || pig.isDestroyed());
//    }
//}
