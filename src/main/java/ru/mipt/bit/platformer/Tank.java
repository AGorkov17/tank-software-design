package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

public class Tank extends GameObject {
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private static final float MOVEMENT_SPEED = 2f;

    public Tank(TextureRegion graphics, GridPoint2 coordinates) {
        super(graphics, coordinates, 0f);
        this.destinationCoordinates = new GridPoint2(coordinates);
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public void move(Direction direction) {
        destinationCoordinates.set(
            coordinates.x + direction.getDx(),
            coordinates.y + direction.getDy()
        );
        setRotation(direction.getRotation());
        movementProgress = 0f;
    }

    public boolean canMove() {
        return movementProgress >= 1f;
    }

    @Override
    public void update(float deltaTime) {
        if (movementProgress < 1f) {
            movementProgress += MOVEMENT_SPEED * deltaTime;
            if (movementProgress > 1f) {
                movementProgress = 1f;
                coordinates.set(destinationCoordinates);
            }
            updateBoundsPosition();
        }
    }

    public Tank(GridPoint2 coordinates) {
    super(coordinates, 0f);
    this.destinationCoordinates = new GridPoint2(coordinates);
    }


    public void update(float deltaTime) {
    if (movementProgress < 1f) {
        movementProgress = Math.min(1f, movementProgress + deltaTime * 2f);
        if (movementProgress >= 1f) {
            coordinates.set(destinationCoordinates);
        }
      }
    }
}