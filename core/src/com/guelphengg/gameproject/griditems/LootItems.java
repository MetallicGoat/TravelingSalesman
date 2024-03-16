package com.guelphengg.gameproject.griditems;

import com.badlogic.gdx.graphics.Texture;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;

public enum LootItems {
    /**
     * This enum represents every collectable item in the game
     */
    SWORD("Sword.png", 2, 10, 0, 10000, ItemType.WEAPON),
    BEJEWELED_SWORD("Bejeweled_Sword.png", 10, 30, 0, 10000, ItemType.WEAPON),
    BOW("Bow.png", 5, 16, 0, 10000, ItemType.WEAPON),

    PALADIN_SHIELD("Paladin_Shield.png", 0, 50, 0.8, 10, ItemType.TREASURE),
    GOLDEN_KEY("Golden_Key.png", 0, 30, 0, 10000, ItemType.TREASURE),
    GOLDEN_GOBLET("Golden_Goblet.png", 0, 45, 0, 10000, ItemType.TREASURE),
    CRYSTAL_GOBLET("Crystal_Goblet.png", 0, 77, 0, 10000, ItemType.TREASURE),
    DIAMOND_RING("Diamond_Ring.png", 0, 80, 0, 10000, ItemType.TREASURE),
    DRAGON_SCROLL("Dragon_Scroll.png", 0, 100, 0, 10000, ItemType.TREASURE);

    private final Texture texture;
    private final int damage;
    private final int sellPrice;
    private final int durability;
    private final double protection;
    private final ItemType itemType;

    // id represents the file name of the texture
    LootItems(String id, int damage, int sellPrice, double protection, int durability, ItemType type) {
        this.texture = new Texture(id);
        this.damage = damage;
        this.durability = durability;
        this.sellPrice = sellPrice;
        this.protection = protection;
        this.itemType = type;
    }

    // Returns a random LootItem
    public static LootItems getRandomItem() {
        return values()[(int) (Math.random() * values().length)];
    }

    // Method to draw the LootItem in certian square on whatever grid u want
    public void render(GameGrid grid, int x, int y) {
        grid.renderTextureInGrid(x, y, texture);
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

    public double getProtection() {
        return protection;
    }

    public ItemType getItemType() {
        return itemType;
    }
}
