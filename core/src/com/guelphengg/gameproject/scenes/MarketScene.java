package com.guelphengg.gameproject.scenes;

import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.griditems.GridObject;
import com.guelphengg.gameproject.griditems.LootItems;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;

public class MarketScene extends Scene{
//    LootItems item_1 = LootItems.getRandomItem();
//    LootItems item_2 = LootItems.getRandomItem();
//    LootItems item_3 = LootItems.getRandomItem();
//    LootItems item_4 = LootItems.getRandomItem();

    GameGrid marketGrid = new GameGrid(200, 200, 0, 0, 1, 4);

    public MarketScene() {
        super(GameState.MARKET);
    }

    @Override
    public void render() {

    }
}
