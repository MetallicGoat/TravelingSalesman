package com.guelphengg.gameproject;

import com.guelphengg.gameproject.scenes.*;

public enum GameState {
  HELP_MENU(new HelpMenuScene()),
  MAIN_MENU(new MainMenuScene()),
  GAME_SETUP(new GameSetupScene()),
  RUNNING(new GameScene()),
  TRANSITION(new TransitionScene()),
  TRAPPED(new TrappedScene()),
  BATTLE(new BattleScene()),
  MARKET(new MarketScene()),
  WINSCREEN(new WinScene());

  private final Scene scene;

  GameState(Scene scene) {
    this.scene = scene;
  }

  public Scene getScene() {
    return scene;
  }
}
