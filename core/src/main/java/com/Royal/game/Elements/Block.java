package com.Royal.game.Elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Disposable;

public abstract class Block implements Disposable {
    protected Sprite sprite;

    public Block(String texturePath, float width, float height, float x, float y) {
        sprite = new Sprite(new Texture(texturePath));
        sprite.setSize(width, height);
        sprite.setPosition(x, y);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }
}

