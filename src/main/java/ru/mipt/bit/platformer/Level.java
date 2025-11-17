package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Logical representation of the tile map.
 */
public class Level {

    private final int width;
    private final int height;
    private final List<GridPoint2> obstacles = new ArrayList<>();
    private final List<Tank> tanks = new ArrayList<>();
    private GridPoint2 playerSpawn;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Level() {
        this(10, 8);
    }

    public void setPlayerSpawn(GridPoint2 spawn) {
        this.playerSpawn = new GridPoint2(spawn);
    }

    public GridPoint2 getPlayerSpawn() {
        return playerSpawn == null ? null : new GridPoint2(playerSpawn);
    }

    public boolean isValidPosition(GridPoint2 position) {
        return position.x >= 0 && position.x < width
                && position.y >= 0 && position.y < height;
    }

    public boolean isPositionFree(GridPoint2 position) {
        for (GridPoint2 obstacle : obstacles) {
            if (obstacle.equals(position)) {
                return false;
            }
        }
        for (Tank tank : tanks) {
            if (tank.getCoordinates().equals(position)) {
                return false;
            }
            if (tank.isMoving() && tank.getDestination().equals(position)) {
                return false;
            }
        }
import java.util.ArrayList;
import java.util.List;

public class Level {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 8;

    private final List<GridPoint2> obstacles = new ArrayList<>();
    private final List<Tank> tanks = new ArrayList<>();

    public boolean isValidPosition(GridPoint2 position) {
        return position.x >= 0 && position.x < WIDTH &&
                position.y >= 0 && position.y < HEIGHT;
    }

    public boolean isPositionFree(GridPoint2 position) {
        // Проверяем препятствия
        for (GridPoint2 obstacle : obstacles) {
            if (obstacle.equals(position)) return false;
        }

        // Проверяем танки
        for (Tank tank : tanks) {
            if (tank.getCoordinates().equals(position)) return false;
            if (tank.isMoving() && tank.getDestination().equals(position)) return false;
        }

        return true;
    }

    public void addObstacle(GridPoint2 position) {
        obstacles.add(new GridPoint2(position));
    }

    public boolean hasObstacle(GridPoint2 position) {
        for (GridPoint2 obstacle : obstacles) {
            if (obstacle.equals(position)) {
                return true;
            }
        }
        return false;
        obstacles.add(position);
    }

    public void addTank(Tank tank) {
        tanks.add(tank);
    }

    public void removeTank(Tank tank) {
        tanks.remove(tank);
    }

    public List<Tank> getTanks() {
        return Collections.unmodifiableList(tanks);
    }

    public List<GridPoint2> getObstacles() {
        return Collections.unmodifiableList(obstacles);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
    public List<Tank> getTanks() {
        return new ArrayList<>(tanks);
    }

    public List<GridPoint2> getObstacles() {
        return new ArrayList<>(obstacles);
    }

    public int getWidth() { return WIDTH; }
    public int getHeight() { return HEIGHT; }
}
