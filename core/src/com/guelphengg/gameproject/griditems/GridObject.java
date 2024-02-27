package com.guelphengg.gameproject.griditems;

import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;

public abstract class GridObject {
  private int x;
  private int y;

  public GridObject(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public abstract void render(GameGrid gameGrid);
}
