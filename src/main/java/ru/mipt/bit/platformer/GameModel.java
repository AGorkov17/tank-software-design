package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Pure game logic: tanks, bullets, obstacles and AI.
 */
public class GameModel {

    private static final int BULLET_DAMAGE = 20;
    private static final float BULLET_SPEED = 650f;

    private final Level level;
    private final Tank playerTank;
    private final List<TankAIController> aiControllers = new ArrayList<>();
    private final List<GameObject> obstacles = new ArrayList<>();
    private final List<Bullet> bullets = new ArrayList<>();
    private final List<Command> pendingCommands = new ArrayList<>();
    private final List<GameModelListener> listeners = new ArrayList<>();

    private TextureRegion bulletTexture;
    private boolean showHealth;

    public GameModel() {
        this.level = LevelGenerator.loadLevel();
        this.playerTank = new Tank(Optional.ofNullable(level.getPlayerSpawn()).orElse(new GridPoint2(0, 0)));
        LevelGenerator.placeTanks(level, playerTank);
        createObstacleObjects();
        createAIControllers();
    }

    private void createObstacleObjects() {
        for (GridPoint2 obstacle : level.getObstacles()) {
            GameObject tree = new GameObject(obstacle, 0f);
            obstacles.add(tree);
            notifyObstacleAdded(tree);
        }
    }

    private void createAIControllers() {
        for (Tank tank : level.getTanks()) {
            notifyTankAdded(tank);
            if (tank != playerTank) {
                aiControllers.add(new TankAIController(tank, this));
            }
        }
    }

    public Tank getPlayer() {
        return playerTank;
    }

    public List<Tank> getTanks() {
        return level.getTanks();
    }

    public List<GameObject> getObstacles() {
        return new ArrayList<>(obstacles);
    }

    public boolean isShowHealth() {
        return showHealth;
    }

    public void toggleHealthDisplay() {
        showHealth = !showHealth;
    }

    public void update(float deltaTime) {
        for (Tank tank : level.getTanks()) {
            tank.update(deltaTime);
        }
        updateBullets(deltaTime);
    }

    public void collectAiCommands(float deltaTime) {
        for (TankAIController ai : aiControllers) {
            ai.update(deltaTime);
        }
    }

    public void enqueueCommand(Command command) {
        if (command != null) {
            pendingCommands.add(command);
        }
    }

    public void processCommands() {
        if (pendingCommands.isEmpty()) {
            return;
        }
        for (Command command : new ArrayList<>(pendingCommands)) {
            command.execute();
        }
        pendingCommands.clear();
    }

    public void movePlayer(Direction direction) {
        moveTank(playerTank, direction);
    }

    public void moveTank(Tank tank, Direction direction) {
        if (tank == null || !tank.canMove()) {
            return;
        }
        GridPoint2 nextPosition = direction.getNextCoordinates(tank.getCoordinates());
        if (level.isValidPosition(nextPosition) && level.isPositionFree(nextPosition)) {
            tank.move(direction);
        }
    }

    public void shoot(Tank tank) {
        if (tank == null || !tank.canMove() || bulletTexture == null) {
            return;
        }
        Direction direction = tank.getDirection();
        GridPoint2 bulletTile = direction.getNextCoordinates(tank.getCoordinates());
        if (!level.isValidPosition(bulletTile)) {
            return;
        }
        if (level.hasObstacle(bulletTile)) {
            return;
        }
        Tank target = findTankAt(bulletTile, tank);
        if (target != null) {
            applyDamage(target);
            return;
        }
        Bullet bullet = createBullet(tank, bulletTile, direction);
        bullets.add(bullet);
        notifyBulletAdded(bullet);
    }

    private Tank findTankAt(GridPoint2 tile, Tank shooter) {
        for (Tank tank : level.getTanks()) {
            if (tank == shooter) {
                continue;
            }
            if (tank.getCoordinates().equals(tile)) {
                return tank;
            }
        }
        return null;
    }

    private Bullet createBullet(Tank owner, GridPoint2 tile, Direction direction) {
        float tileWidth = GameObject.getTileWidth();
        float tileHeight = GameObject.getTileHeight();
        float bulletSize = Math.min(tileWidth, tileHeight) / 4f;

        float centerX = tile.x * tileWidth + tileWidth / 2f;
        float centerY = tile.y * tileHeight + tileHeight / 2f;
        Rectangle bounds = new Rectangle(
                centerX - bulletSize / 2f,
                centerY - bulletSize / 2f,
                bulletSize,
                bulletSize
        );
        Vector2 velocity = new Vector2(direction.getDx(), direction.getDy())
                .nor()
                .scl(BULLET_SPEED);
        return new Bullet(bulletTexture, bounds, velocity, owner);
    }

    private void updateBullets(float deltaTime) {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update(deltaTime);
            if (isBulletOutOfBounds(bullet) || resolveBulletCollisions(bullet)) {
                notifyBulletRemoved(bullet);
                iterator.remove();
            }
        }
    }

    private boolean resolveBulletCollisions(Bullet bullet) {
        for (GameObject obstacle : obstacles) {
            if (bullet.getBounds().overlaps(obstacle.getBounds())) {
                return true;
            }
        }
        for (Tank tank : level.getTanks()) {
            if (tank == bullet.getOwner()) {
                continue;
            }
            if (bullet.getBounds().overlaps(tank.getBounds())) {
                applyDamage(tank);
                return true;
            }
        }
        return false;
    }

    private boolean isBulletOutOfBounds(Bullet bullet) {
        Rectangle bounds = bullet.getBounds();
        float width = level.getWidth() * GameObject.getTileWidth();
        float height = level.getHeight() * GameObject.getTileHeight();
        return bounds.x < 0 || bounds.y < 0
                || bounds.x + bounds.width > width
                || bounds.y + bounds.height > height;
    }

    private void applyDamage(Tank tank) {
        tank.damage(BULLET_DAMAGE);
        if (tank.getHealth() <= 0) {
            handleTankDeath(tank);
        }
    }

    public void setBulletTexture(TextureRegion bulletTexture) {
        this.bulletTexture = bulletTexture;
    }

    public void addListener(GameModelListener listener) {
        listeners.add(listener);
        for (GameObject obstacle : obstacles) {
            listener.onObstacleAdded(obstacle);
        }
        for (Tank tank : level.getTanks()) {
            listener.onTankAdded(tank);
        }
        for (Bullet bullet : bullets) {
            listener.onBulletAdded(bullet);
        }
    }

    private void handleTankDeath(Tank tank) {
        level.removeTank(tank);
        notifyTankRemoved(tank);
        tank.resetHealth();
        tank.teleport(LevelGenerator.getRandomFreePosition(level));
        level.addTank(tank);
        notifyTankAdded(tank);
    }

    private void notifyTankAdded(Tank tank) {
        for (GameModelListener listener : listeners) {
            listener.onTankAdded(tank);
        }
    }

    private void notifyTankRemoved(Tank tank) {
        for (GameModelListener listener : listeners) {
            listener.onTankRemoved(tank);
        }
    }

    private void notifyObstacleAdded(GameObject obstacle) {
        for (GameModelListener listener : listeners) {
            listener.onObstacleAdded(obstacle);
        }
    }

    private void notifyBulletAdded(Bullet bullet) {
        for (GameModelListener listener : listeners) {
            listener.onBulletAdded(bullet);
        }
    }

    private void notifyBulletRemoved(Bullet bullet) {
        for (GameModelListener listener : listeners) {
            listener.onBulletRemoved(bullet);
        }
    }
}
