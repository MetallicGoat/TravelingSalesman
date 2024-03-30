package com.guelphengg.gameproject.scenes.scenecomponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.GameManager;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.util.AdvancedShapeRenderer;

public class ScoreboardPanel {

  private final int width = (int) (SceneManager.getViewWidth() * 0.22);
  private final int height = (int) (SceneManager.getViewHeight() * 0.5);

  private final int x = (int) (SceneManager.getViewWidth() * 0.75);
  private final int y = (int) (SceneManager.getViewHeight() - (SceneManager.getViewHeight() * 0.05) - height);

  // scoreboard panel width (includes background)
  public int getWidth() {
    return width;
  }

  // scoreboard panel height (includes background)
  public int getHeight() {
    return height;
  }

  // scoreboard panel x position
  public int getX() {
    return x;
  }

  // scoreboard panel y position
  public int getY() {
    return y;
  }

  // Draw da mf scoreboard panel
  public void render() {
    final AdvancedShapeRenderer render = SceneManager.getShapeRenderer();
    final GameManager manager = Accessor.getGameManager();

    // This enables transparency
    Gdx.gl.glEnable(GL20.GL_BLEND);

    // Scoreboard background
    render.begin(ShapeRenderer.ShapeType.Filled);
    render.setColor(new Color(0, .4F, .8F, 0.6F));
    render.roundedRect(x, y, width, height, 10);
    render.end();

    // Scoreboard Text
    final SpriteBatch batch = SceneManager.getSpriteBatch();
    final BitmapFont font = SceneManager.getFont();


    // Daw all the text for the scoreboard
    // all the line positions are hard coded (obviously)
    batch.begin();

    font.getData().setScale(2.2F);
    font.draw(batch, "SCOREBOARD", x + 10, y + height - 20);

    font.getData().setScale(1.5F);
    font.draw(batch, "Current Turn: " + manager.getPlayingPlayer().getName(), x + 10, y + height - 60);

    font.draw(batch, "Player 1: " + manager.getPlayer1().getName(), x + 10, y + height - 100);
    font.draw(batch, "    Power: " + manager.getPlayer1().getStrength(), x + 10, y + height - 125);
    font.draw(batch, "    Points: " + manager.getPlayer1().getPower(), x + 10, y + height - 150);
    font.draw(batch, "    Coins: " + manager.getPlayer1().getCoins(), x + 10, y + height - 175);


    font.draw(batch, "Player 2: " + manager.getPlayer2().getName(), x + 10, y + height - 250);
    font.draw(batch, "    Power: " + manager.getPlayer2().getStrength(), x + 10, y + height - 275);
    font.draw(batch, "    Points: " + manager.getPlayer2().getPower(), x + 10, y + height - 300);
    font.draw(batch, "    Coins: " + manager.getPlayer2().getCoins(), x + 10, y + height - 325);

    batch.end();
  }
}
