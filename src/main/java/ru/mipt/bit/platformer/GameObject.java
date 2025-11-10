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

    public GameObject(GridPoint2 coordinates, float rotation) {
        this.coordinates = coordinates;
        this.rotation = rotation;
        this.bounds = new Rectangle();
    }

    public GameObject(TextureRegion graphics, GridPoint2 coordinates, float rotation) {
        this.graphics = graphics;
        this.coordinates = coordinates;
        this.rotation = rotation;
        this.bounds = createBoundingRectangle(graphics);
        updateBounds();
    }

    protected void updateBounds() {
        if (bounds != null) {
            bounds.x = coordinates.x * 128f;
            bounds.y = coordinates.y * 128f;
        }
    }

    public void setCoordinates(GridPoint2 coordinates) {
        this.coordinates = coordinates;
        updateBounds();
    }

    public GridPoint2 getCoordinates() { return coordinates; }
    public float getRotation() { return rotation; }
    public void setRotation(float rotation) { this.rotation = rotation; }
    public Rectangle getBounds() { return bounds; }
    public TextureRegion getGraphics() { return graphics; }

    public void setTexture(TextureRegion texture) {
        this.graphics = texture;
        if (bounds == null) {
            this.bounds = createBoundingRectangle(graphics);
        }
    }

    public void render(Batch batch) {
        if (graphics != null) {
            drawTextureRegionUnscaled(batch, graphics, bounds, rotation);
        }
    }

    public void update(float deltaTime) {

    }
}