package com.guelphengg.gameproject;

import com.badlogic.gdx.InputAdapter;

public class InputListener extends InputAdapter {
  @Override
  public boolean keyDown(int keycode) {
    Accessor.getGameManager().gameInputKeyDown(keycode);

    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    Accessor.getGameManager().gameInputKeyUp(keycode);

    return true;
  }
}
