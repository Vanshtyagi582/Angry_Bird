package com.Royal.game.Screens;

import com.Royal.game.AngryBird;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen implements Screen {
    private AngryBird game;
    private SpriteBatch batch;
    private Texture background;

    private final int buttonX = 540;
    private final int buttonY = 300;
    private final int buttonWidth = 200;
    private final int buttonHeight = 100;

    private final int quitX = 0;
    private final int quitY = 0;
    private final int quitWidth = 100;
    private final int quitHeight = 100;

    public MainMenuScreen(AngryBird game) {
        this.game = game;
        batch = new SpriteBatch();
        background = new Texture("playscreen.png");
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background, 0, 0, 1280, 800);
        batch.end();

        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (touchX >= buttonX && touchX <= buttonX + buttonWidth &&
                touchY >= buttonY && touchY <= buttonY + buttonHeight) {
                game.setScreen(new HomeScreen(game));
                return;
            }

            if (touchX >= quitX && touchX <= quitX + quitWidth &&
                touchY >= quitY && touchY <= quitY + quitHeight) {
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
