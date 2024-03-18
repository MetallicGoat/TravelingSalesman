package com.guelphengg.gameproject;

import com.guelphengg.gameproject.scenes.*;
import com.guelphengg.gameproject.scenes.HelpMenuScene;

public enum GameState {
  HELP_MENU(new HelpMenuScene()),
  MAIN_MENU(new MainMenuScene()),
  GAME_SETUP(new GameSetupScene()),
  RUNNING(new GameScene()),
  TRANSITION(new TransitionScene()),
  BATTLE(new BattleScene());

  private final Scene scene;

  GameState(Scene scene) {
    this.scene = scene;
  }

  public Scene getScene(){
    return scene;
  }
}
