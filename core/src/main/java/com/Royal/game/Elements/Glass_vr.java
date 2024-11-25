package com.Royal.game.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class Glass_vr extends Block {
    public Glass_vr(float x, float y, World world) {
        super("glass_vr.png", 20, 120, x, y,world,120);
    }
}
