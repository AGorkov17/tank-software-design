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
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;
    private TiledMap map;
    private MapRenderer mapRenderer;
    
    private GameModel gameModel;
    private InputController inputController;
    private GameRenderer gameRenderer;

    @Override
    public void create() {
        batch = new SpriteBatch();
    
        map = new TmxMapLoader().load("level.tmx");
        mapRenderer = createSingleLayerMapRenderer(map, batch);
    
        gameModel = new GameModel();
    
    
        gameModel.generateRandomLevel();
    
        inputController = new InputController(gameModel);
        gameRenderer = new GameRenderer(batch, mapRenderer);
    
        loadTextures();
    }
    
    private void loadTextures() {
        Texture tankTexture = new Texture("images/tank_blue.png");
        Texture treeTexture = new Texture("images/greenTree.png");
        
        gameModel.getPlayer().setTexture(new TextureRegion(tankTexture));
        for (GameObject obstacle : gameModel.getObstacles()) {
            obstacle.setTexture(new TextureRegion(treeTexture));
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        float delta = Gdx.graphics.getDeltaTime();
        inputController.update();
        gameModel.update(delta);
        
        gameRenderer.render(gameModel);
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}