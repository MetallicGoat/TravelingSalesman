package com.guelphengg.gameproject.griditems;

import com.badlogic.gdx.graphics.Texture;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;

public enum LootItems {
  /**
   * This enum represents every collectable item in the game
   */
  SWORD("Sword.png", 2, 10, 10000, 1, ItemType.WEAPON, ItemType.SWORD),
  BEJEWELED_SWORD("Bejeweled_Sword.png", 10, 30, 10000, 1, ItemType.WEAPON, ItemType.SWORD),
  BOW("Bow.png", 5, 16, 10000, 1, ItemType.WEAPON, ItemType.BOW),

  GOLDEN_KEY("Golden_Key.png", 0, 30, 10000, 0, ItemType.TREASURE, ItemType.TREASURE),
  GOLDEN_GOBLET("Golden_Goblet.png", 0, 45,  10000, 0, ItemType.TREASURE, ItemType.TREASURE),
  CRYSTAL_GOBLET("Crystal_Goblet.png", 0, 77,  10000, 0, ItemType.TREASURE, ItemType.TREASURE),
  DIAMOND_RING("Diamond_Ring.png", 0, 80,  10000, 0, ItemType.TREASURE, ItemType.TREASURE),
  DRAGON_SCROLL("Dragon_Scroll.png", 0, 100,  10000, 0, ItemType.TREASURE, ItemType.TREASURE);

  private final Texture texture;
  private final int damage;
  private final int sellPrice;
  private final int durability;
  private final int itemPower;
  private final ItemType itemType;
  private final ItemType fancyItemType; // this specifically is used for tie breaking mechanics to distinguish between a sword, wand, and bow.

  // id represents the file name of the texture
  LootItems(String id, int damage, int sellPrice, int durability, int power, ItemType type, ItemType fancy) {
    this.texture = new Texture(id);
    this.damage = damage;
    this.durability = durability;
    this.sellPrice = sellPrice;
    this.itemPower = power;
    this.itemType = type;
    this.fancyItemType = fancy;
  }


  // Method to draw the LootItem in certian square on whatever grid u want
  public void render(GameGrid grid, int x, int y) {
    grid.renderTextureInGrid(x, y, texture);
  }

  public int getItemPower() {
    return itemPower;
  }


  public int getDamage() {
    return damage;
  }

  public int getDurability() {
    return durability;
  }

  public int getSellPrice() {
    return sellPrice;
  }

  // Returns a random LootItem
  public static LootItems getRandomItem() {
    return values()[(int) (Math.random() * values().length)];
  }

  public ItemType getItemType() {
    return itemType;
  }
}
