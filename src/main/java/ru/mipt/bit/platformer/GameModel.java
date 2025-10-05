package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    public Tank player;
    public List<GameObject> obstacles;
    
    public GameModel() {
        player = new Tank(new GridPoint2(1, 1));
        obstacles = new ArrayList<>();
        obstacles.add(new GameObject(new GridPoint2(1, 3), 0f));
    }
    
    public boolean canMoveTo(GridPoint2 position) {
        for (GameObject obstacle : obstacles) {
            if (obstacle.coordinates.equals(position)) {
                return false;
            }
        }
        return true;
    }
    
    public void movePlayer(Direction direction) {
        if (player.canMove()) {
            GridPoint2 newPosition = direction.getNextCoordinates(player.coordinates);
            if (canMoveTo(newPosition)) {
                player.move(direction);
            }
        }
    }
    
    public void update(float deltaTime) {
        player.update(deltaTime);
    }
}