package com.guelphengg.gameproject.util;

import com.badlogic.gdx.Gdx;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.GameManager;
import com.guelphengg.gameproject.griditems.Player;

public class Util {

  // State time is updated every time a frame is rendered
  // We use this to determine which frame of the animation to display
  public static float stateTime = 0;

  // Get the current time
  public static float getStateTime() {
    return stateTime;
  }

  public static String convertSecondsToString(float seconds) {
    final int minutes = (int) seconds / 60;
    final int remainingSeconds = (int) seconds % 60;

    return minutes + "m " + remainingSeconds + "s";
  }

  // Updated (called only on render method in TravelingSalesman class)
  public static void incrementStateTime() {
    final float deltaTime = Gdx.graphics.getDeltaTime();

    stateTime += deltaTime;

    final GameManager manager = Accessor.getGameManager();

    if (!manager.isWaitingForRoll()) {
      final Player player = manager.getPlayingPlayer();
      player.addPlayTime(deltaTime);
    }
  }
}
