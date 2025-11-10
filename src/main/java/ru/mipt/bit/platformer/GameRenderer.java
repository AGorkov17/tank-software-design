package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;

public class GameRenderer {
    private final Batch batch;
    private final MapRenderer mapRenderer;

    public GameRenderer(Batch batch, MapRenderer mapRenderer) {
        this.batch = batch;
        this.mapRenderer = mapRenderer;
    }

    public void render(GameModel gameModel) {
        mapRenderer.render();
        batch.begin();

        renderObstacles(gameModel);
        renderTanks(gameModel);

        batch.end();
    }

    private void renderObstacles(GameModel gameModel) {
        for (GameObject obstacle : gameModel.getObstacles()) {
            obstacle.render(batch);
        }
    }

    private void renderTanks(GameModel gameModel) {
        for (Tank tank : gameModel.getTanks()) {
            if (gameModel.isShowHealth()) {
                new HealthBarDecorator(tank).render(batch);
            } else {
                tank.render(batch);
            }
        }
    }
}