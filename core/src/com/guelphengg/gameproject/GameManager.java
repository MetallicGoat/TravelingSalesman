package com.guelphengg.gameproject;

import com.badlogic.gdx.Input;
import com.guelphengg.gameproject.griditems.*;
import com.guelphengg.gameproject.griditems.GridObject;
import com.guelphengg.gameproject.griditems.LootItems;
import com.guelphengg.gameproject.griditems.Player;
import com.guelphengg.gameproject.scenes.BattleScene;
import com.guelphengg.gameproject.scenes.TransitionScene;

import java.util.Iterator;
import java.util.Random;

public class GameManager {

  // Initialize Default Players
  private final Player player1 = new Player(10, 0, Character.GREENIE);
  private final Player player2 = new Player(10, 0, Character.GRAYIE);

  // This grid defines what exists on what grid tiles (null = empty)
  public GridObject[][] gridObjects = new GridObject[10][10];

  // How many turns before a house can be looted again
  private final int COUNT_MAX = 20;

  // for help menu to know where to return to after they press H
  boolean fromMenu = false;
  boolean fromRunning = false;

  // IF we are waiting for the user to press R
  // true by default cause first turn is always ready
  private boolean waitingForRoll = true;
  private int nextRoll = 0; // Used to determine the roll of the dice
  private int turnsLeft = 0; // How many more moves the playing player has left

  // The player who is currently playing
  private Player playingPlayer = player1; // Player 1 always starts

  // What phase the game is currently in
  private GameState state = GameState.MAIN_MENU;

  // Keeps track of how long ago a house was looted
  private final int[][] houseCounter = new int[10][10];

  // Weather or not the user is trying to view the large map (By holding V)
  private boolean largeMap = false;

  // If the dice is currently spinning to a predetermined roll
  private boolean diceRolling = false;

  // The last time a user pressed R successfully (in ms)
  private long lastRollTime = 0;

  public void startGame() {
    // Stop main menu music, and play main game music
    TSGameMusic.MAIN_MENU_MUSIC.stop();
    TSGameMusic.MAIN_GAME_MUSIC.play();

    //generate all landmarks
    Generation.generateLandmarks();

    // Update player visibilities
    player1.updateVisibleArea();
    player2.updateVisibleArea();

    smoothlySetState(GameState.RUNNING);
  }

  // Check if player is at a treasure house
  public int canPlayerLoot() {
    if (this.playingPlayer.isAtStart())
      return 0;

    final GridObject object = gridObjects[playingPlayer.getX()][playingPlayer.getY()];

    if (object == GridObject.TREASURE_HOUSE)
      return 1;

    else if (object == GridObject.LOST_ITEM_HOUSE)
      return 1;

    else
      return 0;
  }

  // Make the playing player loot the current house
  public void lootHouse() {
    LootItems lootedItem; // I made this a variable so I could use it to change strength
    if (canPlayerLoot() == 0)
      return;

    final GridObject object = gridObjects[playingPlayer.getX()][playingPlayer.getY()];

    if (object == GridObject.TREASURE_HOUSE) {
      // Play Loot Sound
      TSGameSound.LOOT.play();

      lootedItem = LootItems.getRandomItem(playingPlayer);
      gridObjects[playingPlayer.getX()][playingPlayer.getY()] = GridObject.EMPTY_HOUSE;

      // The below if loop checks if there is a weapon in the inventory already.
      if ((lootedItem.getItemType() == ItemType.WEAPON)
          && (playingPlayer.getItems().contains(LootItems.SWORD) || playingPlayer.getItems().contains(LootItems.BEJEWELED_SWORD) || playingPlayer.getItems().contains(LootItems.BOW))) {

        // If there is a weapon, the below for loop will run and remove all weapons from the inventory
        Iterator<LootItems> iterator = playingPlayer.getItems().iterator();
        while (iterator.hasNext()) {
          final LootItems item = iterator.next();
          // If the weapon's looted damage is greater than the item in the inventory's, the inventory weapon is converted to coins
          if ((item.getItemType() == ItemType.WEAPON)&&(lootedItem.getDamage()> item.getDamage())) {
            playingPlayer.addCoins((int)(item.getSellPrice()*0.8));
            // Is removed from the inventory
            iterator.remove();
            // And the appropriate values and inventory are adjusted based on the item.
            playingPlayer.setPower(0);
            playingPlayer.addLoot(lootedItem);
            playingPlayer.addPower(lootedItem);
          }else{
            playingPlayer.addCoins(item.getSellPrice());
            System.out.println("Hello");
          }
        }
      }else{
        playingPlayer.addLoot(lootedItem);
        playingPlayer.addPower(lootedItem);
      }

      houseCounter[playingPlayer.getX()][playingPlayer.getY()]++;
      // and the loot is added and the strength is adjusted
    }
    //logic for looting for lost items aka coins
    Random r = new Random();
    if (object == GridObject.LOST_ITEM_HOUSE) {
      // Play Loot sound
      TSGameSound.LOOT.play();

      playingPlayer.addCoins(r.nextInt(15, 45));
      gridObjects[playingPlayer.getX()][playingPlayer.getY()] = GridObject.EMPTY_HOUSE;
    }
    houseCounter[playingPlayer.getX()][playingPlayer.getY()]++;
  }

