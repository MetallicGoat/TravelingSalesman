package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guelphengg.gameproject.*;
import com.guelphengg.gameproject.griditems.LootItems;
import com.guelphengg.gameproject.griditems.Player;
import com.guelphengg.gameproject.griditems.WeaponType;
import com.guelphengg.gameproject.scenes.scenecomponents.AttackAnimation;

public class BattleScene extends Scene {

  public boolean player1Attacking;
  public boolean player2Attacking;
  int player1Pos;
  int player2Pos;
  int time;
  int swordAttackWait;
  int anmSpeed2;
  int anmTimer2 = 0;
  int anmSpeed1;
  int anmTimer1 = 0;

  public BattleScene() {
    super(GameState.BATTLE);
  }

  @Override
  public void render() {
    renderBattleBackground();

    if (player1Pos < 440)
      player1Pos += 2;

    if (player2Pos < 440)
      player2Pos += 2;

    time += 2;

    final BitmapFont font = SceneManager.getFont();
    final SpriteBatch batch = SceneManager.getSpriteBatch();
    font.getData().setScale(2F);

    batch.begin();

    final GameManager manager = Accessor.getGameManager();


    final TextureRegion player1Model = manager.getPlayer1().getCurrFrameRight();
    final TextureRegion player2Model = manager.getPlayer2().getCurrFrameLeft();

    //player#getCurrFrame() gets the current model that was chosen by player

    batch.draw(player1Model, player1Pos - 200, 30, player1Model.getRegionWidth() * 3, player1Model.getRegionHeight() * 3);
    batch.draw(player2Model, 1255 - player2Pos, 30, player2Model.getRegionWidth() * 3, player2Model.getRegionHeight() * 3);
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

    //TODO make victory text for winning player as well as instructing user to press space bar to continue
//    public void victoryText()

    //TODO Finish Attacks
    if (manager.getPlayer1().getPoints() < manager.getPlayer2().getPoints()) {
      player2Win();
      if (time > 1600) {

        font.setColor(Accessor.getGameManager().getPlayer2().getSolidColour());
        font.getData().setScale(4);

        drawCenteredText(batch, 300, 4, Accessor.getGameManager().getPlayer2().getName() + " WINS");

        font.setColor(Color.BLACK);
        font.getData().setScale(3);
        drawCenteredText(batch, 250, 3, "Press Space To Resume Game");

      }
    }

    if (manager.getPlayer2().getPoints() < manager.getPlayer1().getPoints()) {
      player1Win();

      if (time > 1600) {
        font.setColor(Accessor.getGameManager().getPlayer1().getSolidColour());
        font.getData().setScale(4);

        drawCenteredText(batch, 300, 4, Accessor.getGameManager().getPlayer1().getName() + " WINS");

        font.setColor(Color.BLACK);
        font.getData().setScale(3);
        drawCenteredText(batch, 250, 3, "Press Space To Resume Game");
      }

    } else if (manager.getPlayer2().getPoints() == manager.getPlayer1().getPoints()) {
      if (Accessor.getGameManager().getPlayingPlayer() == Accessor.getGameManager().getPlayer1()) {
        player1WinDraw();

        if (time > 1600) {
          font.setColor(Accessor.getGameManager().getPlayer1().getSolidColour());
          font.getData().setScale(4);

          drawCenteredText(batch, 300, 4, Accessor.getGameManager().getPlayer1().getName() + " WINS");

          font.setColor(Color.BLACK);
          font.getData().setScale(3);
          drawCenteredText(batch, 250, 3, "Press Space To Resume Game");

        }

      } else {
        player2WinDraw();
        if (time > 1600) {
          font.setColor(Accessor.getGameManager().getPlayer2().getSolidColour());
          font.getData().setScale(4);

          drawCenteredText(batch, 300, 4, Accessor.getGameManager().getPlayer2().getName() + " WINS");
          font.setColor(Color.BLACK);
          font.getData().setScale(3);
          drawCenteredText(batch, 250, 3, "Press Space To Resume Game");
        }
      }

      font.setColor(Color.WHITE);
    }


    //TODO make a method for setting things back to how they were before we changed in battle method
    //(eg make colours make to transparent)

    manager.getPlayer1().getTransColour();
    manager.getPlayer2().getTransColour();

    //Player1/2 Attacking in their respective attack classes. Movement code is inside the isAttacking code.
    //Need a variable to trigger these inside render
    if (player1Attacking) { //==true
      final Player player1 = Accessor.getGameManager().getPlayer1();
      final WeaponType type = player1.weaponCheck();
      if (type == WeaponType.SWORD) {
//        if (i < 800) {
//          i += 8;
//        }
//        batch.draw(player1Model, i - 200, 30, player1Model.getRegionWidth() * 3, player1Model.getRegionHeight() * 3);
        // Move forward
        if (swordAttackWait == 0 && player1Pos < 800) {
          player1Pos += 8;
        } else {
          swordAttackWait++; // Increment time
        }
        // once b is 50 move backwards
        if (swordAttackWait > 150)
          player1Pos = Math.max(player1Pos - 8, 439);
      }
      if (swordAttackWait > 200)
        swordAttackWait = 0;
      //this is where any other cases would go if needed
      player1Attacking = false;
    }

    if (player2Attacking) { //==true
      final Player player2 = Accessor.getGameManager().getPlayer2();
      final WeaponType type = player2.weaponCheck();
      if (type == WeaponType.SWORD) {//something about how to time the j's. j will still be < 800 after it comes back from attacking and it will wanna attack again

        // Move forward
        if (swordAttackWait == 0 && player2Pos < 800) {
          player2Pos += 8;
        } else {
          swordAttackWait++; // Increment time
        }

        // once b is 50 move backwards
        if (swordAttackWait > 150)
          player2Pos = Math.max(player2Pos - 8, 439);
      }
      if (swordAttackWait > 200)
        swordAttackWait = 0;


      player2Attacking = false;
    }

    batch.end();
  }

