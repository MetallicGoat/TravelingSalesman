package com.guelphengg.gameproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class TravelingSalesman extends ApplicationAdapter {
	SpriteBatch batch, img2;
	public OrthographicCamera cam = new OrthographicCamera();

	private int widthScreen, heightScreen;
	private int widthImage, heightImage;
	Texture img;
	
	@Override
	public void create () {
		widthScreen = Gdx.graphics.getWidth();
		heightScreen = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		img = new Texture("20230808.jpg");
		img2 = new SpriteBatch();

		widthImage = img.getWidth();
		heightImage = img.getHeight();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		cam.update();
		batch.begin();
		batch.draw(img, 0, 0, widthScreen, heightScreen);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public void move(){
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			cam.translate(-3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			cam.translate(3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			cam.translate(0, -3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			cam.translate(0, 3, 0);
		}
	}
}
