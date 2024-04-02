package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.GameManager;
import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.SceneManager;
import jdk.incubator.vector.VectorOperators;

public class WinScene extends Scene{

    public WinScene(){super(GameState.WINSCREEN);}

    private static final int FRAME_COLS = 53, FRAME_ROWS = 1;
    Animation<TextureRegion> elmoAnimation; // Must declare frame type (TextureRegion)
    Texture elmoSheet;
    float stateTime;


    public void create(){
        elmoSheet = new Texture(Gdx.files.internal("dancingElmoSpriteSheet.png"));

        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(elmoSheet,
                elmoSheet.getWidth() / FRAME_COLS,
                elmoSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] elmoFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                elmoFrames[index++] = tmp[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        elmoAnimation = new Animation<TextureRegion>(0.01f, elmoFrames);

        // time to 0
        stateTime = 0f;
    }

    @Override
    public void render(){
        renderBackground();
        final GameManager manager = Accessor.getGameManager();
        final TextureRegion p1 = manager.getPlayer1().getCurrFrame();
        final TextureRegion p2 = manager.getPlayer2().getCurrFrame();
        final SpriteBatch batch = SceneManager.getSpriteBatch();

        create();

        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = elmoAnimation.getKeyFrame(stateTime, true);

        batch.begin();

        if(manager.getPlayer1().getPoints() >= 10) {
            drawCenteredText(batch, 0, 4, manager.getPlayer1().getCharacter().getName() + " WINS!");
            batch.draw(p1, 500, 450, p1.getRegionWidth() * 4, p1.getRegionHeight() * 4);
        }

        if(manager.getPlayer2().getPoints() >= 10) {
            drawCenteredText(batch, 0, 4,  manager.getPlayer2().getCharacter().getName() + " WINS!");
            batch.draw(p2, 500, 450, p1.getRegionWidth() * 4, p1.getRegionHeight() * 4);
        }

        batch.draw(currentFrame, 0, 450); // Draw current frame at (50, 50)

        drawCenteredText(batch, -200, 4, "Press [Space] to return to main menu");

        batch.end();
        dispose();
    }

    @Override
    public void drawCenteredText(SpriteBatch batch, int yOffset, float scale, String text) {
        final BitmapFont font = SceneManager.getFont();
        final GlyphLayout glyphLayout = new GlyphLayout();

        font.getData().setScale(scale);
        glyphLayout.setText(font, text);

        float w = glyphLayout.width;
        float h = glyphLayout.height;

        // draw magic (some mathies to find the center)
        font.draw(batch, glyphLayout, (SceneManager.getViewWidth() - w) / 2, (SceneManager.getViewHeight() + h) / 2 + yOffset);
    }

    public void dispose(){
        elmoSheet.dispose();
    }

}
