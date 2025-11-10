package ru.mipt.bit.platformer;

import java.util.Random;

public class TankAIController implements Command {
    private final Tank tank;
    private final GameModel gameModel;
    private final Random random = new Random();

    public TankAIController(Tank tank, GameModel gameModel) {
        this.tank = tank;
        this.gameModel = gameModel;
    }

    @Override
    public void execute() {
        if (tank.canMove()) {
            Direction randomDirection = getRandomDirection();
            gameModel.moveTank(tank, randomDirection);
        }
    }

    private Direction getRandomDirection() {
        Direction[] directions = Direction.values();
        return directions[random.nextInt(directions.length)];
    }
}