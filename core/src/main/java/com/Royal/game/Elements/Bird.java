package com.Royal.game.Elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;

public abstract class Bird implements Disposable {
    protected Sprite sprite;
    protected Body body;
    private boolean launched;
    public int hitpoints;
    public float scale;

    public Bird(String texturePath, float size, float x, float y, World world, float weight,int hitpoints, float scale) {
        // Initialize sprite
        sprite = new Sprite(new Texture(texturePath));
        sprite.setSize(size, size);
        sprite.setPosition(x, y);

        // Create physics body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / 100f, y / 100f);

        CircleShape shape = new CircleShape();
        shape.setRadius(size / 2 / 100f); // Box2D works with meters, hence division by 100

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = weight;
        fixtureDef.restitution = 0.1f;
        fixtureDef.friction = 0.8f;
        this.launched = false;
        this.hitpoints = hitpoints;
        this.scale = scale;


        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        body.setAngularDamping(1f);
        (body).setUserData(this);

        shape.dispose();
    }

    public Sprite getSprite() {
        sprite.setPosition(
            body.getPosition().x * 100 - sprite.getWidth() / 2,
            body.getPosition().y * 100 - sprite.getHeight() / 2
        );
        return sprite;
    }

    public Body getBody() {
        return body;
    }
    public boolean isLaunched() {
        return launched;
    }

    public void setLaunched(boolean launched) {
        this.launched = launched;
    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }


}


