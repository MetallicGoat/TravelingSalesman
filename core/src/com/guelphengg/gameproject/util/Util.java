package com.guelphengg.gameproject.util;

import com.badlogic.gdx.Gdx;

public class Util {

  // State time is updated every time a frame is rendered
  // We use this to determine which frame of the animation to display
  public static float stateTime = 0;

  // Get the current time
  public static float getStateTime() {
    return stateTime;
  }

  // Updated (called only on render method in TravelingSalesman class)
  public static void incrementStateTime() {
    stateTime += Gdx.graphics.getDeltaTime();;
  }

}
