package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.Textures;


public abstract class Scene {

  GameState type;

  public Scene(GameState type) {
    this.type = type;
  }

  public abstract void render();

  // Some scenes share a common background
  protected void renderBackground(float transparency){
    renderBackground();

    final ShapeRenderer render = SceneManager.getShapeRenderer();

    // This enables transparency
    Gdx.gl.glEnable(GL20.GL_BLEND);

    // Render aa back transparent rectangle over the scene
    render.begin(ShapeRenderer.ShapeType.Filled);
    render.setColor(0, 0, 0, transparency);
    render.rect(0, 0, SceneManager.getViewWidth(), SceneManager.getViewHeight());
    render.end();
  }

  // Some scenes share a common background
  protected void renderBackground() {
    final SpriteBatch batch = SceneManager.getSpriteBatch();

    batch.begin();
    batch.draw(Textures.MAP_BACKGROUND.get(), 0, 0, SceneManager.getViewWidth(), SceneManager.getViewHeight());
    batch.end();
  }

  protected void renderBattleBackground(){
    final SpriteBatch batch = SceneManager.getSpriteBatch();

    batch.begin();
    batch.draw(Textures.BATTLE_BACKGROUND.get(), 0, 0, SceneManager.getViewWidth(), SceneManager.getViewHeight());
    batch.end();

  }

  public GameState getSceneType() {
    return type;
  }
}
