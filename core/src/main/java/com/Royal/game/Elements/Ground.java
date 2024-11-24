package com.Royal.game.Elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

public class Ground {
    private Texture texture;
    private Rectangle bounds;
    private Body body;

    public Ground(String texturePath, float x, float y, float width, float height, World world) {
        // Load the texture
        texture = new Texture(texturePath);
        bounds = new Rectangle(x, y, width, height);

        // Create a static body for the ground in the Box2D world
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((x + width / 2) / 100f, (y + height / 2) / 100f);

        body = world.createBody(bodyDef);

        // Define the shape of the ground
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(width / 200f, height / 200f);

        // Define fixture properties
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;
        fixtureDef.friction = 4f;

        // Attach the shape to the body
        body.createFixture(fixtureDef);

        // Dispose the shape after use
        groundShape.dispose();
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        texture.dispose();
    }

    public Body getBody() {
        return body;
    }
}
