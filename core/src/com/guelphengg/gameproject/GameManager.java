package com.guelphengg.gameproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.guelphengg.gameproject.griditems.GridObject;
import com.guelphengg.gameproject.griditems.LootItems;
import com.guelphengg.gameproject.griditems.Player;
import com.guelphengg.gameproject.scenes.BattleScene;
import com.guelphengg.gameproject.scenes.TransitionScene;

import java.util.Iterator;
import java.util.Random;

public class GameManager {

  // TODO allow players to pick their character
  private final Player player1 = new Player(10, 0, Character.GREENIE);
  private final Player player2 = new Player(10, 0, Character.GRAYIE);
  private final int COUNT_MAX = 5;
  public GridObject[][] gridObjects = new GridObject[10][10];
  //for help menu to know where to return to
  boolean fromMenu = false;
  boolean fromRunning = false;
  private boolean waitingForRoll = true; // true by default cause first turn is always ready
  private int nextRoll = 0;
  private int turnsLeft = 0;
  private Sound jump = Gdx.audio.newSound(Gdx.files.internal("JumpTS.wav"));
  private Sound battlestart = Gdx.audio.newSound(Gdx.files.internal("LowImpact.mp3"));
  private Sound epicBram = Gdx.audio.newSound(Gdx.files.internal("EpicBram.mp3"));
  // What phase the game is currently in
  private GameState state = GameState.MAIN_MENU;
  private int[][] houseCounter = new int[10][10];
  private Player playingPlayer = player1;
  private boolean largeMap = false;
  private boolean diceRolling = false;
  private long lastRollTime = 0;
  private Music gameMusic;
  private Music battleMusic;
  private Sound rollSound;
  private Sound lootSound;
  private Sound bootSound;
  private Sound sellSound;

  public void startGame() {

    TravelingSalesman.getInstance().getBackgr().pause();
    gameMusic = Gdx.audio.newMusic(Gdx.files.internal("MainGameTS.mp3"));
    gameMusic.setLooping(true);
    gameMusic.play();

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
      lootSound = Gdx.audio.newSound(Gdx.files.internal("LootSound1.wav"));
      lootSound.play();

      lootedItem = LootItems.getRandomItem();
      gridObjects[playingPlayer.getX()][playingPlayer.getY()] = GridObject.EMPTY_HOUSE;

      // The below if loop checks if there is a weapon in the inventory already.
      if ((lootedItem == LootItems.SWORD || lootedItem == LootItems.BEJEWELED_SWORD || lootedItem == LootItems.BOW)
          && (playingPlayer.getItems().contains(LootItems.SWORD) || playingPlayer.getItems().contains(LootItems.BEJEWELED_SWORD) || playingPlayer.getItems().contains(LootItems.BOW))) {

        // If there is a weapon, the below for loop will run and remove all weapons from the inventory
        Iterator<LootItems> iterator = playingPlayer.getItems().iterator();

        while (iterator.hasNext()) {
          final LootItems item = iterator.next();

          if (item == LootItems.SWORD || item == LootItems.BOW || item == LootItems.BEJEWELED_SWORD)
            iterator.remove();
        }

        // The strength is then reset back to the base number
        playingPlayer.setStrength(0);
      }
      houseCounter[playingPlayer.getX()][playingPlayer.getY()]++;
      // and the loot is added and the strength is adjusted
      playingPlayer.addLoot(lootedItem);
      playingPlayer.addStrength(lootedItem);
    }
    //logic for looting for lost items aka coins
    Random r = new Random();
    if (object == GridObject.LOST_ITEM_HOUSE) {
      lootSound = Gdx.audio.newSound(Gdx.files.internal("LootSound1.wav"));
      lootSound.play();

      playingPlayer.addCoins(r.nextInt(15, 45));
      gridObjects[playingPlayer.getX()][playingPlayer.getY()] = GridObject.EMPTY_HOUSE;
    }
    houseCounter[playingPlayer.getX()][playingPlayer.getY()]++;
  }

  public void tradeItems() {
    if (playerOn(GridObject.CASTLE)) {
      for (int i = 0; i < playingPlayer.getItems().size(); i++) {
        playingPlayer.addCoins(playingPlayer.getItems().get(i).getSellPrice());
        playingPlayer.addPower(playingPlayer.getItems().get(i));
        // This iterates through the player's inventory, checks
        // what the object is, and then adds the set value to the player's coins + power
      }

      playingPlayer.setStrength(0); // sets the strength back to the original value
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
    rollSound = Gdx.audio.newSound(Gdx.files.internal("DiceRoll.wav"));
    rollSound.play();

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
      battleMusic = Gdx.audio.newMusic(Gdx.files.internal("BattleMusic.mp3"));
      battleMusic.setLooping(true);
      gameMusic.stop();
      battlestart.play();
      battleMusic.play(); //TODO Dispose of music (idk how Christian Help)
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
      }

    } else if (this.state == GameState.GAME_SETUP) {
      switch (keyCode) {
        case Input.Keys.SPACE:
          bootSound = Gdx.audio.newSound(Gdx.files.internal("Begin.wav"));
          bootSound.play();
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
          if (playerOn(GridObject.TREASURE_HOUSE)) {
            lootHouse();
          } else if (playerOn(GridObject.LOST_ITEM_HOUSE)) {
            lootHouse();
          }

          break;

        case Input.Keys.T://Trade Tings for Gold lol
          if (playerOn(GridObject.CASTLE)) {
            sellSound = Gdx.audio.newSound(Gdx.files.internal("Sell.wav"));
            sellSound.play();
            tradeItems();
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
          break;
        case Input.Keys.NUM_2:
          //lose coins
          playingPlayer.removeCoins(30);
          smoothlySetState(GameState.RUNNING);
          break;
      }
    }

    if (this.state == GameState.BATTLE) {
      switch (keyCode) {
        case Input.Keys.SPACE:
          Accessor.getGameManager().smoothlySetState(GameState.RUNNING);

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

    // Did they land on a trapped house?
    if (playerOn(GridObject.TRAPPED_HOUSE))
      smoothlySetState(GameState.TRAPPED);

    // Update player visibilities
    playingPlayer.updateVisibleArea();

    this.turnsLeft--;

    jump.play(10000);
  }
}


