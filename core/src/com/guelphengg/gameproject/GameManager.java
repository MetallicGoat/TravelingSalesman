package com.guelphengg.gameproject;

import com.badlogic.gdx.Input;
import com.guelphengg.gameproject.griditems.GridObject;
import com.guelphengg.gameproject.griditems.Player;

public class GameManager {
  private int turn = 0;

  // What phase the game is currently in
  private GameState state = GameState.MAIN_MENU;

  // TODO add turns system: rn playing player is the only one who can move
  // TODO allow players to pick their character
  private final Player player1 = new Player(10, 0, Character.GREENIE);
  private final Player player2 = new Player(10, 0, Character.REDIE);

  private Player playingPlayer = player1;

  private boolean largeMap = true;

  // Items on the grid TODO
  public GridObject[][] gridObjects = new GridObject[10][10];

  // TODO check if this was actually supposed to be in the game or if I dumb
  // public boolean[][] visibleArea = new boolean[10][10];

  public void startGame() {

    //TODO Replace this with a system to randomly generate positions
    gridObjects[4][4] = GridObject.CASTLE;
    gridObjects[2][6] = GridObject.TRAPPED_HOUSE;
    gridObjects[1][3] = GridObject.HOUSE;
    gridObjects[4][8] = GridObject.HOUSE;
    gridObjects[1][6] = GridObject.TREASURE_HOUSE;
    gridObjects[6][8] = GridObject.TRAPPED_HOUSE;
    gridObjects[7][7] = GridObject.TREASURE_HOUSE;
    gridObjects[8][8] = GridObject.TRAPPED_HOUSE;
    gridObjects[8][9] = GridObject.HOUSE;
    gridObjects[9][2] = GridObject.HOUSE;
    gridObjects[5][6] = GridObject.TREASURE_HOUSE;
    gridObjects[5][8] = GridObject.TRAPPED_HOUSE;
    gridObjects[9][1] = GridObject.TREASURE_HOUSE;

    this.state = GameState.RUNNING;
  }

  public GameState getState() {
    return this.state;
  }

  // TODO Rewrite this terrible input system
  // Called when a player presses a key while the ONLY when the game is running
  public void gameInput(int keyCode) {

    if (this.state == GameState.MAIN_MENU) {
      switch (keyCode) {
        case Input.Keys.SPACE:
          startGame(); // TODO this could not be called every time they press space

          this.state = GameState.RUNNING;
          break;
      }

    } else if (this.state == GameState.RUNNING) {
      switch (keyCode) {
        case Input.Keys.ESCAPE:
          // TODO Open Pause Menu?
          this.state = GameState.MAIN_MENU;
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

        case Input.Keys.V:
          // Change the grid view
          largeMap = !largeMap;
          break;
      }
    }
  }

  public boolean isLargeMap() {
    return this.largeMap;
  }

  public Player getPlayingPlayer() {
    return this.playingPlayer;
  }

  public Player getPlayer1() {
    return this.player1;
  }

  public Player getPlayer2() {
    return this.player2;
  }

  // Changes playing player to the opposite player
  public void nextTurn() {
    if (this.playingPlayer == this.player1)
      this.playingPlayer = this.player2;

    else if (this.playingPlayer == this.player2)
      this.playingPlayer = this.player1;
  }

  // Moves the player whose turn it currently is
  // returns false id the player cannot move there
  private boolean movePlayingPlayer(int x, int y) {
    // rn we only saying p1 is playing

    final int newX = this.playingPlayer.getX() + x;
    final int newY = this.playingPlayer.getY() + y;

    // Check if the player is trying to leave the grid
    if (newX > 9 || newX < 0 || newY > 9 || newY < 0)
      return false;

    // TODO Check if the player is trying to go somewhere it cannot

    this.playingPlayer.setX(newX);
    this.playingPlayer.setY(newY);

    turn++;

    if (turn % 10 == 0) {
      nextTurn();
    }

    // visibleArea[newX][newY] = true;

    // True if the player can move
    return true;
  }
}


