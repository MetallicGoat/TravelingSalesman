package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.GameManager;
import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.griditems.Player;

public class BattleScene extends Scene {

  SpriteBatch batch;
  Texture play1;
  Texture play2;
  private Rectangle bucket;

  int i;
  //temporary knowledge for learning how to move sprites

  public BattleScene() {
    super(GameState.BATTLE);
  }

  public void create() {
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
    i++;

    final BitmapFont font = SceneManager.getFont();
    final SpriteBatch batch = SceneManager.getSpriteBatch();
    font.getData().setScale(2F);

    batch.begin();

    final GameManager manager = Accessor.getGameManager();

    final Player player1 = manager.getPlayer1();
    final Player player2 = manager.getPlayer2();

    batch.draw(player1.getCurrFrame(), i, 30, player1.getCurrFrame().getRegionWidth() * 2, player1.getCurrFrame().getRegionHeight() * 2);
    batch.draw(player2.getCurrFrame(), 1030, 30, player2.getCurrFrame().getRegionWidth() * 2, player2.getCurrFrame().getRegionHeight() * 2);
    //        batch.draw(play1, bucket.x, bucket.y,100, 100);
    //        batch.draw(play2, bucket.x, bucket.y, 100, 100);
    //
    //        ---------------------------------------------------------
    //    if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
    //    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
    //     if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) bucket.y -= 200 * Gdx.graphics.getDeltaTime();
    //    if(Gdx.input.isKeyPressed(Input.Keys.UP)) bucket.y += 200 * Gdx.graphics.getDeltaTime();
    //
    //    if(bucket.x < 0) bucket.x = 0;
    //    if(bucket.x > 800 - 64) bucket.x = 800 - 64;
    //
    //        //---------------------------------------------------------

    //every time new battle reset values.

    font.setColor(Color.BLUE); // TODO Change the colour by getting the colour attributed to the correct character
    font.draw(batch, "PlayerOne", 30, 770);

    font.setColor(Color.RED);
    font.draw(batch, "PlayerTwo ", 1030, 770);
    // TODO Set coordinates to players off screen, make it so that they slowly move into specified position

    // TODO make reset method so that the players reset to their positions off screen.

    batch.end();
  }

  private void resetbattle() {

  }
}

