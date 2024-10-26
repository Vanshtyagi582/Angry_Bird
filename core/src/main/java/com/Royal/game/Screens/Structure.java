package com.Royal.game.Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Structure {
    private Texture blockTexture;
    private float x, y, width, height;

    public Structure(float x, float y, float width, float height) {
        this.blockTexture = new Texture("block.png");
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(SpriteBatch batch) {
        batch.draw(blockTexture, x, y, width, height);
    }

    public void dispose() {
        blockTexture.dispose();
    }
}
