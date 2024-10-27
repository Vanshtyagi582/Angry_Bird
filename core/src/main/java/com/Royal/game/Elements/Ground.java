package com.Royal.game.Elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Ground {
    private Texture texture;
    private Rectangle bounds;

    public Ground(String texturePath, float x, float y, float width,float height) {
        texture = new Texture(texturePath);
        bounds = new Rectangle(x, y, width,height);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        texture.dispose();
    }
}

