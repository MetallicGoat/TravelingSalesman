package com.guelphengg.gameproject.griditems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guelphengg.gameproject.Character;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.Textures;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;

public class Player {

  private final Character character;
  private boolean small = true;
  private int strength = 10, health = 100;

  public int yOffset = 0;
  public int xOffset = 0;

  private int x;
  private int y;

  float stateTime = 0f;

  public Player(int x, int y, Character character) {
    this.x = x;
    this.y = y;

    this.character = character;
  }

  public String getName() {
    return character.getName();
  }

  public void setSmall(boolean small) {
    this.small = small;
  }

  public boolean isSmall(){
    return this.small;
  }

  private TextureRegion getCurrFrame() {
    stateTime += Gdx.graphics.getDeltaTime();

    return character.getAnimation().getKeyFrame(stateTime, true);
  }

  public boolean isAtStart() {
    return (this.x == 10 && this.y == 0) ;
  }

  void setStrength(int strength) {
    this.strength = strength;
  }

  public int getStrength() {
    return this.strength;
  }

  void setHealth(int health){
    this.health = health;
  }

  public int getHealth() {
    return health;
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

  public void render(GameGrid gameGrid) {
    render(gameGrid, this.x, this.y);
  }

  public void render(GameGrid gameGrid, int x, int y) {
    gameGrid.renderTextureInGrid(x, y, getCurrFrame(), this.small ? 0.5 : 1, xOffset, yOffset);
  }
}
