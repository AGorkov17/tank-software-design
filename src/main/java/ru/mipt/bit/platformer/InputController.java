package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class InputController {
    private final GameModel gameModel;
    private final ToggleHealthDisplayCommand healthCommand;

    public InputController(GameModel gameModel) {
        this.gameModel = gameModel;
        this.healthCommand = new ToggleHealthDisplayCommand(gameModel);
    }

    public void update() {
        handlePlayerMovement();
        handleHealthToggle();
    }

    private void handlePlayerMovement() {
        for (Direction dir : Direction.values()) {
            if (Gdx.input.isKeyPressed(dir.getKey1()) || Gdx.input.isKeyPressed(dir.getKey2())) {
                gameModel.movePlayer(dir);
                break;
            }
        }
    }

    private void handleHealthToggle() {
        if (Gdx.input.isKeyJustPressed(Keys.L)) {
            healthCommand.execute();
        }
    }
}