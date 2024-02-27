package com.guelphengg.gameproject;

import com.guelphengg.gameproject.scenes.GameScene;
import com.guelphengg.gameproject.scenes.MainMenuScene;
import com.guelphengg.gameproject.scenes.Scene;

public enum GameState {
  MAIN_MENU(new MainMenuScene()),
  RUNNING(new GameScene());
  // HELP_MENU;

  private final Scene scene;

  GameState(Scene scene) {
    this.scene = scene;
  }

  public Scene getScene(){
    return scene;
  }
}
