package engine;

import screen.*;

import javax.sound.sampled.Clip;
import java.util.ArrayList;
import java.util.logging.*;

/**
 * Implements core game logic.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public final class Core {

	/** Bgm player */
	private static Clip clip;

	/**
	 * Width of current screen.
	 */
	private static int WIDTH = 1024;
	/**
	 * Height of current screen.
	 */
	private static int HEIGHT = 768;
	/**
	 * Max fps of current screen.
	 */
	private static final int FPS = 60;
	/**
	 * Total number of levels.
	 */
	private static final int NUM_LEVELS = 6;

	/**
	 * Frame to draw the screen on.
	 */
	private static Frame frame;
	/**
	 * Screen currently shown.
	 */
	private static Screen currentScreen;
	/**
	 * /**
	 * Application logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(Core.class
			.getSimpleName());
	/**
	 * Logger handler for printing to disk.
	 */
	private static Handler fileHandler;
	/**
	 * Logger handler for printing to console.
	 */
	private static ConsoleHandler consoleHandler;

	/**
	 * Test only !!
	 * You can add item max 15
	 * If you have fewer than 15 items to add, refer to DrawManager's drawshop
	 * method
	 * Ship skin itemid is start 1000 ~
	 * Bgm itemid is start 2000 ~
	 */

	public static ArrayList<Integer> pass_score;

	/**
	 * Test implementation.
	 *
	 * @param args
	 *             Program args, ignored.
	 */
	public static void main(final String[] args) {
		try {
			LOGGER.setUseParentHandlers(false);

			fileHandler = new FileHandler("engine/log");
			fileHandler.setFormatter(new MinimalFormatter());

			consoleHandler = new ConsoleHandler();
			consoleHandler.setFormatter(new MinimalFormatter());

			LOGGER.addHandler(fileHandler);
			LOGGER.addHandler(consoleHandler);
			LOGGER.setLevel(Level.ALL);

		} catch (Exception e) {
			e.printStackTrace();
		}
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		WIDTH = (int) screenSize.getWidth();
		HEIGHT = (int) screenSize.getHeight();
		frame = new Frame(WIDTH, HEIGHT);
		try {
			frame.setIconImage(FileManager.getInstance().loadImage("icon.png"));
		} catch (Exception e) {
		}
		// frame.setExtendedState(frame.getExtendedState() |
		// javax.swing.JFrame.MAXIMIZED_BOTH);
		// frame.setUndecorated(true);
		// frame.setVisible(true);
		// GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
		DrawManager.getInstance().setFrame(frame);
		int width = frame.getWidth();
		int height = frame.getHeight();

		pass_score = new ArrayList<Integer>();
		pass_score.add(0);
		pass_score.add(400);
		pass_score.add(1200);
		pass_score.add(2000);
		pass_score.add(2800);
		pass_score.add(3600);
		pass_score.add(4400);

		GameState gameState = new GameState(1, 0, 50, 0, 0, 3, 0, 0, 1);
		int returnCode = 1;

		do {
			switch (returnCode) {
				case 1:
					// Main menu.
					currentScreen = new TitleScreen(width, height, FPS);
					LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
							+ " title screen at " + FPS + " fps.");
					returnCode = frame.setScreen(currentScreen);
					LOGGER.info("Closing title screen.");
					break;
				case 2:
					currentScreen = new LevelScreen(width, height, FPS);
					returnCode = frame.setScreen(currentScreen);
					gameState = new GameState(1, 0, 50, 0, 0, 3, 0, ((LevelScreen) currentScreen).character,
							((LevelScreen) currentScreen).difficulty);
					break;
				case 3:
					// Game & score
					do {
						new Sound().backroundmusic();
						// One extra live every few levels.
						currentScreen = new GameScreen(gameState,
								width, height, FPS);
						LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
								+ " game screen at " + FPS + " fps.");
						frame.setScreen(currentScreen);
						LOGGER.info("Closing game screen.");

						gameState = ((GameScreen) currentScreen).getGameState();

						gameState = new GameState(gameState.getLevel() + 1,
								gameState.getScore(),
								gameState.getLivesRemaining(),
								gameState.getBulletsShot(),
								gameState.getShipsDestroyed(),
								gameState.getBombNumber(),
								gameState.getPower(),
								gameState.getch(),
								gameState.getdiff());

					} while (gameState.getLivesRemaining() > 0
							&& gameState.getLevel() % NUM_LEVELS != 0
							&& ((GameScreen) currentScreen).accumulated_score >= pass_score
									.get(((GameScreen) currentScreen).level - 1) && !Countdown.endp);

					LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
							+ " score screen at " + FPS + " fps, with a score of "
							+ gameState.getScore() + ", "
							+ gameState.getLivesRemaining() + " lives remaining, "
							+ gameState.getBulletsShot() + " bullets shot and "
							+ gameState.getShipsDestroyed() + " ships destroyed.");
					currentScreen = new ScoreScreen(width, height, FPS, gameState);
					returnCode = frame.setScreen(currentScreen);
					LOGGER.info("Closing score screen.");

					break;

				case 4:
					// High scores.
					currentScreen = new HighScoreScreen(width, height, FPS);
					LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
							+ " high score screen at " + FPS + " fps.");
					returnCode = frame.setScreen(currentScreen);
					LOGGER.info("Closing high score screen.");
					break;
				case 5:
					// Setting.
					currentScreen = new SettingScreen(width, height, FPS);
					LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
							+ " setting screen at " + FPS + " fps.");
					returnCode = frame.setScreen(currentScreen);
					LOGGER.info("Closing setting screen.");
					break;
				case 7:
					// HUDSettingScreen.
					currentScreen = new HUDSettingScreen(width, height, FPS);
					LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
							+ " HUDSetting screen at " + FPS + " fps.");
					returnCode = frame.setScreen(currentScreen);
					LOGGER.info("Closing HUDSetting screen.");
					break;

				/*
				 * case 400010:
				 * // Main menu.
				 * // This makes the old window disappear
				 * Frame old_frame = frame;
				 * // This creates a new window with new width & height values
				 * frame = new Frame(WIDTH, HEIGHT);
				 * DrawManager.getInstance().setFrame(frame);
				 * width = frame.getWidth();
				 * height = frame.getHeight();
				 * currentScreen = new TitleScreen(width, height, FPS);
				 * old_frame.dispose();
				 * LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
				 * + " title screen at " + FPS + " fps.");
				 * returnCode = frame.setScreen(currentScreen);
				 * LOGGER.info("Closing title screen.");
				 * break;
				 */
				default:
					break;
			}

		} while (returnCode != 0);

		fileHandler.flush();
		fileHandler.close();
		System.exit(0);
	}

	/**
	 * Constructor, not called.
	 */
	private Core() {

	}

	/**
	 * Controls access to the logger.
	 *
	 * @return Application logger.
	 */
	public static Logger getLogger() {
		return LOGGER;
	}

	/**
	 * Controls access to the drawing manager.
	 *
	 * @return Application draw manager.
	 */
	public static DrawManager getDrawManager() {
		return DrawManager.getInstance();
	}

	/**
	 * Controls access to the input manager.
	 *
	 * @return Application input manager.
	 */
	public static InputManager getInputManager() {
		return InputManager.getInstance();
	}

	/**
	 * Controls access to the file manager.
	 *
	 * @return Application file manager.
	 */
	public static FileManager getFileManager() {
		return FileManager.getInstance();
	}

	/**
	 * Controls creation of new cooldowns.
	 * 
	 * @param milliseconds
	 *                     Duration of the cooldown.
	 * @return A new cooldown.
	 */
	public static Cooldown getCooldown(final int milliseconds) {
		return new Cooldown(milliseconds);
	}

	/**
	 * Controls creation of new cooldowns with variance.
	 * 
	 * @param milliseconds
	 *                     Duration of the cooldown.
	 * @param variance
	 *                     Variation in the cooldown duration.
	 * @return A new cooldown with variance.
	 */
	public static Cooldown getVariableCooldown(final int milliseconds,
			final int variance) {
		return new Cooldown(milliseconds, variance);
	}

	public static void setSize(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
	}
}