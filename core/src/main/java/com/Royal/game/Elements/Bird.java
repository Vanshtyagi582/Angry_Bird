package com.Royal.game.Elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;

public abstract class Bird implements Disposable {
    protected Sprite sprite;

    public Bird(String texturePath, float size, float x, float y) {
        sprite = new Sprite(new Texture(texturePath));
        sprite.setSize(size, size);
        sprite.setPosition(x, y);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }
}

