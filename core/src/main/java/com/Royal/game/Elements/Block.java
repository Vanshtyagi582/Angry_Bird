package com.Royal.game.Elements;

import com.Royal.game.Screens.LevelScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;

import java.util.Objects;

public abstract class Block implements Disposable {
    protected Sprite sprite;
    protected Body body;
    public int health;
    private World world;
public String textu;
    public Block(String texturePath, float width, float height, float x, float y, World world, int health) {
        // Initialize sprite
        sprite = new Sprite(new Texture(texturePath));
        sprite.setSize(width, height);
        sprite.setPosition(x, y);
textu=texturePath;
        // Create physics body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Blocks are dynamic, meaning they can move
        bodyDef.position.set((x + width / 2) / 100f, (y + height / 2) / 100f);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / 100f, height / 2 / 100f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 600f;
        fixtureDef.restitution = 0f;
        fixtureDef.friction = 2f;

        // Set health and world
        this.health = health;
        this.world = world;

        // Create the body in the world
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(this); // Set user data to reference this block object

        shape.dispose();
    }

    public Sprite getSprite() {
        sprite.setPosition(
            body.getPosition().x * 100 - sprite.getWidth() / 2,
            body.getPosition().y * 100 - sprite.getHeight() / 2
        );
        return sprite;
    }

    public void takeDamage(float damage) {
        this.health -= damage;
        if (this.health <= 0) {
            markForDestruction();  // Mark the block for destruction
        }
    }

    private void markForDestruction() {
        // Here, we flag the block for destruction
        LevelScreen.bodiesToDestroy.add(this.body);  // Assuming world manages bodies to destroy list
    }
    public int getType(){
        if(Objects.equals(textu, "stone_block.png")){
            return 1;
        }
        if(Objects.equals(textu, "stone_vr.png")){
            return 2;
        }
        if(Objects.equals(textu, "wood_hz.png")){
            return 3;
        }
        if(Objects.equals(textu, "wood_vr.png")){
            return 4;
        }
        if(Objects.equals(textu, "wood.png")){
            return 5;
        }
        return 6;
    }
    public void setMaterial(String material) {
        // Define material properties
        float density = 0f;
        float friction = 0f;
        float restitution = 0f;
        String texturePath = "";

        // Set material properties based on material type
        switch (material.toLowerCase()) {
            case "stone":
                density = 600f; // Stone is dense
                friction = 0.7f; // Higher friction for stone
                restitution = 0.3f; // Stone is not very bouncy
                texturePath = "stone_block.png"; // Path to stone texture
                break;
            case "wood":
                density = 200f; // Wood is less dense
                friction = 0.6f; // Medium friction for wood
                restitution = 0.4f; // Wood is slightly bouncy
                texturePath = "wood.png"; // Path to wood texture
                break;
            case "glass":
                density = 150f; // Glass is lighter
                friction = 0.3f; // Low friction for glass
                restitution = 0.6f; // Glass is bouncy
                texturePath = "glass_block.png"; // Path to glass texture
                break;
            default:
                throw new IllegalArgumentException("Unknown material: " + material);
        }

        // Update texture
        this.sprite.setTexture(new Texture(texturePath));

        // Update physics properties (you need to destroy the old body first to apply new properties)
        if (this.body != null) {
            // First remove the old body from the world
            world.destroyBody(this.body);

            // Create new physics body with updated properties
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set((sprite.getX() + sprite.getWidth() / 2) / 100f,
                (sprite.getY() + sprite.getHeight() / 2) / 100f);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(sprite.getWidth() / 2 / 100f, sprite.getHeight() / 2 / 100f);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = density;
            fixtureDef.friction = friction;
            fixtureDef.restitution = restitution;

            // Create new body in the world with updated material properties
            this.body = world.createBody(bodyDef);
            this.body.createFixture(fixtureDef);
            this.body.setUserData(this);

            shape.dispose();  // Clean up shape after use
        }
    }


    public void applyFallDamage() {
        if (body.getLinearVelocity().y < -5) { // Threshold for fall damage
            takeDamage((int)(-body.getLinearVelocity().y / 5));
        }
    }


    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }

    public Body getBody() {
        return body;
    }
}
