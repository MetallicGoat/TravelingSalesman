package com.guelphengg.gameproject.griditems;

import com.badlogic.gdx.graphics.Texture;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum LootItems {
  /**
   * This enum represents every collectable item in the game
   */
  BEJEWELED_SWORD("Bejeweled_Sword.png", 10, 30, 0, 10000, 1, ItemType.WEAPON, WeaponType.SWORD, 0),
  PALADIN_SHIELD("Paladin_Shield.png", 0, 50, 0.8, 10, 1, ItemType.TREASURE, WeaponType.NOT_APPLICABLE, 0),
  GOLDEN_KEY("Golden_Key.png", 0, 30, 0, 10000, 1, ItemType.TREASURE, WeaponType.NOT_APPLICABLE, 0),
  GOLDEN_GOBLET("Golden_Goblet.png", 0, 45, 0, 10000, 1, ItemType.TREASURE, WeaponType.NOT_APPLICABLE, 0),
  CRYSTAL_GOBLET("Crystal_Goblet.png", 0, 77, 0, 10000, 1, ItemType.TREASURE, WeaponType.NOT_APPLICABLE, 0),
  DIAMOND_RING("Diamond_Ring.png", 0, 80, 0, 10000, 1, ItemType.TREASURE, WeaponType.NOT_APPLICABLE, 0),
  DRAGON_SCROLL("Dragon_Scroll.png", 0, 100, 0, 10000, 1, ItemType.TREASURE, WeaponType.NOT_APPLICABLE, 0),

  SWORD("Sword.png", 2, 10, 0, 10000, 1, ItemType.WEAPON, WeaponType.SWORD, 0),
  ICE_SWORD("Ice Sword.png", 10, 100, 0, 10000, 0, ItemType.WEAPON, WeaponType.SWORD, 0),
  FIRE_SWORD("Fire Sword.png", 15, 150, 0, 10000, 0, ItemType.WEAPON, WeaponType.SWORD, 0),
  EARTH_SWORD("Earth Sword.png", 5, 50, 0, 10000, 0, ItemType.WEAPON, WeaponType.SWORD, 0),

  BOW("Bow.png", 2, 16, 0, 10000, 1, ItemType.WEAPON, WeaponType.BOW, 9),
  ICE_BOW("Ice_Bow_Pull_2.png", 10, 100, 0, 10000, 0, ItemType.WEAPON, WeaponType.BOW, 9),
  FIRE_BOW("Final_Fire_Demon_Bow_Pull_0.png", 15, 150, 0, 10000, 0, ItemType.WEAPON, WeaponType.BOW, 9),
  EARTH_BOW("Earth_Bow.png", 5, 50, 0, 10000, 0, ItemType.WEAPON, WeaponType.BOW, 9),

  // WAND("TreasureX.png", 2, 100, 0, 10000, 0, ItemType.WEAPON, WeaponType.WAND, 6),

  ICE_WAND("Ice Wand.png", 10, 100, 0, 10000, 0, ItemType.WEAPON, WeaponType.WAND, 6),
  GOLDEN_WAND("Golden Wand.png", 15, 150, 0, 10000, 0, ItemType.WEAPON, WeaponType.WAND, 6),
  EARTH_WAND("Earth Wand.png", 5, 50, 0, 10000, 0, ItemType.WEAPON, WeaponType.WAND, 6),

  TREASURE_MAP("TreasureMap.png", 0, 100, 0, 10000, 1, ItemType.OTHER, WeaponType.NOT_APPLICABLE, 0),
  BLANK("Blank.png", 0,0, 0, 0, 0, ItemType.OTHER, WeaponType.NOT_APPLICABLE, 0);




  private final Texture texture;
  private final int damage;
  private final int sellPrice;
  private final int durability;
  private final int itemPower;
  private final ItemType itemType;
  private final WeaponType weaponType;
  private final int animationSpeed;

  // id represents the file name of the texture
  LootItems(String id, int damage, int sellPrice, double protection, int durability, int power, ItemType type, WeaponType weaponType, int speed) {
    this.texture = new Texture(id);
    this.damage = damage;
    this.durability = durability;
    this.sellPrice = sellPrice;
    this.itemPower = power;
    this.itemType = type;
    this.weaponType = weaponType;
    this.animationSpeed = speed;
  }

  // Tries to generate a random LootItem that the player does not already have
  public static LootItems getRandomTreasureItem(Player player) {
    final List<LootItems> treasures = new ArrayList<>();

    for (LootItems item : values()) {
      if (item.getItemType() == ItemType.TREASURE) {
        treasures.add(item);
      }
    }

    // If players dont have a weapon, they might find a basic one in a loot house
    if (player.getWeaponDamage() == 0) {
      treasures.add(BOW);
      treasures.add(SWORD);
    }

    Collections.shuffle(treasures);

    return treasures.get(0);
  }

  public static List<LootItems> getMarketWeapons(Player player) {
    final List<LootItems> weapons = new ArrayList<>();

    for (LootItems item : values()) {
      if (item.getItemType() == ItemType.WEAPON && !player.getItems().contains(item)) {
        weapons.add(item);
      }
    }

    // Dont sell regular weapons in the shop
    weapons.remove(SWORD);
    weapons.remove(BOW);

    return weapons;
  }

  // Method to draw the LootItem in certain square on whatever grid u want
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

  public boolean containsItem(LootItems[] arr, LootItems item){
    for(int i = 0; i<arr.length-1; i++){
      if(arr[i] == item){
        return true;
      }
    }
    return false;
  }

  public int getAnimationSpeed() { return animationSpeed; }
}
