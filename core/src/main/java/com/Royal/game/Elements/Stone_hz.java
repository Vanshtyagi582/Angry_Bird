package com.Royal.game.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class Stone_hz extends Block {
    public Stone_hz(float x, float y, World world) {
        super("stone_hz.png", 120, 20, x, y,world,200);
    }
}
