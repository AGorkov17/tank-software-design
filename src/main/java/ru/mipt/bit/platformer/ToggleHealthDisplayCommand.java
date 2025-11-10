package ru.mipt.bit.platformer;

public class ToggleHealthDisplayCommand {
    private final GameModel gameModel;

    public ToggleHealthDisplayCommand(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void execute() {
        gameModel.toggleHealthDisplay();
    }
}