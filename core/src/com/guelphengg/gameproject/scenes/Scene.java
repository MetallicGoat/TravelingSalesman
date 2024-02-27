package com.guelphengg.gameproject.scenes;

import com.guelphengg.gameproject.GameState;

public abstract class Scene {

  GameState type;

  public Scene(GameState type) {
    this.type = type;
  }

  public abstract void render();

  public GameState getSceneType() {
    return type;
  }
}