  public void player1Win() {
    if (time > 500 && time < 1000) { //i > 439 previously (when they stop moving)
      player2Attack();
    }
    if (time > 1050 && time < 1550) {
      player1Attack();
    }

    final GameManager manager = Accessor.getGameManager();

    final int money = (((manager.getPlayer1().getPoints() - manager.getPlayer2().getPoints()) / (manager.getPlayer1().getPoints() + manager.getPlayer2().getPoints())) * manager.getPlayer2().getCoins());
    manager.getPlayer1().addCoins(money);
    manager.getPlayer2().removeCoins(money);

    final int newStrength = manager.getPlayer1().getPoints() - manager.getPlayer2().getPoints();
    manager.getPlayer1().setPower(newStrength);
    manager.getPlayer2().setPower(0);

    manager.getPlayer2().setX(10);
    manager.getPlayer2().setY(0);

  }

  public void player2Win() {
    if (time > 500 && time < 1000) { //i > 439 previously (when they stop moving)
      player1Attack();
    }
    if (time > 1050 && time < 1550) {
      player2Attack();
    }

    final GameManager manager = Accessor.getGameManager();

    final int money = (((manager.getPlayer2().getPoints() - manager.getPlayer1().getPoints()) / (manager.getPlayer1().getPoints() + manager.getPlayer2().getPoints())) * manager.getPlayer1().getCoins());
    manager.getPlayer2().addCoins(money);
    manager.getPlayer1().removeCoins(money);

    final int newStrength = manager.getPlayer2().getPoints() - manager.getPlayer1().getPoints();
    manager.getPlayer2().setPower(newStrength);
    manager.getPlayer1().setPower(0);

    manager.getPlayer1().setX(10);
    manager.getPlayer1().setY(0);
  }

  public void player1WinDraw() {
    if (time > 500 && time < 1000) { //i > 439 previously (when they stop moving)
      player2Attack();
    }
    if (time > 1050 && time < 1550) {
      player1Attack();
    }

    final GameManager manager = Accessor.getGameManager();

    manager.getPlayer1().setPower(0);
    manager.getPlayer2().setPower(0);

    manager.getPlayer2().setX(10);
    manager.getPlayer2().setY(0);
  }

