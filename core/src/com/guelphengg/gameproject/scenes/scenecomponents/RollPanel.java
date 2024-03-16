package com.guelphengg.gameproject.scenes.scenecomponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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

public class RollPanel {

  // misc panel attributes
  private final int x = (int) (SceneManager.getViewWidth() * .56);
  private final int y = (int) (SceneManager.getViewHeight() * 0.04);
  private final int width = (int) (SceneManager.getViewWidth() * 0.4);
  private final int height = (int) (SceneManager.getViewWidth() * 0.22);

  // Used to keep track of the current frame of the animation
  private float stateTime = 0F;
  private final float secsPerFrame = 0.06F; // The speed of the animation (smaller = faster animation)

  // The animation of the dice rolling
  // private final Animation<TextureRegion> animation;
  private final TextureRegion[][] region2d;

  // IF the dice is currently changing frames
  boolean rollComplete = false;

  private int frameX = 0;
  private int frameY = 0;

  public RollPanel() {
    // Build the animation frames
    final Texture spriteSheet = Textures.DICE_SHEET.get();

    this.region2d = TextureRegion.split(spriteSheet,
        spriteSheet.getWidth() / 16,
        spriteSheet.getHeight() / 9);
  }

  // This method is used to animate the dice rolling
  private TextureRegion spinDice() {
    final GameManager manager = Accessor.getGameManager();

    // If the dice should be spinning aimlessly, or towards the next number
    boolean rollTowards = false;

    if (manager.isDiceRolling()) {
      // only spin it (by updating stateTime) if it is actually supposed to be spinning
      if (System.currentTimeMillis() - manager.getLastRollTime() > 600) {

        // We are done spinning aimlessly and should start rolling towards the next roll
        rollTowards = true;

        if (rollComplete) { // This value is modified when getting the dice frame
          // Notify game manger: the dice has landed on the correct number
          manager.completeRoll();
        }
      }
    }

    return getDiceFrame(rollTowards);
  }

  // This method is used to get the next frame of the dice animation
  // if towardsRoll is true, the dice will start rolling to the randomly generated number
  private TextureRegion getDiceFrame(boolean towardsRoll) {
    // Only change the frame if it is time
    if (Accessor.getGameManager().isDiceRolling() || Accessor.getGameManager().isWaitingForRoll()) {
      stateTime += Gdx.graphics.getDeltaTime();

      // Hack to fix superfast dice rolling (cause a frame takes a long time to render)
      // (probably cause the game was minimized)
      if (stateTime > 1)
        stateTime = 0;

      // Check if it's time to display the next frame
      if (stateTime > secsPerFrame) {
        stateTime -= secsPerFrame;

      } else { // It's not time to roll the dice
        return this.region2d[this.frameY][this.frameX];
      }

    } else { // The dice is not rolling, so dont change the frame
      return this.region2d[this.frameY][this.frameX];
    }

    if (towardsRoll) {
      // In the case we will start spinning the dice to the randomly generated number

      final int towardsX = getDiceFrameX();
      final int towardsY = getDiceFrameY();

      if (frameX < towardsX) {
        frameX++;
      } else if (frameX > towardsX) {
        frameX--;
      } else if (frameY < towardsY) {
        frameY++;
      } else if (frameY > towardsY) {
        frameY--;
      } else {
        // The dice has landed on the correct square
        // The roll is not finished, and "completeRoll" should be called next cycle"
        rollComplete = true;
      }

    } else {
      rollComplete = false; // The dice is still rolling

      // Move up and down in the sprite sheet
      // To make it appear as if the dice is rolling smoothly
      if (frameX % 2 == 0) { // x is even, move up in the y
        // These checks are a little odd, because the sprite sheet has a weird shape
        if (frameY < 6 || (frameX == 0 && frameY < 7)) {
          frameY++;
        } else {
          frameX++;
        }
      } else { // X is odd, so move down in the y
        if (frameY > 1) {
          frameY--;
        } else {
          frameX++;
        }
      }
    }

    // Reset the x position of the sprite sheet (prevent out of bounds error
    if (frameX > 15) {
      frameX = 0;
    }

    return this.region2d[this.frameY][this.frameX];
  }

  // Returns the x position of the dice frame we are currently rolling to
  // (Based on the sprite sheet)
  public int getDiceFrameX() {
    final int roll = Accessor.getGameManager().getNextRoll();

    switch (roll) {
      case 1:
        return 0;
      case 2:
        return 4;
      case 3:
        return 0;
      case 4:
        return 0;
      case 5:
        return 12;
      case 6:
        return 8;
      default:
        return 0;
    }
  }

  // Returns the y position of the dice frame we are currently rolling to
  // (Based on the sprite sheet)
  public int getDiceFrameY() {
    final int roll = Accessor.getGameManager().getNextRoll();

    switch (roll) {
      case 1:
        return 4;
      case 2:
        return 4;
      case 3:
        return 8;
      case 4:
        return 0;
      case 5:
        return 4;
      case 6:
        return 4;
      default:
        return 0;
    }
  }

  // Method for drawing the entire roll panel including:
  // the player, the dice, the black background, and some text
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

    // Draw the player
    batch.begin();
    batch.draw(region, playerX, playerY, region.getRegionWidth() * 2, region.getRegionHeight() * 2);
    batch.end();

    final float diceHeightWidth = (float) height * 0.8F;
    final float diceX = x + ((float) width * 0.5F); // a little more to the left
    final float diceY = y + diceHeightWidth/3.5F; // almost at the top of the panel

    // draw the current frame of the dice
    batch.begin();
    batch.draw(spinDice(), diceX, diceY, diceHeightWidth, diceHeightWidth);
    batch.end();

    // Panel Text
    final BitmapFont font = SceneManager.getFont();

    batch.begin();

    // make BIG text :)
    font.getData().setScale(2F);

    // Current Player Text
    font.draw(batch, "Current Player:", x + 10, y + 240);
    font.draw(batch, ">> " + player.getName() + " <<", x + 10, y + 210);

    // Text displayed under the dice
    if (manager.isDiceRolling())
      font.draw(batch, "Rolling...", diceX + 60, diceY - 20);
    else if (!manager.isWaitingForRoll() && manager.getTurnsLeft() == 0) {
      font.draw(batch, "Press [Enter] to", diceX, diceY);
      font.draw(batch, "complete your turn!", diceX - 18, diceY - 30);
    } else if (manager.isWaitingForRoll())
      font.draw(batch, "Press [R] to roll!", diceX, diceY - 20);
    else
      font.draw(batch, "Turns Left: " + manager.getTurnsLeft(), diceX + 30, diceY - 20);

    batch.end();
  }
}
