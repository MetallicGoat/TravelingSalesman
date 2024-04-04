package com.guelphengg.gameproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public enum TSGameSound {

  DICE_ROLL("DiceRoll.wav"),
  LOOT("LootSound1.wav"),
  SELL("Sell.wav"),
  BEGIN("Begin.wav"),
  JUMP("JumpTS.wav"),
  BATTLE_START("LowImpact.mp3"),
  SWORD_SOUND("SwordAttack.wav"),
  BOW_SOUND("BowSound.wav"),
  MAGIC_SOUND("EpicBram.mp3");

  final Sound sound;

  TSGameSound(String soundName) {
    this.sound = Gdx.audio.newSound(Gdx.files.internal(soundName));
  }

  public void play() {
    sound.play();
  }

  public static void dispose() {
    for (TSGameSound sound : TSGameSound.values()) {
      sound.sound.dispose();
    }
  }
}
