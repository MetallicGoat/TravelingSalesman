package com.guelphengg.gameproject.griditems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guelphengg.gameproject.Character;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;

import java.util.ArrayList;
import java.util.List;

public class Player {

  // This array of loot items represents the players inventory
  private final List<LootItems> loot= new ArrayList<>();

  // The character of the player
  private Character character;

  // If the player is currently small
  private boolean small = true;

  // misc player attributes
  private int strength = 10, health = 100, coins = 0;

  // Offset used for offseting the players position when drawing it on the game grid
  // For when one is small and to thr right
  public int yOffset = 0;
  public int xOffset = 0;

  // The x and y position of the player on the game grid
  private int x;
  private int y;

  // stateTime is used to keep track of the current frame of the player's animation
  float stateTime = 0f;

  // constructor for the player
  public Player(int x, int y, Character character) {
    this.x = x;
    this.y = y;

    this.character = character;
  }

  // get the name of the player using their character
  public String getName() {
    return character.getName();
  }

  public void setCharacter(Character character) {
    this.character = character;
  }

  // used to check if the gamegrid should make the player small
  // For cases where players share a square with another player or a GridObject
  public void setSmall(boolean small) {
    this.small = small;
  }

  // if the player is currently being displayed small
  public boolean isSmall(){
    return this.small;
  }

  // The current frame that should be displayed for the player
  // changes based on the current time (for movement animation)
  public TextureRegion getCurrFrame() {
    stateTime += Gdx.graphics.getDeltaTime();

    return character.getAnimation().getKeyFrame(stateTime, true);
  }

  // If that player is at the start square (which is off the grid)
  public boolean isAtStart() {
    return (this.x == 10 && this.y == 0) ;
  }

  // change the strength of the player
  void setStrength(int strength) {
    this.strength = strength;
  }

  // get the strength of the player
  public int getStrength() {
    return this.strength;
  }

  // change the health of the player
  void setHealth(int health){
    this.health = health;
  }

  // get the health of the player
  public int getHealth() {
    return health;
  }

  // get the amount of coins the player has
  public int getCoins() {
    return coins;
  }

  // get the character of the player
  public Character getCharacter() {
    return character;
  }

  // player's x
  public int getX() {
    return this.x;
  }

  // player's y
  public int getY() {
    return this.y;
  }

  // change the players x
  public void setX(int x) {
    this.x = x;
  }

  // change the players y
  public void setY(int y) {
    this.y = y;
  }

  // draw the player on the gamegrid at its current pos
  public void render(GameGrid gameGrid) {
    render(gameGrid, this.x, this.y);
  }

  // draw the player on the gamegrid at a specific pos (overriding player position)
  public void render(GameGrid gameGrid, int x, int y) {
    gameGrid.renderTextureInGrid(x, y, getCurrFrame(), this.small ? 0.5 : 1, xOffset, yOffset);
  }

  // adds a loot item to the player's inventory
  public void addLoot(LootItems item){
    loot.add(item);
  }

  // gets all loot items in the player's inventory
  public List<LootItems> getItems(){
    return loot;
  }
}
