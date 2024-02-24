package com.guelphengg.gameproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;

import java.awt.*;


public class TravelingSalesman extends ApplicationAdapter {
	SpriteBatch batch;
	public OrthographicCamera cam = new OrthographicCamera();
	private int widthScreen, heightScreen;
	private int widthImage, heightImage;
	BitmapFont font;
	FileHandle text;
	Files.FileType textType;

	Player player2 = new Player("Mark", 100, 100, false);
	Texture img;

	private static TravelingSalesman instance;

	@Override
	public void create () {
		widthScreen = Gdx.graphics.getWidth();
		heightScreen = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		img = new Texture("20230808.jpg");
		text = new FileHandle("Super Cosmic Personal Use.ttf");
		font = new BitmapFont();
		instance = this;

		// Start listening for input
		Gdx.input.setInputProcessor(new InputListener());

		// Initialize the scene manager
		SceneManager.init();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0, widthScreen, heightScreen);

		Rectangle rectangle = new Rectangle(widthScreen-210, heightScreen-200, 250, 100, Color.BLUE);
		rectangle.draw(batch, (float)1);
		font.draw(batch, "Player 2 Strength: ".concat(String.valueOf(player2.getStrength())), rectangle.getX()+rectangle.getWidth()/4, rectangle.getY()+rectangle.getHeight()/2);
        batch.end();

		SceneManager.getCurrentScene().render();
	}
	
	@Override
	public void dispose() {
		SceneManager.dispose();
        batch.dispose();
        img.dispose();
	}

	public static TravelingSalesman getInstance(){
		return instance;
	}
}
