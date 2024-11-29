package com.Royal.game.Elements;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class Pig_data implements Serializable {

    public float X;
    public float Y;
    public float health;
    public int type;
    public Pig_data(Pig b){
        X=(float) b.getBody().getPosition().x*100f;
        Y=(float) b.getBody().getPosition().y*100f;
        health=b.health;
        type=b.getType();


    }

    public float getX() {
        return X;
    }
    public float getY() {
        return Y;
    }
    public int getType() {
        return type;
    }


}
