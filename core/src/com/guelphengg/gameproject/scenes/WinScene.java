package com.guelphengg.gameproject.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.guelphengg.gameproject.Accessor;
import com.guelphengg.gameproject.GameManager;
import com.guelphengg.gameproject.GameState;
import com.guelphengg.gameproject.SceneManager;
import com.guelphengg.gameproject.util.Util;
import jdk.incubator.vector.VectorOperators;

public class WinScene extends Scene{

    private static final int FRAME_COLS = 10, FRAME_ROWS = 5;

    private static int x = 0;

    private Animation<TextureRegion> elmoAnimation; // Must declare frame type (TextureRegion)

    public WinScene() {
        super(GameState.WINSCREEN);

        // Setup the animation on class creation
        create();
    }

    public void create(){
        final Texture elmoSheet = new Texture(Gdx.files.internal("elmoDanceSpriteSheet.png"));

        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        final TextureRegion[][] tmp = TextureRegion.split(elmoSheet,
                elmoSheet.getWidth() / FRAME_COLS,
                elmoSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        final TextureRegion[] elmoFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                elmoFrames[index++] = tmp[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        elmoAnimation = new Animation<>(0.05f, elmoFrames);
    }

    @Override
    public void render(){
        renderBackground();

        final GameManager manager = Accessor.getGameManager();
        final TextureRegion p1 = manager.getPlayer1().getCurrFrame();
        final TextureRegion p2 = manager.getPlayer2().getCurrFrame();
        final SpriteBatch batch = SceneManager.getSpriteBatch();

        // Get current frame of animation for the current stateTime
        final TextureRegion currentFrame = elmoAnimation.getKeyFrame(Util.stateTime, true);

        batch.begin();

        // Move elmo a little
        x -=2;

        batch.draw(currentFrame, x, 400); // Draw current frame at (50, 50)

        if (manager.getPlayer1().getPoints() >= 10) {
            drawCenteredText(batch, 0, 4, manager.getPlayer1().getCharacter().getName() + " WINS!");
            batch.draw(p1, 500, 450, p1.getRegionWidth() * 4, p1.getRegionHeight() * 4);
        }

        if (manager.getPlayer2().getPoints() >= 10) {
            drawCenteredText(batch, 0, 4,  manager.getPlayer2().getCharacter().getName() + " WINS!");
            batch.draw(p2, 500, 450, p1.getRegionWidth() * 4, p1.getRegionHeight() * 4);
        }

        drawCenteredText(batch, -200, 4, "Press [Space] to return to main menu");

        batch.end();

        // Allows it so that Elmo loops across the screen when it leaves frame view
        if(x == -currentFrame.getRegionWidth()){
            reset();
        }
    }

    public static void reset() {
        x = (int) SceneManager.getViewWidth();
    }
}
