package com.guelphengg.gameproject;

import com.guelphengg.gameproject.griditems.Player;

public class Accessor {
  private static final GameManager gameManager = new GameManager();

  public static GameManager getGameManager() {
    return gameManager;
  }

}
