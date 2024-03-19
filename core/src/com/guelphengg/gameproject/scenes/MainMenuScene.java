package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.Textures;
import com.guelphengg.gameproject.scenes.scenecomponents.AttackAnimation;

public class MainMenuScene extends Scene {

  public MainMenuScene() {
    super(GameState.MAIN_MENU);
  }

    @Override
    public void render() {
        renderBackground();

        final SpriteBatch batch = SceneManager.getSpriteBatch();

        batch.begin();

        float widthScreen = SceneManager.getViewWidth();
        float heightScreen = SceneManager.getViewHeight();


        // draw the scroll texture
        batch.draw(Textures.MAP_SCROLL.get(), (int) (widthScreen * .1), 0, (int) (widthScreen * .8), heightScreen);

        // Draw some text
        drawCenteredText(batch, 40, 3.5F, "Travelling Salesman");
        drawCenteredText(batch, -40, 2, "Press [Space] to Start!");
        batch.end();
    }

    // Method for automatically centering the text for the main menu
    private void drawCenteredText(SpriteBatch batch, int yOffset, float scale, String text) {
        final BitmapFont font = SceneManager.getFont();
        final GlyphLayout glyphLayout = new GlyphLayout();

        font.getData().setScale(scale);
        glyphLayout.setText(font, text);

        float w = glyphLayout.width;
        float h = glyphLayout.height;

        // draw magic (some mathies to find the center)
        font.draw(batch, glyphLayout, (SceneManager.getViewWidth() - w)/2, (SceneManager.getViewHeight() + h) / 2 + yOffset);
  }
}
