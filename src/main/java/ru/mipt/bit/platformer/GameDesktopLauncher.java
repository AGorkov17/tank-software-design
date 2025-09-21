package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;
    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private Tank player;
    private GameObject treeObstacle;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        // Create player tank
        Texture blueTankTexture = new Texture("images/tank_blue.png");
        TextureRegion playerGraphics = new TextureRegion(blueTankTexture);
        player = new Tank(playerGraphics, new GridPoint2(1, 1));

        // Create tree obstacle
        Texture greenTreeTexture = new Texture("images/greenTree.png");
        TextureRegion treeGraphics = new TextureRegion(greenTreeTexture);
        treeObstacle = new GameObject(treeGraphics, new GridPoint2(1, 3), 0f);

        // Position objects on the map
        moveRectangleAtTileCenter(groundLayer, player.getRectangle(), player.getCoordinates());
        moveRectangleAtTileCenter(groundLayer, treeObstacle.getRectangle(), treeObstacle.getCoordinates());
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        handlePlayerMovement();
        updatePlayerPosition(deltaTime);

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        player.render(batch);

        // render tree obstacle
        treeObstacle.render(batch);

        // submit all drawing requests
        batch.end();
    }

    private void handlePlayerMovement() {
        for (Direction direction : Direction.values()) {
            if (Gdx.input.isKeyPressed(direction.getKey1()) || 
                Gdx.input.isKeyPressed(direction.getKey2())) {
                
                if (player.canMove()) {
                    GridPoint2 potentialDestination = direction.getNextCoordinates(player.getCoordinates());
                    
                    // Check collision with tree
                    if (!treeObstacle.getCoordinates().equals(potentialDestination)) {
                        player.move(direction);
                    }
                }
                break;
            }
        }
    }

    private void updatePlayerPosition(float deltaTime) {
        if (!player.canMove()) {
            tileMovement.moveRectangleBetweenTileCenters(
                player.getRectangle(),
                player.getCoordinates(),
                player.getDestinationCoordinates(),
                player.getMovementProgress()
            );

            player.setMovementProgress(continueProgress(player.getMovementProgress(), deltaTime, MOVEMENT_SPEED));
            
            if (isEqual(player.getMovementProgress(), 1f)) {
                player.setCoordinates(player.getDestinationCoordinates());
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}