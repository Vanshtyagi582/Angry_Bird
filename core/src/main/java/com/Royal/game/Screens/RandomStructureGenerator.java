package com.Royal.game.Screens;

import com.Royal.game.Elements.*;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class RandomStructureGenerator {
    private static final float STRUCTURE_WIDTH = 700f;
    private static final float STRUCTURE_HEIGHT = 500f;
    private static final float GROUND_Y = 50f;  // Height above the ground

    private World world;
    private Array<Block> blocks;
    private Array<Pig> pigs;

    public RandomStructureGenerator(World world) {
        this.world = world;
        this.blocks = new Array<>();
        this.pigs = new Array<>();
    }

    public void generateRandomStructure() {
        // Clear existing blocks and pigs
        blocks.clear();
        pigs.clear();

        // Randomly generate structures
        generateBlocks();
        generatePigs();
    }

    private void generateBlocks() {
        float x = 580f; // Starting X position for the structure
        float y = GROUND_Y; // Starting Y position, above the ground

        int blockCount = MathUtils.random(5, 15); // Random number of blocks in the structure
        for (int i = 0; i < blockCount; i++) {
            float blockType = MathUtils.random(0, 2);  // Choose random block type: 0 = Stone, 1 = Wood, 2 = Glass
            String material;
            Block block;

            if (blockType == 0) {
                material = "stone";
                block = new StoneBlock(x, y, world);  // Stone block
            } else if (blockType == 1) {
                material = "wood";
                block = new WoodBlock(x, y, world);   // Wood block
            } else {
                material = "glass";
                block = new GlassBlock(x, y, world);  // Glass block
            }

            block.setMaterial(material); // Set the material properties
            blocks.add(block);

            // Randomly vary the position of the next block within a defined range
            x += MathUtils.random(50, 150);  // Horizontal gap between blocks
            y += MathUtils.random(30, 50);   // Vertical variation in placement
        }
    }

    private void generatePigs() {
        // Randomly place pigs within the structure bounds
        int pigCount = MathUtils.random(3, 6);  // Random number of pigs

        for (int i = 0; i < pigCount; i++) {
            float pigX = MathUtils.random(600, 1100);  // Random X position within the structure width
            float pigY = MathUtils.random(100, 300);  // Random Y position above the ground
            Pig pig;

            // Randomly select a pig type
            if (MathUtils.randomBoolean()) {
                pig = new SmallPig(pigX, pigY, world);  // Small pig
            } else {
                pig = new LargePig(pigX, pigY, world);  // Large pig
            }

            pigs.add(pig);
        }
    }

    public Array<Block> getBlocks() {
        return blocks;
    }

    public Array<Pig> getPigs() {
        return pigs;
    }
}

