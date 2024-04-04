package com.guelphengg.gameproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.guelphengg.gameproject.util.Util;

public class TravelingSalesman extends ApplicationAdapter {
  private static TravelingSalesman instance;

  public static TravelingSalesman getInstance() {
    return instance;
  }

  @Override
  public void create() {
    instance = this;

    // Start listening for input
    Gdx.input.setInputProcessor(new InputListener());

    // Initialize the scene manager
    SceneManager.init();

    // Play background music
    TSGameMusic.MAIN_MENU_MUSIC.play();

    Accessor.getGameManager().smoothlySetState(GameState.MAIN_MENU, true, 500);
  }

  @Override
  public void render() {
    // Update the player animation frames
    Util.incrementStateTime();

    ScreenUtils.clear(0, 0, 0, 1);
    SceneManager.getCurrentScene().render();
  }

  @Override
  public void dispose() {
    SceneManager.dispose();
    Textures.dispose();
    TSGameMusic.dispose();
    TSGameSound.dispose();
  }
}
