package com.guelphengg.gameproject.scenes.scenecomponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.GameManager;
import com.guelphengg.gameproject.util.AdvancedShapeRenderer;
import com.guelphengg.gameproject.SceneManager;

public class ScoreboardPanel {

    private final int width = (int) (SceneManager.getViewWidth() * 0.22);
    private final int height = (int) (SceneManager.getViewHeight() * 0.5);

    private final int x = (int) (SceneManager.getViewWidth() * 0.75);
    private final int y = (int) (SceneManager.getViewHeight() - (SceneManager.getViewHeight() * 0.05) - height);

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void render() {
        final AdvancedShapeRenderer render = SceneManager.getShapeRenderer();
        final GameManager manager = Accessor.getGameManager();

        // This enables transparency
        Gdx.gl.glEnable(GL20.GL_BLEND);

        render.begin(ShapeRenderer.ShapeType.Filled);
        render.setColor(new Color( 0, .4F, .8F, 0.6F));
        render.roundedRect(x, y, width, height, 10);
        render.end();

        // Scoreboard Text
        final SpriteBatch batch = SceneManager.getSpriteBatch();
        final BitmapFont font = SceneManager.getFont();

        batch.begin();
        font.getData().setScale(2.2F);
        font.draw(batch, "SCOREBOARD", x + 10, y + height - 20);
        font.getData().setScale(1.5F);
        font.draw(batch, "Current Turn: " + manager.getPlayingPlayer().getName(), x + 10, y + height - 60);

        font.draw(batch, "Player 1: "+manager.getPlayer1().getName(), x + 10, y + height - 100);
        font.draw(batch, "    Health: "+manager.getPlayer1().getHealth(), x + 10, y + height - 125);
        font.draw(batch, "    Strength: "+manager.getPlayer1().getStrength(), x + 10, y + height - 150);
        font.draw(batch, "    Coins: "+manager.getPlayer1().getCoins(), x + 10, y + height - 175);

        font.draw(batch, "Player 2: "+manager.getPlayer2().getName(), x + 10, y + height - 225);
        font.draw(batch, "    Health: "+manager.getPlayer2().getHealth(), x + 10, y + height - 250);
        font.draw(batch, "    Strength: "+manager.getPlayer2().getStrength(), x + 10, y + height - 275);
        font.draw(batch, "    Coins: "+manager.getPlayer2().getCoins(), x + 10, y + height - 300);

        batch.end();
    }
}
