package com.Royal.game.Elements;

import com.badlogic.gdx.physics.box2d.World;

// Subclasses for specific bird types
public class RedBird extends Bird {
    public RedBird(float x, float y, World world, float density) {
        super("red_bird.png",50, x, y, world, density,50);
    }
}
