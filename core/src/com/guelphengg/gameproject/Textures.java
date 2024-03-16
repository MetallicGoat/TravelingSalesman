package com.guelphengg.gameproject;

import com.badlogic.gdx.graphics.Texture;

public enum Textures {
  MAP_BACKGROUND("Map Background.jpg"),
  BATTLE_BACKGROUND("battle_background.jpg"),
  STARTER_HOUSE("start_house.png"),
  SPRITE_SHEET("sprite_sheet.png"),
  DICE_SHEET("dice.png"),
  MAP_SCROLL("Main Scroll.png"),;

  private final Texture texture;

  Textures(String id) {
    this.texture = new Texture(id);
  }

  public Texture get() {
    return texture;
  }

  public static void dispose() {
    for (Textures texture : values()) {
      texture.texture.dispose();
    }
  }
}
