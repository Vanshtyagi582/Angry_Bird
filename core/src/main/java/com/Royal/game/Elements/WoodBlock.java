package com.Royal.game.Elements;

import com.badlogic.gdx.physics.box2d.World;

// Subclasses for block types
public class WoodBlock extends Block {
    public WoodBlock(float x, float y, World world) {
        super("wood.png", 40, 40, x, y,world,200);
    }
}
