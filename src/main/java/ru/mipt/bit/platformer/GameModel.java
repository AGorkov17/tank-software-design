package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private final Level level;
    private final Tank playerTank;
    private final List<TankAIController> aiControllers = new ArrayList<>();
    private boolean showHealth = false;

    public GameModel() {
        this.level = LevelGenerator.generateLevel();
        this.playerTank = new Tank(new GridPoint2(0, 0));

        LevelGenerator.placeTanks(level, playerTank);
        createAIControllers();
    }

    private void createAIControllers() {
        for (Tank tank : level.getTanks()) {
            if (tank != playerTank) {
                aiControllers.add(new TankAIController(tank, this));
            }
        }
    }

    public void movePlayer(Direction direction) {
        moveTank(playerTank, direction);
    }

    public void moveTank(Tank tank, Direction direction) {
        if (tank.canMove()) {
            GridPoint2 newPos = direction.getNextCoordinates(tank.getCoordinates());
            if (isMoveValid(newPos)) {
                tank.move(direction);
            }
        }
    }

    private boolean isMoveValid(GridPoint2 position) {
        return level.isValidPosition(position) && level.isPositionFree(position);
    }

    public void update(float deltaTime) {
        // Обновляем AI
        for (TankAIController ai : aiControllers) {
            ai.execute();
        }

        // Обновляем все танки
        for (Tank tank : level.getTanks()) {
            tank.update(deltaTime);
        }
    }

    public Tank getPlayer() {
        return playerTank;
    }

    public List<Tank> getTanks() {
        return level.getTanks();
    }

    public List<GameObject> getObstacles() {
        List<GameObject> obstacles = new ArrayList<>();
        for (GridPoint2 pos : level.getObstacles()) {
            obstacles.add(new GameObject(pos, 0f));
        }
        return obstacles;
    }

    public boolean isShowHealth() {
        return showHealth;
    }

    public void toggleHealthDisplay() {
        showHealth = !showHealth;
    }
}