package ru.mipt.bit.platformer;

/**
 * Command that toggles health bar rendering for every tank.
 */
public class ToggleHealthDisplayCommand implements Command {

public class ToggleHealthDisplayCommand {
    private final GameModel gameModel;

    public ToggleHealthDisplayCommand(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void execute() {
        gameModel.toggleHealthDisplay();
    }
}
    public void execute() {
        gameModel.toggleHealthDisplay();
    }
}
