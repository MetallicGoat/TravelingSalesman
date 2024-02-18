package com.guelphengg.gameproject;

public class Player {
    static int num = 1;
    private int ID, strength, health;

    public Player(String name, int health, int strength, boolean hasWeapon) { // This is the base model for starting the game
        this.setStrength(10);
        this.setID();
    }

    void setID() {
        this.ID = Player.num;
        num++;
    }

    public int getID() {
        return ID;
    }

    void setStrength(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return this.strength;
    }

    void setHealth(int health){
        this.health = health;
    }

    public int getHealth() {
        return health;
    }
}
