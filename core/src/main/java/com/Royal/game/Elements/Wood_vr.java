package com.Royal.game.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class Wood_vr extends Block {
    public Wood_vr(float x, float y, World world) {
        super("wood_vr.png", 80, 80, x, y,world,150);
    }
}
