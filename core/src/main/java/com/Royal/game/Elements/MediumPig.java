package com.Royal.game.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class MediumPig extends Pig {
    public MediumPig(float x, float y, World world) {
        super("mid_pig.png", 80, x, y,world,50);
    }
}
