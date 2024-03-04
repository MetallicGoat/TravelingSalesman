package com.guelphengg.gameproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class TravelingSalesman extends ApplicationAdapter {
    SpriteBatch batch;

    public Texture img;
    private static TravelingSalesman instance;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("Map Background.jpg");

        instance = this;

        // Start listening for input
        Gdx.input.setInputProcessor(new InputListener());

        // Initialize the scene manager
        SceneManager.init();

        Accessor.getGameManager().smoothlySetState(GameState.MAIN_MENU, true, 500);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();
        batch.draw(img, 0, 0, SceneManager.getViewWidth(), SceneManager.getViewHeight());
        batch.end();

        SceneManager.getCurrentScene().render();
    }

    @Override
    public void dispose() {
        SceneManager.dispose();
        batch.dispose();
        img.dispose();
    }

    public static TravelingSalesman getInstance() {
        return instance;
    }
}
