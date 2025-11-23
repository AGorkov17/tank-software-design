package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Applies loaded textures to model objects once resources are ready.
 */
public class GameTexturesInitializer {

    private final GameModel gameModel;
    private final TextureRegion tankRegion;
    private final TextureRegion treeRegion;
    private final TextureRegion bulletRegion;

    public GameTexturesInitializer(GameModel gameModel,
                                   TextureRegion tankRegion,
                                   TextureRegion treeRegion,
                                   TextureRegion bulletRegion) {
        this.gameModel = gameModel;
        this.tankRegion = tankRegion;
        this.treeRegion = treeRegion;
        this.bulletRegion = bulletRegion;
    }

    public void applyTextures() {
        for (Tank tank : gameModel.getTanks()) {
            tank.setTexture(new TextureRegion(tankRegion));
        }
        for (GameObject obstacle : gameModel.getObstacles()) {
            obstacle.setTexture(new TextureRegion(treeRegion));
        }
        gameModel.setBulletTexture(bulletRegion);
    }
}
