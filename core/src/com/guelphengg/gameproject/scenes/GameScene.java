package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.graphics.Color;
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

    // put some thingies in da grid
    // grid.renderTextureInGrid(0, 0, img);
    // grid.renderTextureInGrid(5, 7,  img);
    // grid.renderTextureInGrid(1, 9, img);
    grid.renderCircleInGrid(GameManager.playerX, GameManager.playerY, Color.CYAN);
  }

  @Override
  public void onKeyDown(int key) {
    GameManager.gameInput(key);
  }

}
