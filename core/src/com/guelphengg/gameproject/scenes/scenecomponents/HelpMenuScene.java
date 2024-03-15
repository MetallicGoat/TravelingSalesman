package com.guelphengg.gameproject.scenes.scenecomponents;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.scenes.Scene;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.GameManager;
import com.guelphengg.gameproject.SceneManager;
//this scene is for a help menu with game instructions
public class HelpMenuScene extends Scene {

    public HelpMenuScene(){super(GameState.HELP_MENU);}

    @Override
    public void render() {
        renderBackground(0.5f);

        SpriteBatch batch = new SpriteBatch();
        final BitmapFont font = SceneManager.getFont();

        batch.begin();

        font.draw(batch,"Test", 90, 700);

        batch.end();
    }

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
