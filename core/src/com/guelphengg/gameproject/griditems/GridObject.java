package com.guelphengg.gameproject.griditems;

import com.badlogic.gdx.graphics.Texture;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;

public enum GridObject {
  CASTLE("castle.png"),
  HOUSE("empty_house.png"),
  TRAPPED_HOUSE("trapped_house.png"),
  TREASURE_HOUSE("treasure_house.png"),;


  private final Texture texture;


  GridObject(String id) {
    this.texture = new Texture(id);
  }

  public void render(GameGrid grid, int x, int y) {
    grid.renderTextureInGrid(x, y, texture);
  }
}

