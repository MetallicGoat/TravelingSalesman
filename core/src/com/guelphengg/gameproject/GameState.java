package com.guelphengg.gameproject;

import com.guelphengg.gameproject.scenes.*;

public enum GameState {
  MAIN_MENU(new MainMenuScene()),
  GAME_SETUP(new GameSetupScene()),
  RUNNING(new GameScene()),
  TRANSITION(new TransitionScene()),
  BATTLE(new BattleScene());
  // HELP_MENU;

  private final Scene scene;

  GameState(Scene scene) {
    this.scene = scene;
  }

  public Scene getScene(){
    return scene;
  }
}
