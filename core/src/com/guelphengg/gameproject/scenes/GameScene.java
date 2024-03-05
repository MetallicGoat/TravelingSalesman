package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guelphengg.gameproject.*;
import com.guelphengg.gameproject.griditems.GridObject;
import com.guelphengg.gameproject.griditems.Player;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;
import com.guelphengg.gameproject.scenes.scenecomponents.InventoryPanel;
import com.guelphengg.gameproject.scenes.scenecomponents.RollPanel;
import com.guelphengg.gameproject.scenes.scenecomponents.ScoreboardPanel;

public class GameScene extends Scene {

  final GameGrid largeGrid = new GameGrid(
      (int) (SceneManager.getViewHeight() * .7), // height
      (int) (SceneManager.getViewHeight() * .7), // width
      (int) (SceneManager.getViewHeight() * .05), // x
      (int) (SceneManager.getViewHeight() * .25), // y
      10, 10 // boxesX, boxesY
  );

  final GameGrid miniGrid = new GameGrid(
      (int) (SceneManager.getViewHeight() * .7), // height
      (int) (SceneManager.getViewHeight() * .7), // width
      (int) (SceneManager.getViewHeight() * .05), // x
      (int) (SceneManager.getViewHeight() * .25), // y
      3, 3 // boxesX, boxesY
  );


  final InventoryPanel inventory = new InventoryPanel();
  final ScoreboardPanel scoreBoard = new ScoreboardPanel();
  final RollPanel rollPanel = new RollPanel();

  public GameScene() {
    super(GameState.RUNNING);
  }

  @Override
  public void render() {
    final GameManager manager = Accessor.getGameManager();


    // Renders the inventory for the players who is currently playing
    inventory.render(manager.getPlayingPlayer());

    scoreBoard.render();
    rollPanel.render();

    // logic to cover the area where players have not been (with white circles)
    // TODO This works, but it's a little weird. Uncomment it if you want to test

//    for (int i = 0; i <=9; i++) {
//      for (int j = 0; j <=9; j++) {
//        if (!manager.visibleArea[i][j]){
//          grid.renderCircleInGrid(i, j, Color.WHITE);
//        }
//      }
//    }

    // Render the start house

    final BitmapFont font = SceneManager.getFont();

    final SpriteBatch batch = SceneManager.getSpriteBatch();
    batch.begin();
    font.getData().setScale(2F);

    // Current Player Text
    font.setColor(Color.GOLD);
    if (manager.playerOn(GridObject.TREASURE_HOUSE))
      font.draw(batch, "Press [L] to Loot!", 100, 150);
    font.setColor(Color.BLUE);
    if (manager.playerOn(GridObject.CASTLE) && !manager.getPlayingPlayer().getItems().isEmpty())
      font.draw(batch, "Press [T] to Trade Loot! ", 100, 150);
    font.setColor(Color.WHITE);


    batch.end();


    if (manager.isLargeMap()) {
      largeGrid.renderGrid(Color.WHITE);


      largeGrid.renderTextureInGrid(10, 0, Textures.STARTER_HOUSE.get(), 1, 0, 0);

      for (int i = 0; i <= 9; i++) {
        for (int j = 0; j <= 9; j++) {
          final GridObject object = manager.gridObjects[i][j];

          if (object != null)
            object.render(largeGrid, i, j);

        }
      }

    } else {
      miniGrid.renderGrid(Color.WHITE);

      // Show objects around playing player close up
      final Player player = manager.getPlayingPlayer();
      final int x = player.getX();
      final int y = player.getY();

      for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
          if (x + i >= 0 && x + i <= 9 && y + j >= 0 && y + j <= 9) {
            final GridObject object = manager.gridObjects[x + i][y + j];

            if (object != null)
              object.render(miniGrid, i + 1, j + 1);

          } else if (x + i == 10 && y + j == 0) { // start square
            miniGrid.renderTextureInGrid(i + 1, j + 1, Textures.STARTER_HOUSE.get(), 1, 0, 0);
          } else {
            miniGrid.renderRectInGrid(i + 1, j + 1, new Color(1, 0, 0,.5F));
          }
        }
      }

    }

    // Render players last so they are not covered by map objects
    renderPlayersInGrid(manager);
  }


  private void renderPlayersInGrid(GameManager manager) {
    final Player player1 = manager.getPlayer1();
    final Player player2 = manager.getPlayer2();

    // Check if the players are in the same square, they should share it (make em smaller)
    if (player1.getX() == player2.getX() && player1.getY() == player2.getY()) {
      player1.setSmall(true);
      player2.setSmall(true);

    } else {
      // There is some object there
      if (player1.isAtStart() || manager.gridObjects[player1.getX()][player1.getY()] != null) {
        player1.setSmall(true);
      } else {
        player1.setSmall(false);
      }

      if (player2.isAtStart() || manager.gridObjects[player2.getX()][player2.getY()] != null) {
        player2.setSmall(true);
      } else {
        player2.setSmall(false);
      }
    }

    final GameManager gameManager = Accessor.getGameManager();

    if (player2.isSmall()) {
      if (gameManager.isLargeMap())
        player2.xOffset = (int) (largeGrid.getBoxWidth() * .5);
      else
        player2.xOffset = (int) (miniGrid.getBoxWidth() * .5);

    } else {
      player2.xOffset = 0;
    }

    if (gameManager.isLargeMap()) {
      player1.render(largeGrid);
      player2.render(largeGrid);

    } else {
      final Player playingPlayer = gameManager.getPlayingPlayer();
      final Player otherPlayer = gameManager.getPlayingPlayer() == gameManager.getPlayer1() ? gameManager.getPlayer2() : gameManager.getPlayer1();

      playingPlayer.render(miniGrid, 1, 1);

      //render other player if they are within 1 square of the playing player
      if (Math.abs(playingPlayer.getX() - otherPlayer.getX()) <= 1 && Math.abs(playingPlayer.getY() - otherPlayer.getY()) <= 1) {
        otherPlayer.render(miniGrid, otherPlayer.getX() - playingPlayer.getX() + 1, otherPlayer.getY() - playingPlayer.getY() + 1);
      }
    }
  }
}
