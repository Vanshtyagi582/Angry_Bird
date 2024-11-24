package com.Royal.game.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class YellowBird extends Bird {
    public YellowBird(float x, float y, World world, float density) {
        super("yellow_bird.png",50, x, y, world, density,70);
    }
}
