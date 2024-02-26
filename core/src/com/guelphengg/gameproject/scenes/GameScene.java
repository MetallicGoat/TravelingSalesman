package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.graphics.Color;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.GameManager;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;

public class GameScene extends Scene {

  final GameGrid grid = new GameGrid();

  public GameScene() {
    super(SceneType.GAME_SCENE);
  }

  @Override
  public void render() {
    grid.renderGrid(Color.WHITE);

    final GameManager manager = Accessor.getGameManager();

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


    // Render the players
    grid.renderCircleInGrid(manager.getPlayer1().getX(), manager.getPlayer1().getY(), manager.getPlayer1().getColor());
    grid.renderCircleInGrid(manager.getPlayer2().getX(), manager.getPlayer2().getY(), manager.getPlayer2().getColor());
  }

  @Override
  public void onKeyDown(int key) {
    Accessor.getGameManager().gameInput(key);
  }

}
