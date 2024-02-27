package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guelphengg.gameproject.*;
import com.guelphengg.gameproject.griditems.Player;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;
import com.guelphengg.gameproject.scenes.scenecomponents.Rectangle;

public class GameScene extends Scene {

  final GameGrid grid = new GameGrid();
  Rectangle rectangle;
  BitmapFont font = new BitmapFont();

  public GameScene() {
    super(GameState.RUNNING);
  }

  @Override
  public void render() {

    final GameManager manager = Accessor.getGameManager();
    final SpriteBatch batch = SceneManager.getSpriteBatch();

    batch.begin();

    grid.renderGrid(Color.WHITE);
    rectangle = new Rectangle(SceneManager.getViewWidth() - 210, SceneManager.getViewHeight() - 200, 2500, 1000, Color.BLUE);
    rectangle.draw(batch, (float) 1);
    font.draw(batch, "Player 2 Strength: ".concat(String.valueOf(manager.getPlayer2().getStrength())), rectangle.getX() + rectangle.getWidth() / 4, rectangle.getY() + rectangle.getHeight() / 2);

    batch.end();
    // put some thingies in da grid
    // grid.renderTextureInGrid(0, 0, img);
    // grid.renderTextureInGrid(5, 7,  img);
    // grid.renderTextureInGrid(1, 9, img);


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
    grid.renderTextureInGrid(10, 0, Textures.STARTER_HOUSE.get(), 1, 0, 0);



    //Render players last so they are not covered by map objects
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

    if (player2.isSmall()){
      player2.xOffset = (int) (grid.getBoxWidth() * .5);
    } else {
      player2.xOffset = 0;
    }

    player1.render(grid);
    player2.render(grid);
  }
}
