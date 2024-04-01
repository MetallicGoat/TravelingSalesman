package com.guelphengg.gameproject.scenes;

import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.GameManager;
import com.guelphengg.gameproject.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.griditems.GridObject;
import com.guelphengg.gameproject.griditems.LootItems;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;
public class WinScene extends Scene{

    public WinScene(){super(GameState.WINSCREEN);}

    @Override
    public void render(){
        renderBackground();
        final GameManager manager = Accessor.getGameManager();

        final SpriteBatch batch = SceneManager.getSpriteBatch();

        batch.begin();

        if(manager.getPlayer1().getPower() == 1) {
            drawCenteredText(batch, 0, 4, manager.getPlayer1().getCharacter().getName() + " WINS!");
        }

        if(manager.getPlayer2().getPower() == 1) {
            drawCenteredText(batch, 0, 4,  manager.getPlayer2().getCharacter().getName() + " WINS!");
        }

        drawCenteredText(batch, -200, 4, "Press [Space] to return to main menu");

        batch.end();
    }

    @Override
    public void drawCenteredText(SpriteBatch batch, int yOffset, float scale, String text) {
        final BitmapFont font = SceneManager.getFont();
        final GlyphLayout glyphLayout = new GlyphLayout();

        font.getData().setScale(scale);
        glyphLayout.setText(font, text);

        float w = glyphLayout.width;
        float h = glyphLayout.height;

        // draw magic (some mathies to find the center)
        font.draw(batch, glyphLayout, (SceneManager.getViewWidth() - w) / 2, (SceneManager.getViewHeight() + h) / 2 + yOffset);
    }

}
