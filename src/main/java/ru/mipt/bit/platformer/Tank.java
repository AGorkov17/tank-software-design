package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

public class Tank extends GameObject {
    private GridPoint2 destination;
    private float movementProgress = 1f;
    private static final float MOVE_SPEED = 2f;
    private int health;

    public Tank(GridPoint2 coordinates) {
        super(coordinates, 0f);
        this.destination = new GridPoint2(coordinates);
        this.health = 80 + (int)(Math.random() * 21); // 80-100 HP
    }

    public Tank(TextureRegion graphics, GridPoint2 coordinates) {
        super(graphics, coordinates, 0f);
        this.destination = new GridPoint2(coordinates);
        this.health = 80 + (int)(Math.random() * 21);
    }

    public void move(Direction direction) {
        destination.set(coordinates.x + direction.getDx(), coordinates.y + direction.getDy());
        setRotation(direction.getRotation());
        movementProgress = 0f;
    }

    public boolean canMove() {
        return movementProgress >= 1f;
    }

    public boolean isMoving() {
        return movementProgress < 1f;
    }

    public GridPoint2 getDestination() {
        return destination;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public void update(float deltaTime) {
        if (isMoving()) {
            movementProgress += MOVE_SPEED * deltaTime;
            if (movementProgress >= 1f) {
                movementProgress = 1f;
                coordinates.set(destination);
            }
            updateBoundsPosition();
        }
    }
}