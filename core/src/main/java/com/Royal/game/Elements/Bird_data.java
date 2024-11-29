package com.Royal.game.Elements;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class Bird_data implements Serializable {
    public float hitpoints;
    public int type;
    public float X;
    public float Y;

    public Bird_data(Bird b){

        X=(float) b.getBody().getPosition().x*100f;
        Y=(float) b.getBody().getPosition().y*100f;
        hitpoints=b.hitpoints;
        type=b.getType();

    }
    public int getType(){
        return type;
    }
    public float getX(){
        return X;
    }
    public float getY(){
        return Y;
    }
}
