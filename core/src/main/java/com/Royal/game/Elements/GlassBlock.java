package com.Royal.game.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class GlassBlock extends Block {
    public GlassBlock(float x, float y, World world) {
        super("tnt.png", 80, 80, x, y,world,40);
    }
}
