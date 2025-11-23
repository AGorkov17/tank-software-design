package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.math.MathUtils.clamp;

/**
 * Tank model stores grid movement state and hit points.
 */
public class Tank extends GameObject {

    private static final float MOVE_SPEED = 3f;
    private static final int MIN_HEALTH = 80;
    private static final int MAX_HEALTH = 100;

    private final GridPoint2 destination = new GridPoint2();
    private float movementProgress = 1f;
    private Direction direction = Direction.UP;
    private int health;
    private int maxHealth;

    public Tank(GridPoint2 coordinates) {
        super(coordinates, Direction.UP.getRotation());
        this.destination.set(coordinates);
        rollHealth();
    }

    public Tank(TextureRegion graphics, GridPoint2 coordinates) {
        super(graphics, coordinates, Direction.UP.getRotation());
        this.destination.set(coordinates);
        rollHealth();
    }

    private void rollHealth() {
        this.maxHealth = MIN_HEALTH + (int) (Math.random() * (MAX_HEALTH - MIN_HEALTH + 1));
        this.health = maxHealth;
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
        return !canMove();
    }

    public GridPoint2 getDestination() {
        return new GridPoint2(destination);
    }

    public Direction getDirection() {
        return direction;
        return movementProgress < 1f;
    }

    public GridPoint2 getDestination() {
        return destination;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void resetHealth() {
        this.health = maxHealth;
    }

    public void move(Direction newDirection) {
        if (!canMove()) {
            return;
        }
        this.direction = newDirection;
        this.rotation = newDirection.getRotation();
        destination.set(coordinates.x + newDirection.getDx(), coordinates.y + newDirection.getDy());
        movementProgress = 0f;
    }

    public void teleport(GridPoint2 newCoordinates) {
        coordinates.set(newCoordinates);
        destination.set(newCoordinates);
        movementProgress = 1f;
        updateBounds();
    }

    public void damage(int amount) {
        health = clamp(health - amount, 0, maxHealth);
    }

    @Override
    public void update(float deltaTime) {
        if (canMove()) {
            updateBounds();
            return;
        }
        movementProgress = clamp(movementProgress + MOVE_SPEED * deltaTime, 0f, 1f);
        float interpolatedX = coordinates.x + (destination.x - coordinates.x) * movementProgress;
        float interpolatedY = coordinates.y + (destination.y - coordinates.y) * movementProgress;
        bounds.setPosition(interpolatedX * GameObject.getTileWidth(), interpolatedY * GameObject.getTileHeight());
        if (movementProgress >= 1f) {
            coordinates.set(destination);
            updateBounds();
        }
    }
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
