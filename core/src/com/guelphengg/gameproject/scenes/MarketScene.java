package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.GameManager;
import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.griditems.LootItems;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;
import com.guelphengg.gameproject.scenes.scenecomponents.ScoreboardPanel;

public class MarketScene extends Scene {

    LootItems[] sellItems = new LootItems[7];
    LootItems item_1 = LootItems.BEJEWELED_SWORD;
    LootItems item_2 = LootItems.GOLDEN_GOBLET;
    LootItems item_3 = LootItems.CRYSTAL_GOBLET;
    LootItems item_4 = LootItems.PALADIN_SHIELD;
    LootItems treasureMap = LootItems.TREASURE_MAP;
    Texture blank = new Texture("Blank.png");
    ScoreboardPanel scoreboardPanel = new ScoreboardPanel(true);
    final SpriteBatch batch = SceneManager.getSpriteBatch();

    final GameGrid leftMarketGrid = new GameGrid((int) (SceneManager.getViewWidth() / (3.5 * 3)), (int) (SceneManager.getViewWidth() / 3.5), (int) (SceneManager.getViewWidth() / 20.0), (int) (SceneManager.getViewHeight() / 3.7), 3, 1);
    final GameGrid centreMarketGrid = new GameGrid((int) (SceneManager.getViewWidth() / (3.5 * 3)), (int) (SceneManager.getViewWidth() / (3.5 * 3)), (int) (SceneManager.getViewWidth() / 2.3), (int) (SceneManager.getViewHeight() / 3.7), 1, 1);
    final GameGrid rightMarketGrid = new GameGrid((int) (SceneManager.getViewWidth() / (3.5 * 3)), (int) (SceneManager.getViewWidth() / (3.5)), (int) (SceneManager.getViewWidth() / 1.6), (int) (SceneManager.getViewHeight() / 3.7), 3, 1);

    final GameGrid leftMarketGridNum = new GameGrid((int)(SceneManager.getViewWidth()/(3.5*4)), (int)(SceneManager.getViewWidth()/3.5),(int)(SceneManager.getViewWidth()/20.0), (int)(SceneManager.getViewHeight()/2.3), 3, 1);
    final GameGrid centreMarketGridNum = new GameGrid((int)(SceneManager.getViewWidth()/(3.5*4)), (int)(SceneManager.getViewWidth()/(3.5*3)),(int)(SceneManager.getViewWidth()/2.3), (int)(SceneManager.getViewHeight()/2.3), 1, 1);
    final GameGrid rightMarketGridNum = new GameGrid((int)(SceneManager.getViewWidth()/(3.5*4)), (int)(SceneManager.getViewWidth()/(3.5)),(int)(SceneManager.getViewWidth()/1.6), (int)(SceneManager.getViewHeight()/2.3), 3, 1);

    final GameGrid inventoryGrid = new GameGrid(
            (int) (SceneManager.getViewWidth() * .3 / 3), // height
            (int) (SceneManager.getViewWidth() * .3), // width
            (int) (SceneManager.getViewHeight() * .47), // x
            (int) (SceneManager.getViewWidth() * .58), // y
            7, 2 // boxesX, boxesY
    );
    public MarketScene() {
        super(GameState.MARKET);
        sellItems[0] = treasureMap;
        sellItems[1] = item_1;
        sellItems[2] = item_2;
        sellItems[3] = item_3;
        sellItems[4] = item_4;
        sellItems[5] = LootItems.BLANK;
        sellItems[6] = LootItems.BLANK;
    }

