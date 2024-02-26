package com.guelphengg.gameproject;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.guelphengg.gameproject.griditems.Player;
import com.guelphengg.gameproject.scenes.SceneType;

public class GameManager {

  // TODO add turns system: rn playing player is the only one who can move
  private final Player player1 = new Player(0, 0, Color.RED);
  private final Player player2 = new Player(0, 1, Color.BLUE);

  // TODO check if this was actually supposed to be in the game or if I dumb
  public boolean[][] visibleArea = new boolean[10][10];


  public void startGame() {
    SceneManager.setCurrentSceneType(SceneType.GAME_SCENE);
  }

  // TODO Rewrite this terrible input system
  // Called when a player presses a key while the ONLY when the game is running
  public void gameInput(int keyCode) {
    switch (keyCode) {
      case Input.Keys.ESCAPE:
        SceneManager.setCurrentSceneType(SceneType.MAIN_MENU);
        break;

      case Input.Keys.UP:
      case Input.Keys.W:
        movePlayingPlayer(0, 1);
        break;

      case Input.Keys.DOWN:
      case Input.Keys.S:
        movePlayingPlayer(0, -1);
        break;

      case Input.Keys.LEFT:
      case Input.Keys.A:
        movePlayingPlayer(-1, 0);
        break;

      case Input.Keys.RIGHT:
      case Input.Keys.D:
        movePlayingPlayer(1, 0);
        break;

    }
  }

  public Player getPlayingPlayer() {
    return this.player1;
  }

  public Player getPlayer1() {
    return this.player1;
  }

  public Player getPlayer2() {
    return this.player2;
  }

  // Moves the player whose turn it currently is
  // returns false id the player cannot move there
  private boolean movePlayingPlayer(int x, int y) {
    // rn we only saying p1 is playing
    final Player playingPlayer = this.player1;

    final int newX = playingPlayer.getX() + x;
    final int newY = playingPlayer.getY() + y;

    // Check if the player is trying to leave the grid
    if (newX > 9 || newX < 0 || newY > 9 || newY < 0)
      return false;

    // TODO Check if the player is trying to go somewhere it cannot

    playingPlayer.setX(newX);
    playingPlayer.setY(newY);

    visibleArea[newX][newY] = true;

    // True if the player can move
    return true;
  }
}
