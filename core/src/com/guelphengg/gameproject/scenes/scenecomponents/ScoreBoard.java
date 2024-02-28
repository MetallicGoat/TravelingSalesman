package com.guelphengg.gameproject.scenes.scenecomponents;

import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.scenes.GameScene;

public class ScoreBoard {
    private int width = (int)(SceneManager.getViewWidth()*0.3), height = (int)(SceneManager.getViewHeight()*0.1);
    private int xPosition = (int)(SceneManager.getViewWidth() - 210), yPosition = (int)(SceneManager.getViewHeight() - 210);

    public ScoreBoard(){

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }
}
