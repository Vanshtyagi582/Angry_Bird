package com.Royal.game.Screens;
import com.Royal.game.AngryBird;
import com.Royal.game.Elements.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.Array;

public class LevelScreen implements Screen {
    private final AngryBird game;
    private OrthographicCamera camera;
    private SpriteBatch batch;


    private Texture background, pauseButton, winButton, lossButton, pausePopup, winPopup, lossPopup, catapult, level1;
    private boolean showPausePopup = false, showWinPopup = false, showLossPopup = false;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private Array<Bird> birds;
    private Array<Pig> pigs;
    private Array<Block> blocks;
    public static Array<Body> bodiesToDestroy = new Array<>();

    private Ground ground;
    private Body groundBody;

    private Bird selectedBird = null;
    private MouseJoint catapultJoint;
    private Vector2 catapultAnchor = new Vector2(1.9f, 1.5f);
    private Texture dot; // Trajectory dot texture
    private Array<Vector2> trajectoryPoints = new Array<>();
    private boolean birdLaunched=false;

    public LevelScreen(AngryBird game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 800);
        batch = AngryBird.batch;

        world = new World(new Vector2(0, -9.8f), true); // Gravity in Y-axis
        debugRenderer = new Box2DDebugRenderer();

        loadTextures();
        initializeGameElements();
        createGroundBody();
        createScreenBoundaries();
    }
    private void updateBirdState() {
        Bird birdToRemove = null;

        for (Bird bird : birds) {
            if (bird.isLaunched()) {
                // Check if the bird's velocity is low (bird has stopped moving significantly)
                if (bird.getBody().getLinearVelocity().len() < 0.1f) {
                    birdLaunched = false;
                    birdToRemove = bird; // Mark bird for removal when it stops
                }

                // Check if the bird has moved off the right side of the screen
                if (bird.getBody().getPosition().x > camera.viewportWidth / 100f) {
                    birdLaunched = false;
                    birdToRemove = bird; // Mark bird for removal when it leaves the screen
                }
            }
        }

        // Remove bird that has either stopped or moved off-screen
        if (birdToRemove != null) {
            birds.removeValue(birdToRemove, true);
            birdToRemove.dispose();
        }

        // If no bird is currently launched and there are birds remaining, make the next bird accessible
        if (!birdLaunched && birds.size > 0) {
            Bird nextBird = birds.first(); // Get the next bird in the queue
            nextBird.setLaunched(false);  // Reset its launched state
        }
    }


    private void loadTextures() {
        background = new Texture("LEVEL-2.png");
        pauseButton = new Texture("pause.png");
        winButton = new Texture("win.png");
        lossButton = new Texture("loss.png");
        pausePopup = new Texture("pausepopup.png");
        winPopup = new Texture("win_.png");
        lossPopup = new Texture("loss_.png");
        catapult = new Texture("catapult.png");
        level1 = new Texture("Level1.png");
        dot = new Texture("dot.png");
    }

    private void initializeGameElements() {
        ground = new Ground("ground2.png", 0, 0, 1280, 50, world); // Ground texture

        birds = new Array<>();
        birds.add(new RedBird(32, 100, world, 1000f));
        birds.add(new BlueBird(83, 100, world, 1000f));
        birds.add(new YellowBird(134, 100, world, 1000f));

        blocks = new Array<>();
        blocks.add(new WoodBlock(880, 50, world)); // Base block
        blocks.add(new WoodBlock(880, 130, world)); // Middle block
        blocks.add(new WoodBlock(960, 50, world)); // Base block

        pigs = new Array<>();
        pigs.add(new SmallPig(900,240,world));
        // Pig on base block
    }



    private void createScreenBoundaries() {
        BodyDef boundaryDef = new BodyDef();
        boundaryDef.type = BodyDef.BodyType.StaticBody;

        PolygonShape wallShape = new PolygonShape();

        // Left wall
        wallShape.setAsBox(1 / 100f, camera.viewportHeight / 2 / 100f);
        boundaryDef.position.set(0, camera.viewportHeight / 2 / 100f);
        Body leftWall = world.createBody(boundaryDef);
        leftWall.createFixture(wallShape, 0);

//        // Right wall
//        boundaryDef.position.set(camera.viewportWidth / 100f, camera.viewportHeight / 2 / 100f);
//        Body rightWall = world.createBody(boundaryDef);
//        rightWall.createFixture(wallShape, 0);

//        // Top wall
//        wallShape.setAsBox(camera.viewportWidth / 2 / 100f, 1 / 100f);
//        boundaryDef.position.set(camera.viewportWidth / 2 / 100f, camera.viewportHeight / 100f);
//        Body topWall = world.createBody(boundaryDef);
//        topWall.createFixture(wallShape, 0);

        wallShape.dispose();
    }
    private void setupContactListener() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                // Check if the contact involves a pig or block and a bird
                Object userDataA = fixtureA.getBody().getUserData();
                Object userDataB = fixtureB.getBody().getUserData();

                if (userDataA instanceof Pig && userDataB instanceof Bird) {
                    ((Pig) userDataA).takeDamage(((Bird) userDataB).hitpoints); // Apply 10 damage to the pig
                } else if (userDataB instanceof Pig && userDataA instanceof Bird) {
                    ((Pig) userDataB).takeDamage(((Bird) userDataA).hitpoints); // Apply 10 damage to the pig
                }

                if (userDataA instanceof Block && userDataB instanceof Bird) {
                    ((Block) userDataA).takeDamage(((Bird) userDataB).hitpoints);; // Apply 5 damage to the block
                } else if (userDataB instanceof Block && userDataA instanceof Bird) {
                    ((Block) userDataB).takeDamage(((Bird) userDataA).hitpoints); // Apply 5 damage to the block
                }
            }

            @Override
            public void endContact(Contact contact) {}

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });
    }


    private void updatePigState(float delta) {
        Array<Pig> pigsToRemove = new Array<>();

        for (Pig pig : pigs) {
            pig.updateTimeOnGround(delta);

            // Remove the pig if it has been on the ground for more than 3 seconds
            if (pig.getTimeOnGround() >= 0.5f || pig.health <= 0) {
                pigsToRemove.add(pig);
            }
        }

        // Remove pigs from the world and the array
        for (Pig pig : pigsToRemove) {
            world.destroyBody(pig.getBody());

            pigs.removeValue(pig, true);
            pig.dispose();
        }
    }
    private void destroyBodies() {
        for (Body body : bodiesToDestroy) {
            world.destroyBody(body);
            for (Pig i :pigs ){
                if (i.getBody()==body){
                    pigs.removeValue(i, true);
                    i.dispose();

                }
            }
            for (Block i :blocks ){
                if (i.getBody()==body){
                    blocks.removeValue(i, true);
                    i.dispose();
                }
            }

        }
        bodiesToDestroy.clear();
        // Clear the list after destroying
    }



    private void renderTrajectory() {
        if (selectedBird == null || catapultJoint == null) return; // Only render if bird is being pulled back

        trajectoryPoints.clear();
        Body birdBody = selectedBird.getBody();
        Vector2 birdPosition = birdBody.getPosition();

        // Corrected: Calculate velocity in the release direction
        Vector2 velocity = catapultAnchor.cpy().sub(birdPosition).scl(13f); // Forward velocity after release

        float timeStep = 0.1f; // Simulation step size
        Vector2 gravity = world.getGravity(); // Gravity vector
        Vector2 point = new Vector2(birdPosition); // Start at the bird's current position

        // Simulate the trajectory
        for (int i = 0; i < 50; i++) { // Generate up to 50 points
            point.x += velocity.x * timeStep; // Update X position
            point.y += velocity.y * timeStep; // Update Y position
            velocity.add(gravity.x * timeStep, gravity.y * timeStep); // Apply gravity to velocity

            if (point.y < ground.getBounds().height / 100f) break; // Stop at ground level
            trajectoryPoints.add(new Vector2(point)); // Add the point to the trajectory
        }

        // Draw each trajectory point as a small dot
        for (Vector2 p : trajectoryPoints) {
            batch.draw(dot, p.x * 100 - 2, p.y * 100 - 2, 4, 4); // Draw each point as a 4x4 pixel dot
        }
    }







    private void createGroundBody() {
        BodyDef groundDef = new BodyDef();
        groundDef.position.set(ground.getBounds().x / 100f, ground.getBounds().y / 100f);
        groundDef.type = BodyDef.BodyType.StaticBody;

        groundBody = world.createBody(groundDef);

        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(ground.getBounds().width / 200f, ground.getBounds().height / 200f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;
        fixtureDef.friction = 0.9f;
        groundBody.createFixture(fixtureDef);

        groundShape.dispose();
    }

    @Override
    public void render(float delta) {
        camera.update();
        updateBirdState();

        updatePigState(delta);

        world.step(1 / 60f, 6, 2);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        renderTrajectory();

        batch.draw(background, 0, 0, 1280, 800);
        batch.draw(pauseButton, 20, 700, 80, 80);
        batch.draw(winButton, 1150, 20, 100, 100);
        batch.draw(lossButton, 1150, 120, 100, 100);
        batch.draw(catapult, 120, 32, 100, 130);
        batch.draw(level1, 640 - level1.getWidth() / 2, 700);

        ground.render(batch);
        renderTrajectory();

        for (Bird bird : birds) bird.getSprite().draw(batch);
        for (Pig pig : pigs) pig.getSprite().draw(batch);
        for (Block block : blocks) block.getSprite().draw(batch);

        float dotX = catapultAnchor.x * 100 - 2; // Convert to screen coordinates
        float dotY = catapultAnchor.y * 100 - 2;
        batch.draw(dot, dotX, dotY, 5, 5);

        if (showPausePopup) batch.draw(pausePopup, 340, 200, 600, 400);
        if (showWinPopup) batch.draw(winPopup, 640 - winPopup.getWidth() / 2, 0, winPopup.getWidth(), 780);
        if (showLossPopup) batch.draw(lossPopup, 640 - lossPopup.getWidth() / 2, 0, lossPopup.getWidth(), 780);
        destroyBodies();

        batch.end();

        debugRenderer.render(world, camera.combined);

        handleInput();
    }


    private void handleInput() {
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (showPausePopup || showWinPopup || showLossPopup) return;

            if (selectedBird == null && !birdLaunched) {
                for (Bird bird : birds) {
                    if (!bird.isLaunched() && bird.getSprite().getBoundingRectangle().contains(touchX, touchY)) {
                        selectedBird = bird;
                        break;
                    }
                }

                if (selectedBird != null) {
                    createCatapultJoint(selectedBird);
                }
            }
        }

        if (Gdx.input.isTouched() && selectedBird != null) {
            float touchX = Math.max(0.5f, Math.min(Gdx.input.getX() / 100f, camera.viewportWidth / 100f - 0.5f));
            float touchY = Math.max(ground.getBounds().height / 100f + 0.5f, Math.min((Gdx.graphics.getHeight() - Gdx.input.getY()) / 100f, camera.viewportHeight / 100f - 0.5f));

            Vector2 dragPosition = new Vector2(touchX, touchY);
            if (dragPosition.dst(catapultAnchor) > 1.0f) { // Restrict movement within catapult radius
                dragPosition.sub(catapultAnchor).nor().scl(1.0f).add(catapultAnchor);
            }

            selectedBird.getBody().setTransform(dragPosition, 0);
        }

        if (!Gdx.input.isTouched() && selectedBird != null) {
            if (catapultJoint != null) {
                world.destroyJoint(catapultJoint);
                catapultJoint = null;
                trajectoryPoints.clear();

                // Apply velocity to the bird in the direction of the pull
                Vector2 launchVelocity = catapultAnchor.cpy().sub(selectedBird.getBody().getPosition()).scl(13f);
                selectedBird.getBody().setLinearVelocity(launchVelocity);

                selectedBird.setLaunched(true); // Mark this bird as launched
                birdLaunched = true;           // Prevent immediate reuse
            }
            selectedBird = null;
        }
    }







    private void createCatapultJoint(Bird bird) {
        if (catapultJoint != null) return;

        MouseJointDef jointDef = new MouseJointDef();
        jointDef.bodyA = groundBody;
        jointDef.bodyB = bird.getBody();
        jointDef.target.set(catapultAnchor);
        jointDef.maxForce = 1000.0f * bird.getBody().getMass();

        catapultJoint = (MouseJoint) world.createJoint(jointDef);
    }


    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        pauseButton.dispose();
        winButton.dispose();
        lossButton.dispose();
        pausePopup.dispose();
        winPopup.dispose();
        lossPopup.dispose();
        dot.dispose();

        for (Bird bird : birds) bird.dispose();
        for (Pig pig : pigs) pig.dispose();
        for (Block block : blocks) block.dispose();

        world.dispose();
        debugRenderer.dispose();
    }

    @Override
    public void show() {
        setupContactListener();

    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
