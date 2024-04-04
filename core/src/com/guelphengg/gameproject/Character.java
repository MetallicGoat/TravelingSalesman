package com.guelphengg.gameproject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guelphengg.gameproject.griditems.Player;
import com.guelphengg.gameproject.util.Util;

public enum Character {

  GREENIE(0, 0, new Color((float) 19 / 255, (float) 115 / 255, (float) 56 / 255, 0.4F)),

  // Reddie does not have left/right animations
  //REDDIE(0, 3, new Color(1, (float)43/255, (float)58/255, 0.4F)),
  WIZEY(0, 6, new Color(1, (float) 43 / 255, (float) 60 / 255, 0.4F)),
  GRAYIE(0, 9, new Color((float) 75 / 255, (float) 90 / 255, (float) 99 / 255, 0.4F)),
  PURPLEY(4, 0, new Color((float) 168 / 255, (float) 65 / 255, (float) 197 / 255, 0.4F)),
  WHITEY(4, 3, new Color((float) 251 / 255, (float) 251 / 255, (float) 232 / 255, 0.4F)),
  YELLOWIE(4, 6, new Color((float) 253 / 255, (float) 214 / 255, (float) 19 / 255, 0.4F)),
  BALDIE(4, 9, new Color((float) 48 / 255, (float) 59 / 255, (float) 90 / 255, 0.4F));

  private final Animation<TextureRegion> animationStraight;
  private final Animation<TextureRegion> animationLeft;
  private final Animation<TextureRegion> animationRight;

  private final Color colour;

  Character(int textureRow, int textureCol, Color colour) {
    final Texture spriteSheet = Textures.SPRITE_SHEET.get();
    this.colour = colour;

    TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
        spriteSheet.getWidth() / 12,
        spriteSheet.getHeight() / 8);

    animationStraight = getAnimation(tmp, textureCol, textureRow);
    animationLeft = getAnimation(tmp, textureCol, textureRow + 1);
    animationRight = getAnimation(tmp, textureCol, textureRow + 2);
  }

  // Allows user to scroll through characters without landing on characters the other player has
  public static Character getNextCharacter(Player player) {
    final GameManager manager = Accessor.getGameManager();
    final Player otherPlayer = manager.getPlayer1() == player ? manager.getPlayer2() : manager.getPlayer1();
    final Character ignoreCharacter = otherPlayer.getCharacter();

    Character next = null;
    int pos = player.getCharacter().ordinal() + 1;

    while (next == null) {
      if (pos >= Character.values().length) {
        pos = 0;
      }

      if (Character.values()[pos] != ignoreCharacter) {
        next = Character.values()[pos];
      }

      pos++;
    }

    return next;
  }

  // Allows user to scroll through characters without landing on characters the other player has
  public static Character getPreviousCharacter(Player player) {
    final GameManager manager = Accessor.getGameManager();
    final Player otherPlayer = manager.getPlayer1() == player ? manager.getPlayer2() : manager.getPlayer1();
    final Character ignoreCharacter = otherPlayer.getCharacter();

    Character next = null;
    int pos = player.getCharacter().ordinal() - 1;

    while (next == null) {
      if (pos < 0) {
        pos = Character.values().length - 1;
      }

      if (Character.values()[pos] != ignoreCharacter) {
        next = Character.values()[pos];
      }

      pos--;
    }

    return next;
  }

  private Animation<TextureRegion> getAnimation(TextureRegion[][] tmp, int x, int y) {
    // Place the regions into a 1D array in the correct order, starting from the top
    // left, going across first. The Animation constructor requires a 1D array.
    TextureRegion[] walkFrames = new TextureRegion[3];

    walkFrames[0] = tmp[y][x];
    walkFrames[1] = tmp[y][x + 1];
    walkFrames[2] = tmp[y][x + 2];

    // Initialize the Animation with the frame interval and array of frames
    return new Animation<>(0.15f, walkFrames);
  }

  // Gets name with capitalized first letter (rest lower case)
  public String getName() {
    final String name = this.name();

    return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
  }

  public Color getColour() {
    return this.colour;
  }

  public TextureRegion getCurrentFrame() {
    return animationStraight.getKeyFrame(Util.getStateTime(), true);
  }

  public TextureRegion getCurrentFrameLeft() {
    return animationLeft.getKeyFrame(Util.getStateTime(), true);
  }

  public TextureRegion getCurrentFrameRight() {
    return animationRight.getKeyFrame(Util.getStateTime(), true);
  }
}
