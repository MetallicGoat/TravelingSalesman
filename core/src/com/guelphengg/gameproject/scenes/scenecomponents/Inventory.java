package com.guelphengg.gameproject.scenes.scenecomponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.griditems.Player;
import com.guelphengg.gameproject.util.AdvancedShapeRenderer;

public class Inventory {

  final GameGrid inventoryGrid = new GameGrid(
      (int) (SceneManager.getViewWidth() * .3), // height
      (int) (SceneManager.getViewWidth() * .3 / 3), // width
      (int) (SceneManager.getViewWidth() * .58), // x
      (int) (SceneManager.getViewHeight() * .47), // y
      2, 6 // boxesX, boxesY
  );

  // Render a certian players inventory
  public void render(Player player) {
    // Render the background
    final AdvancedShapeRenderer shapeRenderer = SceneManager.getShapeRenderer();

    // This enables transparency
    Gdx.gl.glEnable(GL20.GL_BLEND);

    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(new Color(1, 0, 0, 0.4F));

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

    // TODO put the players items in the inventory
  }


}
