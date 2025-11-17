package ru.mipt.bit.platformer;

/**
 * Command that toggles health bar rendering for every tank.
 */
public class ToggleHealthDisplayCommand implements Command {

    private final GameModel gameModel;

    public ToggleHealthDisplayCommand(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void execute() {
        gameModel.toggleHealthDisplay();
    }
}
