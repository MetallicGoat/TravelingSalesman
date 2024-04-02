package com.guelphengg.gameproject;

import com.badlogic.gdx.graphics.Texture;
import com.guelphengg.gameproject.griditems.WeaponType;

public enum AnimationTextures {

    BASIC_SLASH_SHEET("slash_sprite_sheet.png", 5, 5, WeaponType.SWORD),
    FIRE_SLASH_SHEET("fire_slash_sheet.png", 5, 3, WeaponType.SWORD),
    ICE_SLASH_SHEET("ice_slash_sheet.png", 5, 3, WeaponType.SWORD);

    private final Texture texture;
    private final int tileWidth;
    private final int tileHeight;
    private final WeaponType weaponType;

    AnimationTextures(String id, int tileWidth, int tileHeight, WeaponType weaponType){

        this.texture = new Texture(id);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.weaponType = weaponType;
    }

    public Texture getTexture() {
        return texture;
    }
    public int getTileWidth(){
        return tileWidth;
    }
    public int getTileHeight(){
        return tileHeight;
    }
    public WeaponType getWeaponType(){
        return weaponType;
    }
}
