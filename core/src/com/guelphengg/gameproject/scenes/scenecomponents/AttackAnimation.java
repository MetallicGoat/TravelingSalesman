package com.guelphengg.gameproject.scenes.scenecomponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guelphengg.gameproject.AnimationTextures;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.Textures;
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

  public void draw(float x, float y, double scale) {
    final SpriteBatch batch = SceneManager.getSpriteBatch();
    final TextureRegion currFrame = this.animation.getKeyFrame(Util.getStateTime(), true);

    //batch.begin();
    batch.draw(currFrame, x, y, (float) (currFrame.getRegionWidth() * scale), (float) (currFrame.getRegionHeight() * scale));
    //batch.end();
  }

  AttackAnimation basicSwordAnimation = new AttackAnimation(AnimationTextures.BASIC_SLASH_SHEET);
  AttackAnimation fireSwordAnimation = new AttackAnimation(AnimationTextures.FIRE_SLASH_SHEET);
  AttackAnimation iceSwordAnimation = new AttackAnimation(AnimationTextures.ICE_SLASH_SHEET);
  AttackAnimation basicWandAnimation = new AttackAnimation(AnimationTextures.BASIC_SPELL_SHEET);
  AttackAnimation electricWandAnimation = new AttackAnimation(AnimationTextures.ELECTRIC_SPELL_SHEET);
  AttackAnimation earthWandAnimation = new AttackAnimation(AnimationTextures.EARTH_SPELL_SHEET);


}
