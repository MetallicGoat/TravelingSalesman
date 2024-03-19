package com.guelphengg.gameproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.guelphengg.gameproject.scenes.scenecomponents.AttackAnimation;

public class TravelingSalesman extends ApplicationAdapter {
    private static TravelingSalesman instance;
    private Music Backgr;


    public Music getBackgr(){
        return Backgr;
    }
    @Override
    public void create() {
        instance = this;

        // Start listening for input
        Gdx.input.setInputProcessor(new InputListener());

        // Initialize the scene manager
        SceneManager.init();

        Backgr = Gdx.audio.newMusic(Gdx.files.internal("MainMenuTS.mp3"));

        Backgr.setLooping(true);
        Backgr.play();

        Accessor.getGameManager().smoothlySetState(GameState.MAIN_MENU, true, 500);
    }

    @Override
    public void render() {

        // Update the player animation frames
        Character.updateStateTime();

        ScreenUtils.clear(0, 0, 0, 1);
        SceneManager.getCurrentScene().render();
    }

    @Override
    public void dispose() {
        SceneManager.dispose();
        Textures.dispose();
    }

    public static TravelingSalesman getInstance() {
        return instance;
    }
}
