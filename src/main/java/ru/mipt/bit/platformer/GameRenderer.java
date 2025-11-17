package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Draws everything that belongs to the logical model.
 */
public class GameRenderer implements AutoCloseable, GameModelListener {

    private final GameModel model;
    private final Batch batch;
    private final MapRenderer mapRenderer;
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private final List<GameObject> obstacles = new ArrayList<>();
    private final List<Tank> tanks = new ArrayList<>();
    private final List<Bullet> bullets = new ArrayList<>();

    public GameRenderer(GameModel model, Batch batch, MapRenderer mapRenderer) {
        this.model = model;
        this.batch = batch;
        this.mapRenderer = mapRenderer;
        model.addListener(this);
    }

    public void render() {
        mapRenderer.render();

        batch.begin();
        renderObstacles();
        renderBullets();
        renderTanks();
        batch.end();
    }

    private void renderObstacles() {
        for (GameObject obstacle : obstacles) {
            obstacle.render(batch);
        }
    }

    private void renderBullets() {
        for (Bullet bullet : bullets) {
            batch.draw(bullet.getGraphics(), bullet.getBounds().x, bullet.getBounds().y,
                    bullet.getBounds().width, bullet.getBounds().height);
        }
    }

    private void renderTanks() {
        for (Tank tank : tanks) {
            if (model.isShowHealth()) {
                new HealthBarDecorator(tank, shapeRenderer).render(batch);
            } else {
                tank.render(batch);
            }
        }
    }

    @Override
    public void close() {
        shapeRenderer.dispose();
    }

    @Override
    public void onTankAdded(Tank tank) {
        if (!tanks.contains(tank)) {
            tanks.add(tank);
        }
    }

    @Override
    public void onTankRemoved(Tank tank) {
        tanks.remove(tank);
    }

    @Override
    public void onObstacleAdded(GameObject obstacle) {
        if (!obstacles.contains(obstacle)) {
            obstacles.add(obstacle);
        }
    }

    @Override
    public void onObstacleRemoved(GameObject obstacle) {
        obstacles.remove(obstacle);
    }

    @Override
    public void onBulletAdded(Bullet bullet) {
        if (!bullets.contains(bullet)) {
            bullets.add(bullet);
        }
    }

    @Override
    public void onBulletRemoved(Bullet bullet) {
        bullets.remove(bullet);
    }
}
