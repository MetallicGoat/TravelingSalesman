package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.GameManager;
import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.SceneManager;

// This scene is used before a game starts to allow players to select their characters
public class GameSetupScene extends Scene{

  public GameSetupScene() {
    super(GameState.GAME_SETUP);
  }

  @Override
  public void render() {
    renderBackground(0.5F);
    final GameManager manager = Accessor.getGameManager();

    // Display the players and allow them to be selected
    final TextureRegion p1 = manager.getPlayer1().getCurrFrame();
    final TextureRegion p2 = manager.getPlayer2().getCurrFrame();

    // Draw the players
    final SpriteBatch batch = SceneManager.getSpriteBatch();

    batch.begin();

    batch.draw(p1, 200, 250, p1.getRegionWidth() * 4, p1.getRegionHeight() * 4);
    batch.draw(p2, 800, 250, p1.getRegionWidth() * 4, p1.getRegionHeight() * 4);

    // Player names
    final BitmapFont font = SceneManager.getFont();

    // make BIG text :)
    font.getData().setScale(6F);

    font.draw(batch, "Select a Character to start!", 90, 700);

    // Current Player Text
    font.getData().setScale(4F);
    font.draw(batch, manager.getPlayer1().getName(), 200, 220);
    font.draw(batch, manager.getPlayer2().getName(), 800, 220);

    //text to give instruction to how to change character
    font.getData().setScale(2F);
    font.draw(batch,"Use [A] and [D] to cycle", 150, 150);
    font.draw(batch,"Use [<-] and [->] to cycle", 725, 150);

    // Draw start text
    font.getData().setScale(3F);
    font.draw(batch, "Press [SPACE] to start", 375, 75);


    batch.end();
  }
}
