package com.guelphengg.gameproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.guelphengg.gameproject.scenes.Scene;
import com.guelphengg.gameproject.scenes.SceneType;

public class SceneManager {

  private static SceneType currentSceneType;
  private static OrthographicCamera camera;
  private static SpriteBatch batch;
  private static ShapeRenderer shapeRenderer;

  public static void init() {
    camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera.update();

    batch = new SpriteBatch();
    batch.setProjectionMatrix(camera.combined);

    shapeRenderer = new ShapeRenderer();
    shapeRenderer.setProjectionMatrix(camera.combined);

    currentSceneType = SceneType.MAIN_MENU;
  }

  public static SceneType getCurrentSceneType() {
    return currentSceneType;
  }

  public static Scene getCurrentScene() {
    return currentSceneType.getScene();
  }

  public static void setCurrentSceneType(SceneType sceneType) {
    currentSceneType = sceneType;
  }

  public static OrthographicCamera getCamera() {
    return camera;
  }

  public static ShapeRenderer getShapeRenderer() {
    return shapeRenderer;
  }

  public static SpriteBatch getSpriteBatch() {
    return batch;
  }

  public static float getViewWidth() {
    return camera.viewportWidth;
  }

  public static float getViewHeight() {
    return camera.viewportHeight;
  }

  public static void dispose() {
    shapeRenderer.dispose();
    batch.dispose();
  }

}
