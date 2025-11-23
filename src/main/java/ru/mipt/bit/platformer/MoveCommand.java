package ru.mipt.bit.platformer;

/**
 * Command that requests a specific tank to move in a given direction.
 */
public class MoveCommand implements Command {

    private final GameModel model;
    private final Tank tank;
    private final Direction direction;

    public MoveCommand(GameModel model, Tank tank, Direction direction) {
        this.model = model;
        this.tank = tank;
        this.direction = direction;
    }

    @Override
    public void execute() {
        model.moveTank(tank, direction);
    }
}
