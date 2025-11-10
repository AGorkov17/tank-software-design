package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
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
        obstacles.add(position);
    }

    public void addTank(Tank tank) {
        tanks.add(tank);
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