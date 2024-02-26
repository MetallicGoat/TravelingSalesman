package com.guelphengg.gameproject.griditems;

import com.badlogic.gdx.graphics.Color;

public class GridObject {
  private int x;
  private int y;
  private final Color color;

  public GridObject(int x, int y, Color color) {
    this.x = x;
    this.y = y;
    this.color = color;
  }

  public Color getColor() {
    return this.color;
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
}
