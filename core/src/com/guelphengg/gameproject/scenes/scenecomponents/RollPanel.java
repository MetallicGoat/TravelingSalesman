package com.guelphengg.gameproject.scenes.scenecomponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.GameManager;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.Textures;
import com.guelphengg.gameproject.griditems.Player;
import com.guelphengg.gameproject.util.AdvancedShapeRenderer;

import java.util.Random;

public class RollPanel {
  private final int x = (int) (SceneManager.getViewWidth() * .56);
  private final int y = (int) (SceneManager.getViewHeight() * 0.04);
  private final int width = (int) (SceneManager.getViewWidth() * 0.4);
  private final int height = (int) (SceneManager.getViewWidth() * 0.22);
  private final Animation<TextureRegion> animation;

  private float stateTime = 0F;

  public RollPanel(){
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

  private TextureRegion spinDice() {
    final GameManager manager = Accessor.getGameManager();

    if (manager.isDiceRolling()) {
      if (System.currentTimeMillis() - manager.getLastRollTime() < 2000)
         stateTime += Gdx.graphics.getDeltaTime();
      else {
        manager.completeRoll(new Random().nextInt(6) + 1);
      }
    }

    return animation.getKeyFrame(stateTime, true);
  }

  public void render() {
    final GameManager manager = Accessor.getGameManager();
    final SpriteBatch batch = SceneManager.getSpriteBatch();
    final AdvancedShapeRenderer render = SceneManager.getShapeRenderer();

    // This enables transparency
    Gdx.gl.glEnable(GL20.GL_BLEND);

    // Background
    render.begin(ShapeRenderer.ShapeType.Filled);
    render.setColor(0, 0, 0, 0.6F);
    render.roundedRect(x, y, width, height, 10);
    render.end();

    // Display player currently rolling
    final Player player = manager.getPlayingPlayer();
    final TextureRegion region = player.getCurrFrame();
    final float playerX = x + 60;
    final float playerY = y + 30;

    batch.begin();
    batch.draw(region, playerX, playerY, region.getRegionWidth() * 2, region.getRegionHeight() * 2);
    batch.end();

    final float diceHeightWidth = (float) height * 0.8F;

    final float diceX = x + ((float) width * 0.5F); // a little more to the left
    final float diceY = y + diceHeightWidth/3.5F; // almost at the top of the panel

    batch.begin();
    batch.draw(spinDice(), diceX, diceY, diceHeightWidth, diceHeightWidth);
    batch.end();


    // Panel Text
    final BitmapFont font = new BitmapFont();

    batch.begin();
    font.getData().setScale(2F);

    // Current Player Text
    font.draw(batch, "Current Player:", x + 10, y + 240);
    font.draw(batch, ">> " + player.getName() + " <<", x + 10, y + 210);

    // Dice Text
    if (manager.isDiceRolling())
      font.draw(batch, "Rolling...", diceX + 60, diceY - 20);
    else if (manager.getTurnsLeft() == 0)
      font.draw(batch, "Press [R] to roll!", diceX, diceY - 20);
    else
      font.draw(batch, "Turns Left: " + manager.getTurnsLeft(), diceX + 30, diceY - 20);

    batch.end();

  }
}
