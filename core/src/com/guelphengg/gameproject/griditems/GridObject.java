package com.guelphengg.gameproject.griditems;

import com.badlogic.gdx.graphics.Texture;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;

public enum GridObject {

  /**
   * This enum represents every object that can be placed on the game grid
   */
  CASTLE("castle.png"),
  EMPTY_HOUSE("empty_house.png"),
  TRAPPED_HOUSE("trapped_house.png"),
  TREASURE_HOUSE("treasure_house.png");

  // the texture of the object
  private final Texture texture;

  // id represents the file name of the texture
  GridObject(String id) {
    this.texture = new Texture(id);
  }

  // Method to draw the GridObject in certian square on whatever grid u want
  public void render(GameGrid grid, int x, int y) {
    grid.renderTextureInGrid(x, y, texture);
  }
}

