package com.Royal.game.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class StoneBlock extends Block {
    public StoneBlock(float x, float y, World world) {
        super("stone_block.png", 80, 80, x, y,world,100);
    }
}
