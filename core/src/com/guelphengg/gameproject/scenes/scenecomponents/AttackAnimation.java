package com.guelphengg.gameproject.scenes.scenecomponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.AnimationTextures;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.griditems.Player;
import com.guelphengg.gameproject.griditems.WeaponType;
import com.guelphengg.gameproject.util.Util;

public class AttackAnimation {
  // Used to keep track of the current frame of the animation
  final Animation<TextureRegion> animation;
  AnimationTextures animationTexture;


  public AttackAnimation(AnimationTextures animationTexture) {
    // Build the animation frames
    this.animationTexture = animationTexture;

    final Texture spriteSheet = animationTexture.getTexture();

    final TextureRegion[][] region2d = TextureRegion.split(spriteSheet,
        spriteSheet.getWidth() / animationTexture.getTileWidth(),
        spriteSheet.getHeight() / animationTexture.getTileHeight());


    final TextureRegion[] textures1d = new TextureRegion[animationTexture.getDuration()];

    int i = 0;
    for (int y = 0; y < animationTexture.getTileHeight(); y++) {
      for (int x = 0; x < animationTexture.getTileWidth(); x++) {
        if (i >= animationTexture.getDuration())
          continue;

        textures1d[i] = region2d[y][x];
        i++;
      }
    }

    this.animation = new Animation<>(0.05F, textures1d);
  }

  static public AnimationTextures attackP1Animation() {

    final Player player1 = Accessor.getGameManager().getPlayer1();
    final WeaponType type = player1.weaponCheck();
    if (type == WeaponType.SWORD) {
      if (Accessor.getGameManager().getPlayer1().getWeaponDamage() == 2) {
        return AnimationTextures.BASIC_SLASH_SHEET;
      } else if (Accessor.getGameManager().getPlayer1().getWeaponDamage() == 5) {
        return AnimationTextures.BASIC_SLASH_SHEET;
      } else if (Accessor.getGameManager().getPlayer1().getWeaponDamage() == 10) {
        return AnimationTextures.ICE_SLASH_SHEET;
      } else if (Accessor.getGameManager().getPlayer1().getWeaponDamage() == 15) {
        return AnimationTextures.FIRE_SLASH_SHEET;
      }
    } else if (type == WeaponType.BOW) {
      return AnimationTextures.RIGHT_ARROW;

    } else if (type == WeaponType.WAND) {
      if (Accessor.getGameManager().getPlayer1().getWeaponDamage() == 2) {
        return AnimationTextures.BASIC_SPELL_SHEET;
      } else if (Accessor.getGameManager().getPlayer1().getWeaponDamage() == 5) {
        return AnimationTextures.EARTH_SPELL_SHEET;
      } else if (Accessor.getGameManager().getPlayer1().getWeaponDamage() == 10) {
        return AnimationTextures.ICE_SPELL_SHEET;
      } else if (Accessor.getGameManager().getPlayer1().getWeaponDamage() == 15) {
        return AnimationTextures.ELECTRIC_SPELL_SHEET;
      }

    } else if (type == null) {
      return AnimationTextures.BASIC_SLASH_SHEET;
    }
    return AnimationTextures.BASIC_SLASH_SHEET;
  }

  static public AnimationTextures attackP2Animation() {
    final Player player2 = Accessor.getGameManager().getPlayer2();
    final WeaponType type = player2.weaponCheck();
    if (type == WeaponType.SWORD) {
      if (Accessor.getGameManager().getPlayer2().getWeaponDamage() == 2) {
        return AnimationTextures.BASIC_SLASH_SHEET;
      } else if (Accessor.getGameManager().getPlayer2().getWeaponDamage() == 5) {
        return AnimationTextures.BASIC_SLASH_SHEET;
      } else if (Accessor.getGameManager().getPlayer2().getWeaponDamage() == 10) {
        return AnimationTextures.ICE_SLASH_SHEET;
      } else if (Accessor.getGameManager().getPlayer2().getWeaponDamage() == 15) {
        return AnimationTextures.FIRE_SLASH_SHEET;
      }
    } else if (type == WeaponType.BOW) {
      return AnimationTextures.LEFT_ARROW;

    } else if (type == WeaponType.WAND) {
      if (Accessor.getGameManager().getPlayer1().getWeaponDamage() == 2) {
        return AnimationTextures.BASIC_SPELL_SHEET;
      } else if (Accessor.getGameManager().getPlayer1().getWeaponDamage() == 5) {
        return AnimationTextures.EARTH_SPELL_SHEET;
      } else if (Accessor.getGameManager().getPlayer1().getWeaponDamage() == 10) {
        return AnimationTextures.ICE_SPELL_SHEET;
      } else if (Accessor.getGameManager().getPlayer1().getWeaponDamage() == 15) {
        return AnimationTextures.ELECTRIC_SPELL_SHEET;
      }

    } else if (type == null) {
      return AnimationTextures.BASIC_SLASH_SHEET;
    }
    return AnimationTextures.BASIC_SLASH_SHEET;
  }

  public void draw(float x, float y, double scale) {
    final SpriteBatch batch = SceneManager.getSpriteBatch();
    final TextureRegion currFrame = this.animation.getKeyFrame(Util.getStateTime(), true);

    //batch.begin();
    batch.draw(currFrame, x, y, (float) (currFrame.getRegionWidth() * scale), (float) (currFrame.getRegionHeight() * scale));
    //batch.end();
  }
}
