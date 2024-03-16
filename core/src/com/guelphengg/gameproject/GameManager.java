package com.guelphengg.gameproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.guelphengg.gameproject.griditems.GridObject;
import com.guelphengg.gameproject.griditems.LootItems;
import com.guelphengg.gameproject.griditems.Player;
import com.guelphengg.gameproject.scenes.TransitionScene;

import java.util.Iterator;
import java.util.Random;

public class GameManager {
  private int nextRoll = 0;
  private int turnsLeft = 0;
  private Sound jump = Gdx.audio.newSound(Gdx.files.internal("JumpTS.wav"));

  // What phase the game is currently in
  private GameState state = GameState.MAIN_MENU;

  // TODO allow players to pick their character
  private final Player player1 = new Player(10, 0, Character.GREENIE);
  private final Player player2 = new Player(10, 0, Character.REDDIE);

  private Player playingPlayer = player1;

  private boolean largeMap = true;
  private boolean diceRolling = false;
  private long lastRollTime = 0;

  public GridObject[][] gridObjects = new GridObject[10][10];

  private Music gameMusic;
  private Sound rollSound;
  private Sound lootSound;
  private Sound bootSound;
  private Sound sellSound;

  // TODO check if this was actually supposed to be in the game or if I dumb
  // public boolean[][] visibleArea = new boolean[10][10];

