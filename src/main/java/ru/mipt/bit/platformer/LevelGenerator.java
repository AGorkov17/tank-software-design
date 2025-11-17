package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Optional;
import java.util.Random;

/**
 * Creates logical level state either randomly or from a layout file.
 */
public final class LevelGenerator {

    private static final int DEFAULT_WIDTH = 10;
    private static final int DEFAULT_HEIGHT = 8;
    private static final int OBSTACLE_COUNT = 10;
    private static final int AI_TANK_COUNT = 3;
    private static final Random RANDOM = new Random();

    private LevelGenerator() {
    }

    public static Level loadLevel() {
        Optional<Level> fromFile = LevelLoader.loadLevelFromFile();
        return fromFile.orElseGet(LevelGenerator::generateRandomLevel);
    }

    public static Level generateRandomLevel() {
        Level level = new Level(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        for (int i = 0; i < OBSTACLE_COUNT; i++) {
            level.addObstacle(getRandomFreePosition(level));
        }
        level.setPlayerSpawn(getRandomFreePosition(level));
        return level;
    }

    public static void placeTanks(Level level, Tank playerTank) {
        GridPoint2 playerPosition = Optional.ofNullable(level.getPlayerSpawn())
                .orElseGet(() -> getRandomFreePosition(level));
        playerTank.teleport(playerPosition);
        level.addTank(playerTank);

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

    static GridPoint2 getRandomFreePosition(Level level) {
        GridPoint2 position;
        do {
            position = new GridPoint2(
                    RANDOM.nextInt(level.getWidth()),
                    RANDOM.nextInt(level.getHeight())
            );
        } while (!level.isPositionFree(position));
        return position;
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
