package com.guelphengg.gameproject;

import com.badlogic.gdx.graphics.Texture;

public enum Textures {
  MAP_BACKGROUND("Map Background.jpg"),
  BATTLE_BACKGROUND("battle_background.jpg"),
  STARTER_HOUSE("start_house.png"),
  DICE_SHEET("dice.png"),
  SPRITE_SHEET("sprite_sheet.png"),
  HIDDEN_SQUARE("cloud_cover_2.png"),
  MAP_SCROLL("Main Scroll.png"),
  TREASURE_X("TreasureX.png"),
  MARKET_BACKGROUND("MarketStall.png");

  private final Texture texture;

  Textures(String id) {
    this.texture = new Texture(id);
  }

  public static void dispose() {
    for (Textures texture : values()) {
      texture.texture.dispose();
    }
  }

  public Texture get() {
    return texture;
  }
}
