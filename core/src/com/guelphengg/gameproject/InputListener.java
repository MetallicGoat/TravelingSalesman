package com.guelphengg.gameproject;

import com.badlogic.gdx.InputAdapter;

public class InputListener extends InputAdapter {
  @Override
  public boolean keyDown(int keycode) {
    Accessor.getGameManager().gameInput(keycode);

    return true;
  }
}
