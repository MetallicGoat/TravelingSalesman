package com.guelphengg.gameproject;

import com.badlogic.gdx.Input;
import com.guelphengg.gameproject.scenes.SceneType;

public class GameManager {

  public static int playerX = 0, playerY = 0;

  public static void startGame() {
    SceneManager.setCurrentSceneType(SceneType.GAME_SCENE);

  }

  public static void gameInput(int keyCode) {
    switch (keyCode) {
      case Input.Keys.ESCAPE:
        SceneManager.setCurrentSceneType(SceneType.MAIN_MENU);
        break;

      case Input.Keys.UP:
      case Input.Keys.W:
        playerY++;
        break;

      case Input.Keys.DOWN:
      case Input.Keys.S:
        playerY--;
        break;

      case Input.Keys.LEFT:
      case Input.Keys.A:
        playerX--;
        break;

      case Input.Keys.RIGHT:
      case Input.Keys.D:
        playerX++;
        break;

    }
  }
}
