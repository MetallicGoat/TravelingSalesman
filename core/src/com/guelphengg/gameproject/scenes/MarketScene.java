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

  // These are the grids for the items in the market
  final GameGrid leftMarketGrid = new GameGrid((int) (SceneManager.getViewWidth() / (3.5 * 3)), (int) (SceneManager.getViewWidth() / 3.5), (int) (SceneManager.getViewWidth() / 20.0), (int) (SceneManager.getViewHeight() / 3.7), 3, 1);
  final GameGrid centreMarketGrid = new GameGrid((int) (SceneManager.getViewWidth() / (3.5 * 3)), (int) (SceneManager.getViewWidth() / (3.5 * 3)), (int) (SceneManager.getViewWidth() / 2.3), (int) (SceneManager.getViewHeight() / 3.7), 1, 1);
  final GameGrid rightMarketGrid = new GameGrid((int) (SceneManager.getViewWidth() / (3.5 * 3)), (int) (SceneManager.getViewWidth() / (3.5)), (int) (SceneManager.getViewWidth() / 1.6), (int) (SceneManager.getViewHeight() / 3.7), 3, 1);

  // These are the grids for the numbers above the item
  final GameGrid leftMarketGridNum = new GameGrid((int) (SceneManager.getViewWidth() / (3.5 * 4)), (int) (SceneManager.getViewWidth() / 3.5), (int) (SceneManager.getViewWidth() / 20.0), (int) (SceneManager.getViewHeight() / 2.3), 3, 1);
  final GameGrid centreMarketGridNum = new GameGrid((int) (SceneManager.getViewWidth() / (3.5 * 4)), (int) (SceneManager.getViewWidth() / (3.5 * 3)), (int) (SceneManager.getViewWidth() / 2.3), (int) (SceneManager.getViewHeight() / 2.3), 1, 1);
  final GameGrid rightMarketGridNum = new GameGrid((int) (SceneManager.getViewWidth() / (3.5 * 4)), (int) (SceneManager.getViewWidth() / (3.5)), (int) (SceneManager.getViewWidth() / 1.6), (int) (SceneManager.getViewHeight() / 2.3), 3, 1);

  public MarketScene() {
    super(GameState.MARKET);
  }

  // resets the market and it's values
  public static void reset() {
    sellItems[0] = treasureMap;

    final List<LootItems> items = LootItems.getMarketWeapons(Accessor.getGameManager().getPlayingPlayer());

    // Randomize the items
    Collections.shuffle(items);

    // makes it so that the items on the grid aren't the same
    for (int i = 1; i <= 6; i++) {
      if (items.size() < i) // Blank if we run out of items
        sellItems[i] = LootItems.BLANK;
      else
        sellItems[i] = items.get(i - 1);
    }
  }

  @Override
  public void render() {
    int yOffset = 25;

    renderMarketBackground();

    final SpriteBatch batch = SceneManager.getSpriteBatch();
    Gdx.gl.glEnable(GL20.GL_BLEND);

    batch.begin();

    // rendering grids in the market to store the items
    leftMarketGrid.renderGrid(new Color(0, 0, 0, 0));
    centreMarketGrid.renderGrid(new Color(0, 0, 0, 0));
    rightMarketGrid.renderGrid(new Color(0, 0, 0, 0));
    centreMarketGridNum.renderGrid(new Color(0, 0, 0, 0));
    leftMarketGridNum.renderGrid(new Color(0, 0, 0, 0));
    rightMarketGridNum.renderGrid(new Color(0, 0, 0, 0));

    batch.end();

    // rendering the map in the middle of the scene
    if (sellItems[0] == LootItems.BLANK || Accessor.getGameManager().getPlayingPlayer().getItems().contains(LootItems.TREASURE_MAP)) {
      centreMarketGrid.renderTextInGrid(0, 0, "SOLD\nOUT", true, -1, yOffset, 2, false);
    } else {
      sellItems[0].render(centreMarketGrid, 0, 0);
      centreMarketGrid.renderTextInGrid(0, 0, "$" + sellItems[0].getSellPrice(), true, -1, 3, sellItems[0].getSellPrice() > Accessor.getGameManager().getPlayingPlayer().getCoins());
      centreMarketGridNum.renderTextInGrid(0, 0, "0", true, 0, 3, false);
    }

    // renders the scoreboard and inventory panel
    scoreboardPanel.render();
    inventoryPanel.render();

    // renders the items in the grid
    for (int i = 1; i <= 6; i++) {
      if (i <= 3) {
        sellItems[i].render(leftMarketGrid, i - 1, 0);
        if (sellItems[i] == LootItems.BLANK) {
          leftMarketGrid.renderTextInGrid(i - 1, 0, "SOLD\nOUT", true, -1, yOffset, 2, false);
        } else {
          leftMarketGrid.renderTextInGrid(i - 1, 0, "$" + sellItems[i].getSellPrice(), true, -1, 3, sellItems[i].getSellPrice() > Accessor.getGameManager().getPlayingPlayer().getCoins());
          leftMarketGridNum.renderTextInGrid(i - 1, 0, " " + i + " ", true, 0, 3, false);
        }

      }

      if (i == 4) {
        sellItems[i].render(rightMarketGrid, 0, 0);
        if (sellItems[i] == LootItems.BLANK) {
          rightMarketGrid.renderTextInGrid(0, 0, "SOLD\nOUT", true, -1, yOffset, 2, false);
        } else {
          rightMarketGrid.renderTextInGrid(0, 0, "$" + sellItems[i].getSellPrice(), true, -1, 3, sellItems[i].getSellPrice() > Accessor.getGameManager().getPlayingPlayer().getCoins());
          rightMarketGridNum.renderTextInGrid(0, 0, " " + i + " ", true, 0, 3, false);
        }

      }

      if ((Accessor.getGameManager().getPlayingPlayer().getPower() >= 20) && i == 5) {
        sellItems[i].render(rightMarketGrid, 1, 0);
        if (sellItems[i] == LootItems.BLANK) {
          rightMarketGrid.renderTextInGrid(1, 0, "SOLD\nOUT", true, -1, yOffset, 2, false);
        } else {
          rightMarketGrid.renderTextInGrid(1, 0, "$" + sellItems[i].getSellPrice(), true, -1, 3, sellItems[i].getSellPrice() > Accessor.getGameManager().getPlayingPlayer().getCoins());
          rightMarketGridNum.renderTextInGrid(i - 4, 0, " " + i + " ", true, 0, 3, false);
        }

      } else if (i == 5) {

        rightMarketGrid.renderTextInGrid(1, 0, "GET\nMORE\nPOWER", true, 0, 40, 2, false);
      }

      if ((Accessor.getGameManager().getPlayingPlayer().getPower() >= 30) && i == 6) {
        sellItems[i].render(rightMarketGrid, 2, 0);
        if (sellItems[i] == LootItems.BLANK) {
          rightMarketGrid.renderTextInGrid(2, 0, "SOLD\nOUT", true, -1, yOffset, 2, false);
        } else {
          rightMarketGrid.renderTextInGrid(2, 0, "$" + sellItems[i].getSellPrice(), true, -1, 3, sellItems[i].getSellPrice() > Accessor.getGameManager().getPlayingPlayer().getCoins());
          rightMarketGridNum.renderTextInGrid(i - 4, 0, " " + i + " ", true, 0, 3, false);
        }


      } else if (i == 6) {
        rightMarketGrid.renderTextInGrid(2, 0, "GET\nMORE\nPOWER", true, 0, 40, 2, false);
      }

    }

    batch.begin();

        // prints helpful information and prompts
        drawCenteredText(batch, 250, 3.3F, "Welcome To The Market");
        drawCenteredText(batch, 30, 2.5F, "Click the Number on Your Keyboard to Buy an Item");
        drawCenteredText(batch, -300, 2.5F, "Press [SPACE] to return to the map");

    batch.end();
  }

  // logic that checks if the player can buy the item at a specific index
  public static void canBuy(int index) {
    final GameManager manager = Accessor.getGameManager();
    if ((manager.getPlayingPlayer().getCoins() >= sellItems[index].getSellPrice()) && (!sellItems[index].equals(LootItems.BLANK)) && (!manager.getPlayingPlayer().getItems().contains(sellItems[index]))) {
      manager.getPlayingPlayer().addPower(sellItems[index]);
      manager.getPlayingPlayer().addLoot(sellItems[index]);
      manager.getPlayingPlayer().removeCoins(sellItems[index].getSellPrice());
      sellItems[index] = LootItems.BLANK;
    }
  }
}