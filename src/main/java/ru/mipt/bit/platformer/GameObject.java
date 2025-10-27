package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameObject {
    protected TextureRegion graphics;
    protected Rectangle bounds;
    protected GridPoint2 coordinates;
    protected float rotation;

    public GameObject(TextureRegion graphics, GridPoint2 coordinates, float rotation) {
        this.graphics = graphics;
        this.coordinates = coordinates;
        this.rotation = rotation;
        this.bounds = createBoundingRectangle(graphics);
        updateBoundsPosition();
    }

    protected void updateBoundsPosition() {
        if (bounds != null) {
            bounds.x = coordinates.x * 128f;
            bounds.y = coordinates.y * 128f;
        }
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(GridPoint2 coordinates) {
        this.coordinates = coordinates;
        updateBoundsPosition();
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void render(Batch batch) {
        drawTextureRegionUnscaled(batch, graphics, bounds, rotation);
    }

    public void update(float deltaTime) {
        // Базовая реализация
    }

    public void setTexture(TextureRegion texture) {
        this.graphics = texture;
    }

    public GameObject(GridPoint2 coordinates, float rotation) {
    this.coordinates = coordinates;
    this.rotation = rotation;
    this.rectangle = new Rectangle();
    }

    
    public void setTexture(TextureRegion texture) {
    this.graphics = texture;
    if (graphics != null) {
        this.rectangle = createBoundingRectangle(graphics);
    }
    }

}