  public void player2WinDraw() {
    if (time > 500 && time < 1000) { //i > 439 previously (when they stop moving)
      player1Attack();
    }
    if (time > 1050 && time < 1550) {
      player2Attack();
    }


    final GameManager manager = Accessor.getGameManager();

    manager.getPlayer2().setPower(0);
    manager.getPlayer1().setPower(0);

    manager.getPlayer1().setX(10);
    manager.getPlayer1().setY(0);
  }

  public void resetBattle() {
    swordAttackWait = 0;
    player1Pos = -10;
    player2Pos = -10;
    time = 0;
    anmSpeed2 = 0;
    anmTimer2 = 0;
    anmSpeed1 = 0;
    anmTimer1 = 0;
  }

  private void player2Attack() {

    AnimationTextures playerAnimation = AttackAnimation.attackP2Animation();
    AttackAnimation attackP2Animation = new AttackAnimation(playerAnimation);

    player2Attacking = true;
    final Player player2 = Accessor.getGameManager().getPlayer2();
    final WeaponType type = player2.weaponCheck();
    if (type == WeaponType.SWORD) {
      anmSpeed2 = LootItems.SWORD.getAnimationSpeed();
      if (anmTimer2 == 0) TSGameSound.SWORD_SOUND.play();
      for (int i = 0; i < anmSpeed2; i++) anmTimer2++;
      anmTimer2++;
      if (time > 600) attackP2Animation.draw(250, 25, 1.5);
      //TODO Cycle through the different types of Swords/Wands/Bows to see specific weapon
    } else if (type == WeaponType.BOW) {
      anmSpeed2 = LootItems.BOW.getAnimationSpeed();
      if (anmTimer2 == 0) TSGameSound.BOW_SOUND.play();
      for (int i = 0; i < anmSpeed2; i++) anmTimer2++;
      attackP2Animation.draw(600 - anmTimer2, 45, 0.2);
    } else if (type == WeaponType.WAND) {
      anmSpeed2 = LootItems.ICE_WAND.getAnimationSpeed();
      if (anmTimer2 == 0) TSGameSound.MAGIC_SOUND.play();
      for (int i = 0; i < anmSpeed2; i++) anmTimer2++;
      attackP2Animation.draw(620 - anmTimer2, 25, 2.5);
    } else if (type == null) {
      anmSpeed2 = 0;
      for (int i = 0; i < anmSpeed2; i++) anmTimer2++;
      attackP2Animation.draw(620 - anmTimer2, 25, 1.5);
    }
    //TODO check which animation used
  }

  private void player1Attack() {
    AnimationTextures playerAnimation = AttackAnimation.attackP1Animation();
    AttackAnimation attackP1Animation = new AttackAnimation(playerAnimation);

    player1Attacking = true;

    final Player player1 = Accessor.getGameManager().getPlayer1();
    final WeaponType type = player1.weaponCheck();

    if (type == WeaponType.SWORD) {
      anmSpeed1 = LootItems.SWORD.getAnimationSpeed();
      if (anmTimer1 == 0) TSGameSound.SWORD_SOUND.play();
      for (int i = 0; i < anmSpeed1; i++) anmTimer1++;
      anmTimer1++;
      if (time > 600) attackP1Animation.draw(730, 25, 1.5);
      //TODO Cycle through the different types of Swords/Wands/Bows to see specific weapon

    } else if (type == WeaponType.BOW) {
      anmSpeed1 = LootItems.BOW.getAnimationSpeed();
      if (anmTimer1 == 0) TSGameSound.BOW_SOUND.play();
      for (int i = 0; i < anmSpeed1; i++) anmTimer1++;
      attackP1Animation.draw(275 + anmTimer1, 45, 0.2);

    } else if (type == WeaponType.WAND) {
      anmSpeed1 = LootItems.ICE_WAND.getAnimationSpeed();
      if (anmTimer1 == 0) TSGameSound.MAGIC_SOUND.play();
      for (int i = 0; i < anmSpeed1; i++) anmTimer1++;
      attackP1Animation.draw(290 + anmTimer1, 25, 2.5);

    } else if (type == null) {
      anmSpeed1 = 0;
      for (int i = 0; i < anmSpeed1; i++) anmTimer1++;
      attackP1Animation.draw(290 + anmTimer1, 25, 1.5);
    }
  }
}