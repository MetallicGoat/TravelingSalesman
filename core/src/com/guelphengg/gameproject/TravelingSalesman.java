package com.guelphengg.gameproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.guelphengg.gameproject.util.Util;

public class TravelingSalesman extends ApplicationAdapter {

  @Override
  public void create() {
    // Start listening for input
    Gdx.input.setInputProcessor(new InputListener());

    // Initialize the scene manager
    SceneManager.init();

    // Play background music
    TSGameMusic.MAIN_MENU_MUSIC.play();

    // Set the default game state to the main menu
    Accessor.getGameManager().smoothlySetState(GameState.MAIN_MENU, true, 500);
  }

  @Override
  public void render() {
    // Update the curr time (used for player animation frames)
    Util.incrementStateTime();

    // Clear the screen
    ScreenUtils.clear(0, 0, 0, 1);

    // Render the current scene
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
