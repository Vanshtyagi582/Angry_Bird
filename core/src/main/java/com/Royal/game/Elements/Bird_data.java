package com.Royal.game.Elements;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class Bird_data implements Serializable {
    public float hitpoints;
    public int type;
    float X;
    float Y;
    public Vector2 velocity;
    public Bird_data(Bird b){
        hitpoints=b.hitpoints;
        type=b.getType();
        X=(float) b.getBody().getPosition().x*100f;
        Y=(float) b.getBody().getPosition().y*100f;
        velocity=b.getBody().getLinearVelocity();
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
