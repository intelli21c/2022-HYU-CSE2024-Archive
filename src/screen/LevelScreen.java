package screen;

import java.awt.*;
import java.awt.event.KeyEvent;
import engine.Cooldown;
import engine.Core;

/**
 * Implements the HUD setting screen, it shows setting menu about HUD.
 */

public class LevelScreen extends Screen {
    /**
     * Milliseconds between changes in user selection.
     */
    private static final int SELECTION_TIME = 300;

    /**
     * Time between changes in user selection.
     */
    private Cooldown selectionCooldown;

    private enum states {
        diffsel, charsel
    };

    states state = states.diffsel;

    public int difficulty;
    public int character;

    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width  Screen width.
     * @param height Screen height.
     * @param fps    Frames per second, frame rate at which the game is run.
     */
    public LevelScreen(final int width, final int height, final int fps) {
        super(width, height, fps);
        this.returnCode = 101;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
    }

    /**
     * Starts the action.
     *
     * @return Next screen code.
     */
    public final int run() {
        super.run();

        return this.returnCode;
    }

    /**
     * Updates the elements on screen and checks for events.
     */
    protected final void update() {
        super.update();

        if (this.selectionCooldown.checkFinished()) {
            if (inputManager.isKeyDown(KeyEvent.VK_LEFT)
                    || inputManager.isKeyDown(KeyEvent.VK_A)) {

                if (state == states.diffsel) {
                    difficulty--;
                    if (difficulty < 0)
                        difficulty = 2;
                }

                if (state == states.charsel) {
                    character--;
                    if (character < 0)
                        character = 2;
                }
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)
                    || inputManager.isKeyDown(KeyEvent.VK_D)) {
                if (state == states.diffsel) {
                    difficulty = (difficulty + 1) % 3;
                }

                if (state == states.charsel) {
                    character = (character + 1) % 3;

                }
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) {
                if (state == states.diffsel) {
                    this.returnCode = 1;
                    this.isRunning = false;
                }

                if (state == states.charsel)
                    this.state = states.diffsel;
                this.selectionCooldown.reset();
            }

            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                if (state == states.diffsel) {
                    this.state = states.charsel;
                } else if (state == states.charsel) {
                    this.returnCode = 3;
                    this.isRunning = false;
                }

                this.selectionCooldown.reset();
            }

        }

        draw();
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);
        switch (state) {
            case diffsel:
                drawManager.drawleveldiff(difficulty);
                break;
            case charsel:
                drawManager.drawlevelchar(character);
                break;
            default:
                break;
        }
        drawManager.completeDrawing(this);
    }
}
