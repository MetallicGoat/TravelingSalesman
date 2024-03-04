package com.guelphengg.gameproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Dice {

  private final int x = (int) (SceneManager.getViewWidth() * .775);
  private final int y = (int) (SceneManager.getViewHeight() * 0.18);
  private final int width = (int) (SceneManager.getViewWidth() * 0.15);
  private final int height = (int) (SceneManager.getViewWidth() * 0.15);
  private final Animation<TextureRegion> animation;

  private float stateTime = 0F;

  public Dice(){

    // Build the animation frames
    // TODO different texture regions for different characters
    final Texture spriteSheet = Textures.DICE_SHEET.get();

    TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
        spriteSheet.getWidth() / 16,
        spriteSheet.getHeight() / 9);

    // 7 full rows of 18, 2 extra frames
    TextureRegion[] walkFrames = new TextureRegion[(16 * 7) + 2];

    walkFrames[0] = tmp[0][0];

    int index = 1;

    for (int j = 0; j < 16; j++) {
      if (j%2 == 0) {
        for (int i = 1; i < 8; i++) {
          walkFrames[index++] = tmp[i][j];
        }
      } else {
        for (int i = 7; i > 0; i--) {
          walkFrames[index++] = tmp[i][j];
        }
      }
    }

    walkFrames[walkFrames.length - 1] = tmp[8][0];

    // Initialize the Animation with the frame interval and array of frames
    animation = new Animation<>(0.06f, walkFrames);

  }

  private TextureRegion getCurrFrame() {
    stateTime += Gdx.graphics.getDeltaTime();

    return animation.getKeyFrame(stateTime, true);
  }

  public void render() {
    final SpriteBatch batch = SceneManager.getSpriteBatch();

    batch.begin();
    batch.draw(getCurrFrame(), x, y, width, height);
    batch.end();
  }
}
