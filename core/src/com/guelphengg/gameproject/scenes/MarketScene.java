package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.griditems.GridObject;
import com.guelphengg.gameproject.griditems.LootItems;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;

public class MarketScene extends Scene{
    LootItems item_1 = LootItems.BEJEWELED_SWORD;
    LootItems item_2 = LootItems.GOLDEN_GOBLET;
//    LootItems item_3 = LootItems.getRandomItem();
//    LootItems item_4 = LootItems.getRandomItem();
    LootItems treasureMap = LootItems.TREASURE_MAP;

    final GameGrid leftMarketGrid = new GameGrid((int)(SceneManager.getViewWidth()/(3.5*3)), (int)(SceneManager.getViewWidth()/3.5),(int)(SceneManager.getViewWidth()/20.0), (int)(SceneManager.getViewHeight()/3.7), 3, 1);
    final GameGrid centreMarketGrid = new GameGrid((int)(SceneManager.getViewWidth()/(3.5*3)), (int)(SceneManager.getViewWidth()/(3.5*3)),(int)(SceneManager.getViewWidth()/2.3), (int)(SceneManager.getViewHeight()/3.7), 1, 1);
    final GameGrid rightMarketGrid = new GameGrid((int)(SceneManager.getViewWidth()-100)/9, (int)(SceneManager.getViewWidth()-100),25, 205, 9, 1);


    public MarketScene() {
        super(GameState.MARKET);
    }

    @Override
    public void render() {

        renderMarketBackground();

        final SpriteBatch batch = SceneManager.getSpriteBatch();
        Gdx.gl.glEnable(GL20.GL_BLEND);

        batch.begin();
        drawCenteredText(batch, 300,4,"Welcome To The Market");
        leftMarketGrid.renderGrid(new Color(1, 1, 1, 0));
        centreMarketGrid.renderGrid(new Color(1, 1, 1, 0));

        batch.end();
        item_1.render(leftMarketGrid,0, 0);
        leftMarketGrid.renderTextInGrid(0, 0, "$300", true, -1, 3);

        treasureMap.render(centreMarketGrid, 0, 0);
        centreMarketGrid.renderTextInGrid(0, 0, "$100", true, -1, 3);
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

