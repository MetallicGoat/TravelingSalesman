package com.guelphengg.gameproject;

import com.badlogic.gdx.graphics.Texture;

public enum Textures {
  STARTER_HOUSE("start_house.png"),
  SPRITE_SHEET("sprite_sheet.png");

  private final Texture texture;

  Textures(String id) {
    this.texture = new Texture(id);
  }

  public Texture get() {
    return texture;
  }
}