  public void startGame() {

    TravelingSalesman.getInstance().getBackgr().pause();
    gameMusic = Gdx.audio.newMusic(Gdx.files.internal("MainGameTS.mp3"));
    gameMusic.setLooping(true);
    gameMusic.play();

    Random randNum = new Random();

    //generates a random position for the castle in the middle 4 squares of the grid
    int castleRow = randNum.nextInt(4,6);
    int castleCol = randNum.nextInt(4,6);
    gridObjects[castleRow][castleCol] = GridObject.CASTLE;

    //generates random ints between 0-9
    int randRow;
    int randCol;

    //array of all structures in the game
    GridObject[] gridObjList = {GridObject.TREASURE_HOUSE, GridObject.TRAPPED_HOUSE, GridObject.LOST_ITEM_HOUSE, GridObject.MARKET};

    int typesStructs = 3; //number of different types of structures that be generated
    int numStructs = 8; //number of each structure to be generated

    //logic for generating treasure houses
    int numTreasureHouses = 2;
    //Generate in quadrant 1
    for(int i = 0; i < numTreasureHouses; i++) {
      randRow = randNum.nextInt(0,5);
      randCol = randNum.nextInt(0,5);
      if (gridObjects[randRow][randCol] == null) {
        gridObjects[randRow][randCol] = gridObjList[0];
      }
    }
    //Generate in Quad 2
    for(int i = 0; i < numTreasureHouses; i++) {
      randRow = randNum.nextInt(5,10);
      randCol = randNum.nextInt(0,5);
      if (gridObjects[randRow][randCol] == null) {
        gridObjects[randRow][randCol] = gridObjList[0];
      }
    }
    //Generate in Quad 3
    for(int i = 0; i < numTreasureHouses; i++) {
      randRow = randNum.nextInt(0,5);
      randCol = randNum.nextInt(5,10);
      if (gridObjects[randRow][randCol] == null) {
        gridObjects[randRow][randCol] = gridObjList[0];
      }
    }
    //Generate in Quad 4
    for(int i = 0; i < numTreasureHouses; i++) {
      randRow = randNum.nextInt(5,10);
      randCol = randNum.nextInt(5,10);
      if (gridObjects[randRow][randCol] == null) {
        gridObjects[randRow][randCol] = gridObjList[0];
      }
    }

    //logic for generating trapped houses
    int numTrappedHouses = 1;
    //Generate in quadrant 1
    for(int i = 0; i < numTrappedHouses; i++) {
      randRow = randNum.nextInt(0,5);
      randCol = randNum.nextInt(0,5);
      if (gridObjects[randRow][randCol] == null) {
        gridObjects[randRow][randCol] = gridObjList[1];
      }
    }
    //Generate in Quad 2
    for(int i = 0; i < numTreasureHouses; i++) {
      randRow = randNum.nextInt(5,10);
      randCol = randNum.nextInt(0,5);
      if (gridObjects[randRow][randCol] == null) {
        gridObjects[randRow][randCol] = gridObjList[1];
      }
    }
    //Generate in Quad 3
    for(int i = 0; i < numTreasureHouses; i++) {
      randRow = randNum.nextInt(0,5);
      randCol = randNum.nextInt(5,10);
      if (gridObjects[randRow][randCol] == null) {
        gridObjects[randRow][randCol] = gridObjList[1];
      }
    }
    //Generate in Quad 4
    for(int i = 0; i < numTreasureHouses; i++) {
      randRow = randNum.nextInt(5,10);
      randCol = randNum.nextInt(5,10);
      if (gridObjects[randRow][randCol] == null) {
        gridObjects[randRow][randCol] = gridObjList[1];
      }
    }

    //logic for generating lost items
    int numLostItems = 4;
    //Generate in quadrant 1
    for(int i = 0; i < numLostItems; i++) {
      randRow = randNum.nextInt(0,5);
      randCol = randNum.nextInt(0,5);
      if (gridObjects[randRow][randCol] == null) {
        gridObjects[randRow][randCol] = gridObjList[2];
      }
    }
    //Generate in Quad 2
    for(int i = 0; i < numLostItems; i++) {
      randRow = randNum.nextInt(5,10);
      randCol = randNum.nextInt(0,5);
      if (gridObjects[randRow][randCol] == null) {
        gridObjects[randRow][randCol] = gridObjList[2];
      }
    }
    //Generate in Quad 3
    for(int i = 0; i < numLostItems; i++) {
      randRow = randNum.nextInt(0,5);
      randCol = randNum.nextInt(5,10);
      if (gridObjects[randRow][randCol] == null) {
        gridObjects[randRow][randCol] = gridObjList[2];
      }
    }
    //Generate in Quad 4
    for(int i = 0; i < numLostItems; i++) {
      randRow = randNum.nextInt(5,10);
      randCol = randNum.nextInt(5,10);
      if (gridObjects[randRow][randCol] == null) {
        gridObjects[randRow][randCol] = gridObjList[2];
      }
    }

    //logic for generating markets
    int numMarkets = 1;
    //Generate in quadrant 1
    for(int i = 0; i < numMarkets; i++) {
      randRow = randNum.nextInt(0,5);
      randCol = randNum.nextInt(0,5);
      if (gridObjects[randRow][randCol] == null) {
        gridObjects[randRow][randCol] = gridObjList[3];
      }
    }
    //Generate in Quad 2
    for(int i = 0; i < numMarkets; i++) {
      randRow = randNum.nextInt(5,10);
      randCol = randNum.nextInt(0,5);
      if (gridObjects[randRow][randCol] == null) {
        gridObjects[randRow][randCol] = gridObjList[3];
      }
    }
    //Generate in Quad 3
    for(int i = 0; i < numMarkets; i++) {
      randRow = randNum.nextInt(0,5);
      randCol = randNum.nextInt(5,10);
      if (gridObjects[randRow][randCol] == null) {
        gridObjects[randRow][randCol] = gridObjList[3];
      }
    }
    //Generate in Quad 4
    for(int i = 0; i < numMarkets; i++) {
      randRow = randNum.nextInt(5,10);
      randCol = randNum.nextInt(5,10);
      if (gridObjects[randRow][randCol] == null) {
        gridObjects[randRow][randCol] = gridObjList[3];
      }
    }

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

      // and the loot is added and the strength is adjusted
      playingPlayer.addLoot(lootedItem);
      playingPlayer.addStrength(lootedItem);
    }
    //logic for looting for lost items aka coins
    Random r = new Random();
    if(object == GridObject.LOST_ITEM_HOUSE){
      lootSound = Gdx.audio.newSound(Gdx.files.internal("LootSound1.wav"));
      lootSound.play();

      playingPlayer.addHouseCoins(r.nextInt(15,45));
      gridObjects[playingPlayer.getX()][playingPlayer.getY()] = GridObject.EMPTY_HOUSE;
    }
  }


  public void tradeItems() {
    if (playerOn(GridObject.CASTLE)) {
      for (int i = 0; i < playingPlayer.getItems().size(); i++) {
        playingPlayer.addCoins(playingPlayer.getItems().get(i));
        // This iterates through the player's inventory, checks
        // what the object is, and then adds the set value to the player's coins
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

  // Notify that the dice has been rolled
  public void startRolling() {
    rollSound = Gdx.audio.newSound(Gdx.files.internal("DiceRoll.wav"));
    rollSound.play();

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

  //for help menu to know where to return to
  boolean fromMenu = false;
  boolean fromRunning = false;

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
          player1.setCharacter(Character.getPreviousCharacter(player1.getCharacter()));
          break;

        case Input.Keys.D:
          player1.setCharacter(Character.getNextCharacter(player1.getCharacter()));
          break;

        case Input.Keys.LEFT:
          player2.setCharacter(Character.getPreviousCharacter(player2.getCharacter()));
          break;

        case Input.Keys.RIGHT:
          player2.setCharacter(Character.getNextCharacter(player2.getCharacter()));
          break;
      }

    } else if (this.state == GameState.RUNNING) {
      switch (keyCode) {
//        case Input.Keys.ESCAPE:
//          // TODO Open Pause Menu?
//          this.state = GameState.MAIN_MENU;
//          break;

        case Input.Keys.R:
          if (turnsLeft == 0 && !diceRolling)
            startRolling();
          break;

        case Input.Keys.L: // Player is trying to loot house
          if (playerOn(GridObject.TREASURE_HOUSE)) {
            lootHouse();}
            if(playerOn(GridObject.LOST_ITEM_HOUSE)){
              lootHouse();}

          break;
        case Input.Keys.T://Trade Tings for Gold lol
          if (playerOn(GridObject.CASTLE)) {
            sellSound = Gdx.audio.newSound(Gdx.files.internal("Sell.wav"));
            sellSound.play();
            tradeItems();
          }//TODO Make it so that the selling sound only plays when the player actually sold something....
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
    //when in help menu scene, waits for H to be pressed, then returns to scene that they initially came from
    else if(this.state == GameState.HELP_MENU){
      switch (keyCode){
        case Input.Keys.H:
          if(fromRunning){
            smoothlySetState(GameState.RUNNING);
            break;
          }
          if(fromMenu){
            smoothlySetState(GameState.MAIN_MENU);
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

    // Update player visibilities
    playingPlayer.updateVisibleArea();

    this.turnsLeft--;

    if (turnsLeft == 0) {
      nextTurn();
    }

    // visibleArea[newX][newY] = true;

    // True if the player can move
    jump.play(10000);
    return true;
  }
}


