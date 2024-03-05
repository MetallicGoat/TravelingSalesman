package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.Textures;

public class MainMenuScene extends Scene {
    private Music Backgr;
  public MainMenuScene() {
    super(GameState.MAIN_MENU);
  }

    @Override
    public void render() {

        final SpriteBatch batch = SceneManager.getSpriteBatch();

        batch.begin();

        float widthScreen = SceneManager.getViewWidth();
        float heightScreen = SceneManager.getViewHeight();

        Backgr = Gdx.audio.newMusic(Gdx.files.internal("MainMenuTS.mp3"));

        Backgr.setLooping(true);
//        Backgr.play();

        batch.draw(Textures.MAP_SCROLL.get(), (int) (widthScreen * .1), 0, (int) (widthScreen * .8), heightScreen);
        drawCenteredText(batch, 40, 3.5F, "Travelling Salesman");
        drawCenteredText(batch, -40, 2, "Press [Space] to Start!");

        batch.end();
    }

    private void drawCenteredText(SpriteBatch batch, int yOffset, float scale, String text) {
        final BitmapFont font = SceneManager.getFont();
        final GlyphLayout glyphLayout = new GlyphLayout();

        font.getData().setScale(scale);
        glyphLayout.setText(font, text);

        float w = glyphLayout.width;
        float h = glyphLayout.height;

        font.draw(batch, glyphLayout, (SceneManager.getViewWidth() - w)/2, (SceneManager.getViewHeight() + h) / 2 + yOffset);
  }
}
