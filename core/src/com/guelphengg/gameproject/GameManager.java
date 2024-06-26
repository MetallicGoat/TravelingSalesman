package com.guelphengg.gameproject;

import com.badlogic.gdx.Input;
import com.guelphengg.gameproject.griditems.GridObject;
import com.guelphengg.gameproject.griditems.LootItems;
import com.guelphengg.gameproject.griditems.Player;
import com.guelphengg.gameproject.scenes.BattleScene;
import com.guelphengg.gameproject.scenes.MarketScene;
import com.guelphengg.gameproject.scenes.TransitionScene;
import com.guelphengg.gameproject.scenes.WinScene;

import java.util.Random;

public class GameManager {

  // How many turns before a house can be looted again
  private final int COUNT_MAX = 9;

  // for help menu to know where to return to after they press H
  boolean fromMenu;
  boolean fromRunning;

  // Initialize Default Players
  private Player player1;
  private Player player2;

  // This grid defines what exists on what grid tiles (null = empty)
  private GridObject[][] gridObjects;

  // IF we are waiting for the user to press R
  // true by default cause first turn is always ready
  private boolean waitingForRoll;
  private int nextRoll; // Used to determine the roll of the dice
  private int turnsLeft; // How many more moves the playing player has left

  // The player who is currently playing
  private Player playingPlayer; // Player 1 always starts

  // What phase the game is currently in
  private GameState state;

  // Keeps track of how long ago a house was looted
  private int[][] houseCounter;

  // Weather or not the user is trying to view the large map (By holding V)
  private boolean largeMap;

  // If the dice is currently spinning to a predetermined roll
  private boolean diceRolling;

  // The last time a user pressed R successfully (in ms)
  private long lastRollTime;

  // Basic constructor
  public GameManager() {
    // Initialize the game manager for the first time
    resetManager();
  }

  // Reset the game manager to its default state
  // Useful for starting a new game
  // New player instance are created with default data
  // All variables initialized to default values
  public void resetManager() {
    player1 = new Player(10, 0, Character.GREENIE);
    player2 = new Player(10, 0, Character.GRAYIE);
    gridObjects = new GridObject[10][10];
    fromMenu = false;
    fromRunning = false;
    waitingForRoll = true;
    nextRoll = 0;
    turnsLeft = 0;
    playingPlayer = player1;
    houseCounter = new int[10][10];
    largeMap = false;
    diceRolling = false;
    lastRollTime = 0;
    state = GameState.MAIN_MENU;
  }

  public void startGame() {
    // Stop main menu music, and play main game music
    TSGameMusic.MAIN_GAME_MUSIC.play();

    // Generate all landmarks
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
    if (canPlayerLoot() == 0)
      return;

    final GridObject object = gridObjects[playingPlayer.getX()][playingPlayer.getY()];

    if (object == GridObject.TREASURE_HOUSE) {

      final LootItems lootedItem = LootItems.getRandomTreasureItem(playingPlayer);
      // Add the item and their power
      playingPlayer.addLoot(lootedItem);
      playingPlayer.addPower(lootedItem);

    } else if (object == GridObject.LOST_ITEM_HOUSE) {
      // logic for looting for lost items aka coins
      Random r = new Random();
      playingPlayer.addCoins(r.nextInt(15, 45));
    }

    // Play loot sound
    TSGameSound.LOOT.play();

    // Set the house to empty
    gridObjects[playingPlayer.getX()][playingPlayer.getY()] = GridObject.EMPTY_HOUSE;

    houseCounter[playingPlayer.getX()][playingPlayer.getY()]++;
  }

