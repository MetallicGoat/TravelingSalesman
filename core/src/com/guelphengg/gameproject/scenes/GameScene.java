package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.guelphengg.gameproject.*;
import com.guelphengg.gameproject.griditems.GridObject;
import com.guelphengg.gameproject.griditems.Player;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;
import com.guelphengg.gameproject.scenes.scenecomponents.InventoryPanel;
import com.guelphengg.gameproject.scenes.scenecomponents.RollPanel;
import com.guelphengg.gameproject.scenes.scenecomponents.ScoreboardPanel;
import com.guelphengg.gameproject.util.AdvancedShapeRenderer;

public class GameScene extends Scene {

  // This grid is used to render the game map when in large view
  final GameGrid largeGrid = new GameGrid(
      (int) (SceneManager.getViewHeight() * .7), // height
      (int) (SceneManager.getViewHeight() * .7), // width
      (int) (SceneManager.getViewHeight() * .05), // x
      (int) (SceneManager.getViewHeight() * .25), // y
      10, 10 // boxesX, boxesY
  );

  // This grid is used to render the game map when in small view
  final GameGrid miniGrid = new GameGrid(
      (int) (SceneManager.getViewHeight() * .7), // height
      (int) (SceneManager.getViewHeight() * .7), // width
      (int) (SceneManager.getViewHeight() * .05), // x
      (int) (SceneManager.getViewHeight() * .25), // y
      3, 3 // boxesX, boxesY
  );

  // The inventory component
  final InventoryPanel inventory = new InventoryPanel();

  // The scoreboard component
  final ScoreboardPanel scoreBoard = new ScoreboardPanel();

  // the roll panel (dice) component
  final RollPanel rollPanel = new RollPanel();

  public GameScene() {
    super(GameState.RUNNING);
  }

