package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.griditems.GridObject;
import com.guelphengg.gameproject.griditems.LootItems;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;

public class MarketScene extends Scene{
    LootItems item_1 = LootItems.getRandomItem();
    LootItems item_2 = LootItems.getRandomItem();
    LootItems item_3 = LootItems.getRandomItem();
    LootItems item_4 = LootItems.getRandomItem();

    GameGrid marketGrid = new GameGrid(200, 200, 0, 0, 1, 4);

    public MarketScene() {
        super(GameState.MARKET);
    }

    @Override
    public void render() {
    renderMarketBackground();

    final SpriteBatch batch = SceneManager.getSpriteBatch();

    batch.begin();

    drawCenteredText(batch, 300,4,"Welcome To The Market");

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

