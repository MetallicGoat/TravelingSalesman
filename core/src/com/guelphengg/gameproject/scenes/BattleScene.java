package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

        final BitmapFont font = SceneManager.getFont();
        final SpriteBatch batch = SceneManager.getSpriteBatch();
        font.getData().setScale(2F);
        batch.begin();

        //TODO Code Homies Walkin'

        font.setColor(Color.BLUE);//TODO Change the colour by getting the colour attributed to the correct character
        font.draw(batch, "PlayerOne", 30, 770);

        font.setColor(Color.RED);
        font.draw(batch, "PlayerTwo ", 1030, 770);


        batch.end();


    }



}
