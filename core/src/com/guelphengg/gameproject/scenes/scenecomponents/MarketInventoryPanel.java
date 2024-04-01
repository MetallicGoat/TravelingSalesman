package com.guelphengg.gameproject.scenes.scenecomponents;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.griditems.ItemType;
import com.guelphengg.gameproject.griditems.LootItems;

public class MarketInventoryPanel {

  // The grid that the inventory will be rendered on
  // GameGrid is a class that represents a grid that can be drawn on the screen
  // GameGrid is also used for the map
  final GameGrid inventoryGrid = new GameGrid(
      (int) (SceneManager.getViewWidth() * .1), // height
      (int) (SceneManager.getViewWidth() * .25), // width

      (int) (SceneManager.getViewWidth() * .35), // x
      (int) (SceneManager.getViewHeight() * .58), // y
      5, 2 // boxesX, boxesY
  );

  final GameGrid weaponGrid = new GameGrid(
      (int) (SceneManager.getViewWidth() * .05), // height
      (int) (SceneManager.getViewWidth() * .05), // width
      (int) (SceneManager.getViewWidth() * .65), // x
      (int) (SceneManager.getViewHeight() * .58), // y
      1, 1 // boxesX, boxesY
  );

  // Render a certian players inventory
  public void render() {

    // Render the grid
    inventoryGrid.renderGrid(Color.WHITE);
    weaponGrid.renderGrid(Color.WHITE);

    // Scoreboard Text
    final SpriteBatch batch = SceneManager.getSpriteBatch();
    final BitmapFont font = SceneManager.getFont();

    // Daw all the text for the scoreboard
    // all the line positions are hard coded (obviously)
    batch.begin();

    font.getData().setScale(1.5F);
    //font.draw(batch, "Inventory", inventoryGrid.getCornerX(), inventoryGrid.getCornerY() + 325);
    font.draw(batch, "Weapon", weaponGrid.getCornerX() - 10, inventoryGrid.getCornerY() + 90);

    batch.end();

    // Draw the lootitems in the inventory
    // draws only the playing players inventory
    int x = 0;
    int y = 1;
    for (LootItems item : Accessor.getGameManager().getPlayingPlayer().getItems()) {
      if (item.getItemType() == ItemType.WEAPON)
        continue;

      item.render(inventoryGrid, x, y);
      x++;

      if (x > 0 && x % 5 == 0) {
        x = 0;
        y--;
      }
    }

    for (LootItems item : Accessor.getGameManager().getPlayingPlayer().getItems()) {
      if (item.getItemType() == ItemType.WEAPON) {
        item.render(weaponGrid, 0, 0);
        break;
      }
    }
  }
}
