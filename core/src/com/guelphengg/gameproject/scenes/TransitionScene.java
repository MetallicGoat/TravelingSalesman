package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.SceneManager;

public class TransitionScene extends Scene {

  private GameState currentScene;
  private GameState nextScene;
  private float duration;
  private long startTime;


  public TransitionScene() {
    super(GameState.TRANSITION);
  }

  public void start(long duration, GameState currentScene, GameState nextScene) {
    this.currentScene = currentScene;
    this.nextScene = nextScene;
    this.duration = duration;

    startTime = System.currentTimeMillis();
  }


  @Override
  public void render() {
    final float runningTime = System.currentTimeMillis() - startTime;
    final float fade;

    if (currentScene != null && runningTime < duration / 2) { // fade out old scene
      currentScene.getScene().render();
      fade = (runningTime) / (duration / 2);

    } else { // fade in new scene
      nextScene.getScene().render();
      final boolean fullLength = currentScene != null;

      // This fade logic is weird as fuck, dont mess with it
      fade = 1 - ((runningTime - (fullLength ? duration/2 : 0)) / (fullLength ? duration/2 : duration));
    }


    final ShapeRenderer renderer = SceneManager.getShapeRenderer();

    // This enables transparency
    Gdx.gl.glEnable(GL20.GL_BLEND);

    renderer.begin(ShapeRenderer.ShapeType.Filled);
    renderer.setColor(new Color(0, 0, 0, fade));
    renderer.rect(0, 0, SceneManager.getViewWidth(), SceneManager.getViewHeight());
    renderer.end();


    if (runningTime > duration) {
      Accessor.getGameManager().setState(nextScene);
    }
  }
}
