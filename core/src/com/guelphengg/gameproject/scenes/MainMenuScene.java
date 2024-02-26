package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.GameManager;
import com.guelphengg.gameproject.SceneManager;

public class MainMenuScene extends Scene {

  public MainMenuScene() {
    super(SceneType.MAIN_MENU);
  }

  @Override
  public void render() {
    final SpriteBatch batch = SceneManager.getSpriteBatch();

    batch.begin();

    drawCenteredText(batch, 40, 5, "Travelling Salesman");
    drawCenteredText(batch, -40, 3, "Press [Space] to Start!");

    batch.end();
  }

  private void drawCenteredText(SpriteBatch batch, int yOffset, float scale, String text) {
    final BitmapFont font = new BitmapFont();
    final GlyphLayout glyphLayout = new GlyphLayout();

    font.getData().setScale(scale);
    glyphLayout.setText(font, text);

    float w = glyphLayout.width;
    float h = glyphLayout.height;

    font.draw(batch, glyphLayout, (SceneManager.getViewWidth() - w)/2, (SceneManager.getViewHeight() + h) / 2 + yOffset);
  }

  @Override
  public void onKeyDown(int key) {
    switch (key){
      case Input.Keys.SPACE: {
        Accessor.getGameManager().startGame();
        break;
      }
    }
  }
}
