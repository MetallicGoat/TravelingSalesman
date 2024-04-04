package com.guelphengg.gameproject;

import com.badlogic.gdx.graphics.Texture;
import com.guelphengg.gameproject.griditems.WeaponType;

public enum AnimationTextures {

  BASIC_SLASH_SHEET("slash_sprite_sheet.png", 5, 5, 24, WeaponType.SWORD),
  FIRE_SLASH_SHEET("fire_slash_sheet.png", 5, 3, 14, WeaponType.SWORD),
  ICE_SLASH_SHEET("ice_slash_sheet.png", 5, 3, 14, WeaponType.SWORD),
  BASIC_SPELL_SHEET("basic_spell_sheet.png", 5, 7, 32, WeaponType.WAND),
  ELECTRIC_SPELL_SHEET("electric_spell_sheet.png", 5, 7, 32, WeaponType.WAND),
  EARTH_SPELL_SHEET("earth_spell_sheet.png", 5, 10, 48, WeaponType.WAND),
  ICE_SPELL_SHEET("ice_spell_sheet.png", 5, 7, 32, WeaponType.WAND),
  RIGHT_ARROW("rightArrow.png", 1, 1, 1, WeaponType.BOW),
  LEFT_ARROW("leftArrow.png", 1, 1, 1, WeaponType.BOW);
  private final Texture texture;
  private final int tileWidth;
  private final int tileHeight;
  private final int duration;
  private final WeaponType weaponType;

  AnimationTextures(String id, int tileWidth, int tileHeight, int duration, WeaponType weaponType) {

    this.texture = new Texture(id);
    this.tileWidth = tileWidth;
    this.tileHeight = tileHeight;
    this.duration = duration;
    this.weaponType = weaponType;
  }

  public Texture getTexture() {
    return texture;
  }

  public int getTileWidth() {
    return tileWidth;
  }

  public int getTileHeight() {
    return tileHeight;
  }

  public int getDuration() {
    return duration;
  }

  public WeaponType getWeaponType() {
    return weaponType;
  }
}
