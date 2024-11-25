package com.Royal.game.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class GlassBlock extends Block {
    public GlassBlock(float x, float y, World world) {
        super("tnt.png", 40, 40, x, y,world,120);
    }
}

