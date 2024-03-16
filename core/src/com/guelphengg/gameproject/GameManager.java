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

import java.util.Random;

public class GameManager {
    // TODO allow players to pick their character
    private final Player player1 = new Player(10, 0, Character.GREENIE);
    private final Player player2 = new Player(10, 0, Character.REDDIE);
    public GridObject[][] gridObjects = new GridObject[10][10];
    private int nextRoll = 0;
    private int turnsLeft = 0;
    private Sound jump = Gdx.audio.newSound(Gdx.files.internal("JumpTS.wav"));
    // What phase the game is currently in
    private GameState state = GameState.MAIN_MENU;
    private Player playingPlayer = player1;
    private boolean largeMap = true;
    private boolean diceRolling = false;
    private long lastRollTime = 0;
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

        //TODO Replace this with a system to randomly generate positions

        Random randNum = new Random();

        //generates a random position for the castle in the middle 4 squares of the grid
        int castleRow = randNum.nextInt(4, 6);
        int castleCol = randNum.nextInt(4, 6);
        gridObjects[castleRow][castleCol] = GridObject.CASTLE;

        //generates random ints between 0-9
        int randRow;
        int randCol;

        //array of all structures in the game
        GridObject[] gridObjList = {GridObject.TREASURE_HOUSE, GridObject.TRAPPED_HOUSE, GridObject.EMPTY_HOUSE};

        int typesStructs = 3; //number of different types of structures that be generated
        int numStructs = 4; //number of each structure to be generated

        for (int i = 0; i < typesStructs; i++) {
            for (int j = 0; j < numStructs; j++) {
                randRow = randNum.nextInt(0, 10);
                randCol = randNum.nextInt(0, 10);

                if (gridObjects[randRow][randCol] == null) {
                    gridObjects[randRow][randCol] = gridObjList[i];
                } else {
                    randRow = randNum.nextInt(0, 10);
                    randCol = randNum.nextInt(0, 10);
                    gridObjects[randRow][randCol] = gridObjList[i];
                }
            }
        }

//    gridObjects[0][9] = GridObject.TRAPPED_HOUSE;
//    gridObjects[4][8] = GridObject.TREASURE_HOUSE;
//    gridObjects[1][6] = GridObject.TREASURE_HOUSE;
//    gridObjects[6][8] = GridObject.TRAPPED_HOUSE;
//    gridObjects[7][7] = GridObject.TREASURE_HOUSE;
//    gridObjects[8][8] = GridObject.TRAPPED_HOUSE;
//    gridObjects[8][9] = GridObject.TREASURE_HOUSE;
//    gridObjects[9][2] = GridObject.TREASURE_HOUSE;
//    gridObjects[5][6] = GridObject.TREASURE_HOUSE;
//    gridObjects[5][8] = GridObject.TRAPPED_HOUSE;
//    gridObjects[9][1] = GridObject.TREASURE_HOUSE;
//    gridObjects[8][1] = GridObject.TREASURE_HOUSE;
//    gridObjects[9][3] = GridObject.TREASURE_HOUSE;
//    gridObjects[7][1] = GridObject.TREASURE_HOUSE;


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
            lootSound = Gdx.audio.newSound(Gdx.files.internal("LootSound1.wav"));
            lootSound.play();

            lootedItem = LootItems.getRandomItem();
            gridObjects[playingPlayer.getX()][playingPlayer.getY()] = GridObject.EMPTY_HOUSE;
            // The below if loop checks if there is a weapon in the inventory already.
            playingPlayer.removeWeapon(lootedItem);

            // and the loot is added and the strength is adjusted
            playingPlayer.addLoot(lootedItem);
            playingPlayer.addStrength(lootedItem);
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

    // Handles all game input
    public void gameInput(int keyCode) {
        if (this.state == GameState.MAIN_MENU) {
            switch (keyCode) {
                case Input.Keys.SPACE:
                    smoothlySetState(GameState.GAME_SETUP);

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
                        lootHouse();
                    }

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

                case Input.Keys.V:
                    // Change the grid view
                    largeMap = !largeMap;
                    break;

                case Input.Keys.H:

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
        jump.play(10000);
        return true;
    }
}


