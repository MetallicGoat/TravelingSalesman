package com.guelphengg.gameproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public enum TSGameMusic {
  MAIN_MENU_MUSIC("MainMenuTS.mp3"),
  MAIN_GAME_MUSIC("MainGameTS.mp3"),
  BATTLE_MUSIC("BattleMusic.mp3"),
  TRAPPED_MUSIC("TrappedMusic.mp3"),
  MARKET_MUSIC("MarketMusic.mp3"),
  WINNING_MUSIC("Celebration.mp3");

  final Music music;

  TSGameMusic(String musicName) {
    this.music = Gdx.audio.newMusic(Gdx.files.internal(musicName));
    this.music.setLooping(true);
  }

  public static void dispose() {
    for (TSGameMusic music : TSGameMusic.values()) {
      music.music.dispose();
    }
  }

  public void play() {
    music.setLooping(true);
    music.play();
  }

  public void stop() {
    music.stop();
  }
}
