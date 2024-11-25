package com.Royal.game.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class Glass_hz extends Block {
    public Glass_hz(float x, float y, World world) {
        super("glass_hz.png", 120, 20, x, y,world,120);
    }
}
