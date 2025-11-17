package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Random;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.S;
import static com.badlogic.gdx.Input.Keys.UP;
import static com.badlogic.gdx.Input.Keys.W;

/**
 * Direction abstraction stores grid deltas, rendering rotation and keyboard bindings.
 */
public enum Direction {
    UP(0, 1, 90f, UP, W),
    DOWN(0, -1, -90f, DOWN, S),
    LEFT(-1, 0, 180f, LEFT, A),
    RIGHT(1, 0, 0f, RIGHT, D);

    private final int dx;
    private final int dy;
    private final float rotation;
    private final int primaryKey;
    private final int secondaryKey;

    Direction(int dx, int dy, float rotation, int primaryKey, int secondaryKey) {
        this.dx = dx;
        this.dy = dy;
        this.rotation = rotation;
        this.primaryKey = primaryKey;
        this.secondaryKey = secondaryKey;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public float getRotation() {
        return rotation;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public int getSecondaryKey() {
        return secondaryKey;
    }

    public GridPoint2 getNextCoordinates(GridPoint2 current) {
        return new GridPoint2(current.x + dx, current.y + dy);
    }

    public static Direction random(Random random) {
        Direction[] directions = values();
        return directions[random.nextInt(directions.length)];
    }
}
