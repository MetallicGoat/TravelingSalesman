package com.guelphengg.gameproject.scenes.scenecomponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.guelphengg.gameproject.SceneManager;

public class GameGrid {
  private final int cornerX;
  private final int cornerY;
  private final int gridHeight;
  private final int gridWidth;

  public GameGrid() {
    // Grid is 90% the height of the window
    this.gridHeight = this.gridWidth = (int) (SceneManager.getViewHeight() * 0.9);

    // Calculate best spot for grid
    // Logic that determines where the grid should actually get displayed
    this.cornerX = (int) (SceneManager.getViewHeight() * 0.1 / 2);
    this.cornerY = (int) (SceneManager.getViewHeight() * 0.1 / 2);
  }

  // height of an individual box on the grid
  public int getBoxHeight() {
    return this.gridHeight / 10; // TODO make it so its not 10 by 10 hard coded
  }

  // width of an individual box on the grid
  public int getBoxWidth() {
    return this.gridWidth / 10;
  }

  public void renderTextureInGrid(int x, int y, Texture texture) {
    final SpriteBatch batch = SceneManager.getSpriteBatch();

    batch.begin();
    batch.draw(texture, this.cornerX + (x * getBoxWidth()),this.cornerY + (y * getBoxHeight()), getBoxWidth(), getBoxHeight());
    batch.end();
  }

  public void renderCircleInGrid(int x, int y, Color color) {
    final ShapeRenderer shapeRenderer = SceneManager.getShapeRenderer();

    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(color);
    shapeRenderer.ellipse(this.cornerX + (x * getBoxWidth()),this.cornerY + (y * getBoxHeight()), getBoxWidth(), getBoxHeight());
    shapeRenderer.end();
  }

  public void renderGrid(Color color) {
    final ShapeRenderer shapeRenderer = SceneManager.getShapeRenderer();

    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.setColor(color);

    Gdx.gl.glLineWidth(2);


    // TODO more hard coded 10 by 10 logic
    // Horizontal Lines
    for (int i = 0; i <= 10; i++) {
      shapeRenderer.line(this.cornerX, this.cornerY + (getBoxHeight() * i), this.cornerX + this.gridWidth, this.cornerY + (getBoxHeight() * i));
    }

    // Vertical Lines
    for (int i = 0; i <= 10; i++) {
      shapeRenderer.line(this.cornerX + (getBoxWidth() * i), this.cornerY, this.cornerX + (getBoxWidth() * i), this.cornerY + this.gridHeight);
    }

    shapeRenderer.end();
  }
}
