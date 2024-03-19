package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.Character;
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
  int j;
  //temporary knowledge for learning how to move sprites

  public BattleScene() {
    super(GameState.BATTLE);
  }

  @Override
  public void render() {
    renderBattleBackground();
    if (i<440){
      i++;i++;
    }
    if(j<440){
      j++;j++;
    }
    final BitmapFont font = SceneManager.getFont();
    final SpriteBatch batch = SceneManager.getSpriteBatch();
    font.getData().setScale(2F);

    batch.begin();

    final GameManager manager = Accessor.getGameManager();


    final TextureRegion player1Model = manager.getPlayer1().getCurrFrameRight();
    final TextureRegion player2Model = manager.getPlayer2().getCurrFrameLeft();

    //player#.getCurrFrame() gets the current model that was chosen by player

    batch.draw(player1Model, i - 200, 30, player1Model.getRegionWidth() * 3, player1Model.getRegionHeight() * 3);
    batch.draw(player2Model, 1255 - j, 30, player2Model.getRegionWidth() * 3, player2Model.getRegionHeight() * 3);
    //Player one will stop at x:240, Player two will stop at x:815
    //player1 moving 250 since i start at -10


    //every time new battle reset values.
    //every time new battle reset values.
//manager.getPlayer1().getCharacter().getColour()
    font.setColor(manager.getPlayer1().getSolidColour()); // TODO Change the colour by getting the colour attributed to the correct character
    font.draw(batch, manager.getPlayer1().getName(), 30, 770);

    font.setColor(manager.getPlayer2().getSolidColour());
    font.draw(batch, manager.getPlayer2().getName(), 1030, 770);

    // TODO make reset method so that the players reset to their positions off screen.


    //TODO Finish Attacks
    if(manager.getPlayer1().getStrength()<manager.getPlayer2().getStrength()){
      player2Win();
    }
    if(manager.getPlayer2().getStrength()<manager.getPlayer1().getStrength()){
      player1Win();
    }
    else if (manager.getPlayer2().getStrength() == manager.getPlayer1().getStrength()){
      if (Accessor.getGameManager().getPlayingPlayer() == Accessor.getGameManager().getPlayer1()){
        player1WinDraw();
      }
      else{
        player2WinDraw();
      }
    }



    //TODO make a method for setting things back to how they were before we changed in battle method
    //(eg make colours make to transparent)


    batch.end();
  }

  public void player1Win(){
    player2Attack();
    //SET A WAIT TIME IN BETWEEN ATTACKS
    player1Attack();
    int money = (((Accessor.getGameManager().getPlayer1().getStrength() - Accessor.getGameManager().getPlayer2().getStrength()) / (Accessor.getGameManager().getPlayer1().getStrength() + Accessor.getGameManager().getPlayer2().getStrength())) * Accessor.getGameManager().getPlayer2().getCoins());
    Accessor.getGameManager().getPlayer1().gainCoins(money);
    Accessor.getGameManager().getPlayer2().loseCoins(money);

    int newStrength = Accessor.getGameManager().getPlayer1().getStrength() - Accessor.getGameManager().getPlayer2().getStrength();
    Accessor.getGameManager().getPlayer1().setStrength(newStrength);
    Accessor.getGameManager().getPlayer2().setStrength(0);

    Accessor.getGameManager().getPlayer2().setX(10);
    Accessor.getGameManager().getPlayer2().setY(0);
  }

  public void player2Win(){
    player1Attack();
    //SET A WAIT TIME IN BETWEEN ATTACKS
    player2Attack();
    int money = (((Accessor.getGameManager().getPlayer2().getStrength() - Accessor.getGameManager().getPlayer1().getStrength()) / (Accessor.getGameManager().getPlayer1().getStrength() + Accessor.getGameManager().getPlayer2().getStrength())) * Accessor.getGameManager().getPlayer1().getCoins());
    Accessor.getGameManager().getPlayer2().gainCoins(money);
    Accessor.getGameManager().getPlayer1().loseCoins(money);

    int newStrength = Accessor.getGameManager().getPlayer2().getStrength() - Accessor.getGameManager().getPlayer1().getStrength();
    Accessor.getGameManager().getPlayer2().setStrength(newStrength);
    Accessor.getGameManager().getPlayer1().setStrength(0);

    Accessor.getGameManager().getPlayer1().setX(10);
    Accessor.getGameManager().getPlayer1().setY(0);
  }

  public void player1WinDraw(){
    Accessor.getGameManager().getPlayer1().setStrength(0);
    Accessor.getGameManager().getPlayer2().setStrength(0);

    Accessor.getGameManager().getPlayer2().setX(10);
    Accessor.getGameManager().getPlayer2().setY(0);
  }

  public void player2WinDraw(){
    Accessor.getGameManager().getPlayer2().setStrength(0);
    Accessor.getGameManager().getPlayer1().setStrength(0);

    Accessor.getGameManager().getPlayer1().setX(10);
    Accessor.getGameManager().getPlayer1().setY(0);
  }

  public void resetBattle() {

    i= -10;
    j= -10;

  }
  private void player1Attack(){
//play player animation and calculate damage accordingly
  }

  private void player2Attack(){
//play player animation and calculate damage accordingly
  }

}

