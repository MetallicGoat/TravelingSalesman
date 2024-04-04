package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.SceneManager;

public class TrappedScene extends Scene {

  public TrappedScene() {
    super(GameState.TRAPPED);
  }

  @Override
  public void render() {
    final SpriteBatch batch = SceneManager.getSpriteBatch();
    final BitmapFont font = SceneManager.getFont();

    renderTrappedBackground();

    batch.begin();
    font.setColor(Color.RED);
    font.getData().setScale(4);

    drawCenteredText(batch, 300, 4, "YOU HAVE BEEN TRAPPED");
    drawCenteredText(batch, 240, 3, "YOU HAVE TWO CHOICES");
    drawCenteredText(batch, 70, 3, "EITHER");
    drawCenteredText(batch, 0, 2, "LOSE 1 POINT - PRESS [1]");
    drawCenteredText(batch, -70, 3, "OR");
    drawCenteredText(batch, -140, 2, "LOSE 30 COINS - PRESS [2]");
    drawCenteredText(batch, -330, 3, "YOUR CHOICE");

    batch.end();
  }
}
