package com.guelphengg.gameproject.griditems;

import com.badlogic.gdx.graphics.Texture;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;

import java.util.ArrayList;
import java.util.List;

public enum LootItems {
  /**
   * This enum represents every collectable item in the game
   */
  BEJEWELED_SWORD("Bejeweled_Sword.png", 0, 30, 0, 10000, 1, ItemType.TREASURE, WeaponType.NOT_APPLICABLE),
  PALADIN_SHIELD("Paladin_Shield.png", 0, 50, 0.8, 10, 1, ItemType.TREASURE, WeaponType.NOT_APPLICABLE),
  GOLDEN_KEY("Golden_Key.png", 0, 30, 0, 10000, 1, ItemType.TREASURE, WeaponType.NOT_APPLICABLE),
  GOLDEN_GOBLET("Golden_Goblet.png", 0, 45, 0, 10000, 1, ItemType.TREASURE, WeaponType.NOT_APPLICABLE),
  CRYSTAL_GOBLET("Crystal_Goblet.png", 0, 77, 0, 10000, 1, ItemType.TREASURE, WeaponType.NOT_APPLICABLE),
  DIAMOND_RING("Diamond_Ring.png", 0, 80, 0, 10000, 1, ItemType.TREASURE, WeaponType.NOT_APPLICABLE),
  DRAGON_SCROLL("Dragon_Scroll.png", 0, 100, 0, 10000, 1, ItemType.TREASURE, WeaponType.NOT_APPLICABLE),

  SWORD("Sword.png", 0, 10, 0, 10000, 1, ItemType.WEAPON, WeaponType.SWORD),
  ICE_SWORD("Ice Sword.png", 10, 100, 0, 10000, 0, ItemType.WEAPON, WeaponType.SWORD),
  FIRE_SWORD("Fire Sword.png", 15, 150, 0, 10000, 0, ItemType.WEAPON, WeaponType.SWORD),
  EARTH_SWORD("Earth Sword.png", 5, 50, 0, 10000, 0, ItemType.WEAPON, WeaponType.SWORD),

  BOW("Bow.png", 0, 16, 0, 10000, 1, ItemType.WEAPON, WeaponType.BOW),
  ICE_BOW("Ice_Bow_Pull_2.png", 10, 100, 0, 10000, 0, ItemType.WEAPON, WeaponType.BOW),
  FIRE_BOW("Final_Fire_Demon_Bow_Pull_0.png", 15, 150, 0, 10000, 0, ItemType.WEAPON, WeaponType.BOW),
  EARTH_BOW("Earth_Bow.png", 5, 50, 0, 10000, 0, ItemType.WEAPON, WeaponType.BOW),

  ICE_WAND("Ice Wand.png", 10, 100, 0, 10000, 0, ItemType.WEAPON, WeaponType.SWORD),
  FIRE_WAND("Fire Wand.png", 15, 150, 0, 10000, 0, ItemType.WEAPON, WeaponType.SWORD),
  EARTH_WAND("Earth Wand.png", 5, 50, 0, 10000, 0, ItemType.WEAPON, WeaponType.SWORD),

  TREASURE_MAP("TreasureMap.png", 0, 100, 0, 10000, 1, ItemType.OTHER, WeaponType.NOT_APPLICABLE),
  BLANK("Blank.png", 0,0, 0, 0, 0, ItemType.OTHER, WeaponType.NOT_APPLICABLE);

  private final Texture texture;
  private final int damage;
  private final int sellPrice;
  private final int durability;
  private final int itemPower;
  private final ItemType itemType;
  private final WeaponType weaponType;

  // id represents the file name of the texture
  LootItems(String id, int damage, int sellPrice, double protection, int durability, int power, ItemType type, WeaponType weaponType) {
    this.texture = new Texture(id);
    this.damage = damage;
    this.durability = durability;
    this.sellPrice = sellPrice;
    this.itemPower = power;
    this.itemType = type;
    this.weaponType = weaponType;
  }

  // Tries to generate a random LootItem that the player does not already have
  public static LootItems getRandomItem(Player player) {
    final List<LootItems> values = new ArrayList<>(List.of(values()));

    // Remove all items the player already has (+ treasure maps are not given at treasure houses)
    values.removeIf(item -> player.getItems().contains(item) || item == TREASURE_MAP);

    // They have all the items, so just return a random one
    if (values.isEmpty())
      return values()[(int) (Math.random() * values().length)];
    else // Return one of the remaining
      return values.get((int) (Math.random() * values.size()));
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

  public ItemType getItemType() {
    return itemType;
  }

  public WeaponType getWeaponType() {
    return weaponType;
  }
}