  public void tradeItems() {
    if (playerOn(GridObject.CASTLE)) {
      for (int i = 0; i < playingPlayer.getItems().size(); i++) {
        playingPlayer.addCoins(playingPlayer.getItems().get(i).getSellPrice());
        playingPlayer.addPoints(playingPlayer.getItems().get(i));
        // This iterates through the player's inventory, checks
        // what the object is, and then adds the set value to the player's coins + power
      }

      playingPlayer.setPower(0); // sets the strength back to the original value
      playingPlayer.getItems().clear();
    }
    //TODO Give items values and give player gold for trading items
  }

  public boolean playerOn(GridObject obj) {
    if (!playingPlayer.isAtStart() && obj == gridObjects[playingPlayer.getX()][playingPlayer.getY()]) {
      return true;
    } else {
      return false;
    }
  }

  // check how many moves the current player has left (before next turn)
  public int getTurnsLeft() {
    return this.turnsLeft;
  }

  // is waiting for roll
  public boolean isWaitingForRoll() {
    return this.waitingForRoll;
  }

  // Notify that the dice has been rolled
  public void startRolling() {
    // Play roll sound
    TSGameSound.DICE_ROLL.play();

    this.nextRoll = new Random().nextInt(6) + 1;
    this.diceRolling = true;
    this.waitingForRoll = false;
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
    smoothlySetState(nextState, false, 1000);
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

  public void battleCheck() {
    if ((player1.getX() == player2.getX() && player1.getY() == player2.getY()) && !player1.isAtStart()) {

      // Start Battle music
      TSGameMusic.MAIN_GAME_MUSIC.stop();
      TSGameMusic.BATTLE_MUSIC.play();

      // Play battle sound
      TSGameSound.BATTLE_START.play();

      ((BattleScene) GameState.BATTLE.getScene()).resetBattle();
      smoothlySetState(GameState.BATTLE);
    }
  }

  // Handles all game input for pressing down on a key
  public void gameInputKeyDown(int keyCode) {

    if (this.state == GameState.MAIN_MENU) {
      switch (keyCode) {
        //moves into character select scene
        case Input.Keys.SPACE:
          smoothlySetState(GameState.GAME_SETUP);
          break;
        //moves into help menu scene
        case Input.Keys.H:
          smoothlySetState(GameState.HELP_MENU);
          fromMenu = true;
          break;

        case Input.Keys.W:
          for(int i = 0; i < 10; i++){
          player1.addPoints(LootItems.BEJEWELED_SWORD);}
          smoothlySetState(GameState.WINSCREEN);
          break;
      }

    } else if (this.state == GameState.GAME_SETUP) {
      switch (keyCode) {
        case Input.Keys.SPACE:
          TSGameSound.BEGIN.play();
          startGame(); // TODO this could not be called every time they press space
          break;

        case Input.Keys.A:
          player1.setCharacter(Character.getPreviousCharacter(player1));
          break;

        case Input.Keys.D:
          player1.setCharacter(Character.getNextCharacter(player1));
          break;

        case Input.Keys.LEFT:
          player2.setCharacter(Character.getPreviousCharacter(player2));
          break;

        case Input.Keys.RIGHT:
          player2.setCharacter(Character.getNextCharacter(player2));
          break;
      }

    } else if (this.state == GameState.RUNNING) {
      switch (keyCode) {

        // Press enter to complete the turn
        case Input.Keys.ENTER:
          if (!waitingForRoll && turnsLeft == 0)
            nextTurn();

          break;

        case Input.Keys.R: {
          if (waitingForRoll && !diceRolling) {
            startRolling();

            for (int i = 0; i < 10; i++) {
              for (int m = 0; m < 10; m++) {
                if (houseCounter[i][m] > COUNT_MAX) {
                  houseCounter[i][m] = 0;
                  gridObjects[i][m] = GridObject.TREASURE_HOUSE;
                } else if (houseCounter[i][m] > 0) {
                  houseCounter[i][m]++;
                }
              }
            }
          }
        }
        break;

        case Input.Keys.L: // Player is trying to loot house
          if (playerOn(GridObject.TREASURE_HOUSE) || playerOn(GridObject.LOST_ITEM_HOUSE)) {
            playingPlayer.addLootedHouse();
            lootHouse();
          }

          break;

        case Input.Keys.T://Trade Tings for Gold lol
          if (playerOn(GridObject.CASTLE)) {
            TSGameSound.SELL.play();
            tradeItems();
          }

          if (player1.getPoints() >= 10 || player2.getPoints() >= 10){
            smoothlySetState(GameState.WINSCREEN);
          }

          //TODO Make it so that the selling sound only plays when the player actually sold something....
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

        //mapsize toggle
        case Input.Keys.V:
          // Change the grid view
          largeMap = true;
          break;

        //help menu toggle
        case Input.Keys.H:
          fromRunning = true;
          smoothlySetState(GameState.HELP_MENU);
          break;

      }

    }


    // when in help menu scene, waits for H to be pressed, then returns to scene that they initially came from
    else if (this.state == GameState.HELP_MENU) {
      switch (keyCode) {
        case Input.Keys.H:
          if (fromRunning) {
            smoothlySetState(GameState.RUNNING);
            break;
          }
          if (fromMenu) {
            smoothlySetState(GameState.MAIN_MENU);
            break;
          }
      }
    }

    // logic for trapped houses and the inputs while in that scene
    else if (this.state == GameState.TRAPPED) {
      switch (keyCode) {
        case Input.Keys.NUM_1:
          //lose power
          playingPlayer.removeStrength(1);
          smoothlySetState(GameState.RUNNING);

          TSGameMusic.TRAPPED_MUSIC.stop();
          TSGameMusic.MAIN_GAME_MUSIC.play();

          break;
        case Input.Keys.NUM_2:
          //lose coins
          playingPlayer.removeCoins(30);
          smoothlySetState(GameState.RUNNING);

          TSGameMusic.TRAPPED_MUSIC.stop();
          TSGameMusic.MAIN_GAME_MUSIC.play();

          break;
      }
    }

    if (this.state == GameState.BATTLE) {
      switch (keyCode) {
        case Input.Keys.SPACE: {
          Accessor.getGameManager().smoothlySetState(GameState.RUNNING);

          TSGameMusic.BATTLE_MUSIC.stop();
          TSGameMusic.MAIN_GAME_MUSIC.play();
        }
      }
    }
    if (this.state == GameState.MARKET) {
      switch (keyCode) {
        case Input.Keys.SPACE: {
          Accessor.getGameManager().smoothlySetState(GameState.RUNNING);

          TSGameMusic.MARKET_MUSIC.stop();
          TSGameMusic.MAIN_GAME_MUSIC.stop();
        }
      }
    }

    if(this.state == GameState.WINSCREEN){
      switch (keyCode){
        case Input.Keys.SPACE:
          smoothlySetState(GameState.MAIN_MENU);
          break;
      }
    }
  }

  public void gameInputKeyUp(int keyCode) {
    if (this.state == GameState.RUNNING) {
      switch (keyCode) {
        case Input.Keys.V:
          this.largeMap = false;
          break;
      }
    }
  }

  public GridObject[][] getGridObjectArray() {
    return this.gridObjects;
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

    this.waitingForRoll = true;
  }

  // Moves the player whose turn it currently is
  // returns false id the player cannot move there
  private void movePlayingPlayer(int x, int y) {
    if (diceRolling || turnsLeft == 0)
      return;

    // rn we only saying p1 is playing
    final int newX = this.playingPlayer.getX() + x;
    final int newY = this.playingPlayer.getY() + y;

    // Check if the player is trying to leave the grid
    if (newX > 9 || newX < 0 || newY > 9 || newY < 0)
      return; // The player cannot move there

    this.playingPlayer.setX(newX);
    this.playingPlayer.setY(newY);

    // Check if the players can battle
    battleCheck();

    // Check if the player has reached the treasure location
    // And reward them if they have
    playingPlayer.tryCollectTreasure();

    // Did they land on a trapped house?
    if (playerOn(GridObject.TRAPPED_HOUSE)) {
      smoothlySetState(GameState.TRAPPED);

      TSGameMusic.MAIN_GAME_MUSIC.stop();
      TSGameMusic.TRAPPED_MUSIC.play();
    }

    if (playerOn(GridObject.MARKET)) {
      smoothlySetState(GameState.MARKET);

      TSGameMusic.MAIN_GAME_MUSIC.stop();
      TSGameMusic.MARKET_MUSIC.play();
    }

    // Update player visibilities
    playingPlayer.updateVisibleArea();

    this.turnsLeft--;

    TSGameSound.JUMP.play();
  }
}


