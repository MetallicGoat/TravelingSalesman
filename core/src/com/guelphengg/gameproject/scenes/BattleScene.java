package com.guelphengg.gameproject.scenes;

import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;
import com.guelphengg.gameproject.scenes.scenecomponents.ScoreboardPanel;

public class BattleScene extends Scene{

    final ScoreboardPanel scoreBoard = new ScoreboardPanel();
    final GameGrid miniGrid = new GameGrid(
            (int) (SceneManager.getViewHeight() * .7), // height
            (int) (SceneManager.getViewHeight() * .7), // width
            (int) (SceneManager.getViewHeight() * .05), // x
            (int) (SceneManager.getViewHeight() * .25), // y
            3, 3 // boxesX, boxesY
    );

    public BattleScene() {
        super(GameState.BATTLE);
    }


    @Override
    public void render() {

        scoreBoard.render();

    }
}