  public void tradeItems() {
    if (playerOn(GridObject.CASTLE)) {
      boolean itemsSold = false;

      // This iterates through the player's inventory, checks
      // what the object is, and then adds the set value to the player's coins + power
      for (LootItems item : playingPlayer.getItems()) {
        playingPlayer.addCoins(item.getSellPrice());
        playingPlayer.addPoints(item);
        itemsSold = true;
      }

      if (itemsSold) {
        // Play sell sound
        TSGameSound.SELL.play();

        playingPlayer.setPower(0); // sets the strength back to the original value
        playingPlayer.getItems().clear();
      }
    }
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

  // Returns true if they go into a battle
  public boolean battleCheck() {
    if ((player1.getX() == player2.getX() && player1.getY() == player2.getY()) && !player1.isAtStart()) {

      // Start Battle music
      TSGameMusic.BATTLE_MUSIC.play();

      // Play battle sound
      TSGameSound.BATTLE_START.play();

      ((BattleScene) GameState.BATTLE.getScene()).resetBattle();
      smoothlySetState(GameState.BATTLE);

      return true;
    }

    return false;
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

        // TODO THIS IS FOR TESTING PURPOSES ONLY
        case Input.Keys.W:
          for (int i = 0; i < 10; i++) {
            player1.addPoints(LootItems.BEJEWELED_SWORD);
          }

          WinScene.reset();
          TSGameMusic.WINNING_MUSIC.play();
          smoothlySetState(GameState.WINSCREEN);
          break;
      }

    } else if (this.state == GameState.GAME_SETUP) {
      switch (keyCode) {
        case Input.Keys.SPACE:
          TSGameSound.BEGIN.play();
          startGame();
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
          if (!waitingForRoll && turnsLeft == 0 && !diceRolling)
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

        case Input.Keys.T:
          if (playerOn(GridObject.CASTLE)) {
            tradeItems(); // Trade Tings for Gold lol
          }

          if (player1.getPoints() >= 10 || player2.getPoints() >= 10) {
            WinScene.reset();
            TSGameMusic.WINNING_MUSIC.play();
            smoothlySetState(GameState.WINSCREEN);
          }

          // since the middle will ALWAYS be the castle, if player is at the point on the grid where the castle exists,
          // pressing 's' will clear the inventory and give player the gold that is equal to the sum of the players inv.
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

        // Map size toggle
        case Input.Keys.V:
          // Change the grid view
          largeMap = true;
          break;

        // Help menu toggle
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
      gridObjects[playingPlayer.getX()][playingPlayer.getY()] = GridObject.WALL_BURNED_HOUSE;

      switch (keyCode) {
        case Input.Keys.NUM_1:
          // lose point
          playingPlayer.removePoints(1);
          smoothlySetState(GameState.RUNNING);

          TSGameMusic.MAIN_GAME_MUSIC.play();

          break;
        case Input.Keys.NUM_2:
          //lose coins
          playingPlayer.removeCoins(30);
          smoothlySetState(GameState.RUNNING);

          TSGameMusic.MAIN_GAME_MUSIC.play();

          break;
      }
    } else if (this.state == GameState.BATTLE) {
      switch (keyCode) {
        case Input.Keys.SPACE: {
          // THE USER HAS EXISTED THE BATTLE

          // Return to normal music
          TSGameMusic.MAIN_GAME_MUSIC.play();

          // It is possible the user entered a battle but is still on a square that opens a scene
          final boolean playerEnteredScene = checkForSceneTransitions();

          // If the player enter a scene (eg market) directly after a battle, we should not go back to running
          if (!playerEnteredScene)
            smoothlySetState(GameState.RUNNING);

          break;
        }
      }
    } else if (this.state == GameState.MARKET) {
      switch (keyCode) {
        case Input.Keys.SPACE: {
          Accessor.getGameManager().smoothlySetState(GameState.RUNNING);

          TSGameMusic.MAIN_GAME_MUSIC.play();
          break;
        }
        case Input.Keys.NUM_0: {
          MarketScene.canBuy(0);
          break;
        }
        case Input.Keys.NUM_1: {
          MarketScene.canBuy(1);
          break;
        }
        case Input.Keys.NUM_2: {
          MarketScene.canBuy(2);
          break;
        }
        case Input.Keys.NUM_3: {
          MarketScene.canBuy(3);
          break;
        }
        case Input.Keys.NUM_4: {
          MarketScene.canBuy(4);
          break;
        }
        case Input.Keys.NUM_5: {
          MarketScene.canBuy(5);
          break;
        }
        case Input.Keys.NUM_6: {
          MarketScene.canBuy(6);
          break;
        }
      }
    }

    if (this.state == GameState.WINSCREEN) {
      switch (keyCode) {
        case Input.Keys.SPACE: {
          // Reset the game manger for new game after a win
          resetManager();

          // Send em to main menu
          smoothlySetState(GameState.MAIN_MENU);
          TSGameMusic.MAIN_MENU_MUSIC.play();
          break;
        }
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

    // Players cannot walk through walls
    if (gridObjects[newX][newY] == GridObject.WALL_BURNED_HOUSE)
      return;

    this.playingPlayer.setX(newX);
    this.playingPlayer.setY(newY);

    // Check if the players can battle
    final boolean battleStarted = battleCheck();

    // Only go into the scene if there has not been a battle started
    if (!battleStarted)
      checkForSceneTransitions();

    // Update player visibilities
    playingPlayer.updateVisibleArea();

    this.turnsLeft--;

    TSGameSound.JUMP.play();
  }

  // Returns true if they go into a scene
  private boolean checkForSceneTransitions() {
    // Did they land on a trapped house?
    if (playerOn(GridObject.TRAPPED_HOUSE)) {
      smoothlySetState(GameState.TRAPPED);

      TSGameMusic.TRAPPED_MUSIC.play();

      return true;
    }

    // Check if they landed on a market
    if (playerOn(GridObject.MARKET)) {
      MarketScene.reset();
      smoothlySetState(GameState.MARKET);

      TSGameMusic.MARKET_MUSIC.play();

      return true;
    }

    return false;
  }
}


