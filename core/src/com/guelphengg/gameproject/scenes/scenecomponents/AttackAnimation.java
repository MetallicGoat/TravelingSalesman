package com.guelphengg.gameproject.scenes.scenecomponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.Textures;

public class AttackAnimation {
  // Used to keep track of the current frame of the animation
  private static float stateTime = 0F;
  final Animation<TextureRegion> animation;

  public AttackAnimation() {
    // Build the animation frames
    final Texture spriteSheet = Textures.SLASH_SHEET.get();

    final TextureRegion[][] region2d = TextureRegion.split(spriteSheet,
        spriteSheet.getWidth() / 5,
        spriteSheet.getHeight() / 5);

    final TextureRegion[] textures1d = new TextureRegion[24];

    int i = 0;
    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        if (i >= 24)
          continue;

        textures1d[i] = region2d[y][x];
        i++;
      }
    }

    this.animation = new Animation<>(0.05F, textures1d);
  }

  public static void incrementTime() {
    stateTime += Gdx.graphics.getDeltaTime();
  }

  public void draw(float x, float y, double scale) {
    final SpriteBatch batch = SceneManager.getSpriteBatch();
    final TextureRegion currFrame = this.animation.getKeyFrame(stateTime, true);

    //batch.begin();
    batch.draw(currFrame, x, y, (float) (currFrame.getRegionWidth() * scale), (float) (currFrame.getRegionHeight() * scale));
    //batch.end();
  }
}
