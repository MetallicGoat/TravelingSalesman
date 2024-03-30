package com.guelphengg.gameproject.scenes.scenecomponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.guelphengg.gameproject.SceneManager;

public class GameGrid {

  // grid position and size attributes
  private final int cornerY;
  private final int cornerX;
  private final int gridHeight;
  private final int gridWidth;
  private final int boxesX;
  private final int boxesY;

  public GameGrid(int h, int w, int x, int y, int boxesX, int boxesY) {
    // Grid is 90% the height of the window
    // this.gridHeight = this.gridWidth = (int) (SceneManager.getViewHeight() * 0.9);
    this.gridHeight = h;
    this.gridWidth = w;

    // Calculate best spot for grid
    // Logic that determines where the grid should actually get displayed
    this.cornerX = x;
    this.cornerY = y;

    // how many boxes does this grid have
    this.boxesX = boxesX;
    this.boxesY = boxesY;
  }

  // height of an individual box on the grid
  public int getBoxHeight() {
    return this.gridHeight / boxesY;
  }

  // width of an individual box on the grid
  public int getBoxWidth() {
    return this.gridWidth / boxesX;
  }

  // draws a texture in a certain square on the grid
  public void renderTextureInGrid(int x, int y, Texture texture) {
    renderTextureInGrid(x, y, texture, 1, 0, 0);
  }

  // draws a texture in a certain square on the grid with a scale and offset
  public void renderTextureInGrid(int x, int y, Texture texture, double scale, int xOffset, int yOffset) {
    final SpriteBatch batch = SceneManager.getSpriteBatch();

    batch.begin();
    batch.draw(texture, (this.cornerX + (x * getBoxWidth())) + xOffset, (this.cornerY + (y * getBoxHeight())) + yOffset, (float) (getBoxWidth() * scale), (float) (getBoxHeight() * scale));
    batch.end();
  }

  // Same as the above methods but with TextureRegion rather than Texture
  // (TextureRegion is for sprite sheets, eg things that get animated)
  public void renderTextureInGrid(int x, int y, TextureRegion texture) {
    renderTextureInGrid(x, y, texture, 1, 0, 0);
  }

  // Same as the above methods but with TextureRegion rather than Texture
  // (TextureRegion is for sprite sheets, eg things that get animated)
  public void renderTextureInGrid(int x, int y, TextureRegion texture, double scale, int xOffset, int yOffset) {
    final SpriteBatch batch = SceneManager.getSpriteBatch();

    batch.begin();
    batch.draw(texture, (this.cornerX + (x * getBoxWidth())) + xOffset, (this.cornerY + (y * getBoxHeight())) + yOffset, (float) (getBoxWidth() * scale), (float) (getBoxHeight() * scale));
    batch.end();
  }

//  public void renderTextureInGrid(int x, int y, Texture texture, int xOffset, int yOffset) {
//    final SpriteBatch batch = SceneManager.getSpriteBatch();
//
//    final int width = texture.getWidth();
//    final int height = texture.getHeight();
//
//    // Calculate scaling factors
//    float scaleX = (float) getBoxWidth() / width;
//    float scaleY = (float) getBoxHeight() / height;
//    float scale = Math.min(scaleX, scaleY);
//
//    // Calculate scaled dimensions
//    float scaledWidth = width * scale;
//    float scaledHeight = height * scale;
//
//    // Calculate position for centering
//    float posX = (this.cornerX + (x * getBoxWidth())) + xOffset + (getBoxWidth() - scaledWidth) / 2;
//    float posY = (this.cornerY + (y * getBoxHeight())) + yOffset + (getBoxHeight() - scaledHeight) / 2;
//
//    batch.begin();
//    batch.draw(texture, posX, posY, scaledWidth, scaledHeight);
//    batch.end();
//  }

  // draws a rectangle in a certain square on the grid
  public void renderRectInGrid(int x, int y, Color color) {
    renderRectInGrid(x, y, color, 1, 0, 0);
  }

  // draws a rectangle in a certain square on the grid with a scale and offset
  public void renderRectInGrid(int x, int y, Color color, double scale, int xOffset, int yOffset) {
    final ShapeRenderer shapeRenderer = SceneManager.getShapeRenderer();

    // This enables transparency
    Gdx.gl.glEnable(GL20.GL_BLEND);

    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(color);
    shapeRenderer.rect((this.cornerX + (x * getBoxWidth())) + xOffset, (this.cornerY + (y * getBoxHeight())) + yOffset, (float) (getBoxWidth() * scale), (float) (getBoxHeight() * scale));
    shapeRenderer.end();
  }

  // the pixel cord of the bottom left corner of the grid
  public int getCornerX() {
    return cornerX;
  }

  // the pixel cord of the bottom left corner of the grid
  public int getCornerY() {
    return cornerY;
  }

  // the amount of pixels tall the grid is
  public int getGridHeight() {
    return gridHeight;
  }

  // amount of pixels wide the grid is
  public int getGridWidth() {
    return gridWidth;
  }

  // Logic to draw the grid (called 60 times a sec, every frame render)
  public void renderGrid(Color color) {
    final ShapeRenderer shapeRenderer = SceneManager.getShapeRenderer();

    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.setColor(color);

    Gdx.gl.glLineWidth(3);

    // Horizontal Lines
    for (int i = 0; i <= boxesY; i++) {
      shapeRenderer.line(this.cornerX, this.cornerY + (getBoxHeight() * i), this.cornerX + this.gridWidth, this.cornerY + (getBoxHeight() * i));
    }

    // Vertical Lines
    for (int i = 0; i <= boxesX; i++) {
      shapeRenderer.line(this.cornerX + (getBoxWidth() * i), this.cornerY, this.cornerX + (getBoxWidth() * i), this.cornerY + this.gridHeight);
    }

    shapeRenderer.end();
  }
}
