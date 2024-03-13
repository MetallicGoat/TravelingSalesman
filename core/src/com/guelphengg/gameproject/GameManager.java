package com.guelphengg.gameproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.guelphengg.gameproject.griditems.GridObject;
import com.guelphengg.gameproject.griditems.LootItems;
import com.guelphengg.gameproject.griditems.Player;
import com.guelphengg.gameproject.scenes.TransitionScene;

import java.util.Random;

public class GameManager {
  private int nextRoll = 0;
  private int turnsLeft = 0;
  private Music jump;

  // What phase the game is currently in
  private GameState state = GameState.MAIN_MENU;

  // TODO allow players to pick their character
  private final Player player1 = new Player(10, 0, Character.GREENIE);
  private final Player player2 = new Player(10, 0, Character.REDIE);

  private Player playingPlayer = player1;

  private boolean largeMap = true;
  private boolean diceRolling = false;
  private long lastRollTime = 0;

  public GridObject[][] gridObjects = new GridObject[10][10];

  // TODO check if this was actually supposed to be in the game or if I dumb
  // public boolean[][] visibleArea = new boolean[10][10];

  public void startGame() {
    //TODO Replace this with a system to randomly generate positions
    gridObjects[4][4] = GridObject.CASTLE;
    gridObjects[2][6] = GridObject.TRAPPED_HOUSE;
    gridObjects[4][8] = GridObject.TREASURE_HOUSE;
    gridObjects[1][6] = GridObject.TREASURE_HOUSE;
    gridObjects[6][8] = GridObject.TRAPPED_HOUSE;
    gridObjects[7][7] = GridObject.TREASURE_HOUSE;
    gridObjects[8][8] = GridObject.TRAPPED_HOUSE;
    gridObjects[8][9] = GridObject.TREASURE_HOUSE;
    gridObjects[9][2] = GridObject.TREASURE_HOUSE;
    gridObjects[5][6] = GridObject.TREASURE_HOUSE;
    gridObjects[5][8] = GridObject.TRAPPED_HOUSE;
    gridObjects[9][1] = GridObject.TREASURE_HOUSE;
    gridObjects[8][1] = GridObject.TREASURE_HOUSE;
    gridObjects[9][3] = GridObject.TREASURE_HOUSE;
    gridObjects[7][1] = GridObject.TREASURE_HOUSE;
//    gridObjects[3][4] = GridObject.SWORD;

    smoothlySetState(GameState.RUNNING);
  }

  // Make the playing player loot the current house they are sting on
  public void lootHouse() {
    if (playerOn(GridObject.TREASURE_HOUSE)) {
      // Its empty now so turn out the lights
      gridObjects[playingPlayer.getX()][playingPlayer.getY()] = GridObject.EMPTY_HOUSE;

      // give the player a random item
      playingPlayer.addLoot(LootItems.getRandomItem());
    }
  }

  // Function that trades all a players items for (TODO)
  public void tradeItems(){
    if (playerOn(GridObject.CASTLE)){
      playingPlayer.getItems().clear();
    }
    //TODO Give items values and give player gold for trading items
  }

  // Checks if a player is standing on a spesific grid object
  public boolean playerOn(GridObject obj){
    if (!playingPlayer.isAtStart() && obj == gridObjects[playingPlayer.getX()][playingPlayer.getY()]){
      return true;
    }
    else {
      return false;
    }
  }

  // check how many moves the current player has left (before next turn)
  public int getTurnsLeft() {
    return this.turnsLeft;
  }

  // Notify that the dice has been rolled
  public void startRolling() {
    this.nextRoll = new Random().nextInt(6) + 1;
    this.diceRolling = true;
    this.lastRollTime = System.currentTimeMillis();
  }

  // Get the last time the dice was rolled by a player
  public long getLastRollTime() {
    return this.lastRollTime;
  }

  // Notify that the dice has been rolled
  public void completeRoll() {
    this.turnsLeft = this.nextRoll;
    this.diceRolling = false;
  }

  public int getNextRoll() {
    return this.nextRoll;
  }

  // Uses TransitionScene to smoothly transition between game states
  public void smoothlySetState(GameState nextState) {
    smoothlySetState(nextState, false, 500);
  }

  // Uses TransitionScene to smoothly transition between game states
  public void smoothlySetState(GameState nextState, boolean fromBlank, long duration) {
    ((TransitionScene) GameState.TRANSITION.getScene()).start(duration, !fromBlank ? this.state : null, nextState);

    this.state = GameState.TRANSITION;
  }

  public GameState getState() {
    return this.state;
  }

  // Update the current game state
  public void setState(GameState nextState) {
    this.state = nextState;
  }

  // Handles all game input
  public void gameInput(int keyCode) {
    if (this.state == GameState.MAIN_MENU) {
      jump = Gdx.audio.newMusic(Gdx.files.internal("JumpTS.wav"));
      jump.setLooping(false);

      switch (keyCode) {
        case Input.Keys.SPACE:
          startGame(); // TODO this could not be called every time they press space
          break;
      }

    } else if (this.state == GameState.RUNNING) {
      switch (keyCode) {
        case Input.Keys.ESCAPE:
          // TODO Open Pause Menu?
          this.state = GameState.MAIN_MENU;
          break;

        case Input.Keys.R:
          if (turnsLeft == 0 && !diceRolling)
            startRolling();
          break;

        case Input.Keys.L: // Player is trying to loot house
          if (playerOn(GridObject.TREASURE_HOUSE)) {
            lootHouse();
          }

          break;
        case Input.Keys.T://Trade Tings for Gold lol
          tradeItems();
          //TODO Give items values and give player gold for trading items
          //since the middle will ALWAYS be the castle, if player is at the point on the grid where the castle exists,
          //pressing 's' will clear the inventory and give player the gold that is equal to the sum of the players inv.
          break;



        case Input.Keys.UP:
        case Input.Keys.W:
          movePlayingPlayer(0, 1);
          jump.play();
          break;

        case Input.Keys.DOWN:
        case Input.Keys.S:
          movePlayingPlayer(0, -1);
          jump.play();
          break;

        case Input.Keys.LEFT:
        case Input.Keys.A:
          movePlayingPlayer(-1, 0);
          jump.play();
          break;

        case Input.Keys.RIGHT:
        case Input.Keys.D:
          movePlayingPlayer(1, 0);
          jump.play();
          break;

        case Input.Keys.V:
          // Change the grid view
          largeMap = !largeMap;
          break;
      }
    }
  }

  public boolean isDiceRolling() {
    return diceRolling;
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
    if (diceRolling || turnsLeft == 0)
      return false;

    // rn we only saying p1 is playing
    final int newX = this.playingPlayer.getX() + x;
    final int newY = this.playingPlayer.getY() + y;

    // Check if the player is trying to leave the grid
    if (newX > 9 || newX < 0 || newY > 9 || newY < 0)
      return false;

    this.playingPlayer.setX(newX);
    this.playingPlayer.setY(newY);

    this.turnsLeft--;

    if (turnsLeft == 0) {
      nextTurn();
    }

    // visibleArea[newX][newY] = true;

    // True if the player can move
    return true;
  }
}


