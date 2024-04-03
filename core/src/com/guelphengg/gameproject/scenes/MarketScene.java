package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.GameManager;
import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.griditems.LootItems;
import com.guelphengg.gameproject.griditems.Player;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;
import com.guelphengg.gameproject.scenes.scenecomponents.MarketInventoryPanel;
import com.guelphengg.gameproject.scenes.scenecomponents.ScoreboardPanel;

import java.util.Collections;
import java.util.List;

public class MarketScene extends Scene {

    private static final LootItems[] sellItems = new LootItems[7];
    private static final LootItems treasureMap = LootItems.TREASURE_MAP;

    final ScoreboardPanel scoreboardPanel = new ScoreboardPanel(true);
    final MarketInventoryPanel inventoryPanel = new MarketInventoryPanel();

    final GameGrid leftMarketGrid = new GameGrid((int) (SceneManager.getViewWidth() / (3.5 * 3)), (int) (SceneManager.getViewWidth() / 3.5), (int) (SceneManager.getViewWidth() / 20.0), (int) (SceneManager.getViewHeight() / 3.7), 3, 1);
    final GameGrid centreMarketGrid = new GameGrid((int) (SceneManager.getViewWidth() / (3.5 * 3)), (int) (SceneManager.getViewWidth() / (3.5 * 3)), (int) (SceneManager.getViewWidth() / 2.3), (int) (SceneManager.getViewHeight() / 3.7), 1, 1);
    final GameGrid rightMarketGrid = new GameGrid((int) (SceneManager.getViewWidth() / (3.5 * 3)), (int) (SceneManager.getViewWidth() / (3.5)), (int) (SceneManager.getViewWidth() / 1.6), (int) (SceneManager.getViewHeight() / 3.7), 3, 1);

    final GameGrid leftMarketGridNum = new GameGrid((int)(SceneManager.getViewWidth()/(3.5*4)), (int)(SceneManager.getViewWidth()/3.5),(int)(SceneManager.getViewWidth()/20.0), (int)(SceneManager.getViewHeight()/2.3), 3, 1);
    final GameGrid centreMarketGridNum = new GameGrid((int)(SceneManager.getViewWidth()/(3.5*4)), (int)(SceneManager.getViewWidth()/(3.5*3)),(int)(SceneManager.getViewWidth()/2.3), (int)(SceneManager.getViewHeight()/2.3), 1, 1);
    final GameGrid rightMarketGridNum = new GameGrid((int)(SceneManager.getViewWidth()/(3.5*4)), (int)(SceneManager.getViewWidth()/(3.5)),(int)(SceneManager.getViewWidth()/1.6), (int)(SceneManager.getViewHeight()/2.3), 3, 1);

    public MarketScene() {
        super(GameState.MARKET);
    }

