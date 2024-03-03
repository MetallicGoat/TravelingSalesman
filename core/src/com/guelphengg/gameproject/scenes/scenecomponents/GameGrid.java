package com.guelphengg.gameproject.scenes.scenecomponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.guelphengg.gameproject.SceneManager;

public class GameGrid {
  private final int cornerX;
  private final int cornerY;
  private final int gridHeight;
  private final int gridWidth;

  public GameGrid(double ScrPerc, double factor, int h, int w, int x) {
    // Grid is 90% the height of the window
//    this.gridHeight = this.gridWidth = (int) (SceneManager.getViewHeight() * 0.9);
    this.gridHeight = h;
    this.gridWidth = w;

    // Calculate best spot for grid
    // Logic that determines where the grid should actually get displayed
    this.cornerX = (int) ((SceneManager.getViewHeight() * ScrPerc/ factor) + x);
    this.cornerY = (int) (SceneManager.getViewHeight() * ScrPerc / factor);
  }

  // height of an individual box on the grid
  public int getBoxHeight(int h) {
    return this.gridHeight / h; // TODO make it so its not 10 by 10 hard coded
  }

  // width of an individual box on the grid
  public int getBoxWidth(int w) {
    return this.gridWidth / w;
  }

  public void renderTextureInGrid(int x, int y, Texture texture) {
    renderTextureInGrid(x, y, texture, 1, 0, 0);
  }

  public void renderTextureInGrid(int x, int y, Texture texture, double scale, int xOffset, int yOffset) {
    final SpriteBatch batch = SceneManager.getSpriteBatch();

    batch.begin();
    batch.draw(texture, (this.cornerX + (x * getBoxWidth(10))) + xOffset, (this.cornerY + (y * getBoxHeight(10))) + yOffset, (float) (getBoxWidth(10) * scale), (float) (getBoxHeight(10) * scale));
    batch.end();
  }

  public void renderTextureInGrid(int x, int y, TextureRegion texture) {
    renderTextureInGrid(x, y, texture, 1, 0, 0);
  }

  public void renderTextureInGrid(int x, int y, TextureRegion texture, double scale, int xOffset, int yOffset) {
    final SpriteBatch batch = SceneManager.getSpriteBatch();

    batch.begin();
    batch.draw(texture, (this.cornerX + (x * getBoxWidth(10))) + xOffset, (this.cornerY + (y * getBoxHeight(10))) + yOffset, (float) (getBoxWidth(10) * scale), (float) (getBoxHeight(10) * scale));
    batch.end();
  }

  public void renderCircleInGrid(int x, int y, Color color) {
    renderCircleInGrid(x, y, color, 1, 0, 0);
  }

  public void renderCircleInGrid(int x, int y, Color color, double scale, int xOffset, int yOffset) {
    final ShapeRenderer shapeRenderer = SceneManager.getShapeRenderer();

    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(color);
    shapeRenderer.ellipse((this.cornerX + (x * getBoxWidth(10))) + xOffset ,(this.cornerY + (y * getBoxHeight(10))) + yOffset,  (float) (getBoxWidth(10) * scale), (float) (getBoxHeight(10) * scale));
    shapeRenderer.end();
  }

  public void renderGrid(Color color, int h, int w) {
    final ShapeRenderer shapeRenderer = SceneManager.getShapeRenderer();

    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.setColor(color);

    Gdx.gl.glLineWidth(2);


    // TODO more hard coded 10 by 10 logic
    // Horizontal Lines
    for (int i = 0; i <= h; i++) {
      shapeRenderer.line(this.cornerX, this.cornerY + (getBoxHeight(h) * i), this.cornerX + this.gridWidth, this.cornerY + (getBoxHeight(h) * i));
    }

    // Vertical Lines
    for (int i = 0; i <= w; i++) {
      shapeRenderer.line(this.cornerX + (getBoxWidth(w) * i), this.cornerY, this.cornerX + (getBoxWidth(w) * i), this.cornerY + this.gridHeight);
    }

    shapeRenderer.end();
  }
}
