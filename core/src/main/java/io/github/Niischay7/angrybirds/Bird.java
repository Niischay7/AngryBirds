package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Bird extends Actor {
    protected String color;
    protected String ability;
    public float size;
    public int hp;
    protected int damage;
    protected Texture texture;
    private Vector2 velocity;
    private boolean isLaunched = false;
    private ShapeRenderer trajectoryRenderer;
    private Image birdImage;
    private Vector2 initialPosition;
    private static final float GRAVITY = -600f;
    private static final float DRAG = 0.99f;

    public Bird(Texture texture, String color, String ability, float size, int hp, int damage, Vector2 velocity) {
        this.texture = texture;
        this.color = color;
        this.ability = ability;
        this.size = size;
        this.hp = hp;
        this.damage = damage;
        this.velocity = velocity;
        this.trajectoryRenderer = new ShapeRenderer();

        // Create and set up the bird image
        this.birdImage = new Image(texture);
        this.birdImage.setSize(size, size);

        // Store initial position for resetting
        this.initialPosition = new Vector2(getX(), getY());
        System.out.println("Bird created: " + this);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
        System.out.println("Velocity set: " + velocity);
    }

    public boolean isLaunched() {
        return isLaunched;
    }

    public void launch() {
        if (!isLaunched) {
            isLaunched = true;
            initialPosition = new Vector2(getX(), getY());
            System.out.println("Bird launched: " + this);
        }
    }

    public int minDamage() {
        return 1;
    }

    public Image getBirdImage() {
        return birdImage;
    }

    private void checkCollisions() {
        // Ground collision
        if (getY() < 0) {
            setY(0);
            velocity.y = 0;
            velocity.x *= 0.8f; // Ground friction
        }

        // Screen boundaries
        if (getX() < 0 || getX() > Gdx.graphics.getWidth() || getY() > Gdx.graphics.getHeight()) {
            reset();
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isLaunched) {
            // Update velocity with gravity
            velocity.y += GRAVITY * delta;

            // Update position based on velocity
            float newX = getX() + velocity.x * delta;
            float newY = getY() + velocity.y * delta;

            setPosition(newX, newY);

            // Apply drag (air resistance)
            velocity.x *= DRAG;
            velocity.y *= DRAG;

            // Update rotation based on velocity
            float angle = MathUtils.atan2(velocity.y, velocity.x) * MathUtils.radiansToDegrees;
            birdImage.setRotation(angle);

            checkCollisions();
            System.out.println("Bird position: " + getPosition());
            System.out.println("Bird velocity: " + velocity);
        }
    }

    public void reset() {
        isLaunched = false;
        setPosition(initialPosition.x, initialPosition.y);
        velocity.set(0, 0);
        birdImage.setRotation(0);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        if (birdImage != null) {
            birdImage.setPosition(x, y);
        }
    }

    public String getPosition() {
        return "x: " + getX() + ", y: " + getY();
    }

    @Override
    public float getX() {
        return birdImage != null ? birdImage.getX() : super.getX();
    }

    @Override
    public float getY() {
        return birdImage != null ? birdImage.getY() : super.getY();
    }
}
