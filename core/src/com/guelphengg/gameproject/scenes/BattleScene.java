package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.GameManager;
import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.griditems.GridObject;
import com.guelphengg.gameproject.griditems.ItemType;
import com.guelphengg.gameproject.griditems.LootItems;
import com.guelphengg.gameproject.scenes.scenecomponents.AttackAnimation;

import java.util.Timer;

public class BattleScene extends Scene {

  int i;
  int j;
  int t;

  //temporary knowledge for learning how to move sprites
  AttackAnimation animation = new AttackAnimation();

  Timer timer = new Timer();

  public BattleScene() {
    super(GameState.BATTLE);
  }

  @Override
  public void render() {
    renderBattleBackground();
    if (i < 440) {
      i++;
      i++;
    }
    if (j < 440) {
      j++;
      j++;
    }
    t++;
    t++;
    final BitmapFont font = SceneManager.getFont();
    final SpriteBatch batch = SceneManager.getSpriteBatch();
    font.getData().setScale(2F);

    batch.begin();

    final GameManager manager = Accessor.getGameManager();


    final TextureRegion player1Model = manager.getPlayer1().getCurrFrameRight();
    final TextureRegion player2Model = manager.getPlayer2().getCurrFrameLeft();

    //player#getCurrFrame() gets the current model that was chosen by player

    batch.draw(player1Model, i - 200, 30, player1Model.getRegionWidth() * 3, player1Model.getRegionHeight() * 3);
    batch.draw(player2Model, 1255 - j, 30, player2Model.getRegionWidth() * 3, player2Model.getRegionHeight() * 3);
    //Player one will stop at x:240, Player two will stop at x:815
    //player1 moving 250 since i start at -10


    //every time new battle reset values.
    // gets colours associated with each character and then attributes it to the title
    font.setColor(manager.getPlayer1().getCharacter().getColour());// TODO Change the colour by getting the colour attributed to the correct character
    font.setColor(font.getColor().r, font.getColor().g, font.getColor().b, 1);
    font.draw(batch, manager.getPlayer1().getName(), 30, 770);

    font.setColor(manager.getPlayer2().getCharacter().getColour());
    font.setColor(font.getColor().r, font.getColor().g, font.getColor().b, 1);
    font.draw(batch, manager.getPlayer2().getName(), 1030, 770);

    // TODO make reset method so that the players reset to their positions off screen.


    //TODO Finish Attacks
    if (manager.getPlayer1().getStrength() < manager.getPlayer2().getStrength()) {
      player2Win();
    }
    if (manager.getPlayer2().getStrength() < manager.getPlayer1().getStrength()) {
      player1Win();
    } else if (manager.getPlayer2().getStrength() == manager.getPlayer1().getStrength()) {
      if (Accessor.getGameManager().getPlayingPlayer() == Accessor.getGameManager().getPlayer1()) {
        player1WinDraw();
      } else {
        player2WinDraw();
      }
    }


    //TODO make a method for setting things back to how they were before we changed in battle method
    //(eg make colours make to transparent)


    manager.getPlayer1().getTransColour();
    manager.getPlayer2().getTransColour();

    batch.end();
  }

  public void player1Win() {
    player2Attack();
    // SET A WAIT TIME IN BETWEEN ATTACKS
    player1Attack();

    final GameManager manager = Accessor.getGameManager();

    final int money = (((manager.getPlayer1().getStrength() - manager.getPlayer2().getStrength()) / (manager.getPlayer1().getStrength() + manager.getPlayer2().getStrength())) * manager.getPlayer2().getCoins());
    manager.getPlayer1().addCoins(money);
    manager.getPlayer2().removeCoins(money);

    final int newStrength = manager.getPlayer1().getStrength() - manager.getPlayer2().getStrength();
    manager.getPlayer1().setStrength(newStrength);
    manager.getPlayer2().setStrength(0);

    manager.getPlayer2().setX(10);
    manager.getPlayer2().setY(0);

    System.out.println(money);
  }

  public void player2Win() {
    player1Attack();
    //SET A WAIT TIME IN BETWEEN ATTACKS
    player2Attack();

    final GameManager manager = Accessor.getGameManager();

    final int money = (((manager.getPlayer2().getStrength() - manager.getPlayer1().getStrength()) / (manager.getPlayer1().getStrength() + manager.getPlayer2().getStrength())) * manager.getPlayer1().getCoins());
    manager.getPlayer2().addCoins(money);
    manager.getPlayer1().removeCoins(money);

    final int newStrength = manager.getPlayer2().getStrength() - manager.getPlayer1().getStrength();
    manager.getPlayer2().setStrength(newStrength);
    manager.getPlayer1().setStrength(0);

    manager.getPlayer1().setX(10);
    manager.getPlayer1().setY(0);

    System.out.println(money);
  }

  public void player1WinDraw() {
    if (i > 439) {
      player2Attack();
      //SET A WAIT TIME IN BETWEEN ATTACKS
      player1Attack();
    }

    final GameManager manager = Accessor.getGameManager();

    manager.getPlayer1().setStrength(0);
    manager.getPlayer2().setStrength(0);

    manager.getPlayer2().setX(10);
    manager.getPlayer2().setY(0);
  }

  public void player2WinDraw() {
    if (t > 500 && t<1000) { //i > 439 previously (when they stop moving)
      player1Attack();
    }
    if (t > 1000 && t<1500) {
      player2Attack();
    }


    final GameManager manager = Accessor.getGameManager();

    manager.getPlayer2().setStrength(0);
    manager.getPlayer1().setStrength(0);

    manager.getPlayer1().setX(10);
    manager.getPlayer1().setY(0);
  }

  public void resetBattle() {
    i = -10;
    j = -10;
  }

  int o=0;
  private void player2Attack() {
    // play player animation and calculate damage accordingly
//    if ((GridObject.lootedItem.getItemType() == ItemType.WEAPON)
//            && (playingPlayer.getItems().contains(LootItems.SWORD) || playingPlayer.getItems().contains(LootItems.BEJEWELED_SWORD) || playingPlayer.getItems().contains(LootItems.BOW))) {
    o++;
    o++;
      animation.draw(620-o, 25, 1.5);

  }
  int p=0;
  private void player1Attack() {
    // play player animation and calculate damage accordingly
    p++;
    p++;
      animation.draw(290+p, 25, 1.5);
  }

}

