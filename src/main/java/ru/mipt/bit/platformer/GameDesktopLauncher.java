package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

/**
 * Desktop launcher wires LibGDX lifecycle to our pure model/renderer abstractions.
 */
public class GameDesktopLauncher implements ApplicationListener {

    private AnnotationConfigApplicationContext context;
    private GameModel gameModel;
    private InputController inputController;
    private GameRenderer gameRenderer;

    @Override
    public void create() {
        context = new AnnotationConfigApplicationContext(GameConfiguration.class);
        gameModel = context.getBean(GameModel.class);
        inputController = context.getBean(InputController.class);
        gameRenderer = context.getBean(GameRenderer.class);
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
        map = new TmxMapLoader().load("level.tmx");
        mapRenderer = createSingleLayerMapRenderer(map, batch);

        gameModel = new GameModel();
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
    public void resize(int width, int height) {
        // Обработка изменения размера окна
    }

    @Override
    public void pause() {
        // Пауза игры
    }

    @Override
    public void resume() {
        // Возобновление игры
    }

    @Override
    public void dispose() {
        if (context != null) {
            context.close();
        }
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);
        config.setTitle("Tank Game");
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
}
