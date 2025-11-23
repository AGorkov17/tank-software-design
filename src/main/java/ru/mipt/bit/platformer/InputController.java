package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

/**
 * Handles player input and translates it into model commands.
 */
public class InputController {

public class InputController {
    private final GameModel gameModel;
    private final ToggleHealthDisplayCommand healthCommand;

    public InputController(GameModel gameModel) {
        this.gameModel = gameModel;
        this.healthCommand = new ToggleHealthDisplayCommand(gameModel);
    }

    public void collectCommands() {
        handlePlayerMovement();
        handleShooting();
    public void update() {
        handlePlayerMovement();
        handleHealthToggle();
    }

    private void handlePlayerMovement() {
        Tank player = gameModel.getPlayer();
        for (Direction direction : Direction.values()) {
            if (Gdx.input.isKeyPressed(direction.getPrimaryKey())
                    || Gdx.input.isKeyPressed(direction.getSecondaryKey())) {
                gameModel.enqueueCommand(new MoveCommand(gameModel, player, direction));
        for (Direction dir : Direction.values()) {
            if (Gdx.input.isKeyPressed(dir.getKey1()) || Gdx.input.isKeyPressed(dir.getKey2())) {
                gameModel.movePlayer(dir);
                break;
            }
        }
    }

    private void handleShooting() {
        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            gameModel.enqueueCommand(new ShootCommand(gameModel, gameModel.getPlayer()));
        }
    }

    private void handleHealthToggle() {
        if (Gdx.input.isKeyJustPressed(Keys.L)) {
            gameModel.enqueueCommand(healthCommand);
        }
    }
}
    private void handleHealthToggle() {
        if (Gdx.input.isKeyJustPressed(Keys.L)) {
            healthCommand.execute();
        }
    }
}
