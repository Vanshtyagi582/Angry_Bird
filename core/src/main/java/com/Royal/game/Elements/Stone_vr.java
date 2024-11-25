package com.Royal.game.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class Stone_vr extends Block {
    public Stone_vr(float x, float y, World world) {
        super("stone_vr.png", 20, 120, x, y,world,200);
    }
}
