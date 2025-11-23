package ru.mipt.bit.platformer;

import java.util.Random;

/**
 * Naive AI that periodically chooses to move or shoot via commands.
 */
public class TankAIController {

    private static final float DECISION_INTERVAL = 1.2f;
    private static final float SHOOT_PROBABILITY = 0.35f;

    private final Tank tank;
    private final GameModel gameModel;
    private final Random random = new Random();
    private float decisionTimer;
public class TankAIController implements Command {
    private final Tank tank;
    private final GameModel gameModel;
    private final Random random = new Random();

    public TankAIController(Tank tank, GameModel gameModel) {
        this.tank = tank;
        this.gameModel = gameModel;
    }

    public void update(float deltaTime) {
        decisionTimer -= deltaTime;
        if (decisionTimer > 0f) {
            return;
        }
        decisionTimer = DECISION_INTERVAL;
        if (random.nextFloat() < SHOOT_PROBABILITY) {
            gameModel.enqueueCommand(new ShootCommand(gameModel, tank));
        } else {
            Direction direction = Direction.random(random);
            gameModel.enqueueCommand(new MoveCommand(gameModel, tank, direction));
        }
    }
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
