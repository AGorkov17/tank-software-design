package ru.mipt.bit.platformer;

/**
 * Command that asks a tank to fire a projectile in its current direction.
 */
public class ShootCommand implements Command {

    private final GameModel model;
    private final Tank tank;

    public ShootCommand(GameModel model, Tank tank) {
        this.model = model;
        this.tank = tank;
    }

    @Override
    public void execute() {
        model.shoot(tank);
    }
}
