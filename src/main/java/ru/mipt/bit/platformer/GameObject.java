package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameObject {
    protected TextureRegion graphics;
    protected Rectangle rectangle;
    protected GridPoint2 coordinates;
    protected float rotation;

    public GameObject(TextureRegion graphics, GridPoint2 coordinates, float rotation) {
        this.graphics = graphics;
        this.coordinates = coordinates;
        this.rotation = rotation;
        this.rectangle = createBoundingRectangle(graphics);
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void render(Batch batch) {
        drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }
}