    @Override
    public void render() {

        renderMarketBackground();

        final SpriteBatch batch = SceneManager.getSpriteBatch();
        Gdx.gl.glEnable(GL20.GL_BLEND);

        batch.begin();
        drawCenteredText(batch, 300, 4, "Welcome To The Market");

        // rendering grids in the market to store the items
        leftMarketGrid.renderGrid(new Color(0, 0, 0, 0));
        centreMarketGrid.renderGrid(new Color(0, 0, 0, 0));
        rightMarketGrid.renderGrid(new Color(0, 0, 0, 0));
        centreMarketGridNum.renderGrid(new Color(0, 0, 0, 0));
        leftMarketGridNum.renderGrid(new Color(0, 0, 0,0));
        rightMarketGridNum.renderGrid(new Color(0, 0, 0, 0));

        batch.end();
        // rendering the items in the grid to appear with text
        treasureMap.render(centreMarketGrid, 0, 0);
        centreMarketGrid.renderTextInGrid(0, 0, "$"+sellItems[0].getSellPrice(), true, -1, 3);
        centreMarketGridNum.renderTextInGrid(0, 0, "0", true, 0, 3);
        scoreboardPanel.render();
        for(int i = 1; i<=6; i++){
            if(i<=3) {
                sellItems[i].render(leftMarketGrid, i-1, 0);
                leftMarketGrid.renderTextInGrid(i-1, 0, "$"+sellItems[i].getSellPrice(), true, -1, 3);
                leftMarketGridNum.renderTextInGrid(i-1, 0, " "+i+" ", true, 0, 3);

            }
            if(i==4) {
                sellItems[i].render(rightMarketGrid, 0, 0);
                rightMarketGrid.renderTextInGrid(0, 0, "$"+sellItems[i].getSellPrice(), true, -1, 3);
                rightMarketGridNum.renderTextInGrid(0, 0, " "+i+" ", true, 0, 3);

            }
            if((Accessor.getGameManager().getPlayingPlayer().getStrength()>=20)&&i==5){
                sellItems[i] = LootItems.DIAMOND_RING;
                sellItems[i].render(rightMarketGrid, 1, 0);
                rightMarketGrid.renderTextInGrid(1, 0, "$"+sellItems[i].getSellPrice(), true, -1, 3);
                rightMarketGridNum.renderTextInGrid(i-4, 0, " "+i+" ", true, 0, 3);

            } else if (i==5) {
                rightMarketGrid.renderTextInGrid(1, 0, "GET\nMORE\nPOWER", true, 0,40, 2);
            }

            if((Accessor.getGameManager().getPlayingPlayer().getStrength()>=30)&&i==6){
                sellItems[i] = LootItems.GOLDEN_KEY;
                sellItems[i].render(rightMarketGrid, 2, 0);
                rightMarketGrid.renderTextInGrid(2, 0, "$"+sellItems[i].getSellPrice(), true, -1, 3);
                rightMarketGridNum.renderTextInGrid(i-4, 0, " "+i+" ", true, 0, 3);

            } else if(i==6){
                rightMarketGrid.renderTextInGrid(2, 0, "GET\nMORE\nPOWER", true, 0, 40,2);
            }

        }
    }

    @Override
    public void drawCenteredText(SpriteBatch batch, int yOffset, float scale, String text) {
        final BitmapFont font = SceneManager.getFont();
        final GlyphLayout glyphLayout = new GlyphLayout();

        font.getData().setScale(scale);
        glyphLayout.setText(font, text);

        float w = glyphLayout.width;
        float h = glyphLayout.height;

        // draw magic (some mathies to find the center) ;)
        font.draw(batch, glyphLayout, (SceneManager.getViewWidth() - w) / 2, (SceneManager.getViewHeight() + h) / 2 + yOffset);
    }


    public void removeItem(int index) {
        if(index <= 3){
            //leftMarketGrid.renderTextureInGrid(index-1,0, null);
            sellItems[6].render(leftMarketGrid,index - 1,0);
        }
        if(index == 4){
            sellItems[6].render(centreMarketGrid,0,0);
        }
        if(index >= 5){
            sellItems[6].render(rightMarketGrid, index,0);
        }
    }

    public LootItems getSellItems(int index) {
        return sellItems[index];
    }

    public int getPrices(int index) {
        return sellItems[index].getSellPrice();
    }

    public boolean canBuy(int index) {
        final GameManager manager = Accessor.getGameManager();
        if ((manager.getPlayingPlayer().getCoins() >= sellItems[index].getSellPrice())&&(!sellItems[index].equals(LootItems.BLANK))) {
            manager.getPlayingPlayer().addStrength(sellItems[index]);
            return true;
        } else {
            return false;
        }
    }

    public void clearSlot(int index){
        //centreMarketGrid.renderTextureInGrid(0,0, "Blank.png");
    }
}