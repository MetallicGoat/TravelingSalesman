package com.guelphengg.gameproject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum Character {

  GREENIE(0, 0),
  REDIE(0, 3);

  private Animation<TextureRegion> animation;

  public Animation<TextureRegion> getAnimation() {
    return animation;
  }

  public String getName() {
    final String name = this.name();

    return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
  }

  Character(int textureRow, int textureCol) {
    final Texture spriteSheet = Textures.SPRITE_SHEET.get();

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

}
