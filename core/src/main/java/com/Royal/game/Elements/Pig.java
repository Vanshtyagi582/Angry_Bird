package com.Royal.game.Elements;

import com.Royal.game.Screens.LevelScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;

public class Pig implements Disposable {
    protected Sprite sprite;
    protected Body body;
    private float timeOnGround = 0;
    private boolean isOnGround = false;
    public int health;
    private World world;

    public Pig(String texturePath, float size, float x, float y, World world, int health) {
        // Initialize sprite
        sprite = new Sprite(new Texture(texturePath));
        sprite.setSize(size, size);
        sprite.setPosition(x, y);

        // Create physics body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Pigs should react to physics
        bodyDef.position.set(x / 100f, y / 100f);

        CircleShape shape = new CircleShape();
        shape.setRadius(size / 2 / 100f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 500f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.friction = 0.8f;

        // Set health and world
        this.health = health;
        this.world = world;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        body.setAngularDamping(1f);

        // Set userData for collision detection
        body.setUserData(this);  // Add this line to store a reference to the Pig object

        shape.dispose();
    }

    public Sprite getSprite() {
        sprite.setPosition(
            body.getPosition().x * 100 - sprite.getWidth() / 2,
            body.getPosition().y * 100 - sprite.getHeight() / 2
        );
        return sprite;
    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }

    public void setOnGround(boolean isOnGround) {
        this.isOnGround = isOnGround;
    }

    public boolean isOnGround() {
        return isOnGround;
    }



    public float getTimeOnGround() {
        return timeOnGround;
    }



    public void takeDamage(float damage) {
        this.health -= damage;
        if (this.health <= 0) {
            markForDestruction();  // Mark the block for destruction
        }
    }

    private void markForDestruction() {
        // Here, we flag the block for destruction
        LevelScreen.bodiesToDestroy.add(this.body);
    }

    public void applyFallDamage() {
        if (body.getLinearVelocity().y < -5) {
            takeDamage((int)(-body.getLinearVelocity().y / 5));
        }
    }

    public Body getBody() {
        return body;
    }
}
