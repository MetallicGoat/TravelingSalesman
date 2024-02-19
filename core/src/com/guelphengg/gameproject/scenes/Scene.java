package com.guelphengg.gameproject.scenes;

public abstract class Scene {

  SceneType type;

  public Scene(SceneType type) {
    this.type = type;
  }

  public abstract void render();

  // Different scenes might want to handle input differently
  // eg. we ain't gonna move the player in the main menu
  public abstract void onKeyDown(int key);

  public SceneType getSceneType() {
    return type;
  }
}
