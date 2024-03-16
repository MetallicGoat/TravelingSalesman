package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;
import com.guelphengg.gameproject.scenes.scenecomponents.ScoreboardPanel;

public class BattleScene extends Scene{


    final ScoreboardPanel scoreBoard = new ScoreboardPanel();

    public BattleScene() {
        super(GameState.BATTLE);
    }


    @Override
    public void render() {

        renderBattleBackground();

    }

}
