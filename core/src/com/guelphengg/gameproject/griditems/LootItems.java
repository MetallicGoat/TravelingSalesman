package com.guelphengg.gameproject.griditems;

import com.badlogic.gdx.graphics.Texture;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;
public enum LootItems{

    SWORD("Sword.png"),
    BEJEWELED_SWORD("Bejeweled_Sword.png"),
    BOW("Bow.png"),
    PALADIN_SHIELD("Paladin_Shield.png"),
    GOLDEN_KEY("Golden_Key.png"),
    GOLDEN_GOBLET("Golden_Goblet.png"),
    CRYSTAL_GOBLET("Crystal_Goblet.png"),
    DIAMOND_RING("Diamond_Ring.png"),
    DRAGON_SCROLL("Dragon_Scroll.png");

    private final Texture texture;

    LootItems(String id) {
        this.texture = new Texture(id);
    }

    public void render(GameGrid grid, int x, int y) {
        grid.renderTextureInGrid(x, y, texture);
    }

}
