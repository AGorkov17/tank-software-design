package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Projectile travelling with constant velocity until it hits something or leaves the level bounds.
 */
public class Bullet {

    private final TextureRegion graphics;
    private final Rectangle bounds;
    private final Vector2 velocity;
    private final Tank owner;

    public Bullet(TextureRegion graphics, Rectangle bounds, Vector2 velocity, Tank owner) {
        this.graphics = graphics;
        this.bounds = bounds;
        this.velocity = velocity;
        this.owner = owner;
    }

    public void update(float deltaTime) {
        bounds.x += velocity.x * deltaTime;
        bounds.y += velocity.y * deltaTime;
    }

    public TextureRegion getGraphics() {
        return graphics;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Tank getOwner() {
        return owner;
    }
}
