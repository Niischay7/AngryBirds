package io.github.Niischay7.angrybirds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TrajectoryActor extends Actor {
    private Vector2[] trajectoryPoints;
    private ShapeRenderer shapeRenderer;

    public TrajectoryActor() {
        shapeRenderer = new ShapeRenderer();
    }

    public void setTrajectoryPoints(Vector2[] points) {
        this.trajectoryPoints = points;
    }

    @Override
    public void draw(com.badlogic.gdx.graphics.g2d.Batch batch, float parentAlpha) {
        if (trajectoryPoints == null || trajectoryPoints.length < 2) return;

        // Temporarily stop the batch rendering
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);

        // Draw dotted trajectory line
        for (int i = 1; i < trajectoryPoints.length; i++) {
            // Ensure points are within screen bounds
            if (i % 2 == 0) { // Only draw every other segment to create dotted effect
                shapeRenderer.line(trajectoryPoints[i - 1], trajectoryPoints[i]);
            }
        }

        shapeRenderer.end();

        // Restart the batch rendering
        batch.begin();
    }

    // Remove the @Override annotation and modify the method signature
    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}
