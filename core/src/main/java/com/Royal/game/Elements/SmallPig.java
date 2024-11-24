package com.Royal.game.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class SmallPig extends Pig {
    public SmallPig( float x, float y, World world) {
        super("small_pig.png", 60, x, y, world,20);
    }
}

