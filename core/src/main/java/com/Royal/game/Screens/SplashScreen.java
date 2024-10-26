package com.Royal.game.Screens;
import com.Royal.game.AngryBird;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;

public class SplashScreen implements Screen {
    private final AngryBird game;  // Reference to the main game class
    private Texture splashImage;

    public SplashScreen(AngryBird game) {
        this.game = game;  // Store the reference to the main game
    }

    @Override
    public void show() {
        splashImage = new Texture("splash.jpg");  // Load splash image
        // Set a timer to switch to the home screen after 3 seconds
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new MainMenuScreen(game));
            }
        }, 1);
    }

    @Override
    public void render(float delta) {
        int width= Gdx.graphics.getWidth();
        int height=Gdx.graphics.getHeight();
        AngryBird.batch.begin();
        AngryBird.batch.draw(splashImage, 0, 0,width,height);  // Render the splash image
        AngryBird.batch.end();
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        splashImage.dispose();  // Dispose of the splash image
    }
}

