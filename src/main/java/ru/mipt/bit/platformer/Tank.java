package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

public class Tank extends GameObject {
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;

    public Tank(TextureRegion graphics, GridPoint2 coordinates) {
        super(graphics, coordinates, 0f);
        this.destinationCoordinates = new GridPoint2(coordinates);
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public void setMovementProgress(float movementProgress) {
        this.movementProgress = movementProgress;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public void setDestinationCoordinates(GridPoint2 destinationCoordinates) {
        this.destinationCoordinates = destinationCoordinates;
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
        return movementProgress == 1f;
    }
}