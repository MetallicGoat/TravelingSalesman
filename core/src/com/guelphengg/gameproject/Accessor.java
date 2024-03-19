package com.guelphengg.gameproject;

public class Accessor {
  private static final GameManager gameManager = new GameManager();

  public static GameManager getGameManager() {
    return gameManager;
  }

}
