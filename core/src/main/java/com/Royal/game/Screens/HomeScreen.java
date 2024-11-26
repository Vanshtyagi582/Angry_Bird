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
    private Texture background, backButton,load;
    private boolean loadpopup=false;
    private int level;

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
        load=new Texture("load.png");
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

    private void showLoad(){
        if(loadpopup){
            batch.begin();
            batch.draw(load,40+300,90+150,3*200,3*100);
            batch.end();
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (isButtonClicked(touchX, touchY, 388,280,203,80)) {
                //new game
                if(level==1){
                    game.setScreen(new LevelScreen(game));
                }
                else if(level==2){
                    game.setScreen(new Level2Screen(game));
                }
                else if (level==3){
                    game.setScreen(new Level3Screen(game));
                }
            }
            if (isButtonClicked(touchX, touchY, 674,280,203,80)) {
                //new game
                if(level==1){
                    game.setScreen(new LevelScreen(game));
                }
                else if(level==2){
                    game.setScreen(new Level2Screen(game));
                }
                else if (level==3){
                    game.setScreen(new Level3Screen(game));
                }
            }


        }

    }

    @Override
    public void render(float delta) {
        batch.begin();
        // Draw main screen elements
        batch.draw(background, 0, 0, 1280, 800);
        batch.draw(backButton, backButtonX, backButtonY, backButtonWidth, backButtonHeight);
        batch.end();

        // Check for touches
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            // Handle back button
            if (touchX >= backButtonX && touchX <= backButtonX + backButtonWidth &&
                touchY >= backButtonY && touchY <= backButtonY + backButtonHeight) {
                game.setScreen(new MainMenuScreen(game));
                return;
            }

            // Check level selection
            for (int i = 0; i < 3; i++) {
                int[] bounds = levelBounds[i];
                if (touchX >= bounds[0] && touchX <= bounds[0] + bounds[2] &&
                    touchY >= bounds[1] && touchY <= bounds[1] + bounds[3]) {
                    loadpopup = true;
                    level = i + 1;
                }
            }
        }

        // Show load popup if active
        if (loadpopup) {
            showLoad();
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
