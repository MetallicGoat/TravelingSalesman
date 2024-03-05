package com.guelphengg.gameproject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum Character {

  GREENIE(0, 0, new Color((float)81/255, (float)115/255, (float)56/255, 0.4F)),
  REDIE(0, 3, new Color(1, (float)43/255, (float)58/255, 0.4F));

  private Animation<TextureRegion> animation;

  private Color colour;

  public Animation<TextureRegion> getAnimation() {
    return animation;
  }

  public String getName() {
    final String name = this.name();

    return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
  }

  public Color getColour(){
    return this.colour;
  }

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

}
