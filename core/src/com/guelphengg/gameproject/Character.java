package com.guelphengg.gameproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guelphengg.gameproject.griditems.Player;

public enum Character {

  GREENIE(0, 0, new Color((float)81/255, (float)115/255, (float)56/255, 0.4F)),
  REDDIE(0, 3, new Color(1, (float)43/255, (float)58/255, 0.4F)),
  GRAYIE(0, 9, new Color(1, (float)43/255, (float)58/255, 0.4F)),
  PRUPLEY(4, 0, new Color(1, (float)43/255, (float)58/255, 0.4F)),
  WHITEY(4, 3, new Color(1, (float)43/255, (float)58/255, 0.4F)),
  YELLOWIE(4, 6, new Color(1, (float)43/255, (float)58/255, 0.4F)),
  BALDIE(4, 9, new Color(1, (float)43/255, (float)58/255, 0.4F));

  public static float stateTime = 0F;

  private final Animation<TextureRegion> animation;
  private final Color colour;

  Character(int textureRow, int textureCol, Color colour) {
    final Texture spriteSheet = Textures.SPRITE_SHEET.get();
    this.colour = colour;

    TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
        spriteSheet.getWidth() / 12,
        spriteSheet.getHeight() / 8);

    // Place the regions into a 1D array in the correct order, starting from the top
    // left, going across first. The Animation constructor requires a 1D array.
    TextureRegion[] walkFrames = new TextureRegion[3];

    walkFrames[0] = tmp[textureRow][textureCol];
    walkFrames[1] = tmp[textureRow][textureCol + 1];
    walkFrames[2] = tmp[textureRow][textureCol + 2];

    // Initialize the Animation with the frame interval and array of frames
    animation = new Animation<>(0.15f, walkFrames);
  }

  public String getName() {
    final String name = this.name();

    return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
  }

  public Color getColour(){
    return this.colour;
  }

  public TextureRegion getCurrentFrame() {
    return animation.getKeyFrame(stateTime, true);
  }

  public static void updateStateTime() {
    stateTime += Gdx.graphics.getDeltaTime();
  }

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
}
