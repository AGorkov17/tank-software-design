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
