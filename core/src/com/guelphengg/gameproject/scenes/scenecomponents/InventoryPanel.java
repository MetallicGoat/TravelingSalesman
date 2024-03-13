package com.guelphengg.gameproject.scenes.scenecomponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.GameManager;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.griditems.LootItems;
import com.guelphengg.gameproject.griditems.Player;
import com.guelphengg.gameproject.util.AdvancedShapeRenderer;

public class InventoryPanel {

    // The grid that the inventory will be rendered on
    // GameGrid is a class that represents a grid that can be drawn on the screen
    // GameGrid is also used for the map
    final GameGrid inventoryGrid = new GameGrid(
            (int) (SceneManager.getViewWidth() * .3), // height
            (int) (SceneManager.getViewWidth() * .3 / 3), // width
            (int) (SceneManager.getViewWidth() * .58), // x
            (int) (SceneManager.getViewHeight() * .47), // y
            2, 7 // boxesX, boxesY
    );

    // Render a certian players inventory
    public void render(Player player) {

        // Render the background
        final AdvancedShapeRenderer shapeRenderer = SceneManager.getShapeRenderer();
        final GameManager manager = Accessor.getGameManager();

        // This enables transparency
        Gdx.gl.glEnable(GL20.GL_BLEND);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Set the color to the players colour
        shapeRenderer.setColor(manager.getPlayingPlayer().getCharacter().getColour());

        // Draw the transparent background
        shapeRenderer.roundedRect(
                inventoryGrid.getCornerX() - 20,
                inventoryGrid.getCornerY() - 20,
                inventoryGrid.getGridWidth() + 40,
                inventoryGrid.getGridHeight() + 40,
                10
        );

        shapeRenderer.end();

        // Render the grid
        inventoryGrid.renderGrid(Color.WHITE);

        // Draw the lootitems in the inventory
        // draws only the playing players inventory
        int x = 0;
        int y = 5;
        for (LootItems item : Accessor.getGameManager().getPlayingPlayer().getItems()) {

            item.render(inventoryGrid, x, y);
            x++;
            if (x > 0 && x % 2 == 0) {
                x = 0;
                y--;

            }
        }
    }
}
