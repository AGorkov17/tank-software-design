package ru.mipt.bit.platformer;

import com.badlogic.gdx.Input.Keys;

public enum Direction {
    UP(0, 1, 90f, Keys.UP, Keys.W),
    DOWN(0, -1, -90f, Keys.DOWN, Keys.S),
    LEFT(-1, 0, -180f, Keys.LEFT, Keys.A),
    RIGHT(1, 0, 0f, Keys.RIGHT, Keys.D);

    private final int dx;
    private final int dy;
    private final float rotation;
    private final Keys key1;
    private final Keys key2;

    Direction(int dx, int dy, float rotation, Keys key1, Keys key2) {
        this.dx = dx;
        this.dy = dy;
        this.rotation = rotation;
        this.key1 = key1;
        this.key2 = key2;
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

    public Keys getKey1() {
        return key1;
    }

    public Keys getKey2() {
        return key2;
    }

    public GridPoint2 getNextCoordinates(GridPoint2 current) {
        return new GridPoint2(current.x + dx, current.y + dy);
    }
}