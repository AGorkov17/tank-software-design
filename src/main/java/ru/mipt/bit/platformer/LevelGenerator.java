package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import java.util.Random;

public class LevelGenerator {
    private static final int OBSTACLE_COUNT = 10;
    private static final int AI_TANK_COUNT = 3;
    private static final Random random = new Random();

    public static Level generateLevel() {
        Level level = new Level();

        generateObstacles(level);
        return level;
    }

    private static void generateObstacles(Level level) {
        for (int i = 0; i < OBSTACLE_COUNT; i++) {
            GridPoint2 pos = getRandomFreePosition(level);
            level.addObstacle(pos);
        }
    }

    public static void placeTanks(Level level, Tank playerTank) {
        // Размещаем игрока
        GridPoint2 playerPos = getRandomFreePosition(level);
        playerTank.setCoordinates(playerPos);
        level.addTank(playerTank);

        // Размещаем AI танки
        for (int i = 0; i < AI_TANK_COUNT; i++) {
            Tank aiTank = new Tank(getRandomFreePosition(level));
            level.addTank(aiTank);
        }
    }

    private static GridPoint2 getRandomFreePosition(Level level) {
        GridPoint2 pos;
        do {
            pos = new GridPoint2(
                    random.nextInt(level.getWidth()),
                    random.nextInt(level.getHeight())
            );
        } while (!level.isPositionFree(pos));
        return pos;
    }
}