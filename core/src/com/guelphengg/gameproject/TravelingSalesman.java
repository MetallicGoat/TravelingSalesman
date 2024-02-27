package com.guelphengg.gameproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class TravelingSalesman extends ApplicationAdapter {
    SpriteBatch batch;
    private int widthScreen, heightScreen;
    private int widthImage, heightImage;
    BitmapFont font;
    Sprite mapSprite;
    Player player2;
    public Texture img, imgMap;
    private static TravelingSalesman instance;

    @Override
    public void create() {
        widthScreen = Gdx.graphics.getWidth();
        heightScreen = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        img = new Texture("Map Background.jpg");
        imgMap = new Texture("Main Scroll.png");
        player2 = new Player("Mark", 100, 100, false);
        font = new BitmapFont();
        mapSprite = new Sprite(imgMap);
        instance = this;

        // Start listening for input
        Gdx.input.setInputProcessor(new InputListener());

        // Initialize the scene manager
        SceneManager.init();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(img, 0, 0, widthScreen, img.getHeight());
        batch.draw(mapSprite, (int) (widthScreen / 2 - mapSprite.getWidth() / 2), (int) (heightScreen / 2 - mapSprite.getHeight() / 2) - 60, mapSprite.getWidth(), mapSprite.getHeight());
        Rectangle rectangle = new Rectangle(widthScreen - 210, heightScreen - 200, 250, 100, Color.BLUE);
        rectangle.draw(batch, (float) 1);
        font.draw(batch, "Player 2 Strength: ".concat(String.valueOf(player2.getStrength())), rectangle.getX() + rectangle.getWidth() / 4, rectangle.getY() + rectangle.getHeight() / 2);
        batch.end();

        SceneManager.getCurrentScene().render();
    }

    @Override
    public void dispose() {
        SceneManager.dispose();
        batch.dispose();
        img.dispose();
    }

    public static TravelingSalesman getInstance() {
        return instance;
    }
}
