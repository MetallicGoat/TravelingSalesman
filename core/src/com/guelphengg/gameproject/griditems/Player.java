package com.guelphengg.gameproject.griditems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guelphengg.gameproject.Character;
import com.guelphengg.gameproject.Textures;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;

public class Player extends GridObject {

  private final Animation<TextureRegion> playerAnimation;
  private final Character character;
  private boolean small = true;
  private int strength = 10, health = 100;

  public int yOffset = 0;
  public int xOffset = 0;

  float stateTime = 0f;

  public Player(int x, int y, Character character) {
    super(x, y);

    this.character = character;

    // TODO different texture regions for different characters
    final Texture spriteSheet = Textures.SPRITE_SHEET.get();

    TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
        spriteSheet.getWidth() / 12,
        spriteSheet.getHeight() / 8);

    // Place the regions into a 1D array in the correct order, starting from the top
    // left, going across first. The Animation constructor requires a 1D array.
    TextureRegion[] walkFrames = new TextureRegion[3];

    walkFrames[0] = tmp[0][0];
    walkFrames[1] = tmp[0][1];
    walkFrames[2] = tmp[0][2];

    // Initialize the Animation with the frame interval and array of frames
    playerAnimation = new Animation<>(0.1f, walkFrames);

    stateTime = 0f;
  }

  public void setSmall(boolean small) {
    this.small = small;
  }

  public boolean isSmall(){
    return this.small;
  }

  private TextureRegion getCurrFrame() {
    stateTime += Gdx.graphics.getDeltaTime();

    return playerAnimation.getKeyFrame(stateTime, true);
  }

  public boolean isAtStart() {
    return (getX() == 10 && getY() == 0) ;
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


  @Override
  public void render(GameGrid gameGrid) {
    gameGrid.renderTextureInGrid(getX(), getY(), getCurrFrame(), this.small ? 0.5 : 1, xOffset, yOffset);
  }
}
