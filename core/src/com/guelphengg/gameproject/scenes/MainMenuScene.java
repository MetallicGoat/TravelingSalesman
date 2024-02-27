package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.SceneManager;

public class MainMenuScene extends Scene {
    Sprite mapSprite;
    private int widthImage, heightImage;
    Texture imgMap;

  public MainMenuScene() {
    super(GameState.MAIN_MENU);
  }

    @Override
    public void render() {

        final SpriteBatch batch = SceneManager.getSpriteBatch();

        batch.begin();
        imgMap = new Texture("Main Scroll.png");
        mapSprite = new Sprite(imgMap);
        int widthScreen = Gdx.graphics.getWidth();
        int heightScreen = Gdx.graphics.getHeight();
        batch.draw(mapSprite, (int) (widthScreen / 2 - mapSprite.getWidth() / 2), (int) (heightScreen / 2 - mapSprite.getHeight() / 2) - 60, mapSprite.getWidth(), mapSprite.getHeight());
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
}
