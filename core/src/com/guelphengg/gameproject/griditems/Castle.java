package com.guelphengg.gameproject.griditems;

import com.badlogic.gdx.graphics.Color;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;

public class Castle extends GridObject {
  public Castle(int x, int y) {
    super(x, y);
  }

  @Override
  public void render(GameGrid gameGrid) {
    gameGrid.renderCircleInGrid(getX(), getY(), Color.GOLD);
  }
}
