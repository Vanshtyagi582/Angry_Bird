package com.Royal.game.Screens;

import com.Royal.game.AngryBird;
import com.Royal.game.Elements.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Level2Screen implements Screen {
    private final AngryBird game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture background, pauseButton, winButton, lossButton, pausePopup, winPopup, lossPopup,catapult,level2;
    private boolean showPausePopup = false, showWinPopup = false, showLossPopup = false;

    private Array<Bird> birds;
    private Array<Pig> pigs;
    private Array<Block> blocks;
    private Ground ground2;

    public Level2Screen(AngryBird game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 800);
        batch = AngryBird.batch;


        loadTextures();
        initializeGameElements();
    }

    private void loadTextures() {
        background = new Texture("LEVEL-3.png");
        pauseButton = new Texture("pause.png");
        winButton = new Texture("win.png");
        lossButton = new Texture("loss.png");
        pausePopup = new Texture("pausepopup.png");
        winPopup = new Texture("win_.png");
        lossPopup = new Texture("loss_.png");
        catapult=new Texture("catapult.png");
        level2=new Texture("Level2.png");
    }

    private void initializeGameElements() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0, 1280, 800);
        ground2.render(batch);
        batch.draw(pauseButton, 20, 700, 80, 80);
        batch.draw(winButton, 1150, 20, 100, 100);
        batch.draw(lossButton, 1150, 120, 100,100 );
        batch.draw(catapult,120,122,100,130);
        batch.draw(level2,640-level2.getWidth()/2,700);

        for (Bird bird : birds) bird.getSprite().draw(batch);
        for (Pig pig : pigs) pig.getSprite().draw(batch);
        for (Block block : blocks) block.getSprite().draw(batch);

        if (showPausePopup) batch.draw(pausePopup, 340, 200, 600, 400);
        if (showWinPopup) batch.draw(winPopup, 640-winPopup.getWidth()/2, 0, winPopup.getWidth(), 780);
        if (showLossPopup) batch.draw(lossPopup, 640-lossPopup.getWidth()/2, 0, lossPopup.getWidth(), 780);

        batch.end();
        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();  // Invert Y-axis
            if (showPausePopup) {
                handlePausePopup(touchX, touchY);
                return;
            }
            if (showWinPopup) {
                handleWinPopup(touchX, touchY);
                return;
            }
            if (showLossPopup) {
                handleLossPopup(touchX, touchY);
                return;
            }

            if (isTouched(touchX, touchY, 20, 700, 80, 80)) {
                showPausePopup = true;
            } else if (isTouched(touchX, touchY, 1150, 20, 100, 100)) {
                showWinPopup = true;
            } else if (isTouched(touchX, touchY, 1150, 120, 100, 100)) {
                showLossPopup = true;
            }
        }
    }

    private void handlePausePopup(float touchX, float touchY) {
        if (isTouched(touchX, touchY, 480, 265, 340, 70)) {
            showPausePopup = false;
        } else if (isTouched(touchX, touchY, 480, 360, 84, 84)) {
            game.setScreen(new HomeScreen(game));
        }
    }

    private void handleWinPopup(float touchX, float touchY) {
        if (isTouched(touchX, touchY, 500, 110, 60, 60)) {
            game.setScreen(new HomeScreen(game));
        } else if (isTouched(touchX, touchY, 600, 110, 60, 60)) {
            game.setScreen(new LevelScreen(game));
        }else if (isTouched(touchX, touchY, 710, 110, 60, 60)) {
            game.setScreen(new LevelScreen(game));
        }
    }

    private void handleLossPopup(float touchX, float touchY) {
        if (isTouched(touchX, touchY, 669, 115, 100, 100)) {
            game.setScreen(new Level2Screen(game));
        } else if (isTouched(touchX, touchY, 505, 115, 100, 100)) {
            game.setScreen(new HomeScreen(game));
        }
    }

    private boolean isTouched(float x, float y, int rectX, int rectY, int width, int height) {
        return x >= rectX && x <= rectX + width && y >= rectY && y <= rectY + height;
    }

    @Override
    public void dispose() {
        batch.dispose();
        ground2.dispose();
        background.dispose();
        pauseButton.dispose();
        winButton.dispose();
        lossButton.dispose();
        pausePopup.dispose();
        winPopup.dispose();
        lossPopup.dispose();
        disposeArray(birds);
        disposeArray(pigs);
        disposeArray(blocks);
    }

    private <T extends Disposable> void disposeArray(Array<T> items) {
        for (T item : items) {
            item.dispose();
        }
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

