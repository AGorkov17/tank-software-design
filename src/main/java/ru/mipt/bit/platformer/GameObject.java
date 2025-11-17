package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

/**
 * Base entity that tracks tile coordinates and rendering bounds.
 */
public class GameObject {

    private static float tileWidth = 128f;
    private static float tileHeight = 128f;

    protected final GridPoint2 coordinates;
    protected Rectangle bounds;
    protected TextureRegion graphics;
    protected float rotation;

    public GameObject(GridPoint2 coordinates, float rotation) {
        this.coordinates = new GridPoint2(coordinates);
        this.rotation = rotation;
        this.bounds = new Rectangle()
                .setSize(tileWidth, tileHeight);
        updateBounds();
    }

    public GameObject(TextureRegion graphics, GridPoint2 coordinates, float rotation) {
        this.coordinates = new GridPoint2(coordinates);
        this.rotation = rotation;
        this.graphics = graphics;
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

    public static void setTileSize(float width, float height) {
        tileWidth = width;
        tileHeight = height;
    }

    public static float getTileWidth() {
        return tileWidth;
    }

    public static float getTileHeight() {
        return tileHeight;
    }

    public void setTexture(TextureRegion texture) {
        this.graphics = texture;
        if (bounds == null) {
            bounds = createBoundingRectangle(texture);
        } else {
            bounds.setSize(texture.getRegionWidth(), texture.getRegionHeight());
        }
        updateBounds();
    }

    public void setCoordinates(GridPoint2 newCoordinates) {
        coordinates.set(newCoordinates);
        updateBounds();
    }

    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
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

    protected void updateBounds() {
        if (bounds == null) {
            return;
        }
        bounds.setPosition(coordinates.x * tileWidth, coordinates.y * tileHeight);
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
        // default implementation does nothing
    }
}

    }
}
