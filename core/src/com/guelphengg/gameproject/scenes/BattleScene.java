package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.scenes.scenecomponents.GameGrid;
import com.guelphengg.gameproject.scenes.scenecomponents.ScoreboardPanel;

import java.util.Iterator;

public class BattleScene extends Scene{

    SpriteBatch batch;
    Texture play1;
    Texture play2;
    private Rectangle bucket;//temporary knowledge for learning how to move sprites


    final ScoreboardPanel scoreBoard = new ScoreboardPanel();

    public BattleScene() {
        super(GameState.BATTLE);
    }

    public void create () {

        play1 = new Texture("sword.png");;
        play2 = new Texture(Gdx.files.internal("bow.png"));

//        camera = new OrthographicCamera();
//        camera.setToOrtho(false, 800, 480);
        //make camera (idk)
        batch = new SpriteBatch();

        //make sound play on a loop, working on sound effects


        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;

    }
    @Override
    public void render() {
        renderBattleBackground();

        final BitmapFont font = SceneManager.getFont();
        final SpriteBatch batch = SceneManager.getSpriteBatch();
        font.getData().setScale(2F);
        batch.begin();

//        batch.draw(play1, bucket.x, bucket.y,100, 100);
//        batch.draw(play2, bucket.x, bucket.y, 100, 100);
//
//        ---------------------------------------------------------
//    if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
//    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
//    if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) bucket.y -= 200 * Gdx.graphics.getDeltaTime();
//    if(Gdx.input.isKeyPressed(Input.Keys.UP)) bucket.y += 200 * Gdx.graphics.getDeltaTime();
//
//    if(bucket.x < 0) bucket.x = 0;
//    if(bucket.x > 800 - 64) bucket.x = 800 - 64;
//
//        //---------------------------------------------------------

        //every time new battle reset values.

        font.setColor(Color.BLUE);//TODO Change the colour by getting the colour attributed to the correct character
        font.draw(batch, "PlayerOne", 30, 770);

        font.setColor(Color.RED);
        font.draw(batch, "PlayerTwo ", 1030, 770);
        //TODO Set coordinates to players off screen, make it so that they slowly move into specified position

         //TODO make reset method so that the players reset to their positions off screen.

        batch.end();


    }

    private void resetbattle(){

    }




}