    @Override
    public void render() {
        int yOffset = 25;
        Player playingPlayer = Accessor.getGameManager().getPlayingPlayer();

        for(int i = 1; i<=6; i++) {
            if (playingPlayer.getItems().contains(sellItems[i])) {
                sellItems[i] = LootItems.BLANK;
            }
        }

        renderMarketBackground();

        final SpriteBatch batch = SceneManager.getSpriteBatch();
        Gdx.gl.glEnable(GL20.GL_BLEND);

        batch.begin();

        // rendering grids in the market to store the items
        leftMarketGrid.renderGrid(new Color(0, 0, 0, 0));
        centreMarketGrid.renderGrid(new Color(0, 0, 0, 0));
        rightMarketGrid.renderGrid(new Color(0, 0, 0, 0));
        centreMarketGridNum.renderGrid(new Color(0, 0, 0, 0));
        leftMarketGridNum.renderGrid(new Color(0, 0, 0,0));
        rightMarketGridNum.renderGrid(new Color(0, 0, 0, 0));

        batch.end();
        // rendering the items in the grid to appear with text
        if(sellItems[0] == LootItems.BLANK || Accessor.getGameManager().getPlayingPlayer().getItems().contains(LootItems.TREASURE_MAP)){
            centreMarketGrid.renderTextInGrid(0, 0, "SOLD\nOUT", true, -1, yOffset, 2);
        } else {
            sellItems[0].render(centreMarketGrid, 0, 0);
            centreMarketGrid.renderTextInGrid(0, 0, "$"+sellItems[0].getSellPrice(), true, -1, 3);
            centreMarketGridNum.renderTextInGrid(0, 0, "0", true, 0, 3);
        }

        scoreboardPanel.render();
        inventoryPanel.render();

        for(int i = 1; i<=6; i++){
            if(i<=3) {
                sellItems[i].render(leftMarketGrid, i-1, 0);
                if(sellItems[i] == LootItems.BLANK){
                    leftMarketGrid.renderTextInGrid(i-1, 0, "SOLD\nOUT", true, -1, yOffset, 2);
                }else{
                    leftMarketGrid.renderTextInGrid(i-1, 0, "$"+sellItems[i].getSellPrice(), true, -1, 3);
                    leftMarketGridNum.renderTextInGrid(i-1, 0, " "+i+" ", true, 0, 3);
                }

            }

            if(i==4) {
                sellItems[i].render(rightMarketGrid, 0, 0);
                if(sellItems[i] == LootItems.BLANK) {
                    rightMarketGrid.renderTextInGrid(0, 0, "SOLD\nOUT", true, -1, yOffset, 2);
                }else {
                    rightMarketGrid.renderTextInGrid(0, 0, "$" + sellItems[i].getSellPrice(), true, -1, 3);
                    rightMarketGridNum.renderTextInGrid(0, 0, " "+i+" ", true, 0, 3);
                }

            }

            if((Accessor.getGameManager().getPlayingPlayer().getPower()>=20)&&i==5){
                sellItems[i].render(rightMarketGrid, 1, 0);
                if(sellItems[i] == LootItems.BLANK) {
                    rightMarketGrid.renderTextInGrid(1, 0, "SOLD\nOUT", true, -1, yOffset, 2);
                } else {
                    rightMarketGrid.renderTextInGrid(1, 0, "$" + sellItems[i].getSellPrice(), true, -1, 3);
                    rightMarketGridNum.renderTextInGrid(i-4, 0, " "+i+" ", true, 0, 3);
                }

            } else if (i==5) {

                rightMarketGrid.renderTextInGrid(1, 0, "GET\nMORE\nPOWER", true, 0,40, 2);
            }

            if((Accessor.getGameManager().getPlayingPlayer().getPower()>=30)&&i==6){
                sellItems[i].render(rightMarketGrid, 2, 0);
                if(sellItems[i] == LootItems.BLANK) {
                    rightMarketGrid.renderTextInGrid(2, 0, "SOLD\nOUT", true, -1, yOffset, 2);
                } else {
                    rightMarketGrid.renderTextInGrid(2, 0, "$" + sellItems[i].getSellPrice(), true, -1, 3);
                    rightMarketGridNum.renderTextInGrid(i-4, 0, " "+i+" ", true, 0, 3);
                }


            } else if(i==6){
                rightMarketGrid.renderTextInGrid(2, 0, "GET\nMORE\nPOWER", true, 0, 40,2);
            }

        }

        batch.begin();

        drawCenteredText(batch, 250, 3.3F, "Welcome To The Market");
        drawCenteredText(batch, -300, 2.5F, "Press [SPACE] to return to the map");

        batch.end();
    }

    public void canBuy(int index) {
        final GameManager manager = Accessor.getGameManager();
        if ((manager.getPlayingPlayer().getCoins() >= sellItems[index].getSellPrice())&&(!sellItems[index].equals(LootItems.BLANK))&&(!manager.getPlayingPlayer().getItems().contains(sellItems[index]))) {
            manager.getPlayingPlayer().addPower(sellItems[index]);
            manager.getPlayingPlayer().addLoot(sellItems[index]);
            manager.getPlayingPlayer().removeCoins(sellItems[index].getSellPrice());
            sellItems[index] = LootItems.BLANK;
        }
    }

    public static void reset() {
        sellItems[0] = treasureMap;

        final List<LootItems> items = LootItems.getWeapons(Accessor.getGameManager().getPlayingPlayer());

        // Randomize the items
        Collections.shuffle(items);

        for (int i = 1; i<=6; i++) {
            if (items.size() < i) // Blank if we run out of items
                sellItems[i] = LootItems.BLANK;
            else
                sellItems[i] = items.get(i - 1);
        }
    }
}