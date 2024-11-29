package com.Royal.game.Screens;
import com.Royal.game.AngryBird;
import com.Royal.game.Elements.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.Array;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Level2Screen implements Screen {
    private final AngryBird game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    public boolean n_game;
    public Data data;

    private Texture background, pauseButton, winButton, lossButton, pausePopup, winPopup, lossPopup, catapult, level1;
    private boolean showPausePopup = false, showWinPopup = false, showLossPopup = false;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private Array<Bird> birds= new Array<>();
    private Array<Pig> pigs=new Array<>();
    private Array<Block> blocks=new Array<>();
    public static Array<Body> bodiesToDestroy = new Array<>();

    private Ground ground;
    private Body groundBody;

    private Bird selectedBird = null;
    private MouseJoint catapultJoint;
    private Vector2 catapultAnchor = new Vector2(1.9f, 1.5f);
    private Texture dot; // Trajectory dot texture
    private Array<Vector2> trajectoryPoints = new Array<>();
    private boolean birdLaunched=false;

    private float popupDelayTimer = 0f;
    private boolean isPopupTriggered = false;
    private boolean isGameWon = false;
    private boolean isGameLost = false;
    private boolean ispaused=false;


    public Level2Screen(AngryBird game,boolean ng,Data d) {
        this.game = game;
        n_game=ng;
        data=d;
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
        background = new Texture("LEVEL-3.jpg");
        pauseButton = new Texture("pause.png");

        pausePopup = new Texture("pause1.png");
        winPopup = new Texture("win_.png");
        lossPopup = new Texture("loss_.png");
        catapult = new Texture("catapult.png");
        level1 = new Texture("Level2.png");
        dot = new Texture("dot.png");
    }

    private void initializeGameElements() {
        ground = new Ground("ground2.png", 0, 0, 1280, 50, world); // Ground texture
        if(!n_game){
            for(Bird_data i: data.birds_data){
                if(i.getType()==1){
                    birds.add(new RedBird(i.getX(),i.getY(),world,1000f));
                }
                else if(i.getType()==2){
                    birds.add(new BlueBird(i.getX(),i.getY(),world,1000f));
                }
                else{
                    birds.add(new YellowBird(i.getX(),i.getY(),world,1000f));
                }
            }
            for(Block_data i: data.blockData){
                if(i.getType()==1){
                    blocks.add(new StoneBlock(i.getX(),i.getY(),world));
                }
                else if(i.getType()==2){
                    blocks.add(new Stone_vr(i.getX(),i.getY(),world));
                }
                else if(i.getType()==3){
                    blocks.add(new Wood_hz(i.getX(),i.getY(),world));
                }
                else if(i.getType()==4){
                    blocks.add(new Wood_vr(i.getX(),i.getY(),world));
                }
                else if(i.getType()==5){
                    blocks.add(new WoodBlock(i.getX(),i.getY(),world));
                }
                else if(i.getType()==6){
                    blocks.add(new Stone_hz(i.getX(),i.getY(),world));
                }
                else if(i.getType()==7){
                    blocks.add(new GlassBlock(i.getX(),i.getY(),world));
                }
                else if(i.getType()==8){
                    blocks.add(new Glass_hz(i.getX(),i.getY(),world));
                }
                else if(i.getType()==9){
                    blocks.add(new Glass_vr(i.getX(),i.getY(),world));
                }

            }
            for(Pig_data i: data.pigs_data){
                if(i.getType()==1){
                    pigs.add(new SmallPig(i.getX(),i.getY(),world));
                }
                if(i.getType()==2){
                    pigs.add(new MediumPig(i.getX(),i.getY(),world));
                }
                if(i.getType()==3){
                    pigs.add(new LargePig(i.getX(),i.getY(),world));
                }

            }
        }
        else{
            birds = new Array<>();
            birds.add(new RedBird(32, 100, world, 1000f));
            birds.add(new BlueBird(83, 100, world, 1000f));
            birds.add(new YellowBird(134, 100, world, 1000f));

            blocks = new Array<>();
            blocks.add(new StoneBlock(700, 50, world));
            blocks.add(new StoneBlock(780, 50, world));
            blocks.add(new Stone_hz(700, 90, world));

            blocks.add(new StoneBlock(900, 50, world));
            blocks.add(new StoneBlock(980, 50, world));
            blocks.add(new Stone_hz(900, 90, world));

            blocks.add(new StoneBlock(1100, 50, world));
            blocks.add(new StoneBlock(1180, 50, world));
            blocks.add(new Stone_hz(1100, 90, world));

            blocks.add(new Wood_vr(800, 110, world));
            blocks.add(new Wood_vr(900, 110, world));
            blocks.add(new Wood_vr(1000, 110, world));
            blocks.add(new Wood_vr(1100, 110, world));

            blocks.add(new Stone_hz(800, 230, world));
            blocks.add(new Stone_hz(1000, 230, world));

            blocks.add(new Glass_hz(900,250,world));
            blocks.add(new StoneBlock(940, 270, world));
            blocks.add(new StoneBlock(940, 310, world));
            blocks.add(new Glass_hz(900,350,world));



            pigs = new Array<>();
            pigs.add(new SmallPig(720,160,world));
            pigs.add(new SmallPig(1040,160,world));
            pigs.add(new SmallPig(1140,160,world));
            pigs.add(new SmallPig(840,160,world));
            pigs.add(new LargePig(940,400,world));
        }

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
                    ((Pig) userDataA).takeDamage(((Bird) userDataB).hitpoints);
                } else if (userDataB instanceof Pig && userDataA instanceof Bird) {
                    ((Pig) userDataB).takeDamage(((Bird) userDataA).hitpoints);
                }

                if (userDataA instanceof Block && userDataB instanceof Bird) {
                    ((Block) userDataA).takeDamage(((Bird) userDataB).hitpoints);
                } else if (userDataB instanceof Block && userDataA instanceof Bird) {
                    ((Block) userDataB).takeDamage(((Bird) userDataA).hitpoints);
                }

                if (userDataA instanceof Block && userDataB instanceof Pig) {
                    Block block = (Block) userDataA;
                    Pig pig = (Pig) userDataB;

                    // Check the block's velocity to prevent damage from slow rolling
                    if (block.getBody().getLinearVelocity().len() > 0.5f) { // Adjust threshold as needed
                        block.takeDamage(20);
                        pig.takeDamage(20);
                    }
                } else if (userDataB instanceof Block && userDataA instanceof Pig) {
                    Pig pig = (Pig) userDataA;
                    Block block = (Block) userDataB;

                    // Check the block's velocity to prevent damage from slow rolling
                    if (block.getBody().getLinearVelocity().len() > 0.5f) { // Adjust threshold as needed
                        block.takeDamage(20);
                        pig.takeDamage(20);
                    }
                }


                if (userDataA instanceof Pig && userDataB instanceof Ground) {
                    ((Pig) userDataA).setOnGround(true);
                } else if (userDataB instanceof Pig && userDataA instanceof Ground) {
                    ((Pig) userDataB).setOnGround(true);
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



            if (pig.isOnGround() || pig.health <= 0) {
                pigsToRemove.add(pig);
            }
            if (pig.getBody().getPosition().x > camera.viewportWidth / 100f) {

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

    }



    private void renderTrajectory() {
        if (selectedBird == null || catapultJoint == null) return; // Only render if bird is being pulled back

        trajectoryPoints.clear();
        Body birdBody = selectedBird.getBody();
        Vector2 birdPosition = birdBody.getPosition();

        // Corrected: Calculate velocity in the release direction
        Vector2 velocity = catapultAnchor.cpy().sub(birdPosition).scl(selectedBird.scale); // Forward velocity after release

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
        if (!renderPopups(delta)) { // Only render the game if no popups are active
            batch.draw(background, 0, 0, 1280, 800);
            batch.draw(pauseButton, 20, 700, 80, 80);
            batch.draw(catapult, 120, 32, 100, 130);
            batch.draw(level1, 640 - level1.getWidth() / 2, 700);

            ground.render(batch);

            renderTrajectory(); // Draw trajectory only if no popups are active

            for (Bird bird : birds) {
                Sprite sprite = bird.getSprite();
                Body body = bird.getBody();

                sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
                sprite.setPosition(body.getPosition().x * 100 - sprite.getWidth() / 2,
                    body.getPosition().y * 100 - sprite.getHeight() / 2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }

            for (Pig pig : pigs) {
                Sprite sprite = pig.getSprite();
                Body body = pig.getBody();

                sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
                sprite.setPosition(body.getPosition().x * 100 - sprite.getWidth() / 2,
                    body.getPosition().y * 100 - sprite.getHeight() / 2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }

            for (Block block : blocks) {
                Sprite sprite = block.getSprite();
                Body body = block.getBody();

                sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
                sprite.setPosition(body.getPosition().x * 100 - sprite.getWidth() / 2,
                    body.getPosition().y * 100 - sprite.getHeight() / 2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }

            float dotX = catapultAnchor.x * 100 - 2;
            float dotY = catapultAnchor.y * 100 - 2;
            batch.draw(dot, dotX, dotY, 5, 5);
        }

        destroyBodies();
        batch.end();

        debugRenderer.render(world, camera.combined);
        handleInput();
    }




    private void handleInput() {
        if (isGameWon || isGameLost || ispaused){
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (isGameWon){
                if (isButtonClicked(touchX, touchY, 453, 138, 90, 90)) {
                    game.setScreen(new HomeScreen(game));
                }
                if (isButtonClicked(touchX, touchY, 592, 138, 90, 90)) {
                    game.setScreen(new Level2Screen(game,true,new Data()));

                }
                if (isButtonClicked(touchX, touchY, 734, 138, 90, 90)) {
                    game.setScreen(new Level3Screen(game,true,new Data()));

                }
                showWinPopup = false;

            }
            else if (isGameLost) {

                if (isButtonClicked(touchX, touchY, 505, 115, 100, 100)) {
                    game.setScreen(new HomeScreen(game));

                }
                if (isButtonClicked(touchX, touchY,  669, 115, 100, 100)) {
                    game.setScreen(new Level2Screen(game,true,new Data()));

                }

            }
            else if (ispaused){
                if (isButtonClicked(touchX, touchY, 30, 232, 92, 92)) {
                    // save the game
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("lvl2.dat"))) {
                        ArrayList<Bird_data> bd=new ArrayList<>();
                        ArrayList<Block_data> blc_d=new ArrayList<>();
                        ArrayList<Pig_data> pd=new ArrayList<>();
                        for(Bird i: birds){
                            bd.add(new Bird_data(i));
                        }
                        for(Block i: blocks){
                            blc_d.add(new Block_data(i));
                        }
                        for(Pig i: pigs){
                            pd.add(new Pig_data(i));
                        }
                        data.pigs_data=pd;
                        data.birds_data=bd;
                        data.blockData=blc_d;
                        oos.writeObject(data);
                        System.out.println("Data saved successfully to " + "lvl2.dat");
                    } catch (IOException e) {
                        System.err.println("Error saving data: " + e.getMessage());
                    }
                    game.setScreen(new HomeScreen(game));
                }
                if (isButtonClicked(touchX, touchY, 30, 455, 92, 92)) {
                    game.setScreen(new Level2Screen(game,true,new Data()));
                }
                if (isButtonClicked(touchX, touchY, 100, 358, 60, 60)) {
                    ispaused=false;
                    isPopupTriggered = false;
                }
            }
        }

        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();


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
            if (dragPosition.dst(catapultAnchor) > 1.0f) {
                dragPosition.sub(catapultAnchor).nor().scl(1.0f).add(catapultAnchor);
            }

            selectedBird.getBody().setTransform(dragPosition, 0);
        }

        if (!Gdx.input.isTouched() && selectedBird != null) {
            if (catapultJoint != null) {
                world.destroyJoint(catapultJoint);
                catapultJoint = null;
                trajectoryPoints.clear();

                Vector2 launchVelocity = catapultAnchor.cpy().sub(selectedBird.getBody().getPosition()).scl(selectedBird.scale);
                selectedBird.getBody().setLinearVelocity(launchVelocity);

                selectedBird.setLaunched(true);
                birdLaunched = true;
            }
            selectedBird = null;
        }
    }
    private boolean isButtonClicked(float touchX, float touchY, float x, float y, float width, float height) {
        // Check if the touch is inside the button region and if the touch is down and then up.
        if (Gdx.input.justTouched()) {

            touchX = Gdx.input.getX();
            touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            // Check if the touch is within the button bounds
            if (touchX >= x && touchX <= x + width && touchY >= y && touchY <= y + height) {
                return true; // Button was clicked
            }
        }
        return false;
    }




    private boolean renderPopups(float delta) {
        if (isPopupTriggered) {
            popupDelayTimer += delta;

            if (popupDelayTimer >= 2f) {
                if (isGameWon) {
                    batch.draw(winPopup, 640 - winPopup.getWidth(), 40, 2 * winPopup.getWidth(), 2 * winPopup.getHeight());

                }else if (isGameLost) {
                    batch.draw(lossPopup, 640 - (lossPopup.getWidth() / 2), 0, lossPopup.getWidth(), 800);
                }
                else if(ispaused){
                    batch.draw(pausePopup, 0, 0, pausePopup.getWidth()+200, 800);
                }
                return true;



            }


        } else {
            if (pigs.isEmpty()) {
                isPopupTriggered = true;
                isGameWon = true;
            } else if (birds.isEmpty() && !pigs.isEmpty()) {
                isPopupTriggered = true;
                isGameLost = true;
            }
            else if(Gdx.input.isTouched()){
                float touchX = Gdx.input.getX();
                float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

                if (isButtonClicked(touchX,touchY,20, 700, 80, 80)) {
                    isPopupTriggered = true;
                    ispaused = true;

                }
            }
        }

        return false;
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
