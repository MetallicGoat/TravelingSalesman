package com.guelphengg.gameproject.scenes;

public enum SceneType {
  MAIN_MENU(new MainMenuScene()),
  GAME_SCENE(new GameScene());
  // HELP_MENU;

  private final Scene scene;

  SceneType(Scene scene) {
    this.scene = scene;
  }

  public Scene getScene(){
    return scene;
  }
}
