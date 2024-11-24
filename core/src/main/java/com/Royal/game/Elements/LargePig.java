package com.Royal.game.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class LargePig extends Pig {
    public LargePig(float x, float y, World world) {
        super("KING.png", 100, x, y,world,80);
    }
}
