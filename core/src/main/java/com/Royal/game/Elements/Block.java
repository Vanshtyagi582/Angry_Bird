package com.Royal.game.Elements;

import com.Royal.game.Screens.LevelScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;

public abstract class Block implements Disposable {
    protected Sprite sprite;
    protected Body body;
    public int health;
    private World world;

    public Block(String texturePath, float width, float height, float x, float y, World world, int health) {
        // Initialize sprite
        sprite = new Sprite(new Texture(texturePath));
        sprite.setSize(width, height);
        sprite.setPosition(x, y);

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