  @Override
  public void render() {
    renderBackground();

    final GameManager manager = Accessor.getGameManager();

    // Render all the components in the game
    inventory.render(manager.getPlayingPlayer());
    scoreBoard.render();
    rollPanel.render();

    // Logic to display things that dont have their own component classes
    // mostly just for the text at the bottom (eg the message displayed when a player can loot)
    {
      final BitmapFont font = SceneManager.getFont();
      final SpriteBatch batch = SceneManager.getSpriteBatch();

      batch.begin();

      // make BIG text :)
      font.getData().setScale(3F);

      //Open help menu
      font.draw(batch, "Press [H] for Help", 30, 60);
      font.draw(batch, "Press [V] to Toggle Map", 30, 110);

      // Can they loot?
      if (manager.playerOn(GridObject.TREASURE_HOUSE) || manager.playerOn(GridObject.LOST_ITEM_HOUSE)) {
        font.setColor(Color.GOLD);
        font.draw(batch, "Press [L] to Loot!", 160, 180);
      }

      // Can they trade?
      if (manager.playerOn(GridObject.CASTLE) && !manager.getPlayingPlayer().getItems().isEmpty()) {
        font.setColor(Color.BLUE);
        font.draw(batch, "Press [T] to Trade Loot! ", 100, 150);
      }

      // Set the color back to white for other things later
      font.setColor(Color.WHITE);

      batch.end();
    }


    // Crazy logic for rendering the map
    if (manager.isLargeMap()) {
      addGridBackground(largeGrid, 0.25F);

      // Render in lage mode:
      largeGrid.renderGrid(Color.WHITE);
      largeGrid.renderTextureInGrid(10, 0, Textures.STARTER_HOUSE.get(), 1, 0, 0);

      for (int i = 0; i <= 9; i++) {
        for (int j = 0; j <= 9; j++) {
          final GridObject object = manager.gridObjects[i][j];

          if (!manager.getPlayingPlayer().canPlayerSee(i, j) && object != GridObject.CASTLE && object != GridObject.MARKET) {
            GridObject.HIDDEN_SQUARE.render(largeGrid, i, j);
            continue;
          }

          if (object != null)
            object.render(largeGrid, i, j);

        }
      }

      // Display the treasure X
      if (manager.getPlayingPlayer().isTreasureLocVisible())
        largeGrid.renderTextureInGrid(manager.getPlayingPlayer().getTreasureX(), manager.getPlayingPlayer().getTreasureY(), Textures.TREASURE_X.get());

    } else {
      addGridBackground(miniGrid, 0.45F);

      // render grid in small mode:
      miniGrid.renderGrid(Color.WHITE);

      // Show objects around playing player close up
      final Player player = manager.getPlayingPlayer();
      final int x = player.getX();
      final int y = player.getY();

      for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
          if (x + i >= 0 && x + i <= 9 && y + j >= 0 && y + j <= 9) {

            // Render nearby grid objects
            final GridObject object = manager.gridObjects[x + i][y + j];

            if (object != null)
              object.render(miniGrid, i + 1, j + 1);

          } else if (x + i == 10 && y + j == 0) { // start square
            miniGrid.renderTextureInGrid(i + 1, j + 1, Textures.STARTER_HOUSE.get(), 1, 0, 0);
          } else {
            miniGrid.renderRectInGrid(i + 1, j + 1, new Color(1, 0, 0, .5F));
          }
        }
      }

    }

    // Render players last (so they are not covered by map objects)
    renderPlayersInGrid(manager);
  }

  private void addGridBackground(GameGrid grid, float transparency) {
    final AdvancedShapeRenderer render = SceneManager.getShapeRenderer();

    // This enables transparency
    Gdx.gl.glEnable(GL20.GL_BLEND);

    render.begin(ShapeRenderer.ShapeType.Filled);
    render.setColor(new Color(0, 0, 0, transparency));
    render.rect(grid.getCornerX(), grid.getCornerY(), grid.getGridWidth(), grid.getGridHeight());
    render.end();
  }

  // Crazy logic to determine how we gotta render the players
  private void renderPlayersInGrid(GameManager manager) {
    final Player player1 = manager.getPlayer1();
    final Player player2 = manager.getPlayer2();
    final Player playingPlayer = manager.getPlayingPlayer();
    final Player otherPlayer = manager.getPlayingPlayer() == manager.getPlayer1() ? manager.getPlayer2() : manager.getPlayer1();

    // Check if the players are in the same square, they should share it (make em smaller)
    if (player1.getX() == player2.getX() && player1.getY() == player2.getY()) {
      player1.setSmall(true);
      player2.setSmall(true);

    } else {
      // There is an object at player 1
      if (player1.isAtStart() || manager.gridObjects[player1.getX()][player1.getY()] != null) {
        player1.setSmall(true);
      } else {
        player1.setSmall(false);
      }

      // There is an object at player 2
      if (player2.isAtStart() || manager.gridObjects[player2.getX()][player2.getY()] != null) {
        player2.setSmall(true);
      } else {
        player2.setSmall(false);
      }
    }

    // add the offset of player 2 is small (so players dont overlap)
    if (player2.isSmall()) {
      if (manager.isLargeMap())
        player2.xOffset = (int) (largeGrid.getBoxWidth() * .5);
      else
        player2.xOffset = (int) (miniGrid.getBoxWidth() * .5);

    } else {
      // remove the offset back they are not small
      player2.xOffset = 0;
    }

    // Make sure we render them thr right size and in the right place
    // (depending on what view the user has on)
    if (manager.isLargeMap()) {
      // Always show the playing player
      playingPlayer.render(largeGrid);

      // We only want to render the other player if they are within 1 square of the playing player
      if (Math.abs(playingPlayer.getX() - otherPlayer.getX()) <= 1 && Math.abs(playingPlayer.getY() - otherPlayer.getY()) <= 1) {
        otherPlayer.render(largeGrid);
      }

    } else {
      // Display the playing player in the center of the map in mini view
      playingPlayer.render(miniGrid, 1, 1);

      // render other player if they are within 1 square of the playing player
      if (Math.abs(playingPlayer.getX() - otherPlayer.getX()) <= 1 && Math.abs(playingPlayer.getY() - otherPlayer.getY()) <= 1) {
        otherPlayer.render(miniGrid, otherPlayer.getX() - playingPlayer.getX() + 1, otherPlayer.getY() - playingPlayer.getY() + 1);
      }
    }
  }
}
