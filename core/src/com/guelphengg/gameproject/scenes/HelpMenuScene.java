package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.scenes.Scene;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.GameManager;
import com.guelphengg.gameproject.SceneManager;
//this scene is for a help menu with game instructions
public class HelpMenuScene extends Scene {

    public HelpMenuScene(){super(GameState.HELP_MENU);}

    @Override
    public void render() {
        renderBackground(0.5f);

        final SpriteBatch batch = SceneManager.getSpriteBatch();
        final BitmapFont font = SceneManager.getFont();

        //create text for help menu scene
        batch.begin();

        drawCenteredText(batch, 340, 4, "CONTROLS");
        drawCenteredText(batch, 270, 2, "T - Trade at Castle");
        drawCenteredText(batch, 230, 2, "L - Loot Glowing Houses");
        drawCenteredText(batch, 190, 2, "R - Roll Dice");
        drawCenteredText(batch, 150, 2, "V - Open/Close Map");

        drawCenteredText(batch, 40, 4, "How To Play");
        drawCenteredText(batch, -30, 2, "Start your turn by rolling the dice");
        drawCenteredText(batch, -70, 2, "Move around the map to loot glowing houses");
        drawCenteredText(batch, -110, 2, "Collect loot and sell it at the castle for money");
        drawCenteredText(batch, -150, 2, "You can battle the other player by entering the same tile as them");
        drawCenteredText(batch, -190, 2, "Battles are decided by who has the highest strength");
        drawCenteredText(batch, -230, 2, "Strength is determined by what weapons you have");
        drawCenteredText(batch, -270, 2, "Winning the battle gives you part of your opponents coins");
        drawCenteredText(batch, -310, 2, "Win the game by having the most coins at the end");

        drawCenteredText(batch, -360, 3, "[H] to Return");

        batch.end();
    }

    //method to print text in center of scene
    private void drawCenteredText(SpriteBatch batch, int yOffset, float scale, String text) {
        final BitmapFont font = SceneManager.getFont();
        final GlyphLayout glyphLayout = new GlyphLayout();

        font.getData().setScale(scale);
        glyphLayout.setText(font, text);

        float w = glyphLayout.width;
        float h = glyphLayout.height;

        // draw magic (some mathies to find the center)
        font.draw(batch, glyphLayout, (SceneManager.getViewWidth() - w)/2, (SceneManager.getViewHeight() + h) / 2 + yOffset);
    }
}
