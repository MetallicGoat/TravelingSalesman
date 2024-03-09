package com.guelphengg.gameproject;

import com.badlogic.gdx.Input;
import com.guelphengg.gameproject.griditems.GridObject;
import com.guelphengg.gameproject.griditems.LootItems;
import com.guelphengg.gameproject.griditems.Player;
import com.guelphengg.gameproject.scenes.TransitionScene;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;

public class GameManager {
  private int turnsLeft = 0;

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

  // Check if player is at a treasure house
  public boolean canPlayerLoot() {
    if (this.playingPlayer.isAtStart())
      return false;

    final GridObject object = gridObjects[playingPlayer.getX()][playingPlayer.getY()];

    if (object == GridObject.TREASURE_HOUSE)
      return true;

    return false;
  }

  // Make the playing player loot the current house
  public void lootHouse() {
    LootItems lootedItem; // I made this a variable so I could use it to change strength
    if (!canPlayerLoot())
      return;

    final GridObject object = gridObjects[playingPlayer.getX()][playingPlayer.getY()];

    if (object == GridObject.TREASURE_HOUSE) {
      lootedItem = LootItems.getRandomItem();
      gridObjects[playingPlayer.getX()][playingPlayer.getY()] = GridObject.EMPTY_HOUSE;
      // The below if loop checks if there is a weapon in the inventory already.
      if((lootedItem==LootItems.SWORD || lootedItem==LootItems.BEJEWELED_SWORD || lootedItem == LootItems.BOW)
          && (playingPlayer.getItems().contains(LootItems.SWORD)||playingPlayer.getItems().contains(LootItems.BEJEWELED_SWORD)|| playingPlayer.getItems().contains(LootItems.BOW))) {
        // If there is a weapon, the below for loop will run and remove all weapons from the inventory
        for(int i = 0; i<playingPlayer.getItems().size(); i++){
          if(playingPlayer.getItems().get(i) == LootItems.SWORD)
          playingPlayer.getItems().remove(i);
          else if(playingPlayer.getItems().get(i) == LootItems.BOW)
          playingPlayer.getItems().remove(i);
          else if(playingPlayer.getItems().get(i) == LootItems.BEJEWELED_SWORD)
          playingPlayer.getItems().remove(i);
        }
        // The strength is then reset back to the base number
        playingPlayer.setStrength(0);
      }
      // and the loot is added and the strength is adjusted
      playingPlayer.addLoot(lootedItem);
      playingPlayer.addStrength(lootedItem);
    }

  }
  public void tradeItems(){
    if (playerOn(GridObject.CASTLE)){
      for(int i = 0; i<playingPlayer.getItems().size(); i++) {
        playingPlayer.addCoins(playingPlayer.getItems().get(i));
        // This iterates through the player's inventory, checks
        // what the object is, and then adds the set value to the player's coins
      }
      playingPlayer.setStrength(0); // sets the strength back to the original value
      playingPlayer.getItems().clear();
    }
    //TODO Give items values and give player gold for trading items
  }
  public boolean playerOn(GridObject obj){
    if (!playingPlayer.isAtStart() && obj == gridObjects[playingPlayer.getX()][playingPlayer.getY()]){
      return true;
    }
    else {
      return false;
    }
  }

  public int getTurnsLeft() {
    return this.turnsLeft;
  }

  public void startRolling() {
    this.diceRolling = true;
    this.lastRollTime = System.currentTimeMillis();
  }

  public long getLastRollTime() {
    return this.lastRollTime;
  }

  public void completeRoll(int roll) {
    this.turnsLeft = roll;
    this.diceRolling = false;
  }

  public void smoothlySetState(GameState nextState) {
    smoothlySetState(nextState, false, 500);
  }

  public void smoothlySetState(GameState nextState, boolean fromBlank, long duration) {
    ((TransitionScene) GameState.TRANSITION.getScene()).start(duration, !fromBlank ? this.state : null, nextState);

    this.state = GameState.TRANSITION;
  }

  public GameState getState() {
    return this.state;
  }

  public void setState(GameState nextState) {
    this.state = nextState;
  }

  public void gameInput(int keyCode) {
    if (this.state == GameState.MAIN_MENU) {
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
          if (canPlayerLoot()) {
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


