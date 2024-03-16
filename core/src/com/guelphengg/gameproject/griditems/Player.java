package com.guelphengg.gameproject.griditems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guelphengg.gameproject.Character;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {
    // base strength for a character
    private final int BASE_STRENGTH = 10;

    // This array of loot items represents the players inventory
    private final List<LootItems> loot = new ArrayList<>();
    // Offset used for offseting the players position when drawing it on the game grid
    // For when one is small and to thr right
    public int yOffset = 0;
    public int xOffset = 0;
    // stateTime is used to keep track of the current frame of the player's animation
    float stateTime = 0f;
    // The character of the player
    private Character character;
    // If the player is currently small
    private boolean small = true;
    // misc player attributes
    private int strength = 10, health = 100, coins = 0;
    // The x and y position of the player on the game grid
    private int x;
    private int y;

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
        stateTime += Gdx.graphics.getDeltaTime();

        return character.getAnimation().getKeyFrame(stateTime, true);
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
        if (newStrength < 10) { // catches if the new value is less than the base strength
            newStrength = BASE_STRENGTH;
        }

        this.strength = newStrength;
    }

    public void addStrength(LootItems item) {
        this.strength += item.getDamage();
    }

    // get the health of the player
    public int getHealth() {
        return health;
    }

    // change the health of the player
    void setHealth(int health) {
        this.health = health;
    }

    // get the amount of coins the player has
    public int getCoins() {
        return coins;
    }

    // get the character of the player
    public Character getCharacter() {
        return character;
    }

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

    // draw the player on the gamegrid at its current pos
    public void render(GameGrid gameGrid) {
        render(gameGrid, this.x, this.y);
    }

    // draw the player on the gamegrid at a specific pos (overriding player position)
    public void render(GameGrid gameGrid, int x, int y) {
        gameGrid.renderTextureInGrid(x, y, getCurrFrame(), this.small ? 0.5 : 1, xOffset, yOffset);
    }

    public void addLoot(LootItems item) {
        loot.add(item);
    }

    public List<LootItems> getItems() {

        return loot;
    }

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

    public void addCoins(LootItems item) {
        this.coins += item.getSellPrice();
    }

    public void removeCoins(int amount) {
        this.coins -= amount;
    }

    public void removeWeapon(LootItems lootedItem) { // specifically for looting houses
        if ((lootedItem == LootItems.SWORD || lootedItem == LootItems.BEJEWELED_SWORD || lootedItem == LootItems.BOW)
                && (this.getItems().contains(LootItems.SWORD) || this.getItems().contains(LootItems.BEJEWELED_SWORD) || this.getItems().contains(LootItems.BOW))) {

            // If there is a weapon, the below for loop will run and remove all weapons from the inventory
            this.removeWeapon();
        }

    }

    public void removeWeapon() { // This is for removing in general
        Iterator<LootItems> iterator = this.getItems().iterator();

        while (iterator.hasNext()) {
            final LootItems item = iterator.next();

            if (item == LootItems.SWORD || item == LootItems.BOW || item == LootItems.BEJEWELED_SWORD)
                iterator.remove();
        }

        // The strength is then reset back to the base number
        this.setStrength(0);
    }
}
