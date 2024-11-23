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

    public collisionmanager(Stage stage, Array<blocks> allBlocks, Array<pig> allPigs) {
        this.stage = stage;
        this.allBlocks = allBlocks;
        this.allPigs = allPigs;
        this.actorsToRemove = new Array<>();
        this.game = game;
        this.gameScreen = gameScreen;

    }

    public void collisionmanager(Stage stage, Array<blocks> allBlocks, Array<pig> allPigs) {
        this.stage = stage;
        this.allBlocks = allBlocks;
        this.allPigs = allPigs;
        this.actorsToRemove = new Array<>();
    }

    public void handleCollisions(Bird bird) {
        if (!bird.isLaunched()) return;

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
    private void processBlockCollision(blocks block, Bird bird, float angle, float speed) {
        block.takeDamage(bird.damage, angle, speed);

        if (block.isDestroyed()) {
            Actor blockActor = findActorForBlock(block);
            if (blockActor != null) {
                actorsToRemove.add(blockActor);
            }

            // Process chain reactions
            for (blocks blockAbove : block.blocksAbove) {
                if (!blockAbove.isDestroyed()) {
                    float reducedSpeed = speed * 0.7f;
                    processBlockCollision(blockAbove, bird, 90, reducedSpeed);
                }
            }

            for (pig pigAbove : block.pigsAbove) {
                if (!pigAbove.isDestroyed()) {
                    float reducedSpeed = speed * 0.7f;
                    processPigCollision(pigAbove, bird, 90, reducedSpeed);
                }
            }
        }
    }
    private void checkGameState(Bird currentBird) {
        if (gameStateChecked) return;

        boolean allPigsDestroyed = true;
        for (pig pig : allPigs) {
            if (!pig.isDestroyed()) {
                allPigsDestroyed = false;
                break;
            }
        }

        // Count remaining active birds
        int remainingBirds = 0;
        for (Actor actor : stage.getActors()) {
            if (actor instanceof Image) {
                Image img = (Image)actor;
                // Check if it's a bird that hasn't been launched
                if (!img.getName().equals("launched") && (
                    img.getName().equals("Red") ||
                        img.getName().equals("Blue") ||
                        img.getName().equals("Yellow"))) {
                    remainingBirds++;
                }
            }
        }

        // Check if current bird has stopped moving
        boolean birdStopped = currentBird.getVelocity().len() < 1f;

        if (allPigsDestroyed) {
            gameStateChecked = true;
            game.setScreen(new winscreen(game));
        } else if (remainingBirds == 0 && birdStopped) {
            gameStateChecked = true;
            game.setScreen(new losescreen(game, gameScreen));
        }
    }
    private void processPigCollision(pig pig, Bird bird, float angle, float speed) {
        pig.takeDamage(bird.damage, angle, speed);

        if (pig.isDestroyed()) {
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

}

