package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Decorates tank rendering with an additional health bar overlay.
 */
public class HealthBarDecorator {

    private static final float HEIGHT = 6f;

    private final Tank tank;
    private final ShapeRenderer shapeRenderer;

    public HealthBarDecorator(Tank tank, ShapeRenderer shapeRenderer) {
        this.tank = tank;
        this.shapeRenderer = shapeRenderer;
    }

    public void render(Batch batch) {
        if (tank.getBounds() == null) {
            return;
        }
        float x = tank.getBounds().x;
        float width = tank.getBounds().width;
        float y = tank.getBounds().y + tank.getBounds().height + 4f;
        float percentage = (float) tank.getHealth() / tank.getMaxHealth();

        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(x, y, width, HEIGHT);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(x, y, width * percentage, HEIGHT);
        shapeRenderer.end();
        batch.begin();

        tank.render(batch);
    }
}
