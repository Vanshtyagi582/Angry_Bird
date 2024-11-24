package com.Royal.game.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class BlueBird extends Bird {
    public BlueBird( float x, float y, World world, float density) {
        super("blue_bird.png",50, x, y, world, density,30);
    }
}
