package com.Royal.game.Screens;

import com.Royal.game.AngryBird;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HomeScreen implements Screen {
    private AngryBird game;
    private SpriteBatch batch;
    private Texture background, backButton;

    private final int[][] levelBounds = {
        {76, 385, 200, 200},
        {376, 385, 200, 200},
        {680, 385, 200, 200}
    };

    private final int backButtonX = 0;
    private final int backButtonY = 0;
    private final int backButtonWidth = 100;
    private final int backButtonHeight = 100;

    public HomeScreen(AngryBird game) {
        this.game = game;
        batch = new SpriteBatch();
        background = new Texture("home.jpg");
        backButton = new Texture("back_button.png");
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background, 0, 0, 1280, 800);
        batch.draw(backButton, backButtonX, backButtonY, backButtonWidth, backButtonHeight);
        batch.end();

        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (touchX >= backButtonX && touchX <= backButtonX + backButtonWidth &&
                touchY >= backButtonY && touchY <= backButtonY + backButtonHeight) {
                game.setScreen(new MainMenuScreen(game));
                return;
            }


            for (int i = 0; i < 3; i++) {
                int[] bounds = levelBounds[i];
                if (touchX >= bounds[0] && touchX <= bounds[0] + bounds[2] &&
                    touchY >= bounds[1] && touchY <= bounds[1] + bounds[3]) {
                    if (i+1==1){
                        game.setScreen(new LevelScreen(game));

                        break;
                    }
                    else if (i+1==2){
                        game.setScreen(new Level2Screen(game));

                    }
                    else if (i+1==3){
                        game.setScreen(new Level3Screen(game));
                    }

                }


            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        backButton.dispose();
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
