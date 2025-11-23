package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

@Configuration
public class GameConfiguration {

    @Bean(destroyMethod = "dispose")
    public Batch batch() {
        return new SpriteBatch();
    }

    @Bean(destroyMethod = "dispose")
    public TiledMap tiledMap() {
        return new TmxMapLoader().load("level.tmx");
    }

    @Bean
    public TiledMapTileLayer groundLayer(TiledMap tiledMap) {
        TiledMapTileLayer groundLayer = getSingleLayer(tiledMap);
        GameObject.setTileSize(groundLayer.getTileWidth(), groundLayer.getTileHeight());
        return groundLayer;
    }

    @Bean
    public MapRenderer mapRenderer(TiledMap tiledMap, Batch batch) {
        return createSingleLayerMapRenderer(tiledMap, batch);
    }

    @Bean
    public GameModel gameModel(TiledMapTileLayer groundLayer) {
        // groundLayer ensures tile size is configured before model creates game objects
        return new GameModel();
    }

    @Bean
    public InputController inputController(GameModel gameModel) {
        return new InputController(gameModel);
    }

    @Bean(destroyMethod = "close")
    public GameRenderer gameRenderer(GameModel gameModel, Batch batch, MapRenderer mapRenderer) {
        return new GameRenderer(gameModel, batch, mapRenderer);
    }

    @Bean(destroyMethod = "dispose")
    public Texture tankTexture() {
        return new Texture("images/tank_blue.png");
    }

    @Bean(destroyMethod = "dispose")
    public Texture treeTexture() {
        return new Texture("images/greenTree.png");
    }

    @Bean(destroyMethod = "dispose")
    public Texture bulletTexture() {
        return createBulletTexture();
    }

    @Bean
    public TextureRegion tankRegion(Texture tankTexture) {
        return new TextureRegion(tankTexture);
    }

    @Bean
    public TextureRegion treeRegion(Texture treeTexture) {
        return new TextureRegion(treeTexture);
    }

    @Bean
    public TextureRegion bulletRegion(Texture bulletTexture) {
        return new TextureRegion(bulletTexture);
    }

    @Bean(initMethod = "applyTextures")
    public GameTexturesInitializer gameTexturesInitializer(
            GameModel gameModel,
            TextureRegion tankRegion,
            TextureRegion treeRegion,
            TextureRegion bulletRegion
    ) {
        return new GameTexturesInitializer(gameModel, tankRegion, treeRegion, bulletRegion);
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
