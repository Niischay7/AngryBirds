package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class collisionmanager {
    private Stage stage;
    private Array<blocks> allBlocks;
    private Array<pig> allPigs;
    private Array<Actor> actorsToRemove;
    private Main game;
    private thirdscreen gameScreen;
    private boolean gameStateChecked = false;
    private static final float GROUND_Y = 68f;

    public collisionmanager(Stage stage, Array<blocks> allBlocks, Array<pig> allPigs, Main game, thirdscreen gameScreen) {
        this.stage = stage;
        this.allBlocks = allBlocks;
        this.allPigs = allPigs;
        this.actorsToRemove = new Array<>();
        this.game = game;
        this.gameScreen = gameScreen;
    }

    public void handleCollisions(Bird bird) {
        if (!bird.isLaunched()) return;

        // Check for ground collision first
        if (bird.getY() <= GROUND_Y) {
            handleGroundCollision(bird);
            return;
        }

        Rectangle birdBounds = new Rectangle(bird.getX(), bird.getY(), bird.size, bird.size);
        Vector2 velocity = bird.getVelocity();
        float speed = velocity.len();
        float angle = (float)Math.toDegrees(Math.atan2(velocity.y, velocity.x));

        // Check collisions with blocks
        for (blocks block : allBlocks) {
            if (block != null && !block.isDestroyed() && block.getBounds().overlaps(birdBounds)) {
                processBlockCollision(block, bird, angle, speed);
            }
        }

        // Check collisions with pigs
        for (pig pig : allPigs) {
            if (pig != null && !pig.isDestroyed() && pig.getBounds().overlaps(birdBounds)) {
                processPigCollision(pig, bird, angle, speed);
            }
        }

        // Remove destroyed actors
        removeDestroyedActors();

        // Check game state after processing collisions
        checkGameState(bird);
    }

    private void handleGroundCollision(Bird bird) {
        // Find and remove the bird's image from the stage
        for (Actor actor : stage.getActors()) {
            if (actor instanceof Image && actor.getX() == bird.getX() && actor.getY() == bird.getY()) {
                actorsToRemove.add(actor);
                break;
            }
        }

        // Set bird as "destroyed" or inactive
        bird.setVelocity(new Vector2(0, 0));
        removeDestroyedActors();

        // Check game state after ground collision
        checkGameState(bird);
    }

    private void processBlockCollision(blocks block, Bird bird, float angle, float speed) {
        block.takeDamage(bird.damage, angle, speed);

        if (block.isDestroyed()) {
            // Award points for destroying a block
            gameScreen.updateScore(25);

            Actor blockActor = findActorForBlock(block);
            if (blockActor != null) {
                actorsToRemove.add(blockActor);
            }

            // Existing chain reaction code...
        }
    }

    private void checkGameState(Bird currentBird) {
        if (gameStateChecked) return;

        // Check if all pigs are destroyed
        boolean allPigsDestroyed = true;
        for (pig pig : allPigs) {
            if (!pig.isDestroyed()) {
                allPigsDestroyed = false;
                break;
            }
        }

        // If all pigs are destroyed, trigger win screen instead of lose screen
        if (allPigsDestroyed) {
            gameStateChecked = true;
            game.setScreen(new winscreen(game, gameScreen.getScore())); // Pass the score to winscreen
            return;
        }

        int remainingBirds = 0;
        for (Actor actor : stage.getActors()) {
            if (actor instanceof Image) {
                Image img = (Image)actor;
                String imageName = img.getName();
                if (imageName != null && !imageName.equals("launched") &&
                    (imageName.contains("terence") ||
                        imageName.contains("bluebird") ||
                        imageName.contains("yellow")) ) {
                    remainingBirds++;
                }
            }
        }

        boolean birdStopped = currentBird.getVelocity().len() < 1f || currentBird.getY() <= GROUND_Y;
//

        if (remainingBirds == 0 && birdStopped && game != null && gameScreen != null ) {
            System.out.println("Triggering Lose Screen");
            gameStateChecked = true;
            game.setScreen(new losescreen(game, gameScreen));
        }
    }

    private void processPigCollision(pig pig, Bird bird, float angle, float speed) {
        pig.takeDamage(bird.damage, angle, speed);

        if (pig.isDestroyed()) {
            // Award points for destroying a pig
            gameScreen.updateScore(100);

            Actor pigActor = findActorForPig(pig);
            if (pigActor != null) {
                actorsToRemove.add(pigActor);
            }
        }
    }

    private Actor findActorForBlock(blocks block) {
        for (Actor actor : stage.getActors()) {
            if (actor instanceof Image) {
                if (actor.getX() == block.getX() && actor.getY() == block.getY()
                    && actor.getWidth() == block.size && actor.getHeight() == block.size) {
                    return actor;
                }
            }
        }
        return null;
    }

    private Actor findActorForPig(pig pig) {
        for (Actor actor : stage.getActors()) {
            if (actor instanceof Image) {
                if (actor.getX() == pig.getX() && actor.getY() == pig.getY()
                    && actor.getWidth() == pig.size && actor.getHeight() == pig.size) {
                    return actor;
                }
            }
        }
        return null;
    }

    private void removeDestroyedActors() {
        for (Actor actor : actorsToRemove) {
            actor.remove();
        }
        actorsToRemove.clear();
    }

    public void updatePigs(float delta) {
        for (pig pig : allPigs) {
            if (pig.isFalling()) {
                pig.updateFalling(delta);
            }
        }
    }
}
