package com.Royal.game.Elements;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class Block_data implements Serializable {
    public float health;
    public int type;
    public float X;
    public float Y;

    public Block_data(Block b){
        X=(float)b.getBody().getPosition().x*100f-(b.sprite.getWidth()/2);
        Y=(float)b.getBody().getPosition().y*100f-(b.sprite.getHeight()/2);
        health=b.health;
        type=b.getType();
    }

    public int getType() {
        return type;
    }
    public float getX() {
        return X;
    }
    public float getY() {
        return Y;
    }
}
