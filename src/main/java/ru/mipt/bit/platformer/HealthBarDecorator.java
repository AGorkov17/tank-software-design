package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class HealthBarDecorator {
    private final Tank tank;
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    public HealthBarDecorator(Tank tank) {
        this.tank = tank;
    }

    public void render(Batch batch) {
        if (tank.getGraphics() == null) return;

        float x = tank.getBounds().x;
        float y = tank.getBounds().y + tank.getBounds().height + 5;
        float width = tank.getBounds().width;
        float healthPercent = (float) tank.getHealth() / 100f;

        batch.end();

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Красный фон
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(x, y, width, 5);

        // Зеленая полоса здоровья
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(x, y, width * healthPercent, 5);

        shapeRenderer.end();
        batch.begin();

        tank.render(batch);
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}