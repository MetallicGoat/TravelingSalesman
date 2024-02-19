package com.guelphengg.gameproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;

public class TravelingSalesman extends ApplicationAdapter {

	private static TravelingSalesman instance;
	
	@Override
	public void create () {
		instance = this;

		// Start listening for input
		Gdx.input.setInputProcessor(new InputListener());

		// Initialize the scene manager
		SceneManager.init();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		SceneManager.getCurrentScene().render();
	}
	
	@Override
	public void dispose() {
		SceneManager.dispose();
	}

	public static TravelingSalesman getInstance(){
		return instance;
	}
}
