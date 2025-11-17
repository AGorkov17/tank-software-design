package ru.mipt.bit.platformer;

/**
 * Observer that reacts to logical model changes (object additions/removals).
 */
public interface GameModelListener {

    void onTankAdded(Tank tank);

    void onTankRemoved(Tank tank);

    void onObstacleAdded(GameObject obstacle);

    void onObstacleRemoved(GameObject obstacle);

    void onBulletAdded(Bullet bullet);

    void onBulletRemoved(Bullet bullet);
}
