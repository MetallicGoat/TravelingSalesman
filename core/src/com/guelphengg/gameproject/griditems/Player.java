package com.guelphengg.gameproject.griditems;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.Character;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;

import java.util.ArrayList;
import java.util.List;

public class Player {

  // This array of loot items represents the players inventory
  private final List<LootItems> loot = new ArrayList<>();

  // The area the player is currently able to see
  private final boolean[][] visibleArea = new boolean[10][10];

  // Offset used for offseting the players position when drawing it on the game grid
  // For when one is small and to thr right
  public int yOffset = 0;
  public int xOffset = 0;

  // The character of the player
  private Character character;

  // If the player is currently small
  private boolean small = true;

  // misc player attributes
  private int strength = 10, health = 100, coins = 0, power = 0;

  // The x and y position of the player on the game grid
  private int x;
  private int y;

  // The colour of the player
  private final Color solidColour;

  private boolean treasureLocated = false;
  private boolean tresaureCollected = false;
  private int tresaureX = 0;
  private int tresaureY = 0;


  // constructor for the player
  public Player(int x, int y, Character character) {
    this.x = x;
    this.y = y;

    this.character = character;
    this.solidColour = this.character.getColour();
  }

  // get the name of the player using their character
  public String getName() {
    return character.getName();
  }

  // if the player is currently being displayed small
  public boolean isSmall() {
    return this.small;
  }

  // used to check if the gamegrid should make the player small
  // For cases where players share a square with another player or a GridObject
  public void setSmall(boolean small) {
    this.small = small;
  }

  // The current frame that should be displayed for the player
  // changes based on the current time (for movement animation)
  public TextureRegion getCurrFrame() {
    return character.getCurrentFrame();
  }

  // The current frame that should be displayed for the player
  // changes based on the current time (for movement animation)
  public TextureRegion getCurrFrameLeft() {
    return character.getCurrentFrameLeft();
  }

  // The current frame that should be displayed for the player
  // changes based on the current time (for movement animation)
  public TextureRegion getCurrFrameRight() {
    return character.getCurrentFrameRight();
  }

  // If that player is at the start square (which is off the grid)
  public boolean isAtStart() {
    return (this.x == 10 && this.y == 0);
  }

  // get the strength of the player
  public int getStrength() {
    return this.strength;
  }

  // change the strength of the player
  public void setStrength(int newStrength) {
    this.strength = newStrength;
  }

  public void addStrength(LootItems item) {
    this.strength += item.getDamage();
  }

  // get the amount of coins the player has
  public int getCoins() {
    return coins;
  }

  // get the character of the player
  public Character getCharacter() {
    return character;
  }

  // sets the character of the player
  public void setCharacter(Character character) {
    this.character = character;
  }

  // player's x
  public int getX() {
    return this.x;
  }

  // change the players x
  public void setX(int x) {
    this.x = x;
  }

  // player's y
  public int getY() {
    return this.y;
  }

  // change the players y
  public void setY(int y) {
    this.y = y;
  }

  // Update the visible area of the player based on their current position
  public void updateVisibleArea() {
    // The player can see a 3 by 3 area around them
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        if (x + i >= 0 && x + i <= 9 && y + j >= 0 && y + j <= 9) {
          final int squareX = Math.max(0, Math.min(9, x + i));
          final int squareY = Math.max(0, Math.min(9, y + j));

          visibleArea[squareX][squareY] = true;
        }
      }
    }
  }

  // Check if a player has explored a certain square
  public boolean canPlayerSee(int x, int y) {
    return visibleArea[x][y];
  }

  // draw the player on the gamegrid at its current pos
  public void render(GameGrid gameGrid) {
    render(gameGrid, this.x, this.y);
  }

  // draw the player on the gamegrid at a specific pos (overriding player position)
  public void render(GameGrid gameGrid, int x, int y) {
    gameGrid.renderTextureInGrid(x, y, getCurrFrame(), !isSmall(), xOffset);
  }

  // adds loot to the players inventory
  public void addLoot(LootItems item) {
    loot.add(item);

    if (item == LootItems.TREASURE_MAP)
      findTreasure();
  }

  // Generate a random location where the player will find a treasure
  public void findTreasure() {
    if (treasureLocated)
      return;

    final int[] treasureLocation = getRandomHiddenTreasure();

    if (treasureLocation == null)
      return;

    treasureLocated = true;
    tresaureX = treasureLocation[0];
    tresaureY = treasureLocation[1];
  }

  public boolean hasLocatedTreasure() {
    return treasureLocated;
  }

  public boolean isTreasureLocVisible() {
    return treasureLocated && !tresaureCollected;
  }

  public int getTreasureX() {
    return tresaureX;
  }

  public int getTreasureY() {
    return tresaureY;
  }

  public void tryCollectTreasure() {
    if (!tresaureCollected && tresaureX == x && tresaureY == y) {
      tresaureCollected = true;
      loot.remove(LootItems.TREASURE_MAP);
      addCoins(1000);
    }
  }

  private int[] getRandomHiddenTreasure() {
    final List<int[]> locations = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        if (!visibleArea[i][j] && Accessor.getGameManager().gridObjects[i][j] == GridObject.TREASURE_HOUSE) {
          locations.add(new int[] {i, j});
        }
      }
    }

    if (locations.isEmpty())
      return null;

    return locations.get((int) (Math.random() * locations.size()));
  }

  // returns a list of the players loot
  public List<LootItems> getItems() {
    return loot;
  }

  // gets the damage based on weapon in the inventory
  public int getDamage() {
    int damage = 1;
    if (this.loot.contains(LootItems.SWORD)) {
      damage += LootItems.SWORD.getDamage();
    } else if (this.loot.contains(LootItems.BEJEWELED_SWORD)) {
      damage += LootItems.BEJEWELED_SWORD.getDamage();
    } else if (this.loot.contains(LootItems.BOW)) {
      damage += LootItems.BOW.getDamage();
    }
    return damage;
  }

  // adds and removes coins to the players variable
  public void addCoins(int coins) {
    this.coins += coins;
  }

  public void removeCoins(int amount) {
    this.coins -= amount;
    if (this.coins < 0) {
      this.coins = 0;
    }
  }

  // removes the weapon from the player's inventory
  public void removeWeapon() {
    this.getItems().removeIf(item -> item.getItemType() == ItemType.WEAPON);
//        this.getItems().removeIf(item -> item == LootItems.SWORD || item == LootItems.BOW || item == LootItems.BEJEWELED_SWORD);

    // The strength is then reset back to the base number
    this.setStrength(0);
  }

  // removes the strength from a player
  public void removeStrength(int amount) {
    this.strength -= amount;
  }

  // Get the power player has
  public int getPower() {
    return power;
  }

  // adds to the players power based on the item
  public void addPower(LootItems item) {
    this.power += item.getItemPower();
  }

  // removes power from a player
  public void removePower(int pwr) {
    this.power -= pwr;
  }

  public Color getSolidColour() {
    this.solidColour.a = 1F;
    return solidColour;
  }

  public Color getTransColour() {
    this.solidColour.a = 0.4F;
    return solidColour;
  }
}
