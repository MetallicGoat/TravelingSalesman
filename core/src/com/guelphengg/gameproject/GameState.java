package com.guelphengg.gameproject;

import com.guelphengg.gameproject.scenes.GameScene;
import com.guelphengg.gameproject.scenes.MainMenuScene;
import com.guelphengg.gameproject.scenes.Scene;
import com.guelphengg.gameproject.scenes.TransitionScene;

public enum GameState {
  MAIN_MENU(new MainMenuScene()),
  RUNNING(new GameScene()),
  TRANSITION(new TransitionScene());
  // HELP_MENU;

  private final Scene scene;

  GameState(Scene scene) {
    this.scene = scene;
  }

  public Scene getScene(){
    return scene;
  }
}
