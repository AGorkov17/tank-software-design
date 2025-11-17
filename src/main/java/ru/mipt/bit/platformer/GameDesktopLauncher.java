package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

/**
 * Desktop launcher wires LibGDX lifecycle to our pure model/renderer abstractions.
 */
public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;
    private TiledMap level;
    private MapRenderer levelRenderer;

    private GameModel gameModel;
    private InputController inputController;
    private GameRenderer gameRenderer;

    private Texture tankTexture;
    private Texture treeTexture;
    private Texture bulletTexture;

    private TextureRegion tankRegion;
    private TextureRegion treeRegion;
    private TextureRegion bulletRegion;

    @Override
    public void create() {
        batch = new SpriteBatch();

        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        GameObject.setTileSize(groundLayer.getTileWidth(), groundLayer.getTileHeight());

        gameModel = new GameModel();
        inputController = new InputController(gameModel);
        gameRenderer = new GameRenderer(gameModel, batch, levelRenderer);

        loadTextures();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();
        inputController.collectCommands();
        gameModel.collectAiCommands(deltaTime);
        gameModel.processCommands();
        gameModel.update(deltaTime);
        gameRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        if (gameRenderer != null) {
            gameRenderer.close();
        }
        if (tankTexture != null) {
            tankTexture.dispose();
        }
        if (treeTexture != null) {
            treeTexture.dispose();
        }
        if (bulletTexture != null) {
            bulletTexture.dispose();
        }
        if (level != null) {
            level.dispose();
        }
        if (batch != null) {
            batch.dispose();
        }
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);
        config.setTitle("Tank Game");
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }

    private void loadTextures() {
        tankTexture = new Texture("images/tank_blue.png");
        treeTexture = new Texture("images/greenTree.png");
        bulletTexture = createBulletTexture();

        tankRegion = new TextureRegion(tankTexture);
        treeRegion = new TextureRegion(treeTexture);
        bulletRegion = new TextureRegion(bulletTexture);

        for (Tank tank : gameModel.getTanks()) {
            tank.setTexture(new TextureRegion(tankRegion));
        }
        for (GameObject obstacle : gameModel.getObstacles()) {
            obstacle.setTexture(new TextureRegion(treeRegion));
        }
        gameModel.setBulletTexture(bulletRegion);
    }

    private Texture createBulletTexture() {
        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        pixmap.setColor(1f, 0.7f, 0f, 1f);
        pixmap.fillCircle(8, 8, 6);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
}
