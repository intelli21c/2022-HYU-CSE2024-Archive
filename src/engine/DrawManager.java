package engine;

import entity.*;
import screen.GameScreen;
import screen.HUDSettingScreen;
import screen.Screen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.logging.Logger;

import static engine.Core.pass_score;
import static engine.Countdown.countDown;

/**
 * Manages screen drawing.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public final class DrawManager {

	/**
	 * Singleton instance of the class.
	 */
	private static DrawManager instance;
	/**
	 * Current frame.
	 */
	private static Frame frame;
	/**
	 * FileManager instance.
	 */
	private static FileManager fileManager;
	/**
	 * Application logger.
	 */
	private static Logger logger;
	/**
	 * Graphics context.
	 */
	private static Graphics graphics;
	/**
	 * Buffer Graphics.
	 */
	private static Graphics backBufferGraphics;
	/**
	 * Buffer image.
	 */
	private static BufferedImage backBuffer;
	/**
	 * Normal sized font.
	 */
	private static Font fontRegular;
	private static Font fontRegular2;
	/**
	 * Normal sized font properties.
	 */
	private static FontMetrics fontRegularMetrics;
	private static FontMetrics fontRegular2Metrics;
	/**
	 * Big sized font.
	 */
	private static Font fontBig;
	/**
	 * Big sized font properties.
	 */
	private static FontMetrics fontBigMetrics;

	private static Font fontSmall;

	/**
	 * Item icon and Image observer
	 */
	BufferedImage Dummy_icon;
	BufferedImage Dummy_data_icon;
	BufferedImage coin_icon;
	BufferedImage ship_1;
	BufferedImage ship_2;
	BufferedImage ship_3;
	BufferedImage bgm_1;
	BufferedImage bgm_2;
	BufferedImage bgm_3;
	ImageObserver observer;
	int position1 = 0;
	int position2 = 0;

	/**
	 * Sprite types mapped to their images.
	 */
	private static Map<SpriteType, boolean[][]> spriteMap;
	public static Map<String, BufferedImage> imagemap;

	/**
	 * Sprite types.
	 */
	public static enum SpriteType {
		/**
		 * Player ship.
		 */
		Ship,
		/**
		 * Destroyed player ship.
		 */
		ShipDestroyed,
		/**
		 * Player bullet.
		 */
		Bullet,
		uzBullet,
		/** Enemy bullets. */
		EnemyBullet,
		EnemyBulletN,
		EnemyBulletH,
		/** First enemy ship - first form. */
		EnemyShipA1,
		/**
		 * First enemy ship - second form.
		 */
		EnemyShipA2,
		/**
		 * Second enemy ship - first form.
		 */
		EnemyShipB1,
		/**
		 * Second enemy ship - second form.
		 */
		EnemyShipB2,
		/**
		 * Third enemy ship - first form.
		 */
		EnemyShipC1,
		/**
		 * Third enemy ship - second form.
		 */
		EnemyShipC2,
		/**
		 * Bonus ship.
		 */
		EnemyShipSpecial,
		Boss,
		/** Destroyed enemy ship - first form. */
		Explosion,
		/** Destroyed enemy ship - second form. */
		Explosion2,
		/** Destroyed enemy ship - third form. */
		Explosion3,
		/** Destroyed enemy ship - fourth form. */
		Explosion4,
		/** Custom Ship Image */
		ShipCustom,
		/**
		 * Custom Ship Image
		 */
		ShipCustomDestroyed,
		/** dropped item */
		Item,
		/** Current Ship Lives */
		ShipLive;

	};

	/**
	 * Private constructor.
	 */
	private DrawManager() {
		fileManager = Core.getFileManager();
		logger = Core.getLogger();
		logger.info("Started loading resources.");

		try {
			spriteMap = new LinkedHashMap<SpriteType, boolean[][]>();
			spriteMap.put(SpriteType.Ship, new boolean[13][8]);
			spriteMap.put(SpriteType.ShipDestroyed, new boolean[13][8]);
			spriteMap.put(SpriteType.Bullet, new boolean[3][5]);
			spriteMap.put(SpriteType.EnemyBullet, new boolean[3][5]);
			spriteMap.put(SpriteType.EnemyBulletN, new boolean[5][5]);
			spriteMap.put(SpriteType.EnemyBulletH, new boolean[7][5]);
			spriteMap.put(SpriteType.EnemyShipA1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipA2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipB1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipB2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipC1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipC2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipSpecial, new boolean[16][7]);
			spriteMap.put(SpriteType.Explosion, new boolean[13][7]);
			spriteMap.put(SpriteType.Explosion2, new boolean[13][7]);
			spriteMap.put(SpriteType.Explosion3, new boolean[13][7]);
			spriteMap.put(SpriteType.Explosion4, new boolean[13][9]);
			spriteMap.put(SpriteType.Item, new boolean[9][8]);
			spriteMap.put(SpriteType.ShipLive, new boolean[13][8]);
			fileManager.loadSprite(spriteMap);
			logger.info("Finished loading the sprites.");

			// Font loading.
			fontRegular = fileManager.loadFont(14f);
			fontRegular2 = fileManager.loadFont(17f);
			fontBig = fileManager.loadFont(24f);
			fontSmall = fileManager.loadFont(12f);
			logger.info("Finished loading the fonts.");

			// Images Loading
			imagemap = new LinkedHashMap<String, BufferedImage>();
			imagemap.put("macarona", fileManager.loadImage("macarona.png"));
			imagemap.put("midoriport", fileManager.loadImage("midoridotport.png"));
			imagemap.put("arisport", fileManager.loadImage("ArisDot.png"));
			imagemap.put("uzport", fileManager.loadImage("UzDot.png"));
			imagemap.put("MidoriBackSprite", fileManager.loadImage("MidoriBackSprite.png"));
			imagemap.put("MidoriBackSpriteLeft", fileManager.loadImage("MidoriBackSpriteLeft.png"));
			imagemap.put("MidoriBackSpriteRight", fileManager.loadImage("MidoriBackSpriteRight.png"));
			imagemap.put("UzBackSprite", fileManager.loadImage("UzBackSprite.png"));
			imagemap.put("UzBackSpriteLeft", fileManager.loadImage("UzBackSpriteLeft.png"));
			imagemap.put("UzBackSpriteRight", fileManager.loadImage("UzBackSpriteRight.png"));
			imagemap.put("ArisBackSprite", fileManager.loadImage("ArisBackSprite.png"));
			imagemap.put("ArisBackSpriteLeft", fileManager.loadImage("ArisBackSpriteLeft.png"));
			imagemap.put("ArisBackSpriteRight", fileManager.loadImage("ArisBackSpriteRight.png"));
			imagemap.put("coin", fileManager.loadImage("coin.png"));
			imagemap.put("sel", fileManager.loadImage("selected.png"));
			imagemap.put("bgm1", fileManager.loadImage("bgm_1.png"));
			imagemap.put("bgm2", fileManager.loadImage("bgm_2.png"));
			imagemap.put("bgm3", fileManager.loadImage("bgm_3.png"));
			imagemap.put("item_heart", fileManager.loadImage("heart.png"));
			imagemap.put("item_bulletspeed", fileManager.loadImage("bulspeed.png"));
			imagemap.put("item_movespeed", fileManager.loadImage("movspeed.png"));
			imagemap.put("background1", fileManager.loadImage("background1.png"));
			imagemap.put("background2", fileManager.loadImage("background2.png"));
			imagemap.put("BlueStar", fileManager.loadImage("BlueStar.png"));
			imagemap.put("GreenHeart", fileManager.loadImage("GreenHeart.png"));
			imagemap.put("RedLunar", fileManager.loadImage("RedLunar.png"));
			imagemap.put("PinkDoll", fileManager.loadImage("PinkDoll.png"));
			imagemap.put("UzBullet", fileManager.loadImage("UzBullet.png"));
			imagemap.put("UzSubBullet", fileManager.loadImage("UzSubBullet.png"));
			imagemap.put("ArisBullet", fileManager.loadImage("ArisBullet.png"));
			imagemap.put("MidoriBullet", fileManager.loadImage("MidoriBullet.png"));
			imagemap.put("enemyBullet", fileManager.loadImage("enemyBullet.png"));
			imagemap.put("bombIcon", fileManager.loadImage("bombIcon.png"));
			imagemap.put("powerItem", fileManager.loadImage("powerItem.png"));
			imagemap.put("bombItem", fileManager.loadImage("bombItem.png"));
			imagemap.put("scoreItem", fileManager.loadImage("scoreItem.png"));
			imagemap.put("LastBoss", fileManager.loadImage("LastBoss.png"));
			imagemap.put("LastBoss2", fileManager.loadImage("LastBoss2.png"));
			imagemap.put("orin1", fileManager.loadImage("Stage2Boss.png"));
			imagemap.put("BombEffect", fileManager.loadImage("BombEffect.png"));

		} catch (IOException e) {
			logger.warning("Loading failed.");
		} catch (FontFormatException e) {
			logger.warning("Font formating failed.");
		}
	}

	/**
	 * Returns shared instance of DrawManager.
	 *
	 * @return Shared instance of DrawManager.
	 */
	protected static DrawManager getInstance() {
		if (instance == null)
			instance = new DrawManager();
		return instance;
	}

	/**
	 * Sets the frame to draw the image on.
	 *
	 * @param currentFrame Frame to draw on.
	 */
	public void setFrame(final Frame currentFrame) {
		frame = currentFrame;
		graphics = frame.getGraphics();
		fontRegularMetrics = graphics.getFontMetrics(fontRegular);
		fontRegular2Metrics = graphics.getFontMetrics(fontRegular2);
		fontBigMetrics = graphics.getFontMetrics(fontBig);
	}

	// scrolling background
	public void backgroundDrawing(Screen screen) {
		int width = screen.getWidth();
		int height = screen.getHeight();
		backBuffer = new BufferedImage(screen.getWidth(), screen.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		backBufferGraphics = backBuffer.getGraphics();
		if (position1 > -height) {
			drawimg("background1", 0, position1, width, height * 3);
			drawimg("background2", 0, position1 + height, width, height * 3);
			position1 -= 5;
		} else if (position1 <= -height && position2 > -height) {
			drawimg("background2", 0, position2, width, height * 3);
			drawimg("background1", 0, position2 + height, width, height * 3);
			position2 -= 5;
		} else {
			position1 = 0;
			position2 = 0;
		}
	}

	/**
	 * First part of the drawing process. Initialices buffers, draws the
	 * background and prepares the images.
	 *
	 * @param screen Screen to draw in.
	 */
	public Graphics initDrawing(final Screen screen) {
		backBuffer = new BufferedImage(screen.getWidth(), screen.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		backBufferGraphics = backBuffer.getGraphics();

		backBufferGraphics.setColor(Color.BLACK);

		fontRegularMetrics = backBufferGraphics.getFontMetrics(fontRegular);
		fontRegular2Metrics = backBufferGraphics.getFontMetrics(fontRegular2);
		fontBigMetrics = backBufferGraphics.getFontMetrics(fontBig);
		backBufferGraphics
				.fillRect(0, 0, screen.getWidth(), screen.getHeight());
		return backBufferGraphics;
	}

	/**
	 * Draws the completed drawing on screen.
	 *
	 * @param screen Screen to draw on.
	 */
	public void completeDrawing(final Screen screen) {
		graphics.drawImage(backBuffer, frame.getInsets().left,
				frame.getInsets().top, frame);
	}

	/**
	 * Draws an entity, using the apropiate image.
	 *
	 * @param entity    Entity to be drawn.
	 * @param positionX Coordinates for the left side of the image.
	 * @param positionY Coordinates for the upper side of the image.
	 */
	public void drawEntity(final Entity entity, final int positionX,
			final int positionY) {
		boolean[][] image = spriteMap.get(entity.getSpriteType());
		backBufferGraphics.setColor(entity.getColor());
		for (int i = 0; i < image.length; i++)
			for (int j = 0; j < image[i].length; j++)
				if (image[i][j])
					backBufferGraphics.drawRect(positionX + i * 2, positionY
							+ j * 2, 1, 1);
	}

	public void drawEBullet(final Bullet b, final int positionX,
			final int positionY) {
		switch (b.getSpriteType()) {
			case Bullet -> drawimg("UzBullet", positionX, positionY, 20, 20);
			case EnemyBullet -> drawimg("enemyBullet", positionX - 10, positionY - 10, 20, 20);
		}
	}

	public void drawPBullet(final Bullet b, final int positionX,
			final int positionY, final int ch) {
		switch (ch) {
			// Midori
			case 0 -> drawimg("MidoriBullet", positionX - 10, positionY - 10, 20, 20);
			// Uz
			case 1 -> drawimg("UzBullet", positionX - 10, positionY - 10, 20, 20);
			// Aris
			case 2 -> drawimg("ArisBullet", positionX - 10, positionY - 10, 20, 20);
		}
	}

	public void drawUBullet(final UzBullet u, final int positionX, final int positionY) {
		drawimg("UzSubBullet", positionX - 10, positionY - 10, 15, 15);
	}

	public void drawPower(Screen screen, final int bulletpower) {
		backBufferGraphics.drawString("Power", 1420, 25);
		backBufferGraphics.drawString(String.valueOf(bulletpower), 1490, 25);

	}

	public void drawBombEffect(Screen screen, final int positionX, final int positionY) {
		drawimg("BombEffect", positionX, positionY, 1000, 1000);
	}

	public void drawEnemy(final EnemyShip enemy, final int positionX,
			final int positionY) {
		switch (enemy.getSpriteType()) {
			case EnemyShipA1, EnemyShipA2 -> drawimg("RedLunar", positionX - 31, positionY - 31, 62, 62);
			case EnemyShipB1, EnemyShipB2 -> drawimg("GreenHeart", positionX - 31, positionY - 31, 62, 62);
			case EnemyShipC1, EnemyShipC2 -> drawimg("BlueStar", positionX - 30, positionY - 29, 60, 58);
			case EnemyShipSpecial -> drawimg("PinkDoll", positionX - 12, positionY - 13, 25, 27);
			case Boss -> drawimg("orin1", positionX - 50, positionY - 50, 100, 100);
		}
	}

	public void drawItem(final Item item, final int positionX,
			final int positionY) {
		switch (item.type) {
			case power -> drawimg("powerItem", positionX, positionY, 20, 20);
			case bomb -> drawimg("bombItem", positionX, positionY, 20, 20);
			case score -> drawimg("scoreItem", positionX, positionY, 20, 20);
		}
	}

	public void drawimg(String name, int positionX, int positionY, int sizex, int sizey) {
		try {
			backBufferGraphics.drawImage(imagemap.get(name), positionX, positionY, sizex, sizey, observer);
		} catch (Exception e) {
		}
	}

	public void drawimgtrans(String name, int positionX, int positionY, int sizex, int sizey, float alpha) {
		BufferedImage source = imagemap.get(name);
		BufferedImage target = new BufferedImage(source.getWidth(),
				source.getHeight(), java.awt.Transparency.TRANSLUCENT);
		// Get the images graphics
		Graphics2D g = target.createGraphics();
		// Set the Graphics composite to Alpha
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				(float) alpha));
		// Draw the image into the prepared reciver image
		g.drawImage(source, null, 0, 0);
		// let go of all system resources in this Graphics
		g.dispose();
		// Return the image
		backBufferGraphics.drawImage(target, positionX, positionY, sizex, sizey, observer);
	}

	/**
	 * For debugging purpouses, draws the canvas borders.
	 *
	 * @param screen Screen to draw in.
	 */
	@SuppressWarnings("unused")
	private void drawBorders(final Screen screen) {
		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		backBufferGraphics.drawLine(0, 0, screen.getWidth() - 1, 0);
		backBufferGraphics.drawLine(0, 0, 0, screen.getHeight() - 1);
		backBufferGraphics.drawLine(screen.getWidth() - 1, 0,
				screen.getWidth() - 1, screen.getHeight() - 1);
		backBufferGraphics.drawLine(0, screen.getHeight() - 1,
				screen.getWidth() - 1, screen.getHeight() - 1);
	}

	/**
	 * For debugging purpouses, draws a grid over the canvas.
	 *
	 * @param screen Screen to draw in.
	 */
	@SuppressWarnings("unused")
	private void drawGrid(final Screen screen) {
		backBufferGraphics.setColor(Color.DARK_GRAY);
		for (int i = 0; i < screen.getHeight() - 1; i += 2)
			backBufferGraphics.drawLine(0, i, screen.getWidth() - 1, i);
		for (int j = 0; j < screen.getWidth() - 1; j += 2)
			backBufferGraphics.drawLine(j, 0, j, screen.getHeight() - 1);
	}

	/**
	 * Draws current score on screen.
	 *
	 * @param screen Screen to draw on.
	 * @param score  Current score.
	 */

	public void drawTimer(final Screen screen, final int count) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.setColor(Color.WHITE);
		int l = fontRegularMetrics.stringWidth("aaaaaaaaaaaa");
		backBufferGraphics.drawString("Timer : " + String.valueOf(count), screen.getWidth() - 167 - l, 25);
	}

	public void drawScore(final Screen screen, final int score) {
		/*
		 * backBufferGraphics.setFont(fontRegular);
		 * backBufferGraphics.setColor(Color.WHITE);
		 * String scoreString = String.format("%04d", score);
		 * backBufferGraphics.drawString(scoreString, screen.getWidth() - 60, 25);
		 */
		backBufferGraphics.setColor(Color.BLACK);
		backBufferGraphics.drawRect(0, 0, screen.getWidth(), 41);
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.setColor(Color.WHITE);

		String scoreString = "";

		// implementation of logic
		fileManager = Core.getFileManager();
		List<Score> highScores;
		// highScores = fileManager.loadHighScores();
		highScores = new ArrayList<Score>();
		highScores.add(new Score("RMH", 1000));
		int max = -1;
		for (int i = 0; i < highScores.size(); i++) {
			if (max < highScores.get(i).getScore()) {
				max = highScores.get(i).getScore();
			}
		}

		if (max < score) {
			scoreString = "new score : ";
			scoreString += String.format("%04d", score);
		} else {
			scoreString = "score : ";
			scoreString += String.format("%04d", score);
		}
		if (score < pass_score.get(((GameScreen) screen).level))
			backBufferGraphics.setColor(Color.RED);
		backBufferGraphics.drawString(scoreString, screen.getWidth() - 167, 25);
	}

	/**
	 * Draws number of remaining lives on screen.
	 *
	 * @param screen Screen to draw on.
	 * @param lives  Current lives.
	 */
	public void drawLives(final Screen screen, final int lives) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.setColor(Color.WHITE);
		Entity dummy = new Entity(0, 0, 0, 0, Color.GREEN);
		dummy.setSpriteType(SpriteType.ShipLive);
		backBufferGraphics.drawString(Integer.toString(lives), 20, 25);
		for (int i = 0; i < lives; i++)
			drawEntity(dummy, 50 + 25 * i, 12);

	}

	/**
	 * Draws a thick line from side to side of the screen.
	 *
	 * @param screen    Screen to draw on.
	 * @param positionY Y coordinate of the line.
	 */
	public void drawHorizontalLine(final Screen screen, final int positionY) {
		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		backBufferGraphics.drawLine(0, positionY, screen.getWidth(), positionY);
		backBufferGraphics.drawLine(0, positionY + 1, screen.getWidth(),
				positionY + 1);
	}

	public void drawBomb(final Screen screen, final int BombNumber) {
		drawimg("bombIcon", 1330, 5, 30, 30);
		backBufferGraphics.drawString("X", 1380, 25);
		backBufferGraphics.drawString(String.valueOf(BombNumber), 1405, 25);
	}

	/**
	 * Draws game title.
	 *
	 * @param screen Screen to draw on.
	 */
	public void drawTitle(final Screen screen) {
		String titleString = "Invaders";
		String instructionsString = "select with w+s / arrows, confirm with space";

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString,
				screen.getHeight() / 2);

		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		drawCenteredBigString(screen, titleString, screen.getHeight() / 3);
	}

	/**
	 * Draws main menu.
	 *
	 * @param screen Screen to draw on.
	 */

	public void drawSettingsMenu(final Screen screen) {
		String settingsString = "Settings";
		String instructionsString = "Press Space to Save Changes";

		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		drawCenteredBigString(screen, settingsString, screen.getHeight() / 8);

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString, screen.getHeight() / 5);
	}

	public void drawMenu(final Screen screen, final int option) {
		String playString = "Play";
		String highScoresString = "High scores";
		String storeString = "Store";
		String settingString = "Settings";
		String exitString = "exit";

		// returnCode == 2 : play
		if (option == 2)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, playString,
				screen.getHeight() / 3 * 2);

		// returnCode == 3 : highscores
		if (option == 3)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, highScoresString, screen.getHeight()
				/ 3 * 2 + fontRegularMetrics.getHeight() * 2);

		// returnCode == 4 : settings
		if (option == 4)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, settingString, screen.getHeight() / 3
				* 2 + fontRegularMetrics.getHeight() * 3);

		// returnCode == 5 : store
		if (option == 5)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, storeString, screen.getHeight() / 3
				* 2 + fontRegularMetrics.getHeight() * 1);

		// returnCode == 0 : exit
		if (option == 0)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, exitString, screen.getHeight() / 3
				* 2 + fontRegularMetrics.getHeight() * 4);
	}

	public void drawSettingItems(final Screen screen, final int option) {

		String screensizeString = "Screen Size";
		String mastersoundString = "Master Sound";
		String musicsoundString = "Music Sound";
		String effectsoundString = "Effect Sound";
		String hudoptionString = "Hud Option";
		String helpString = "Help";
		String exitString = "Exit";

		// returnCode == 400010 : Screen Size
		if (option == 400010)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawLeftRegular2String(screen, screensizeString, screen.getHeight() / 3);

		// returnCode == 400020 : masterSound
		if (option == 400020)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawLeftRegular2String(screen, mastersoundString, screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 2);

		// returnCode == 400030 : musicSound
		if (option == 400030)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawLeftRegular2String(screen, musicsoundString, screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 4);

		// returnCode == 400040 : effectSound
		if (option == 400040)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawLeftRegular2String(screen, effectsoundString, screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 6);

		// returnCode == 400050 : HUD Options
		if (option == 400050)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegular2String(screen, hudoptionString,
				screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 9);

		// returnCode == 400060 : help
		if (option == 400060)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegular2String(screen, helpString, screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 11);

		// returnCode == 1 : exit
		if (option == 1)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegular2String(screen, exitString, screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 13);
	}

	// 스크린 설정
	public void drawSettingOption(final Screen screen, final int option, final int screenchange,
			final int MasterSoundchange, final int MusicSoundchange, final int EffectSoundchange) {
		String defaultScreenmessage = "Three mod";
		String defaultMasterSoundmessage = "Five mod";
		String defaultMusicSoundmessage = "Five mod";
		String defaultEffectSoundmessage = "Five mod";
		String screenSizeOption1 = "Standard";
		String screenSizeOption2 = "Wide  Mode";
		String screenSizeOption3 = "Full Mode";
		String SoundOption1 = "25%";
		String SoundOption2 = "50%";
		String SoundOption3 = "75%";
		String SoundOption4 = "100%";
		String SoundOption5 = "0%";

		// 스크롤로 대체 예정이니까 Sound 담당하는 사람이 지우고 사용하면 됩니다.

		// screenSize
		if (option == 400010) {
			if (screenchange == 1) {
				backBufferGraphics.setColor(Color.white);
				defaultScreenmessage = screenSizeOption1;
			}
			if (screenchange == 2) {
				backBufferGraphics.setColor(Color.white);
				defaultScreenmessage = screenSizeOption2;
			}
			if (screenchange == 3) {
				backBufferGraphics.setColor(Color.white);
				defaultScreenmessage = screenSizeOption3;
			}
		} else {
			backBufferGraphics.setColor(Color.darkGray);
		}
		// drawRightRegular2String(screen, defaultScreenmessage, screen.getHeight() /
		// 3);
		drawRightRegular2String(screen, defaultScreenmessage, screen.getHeight() / 3);

		// Master sound, Music sound, Effect sound
		if (option == 400020) {
			if (MasterSoundchange == 1) {
				backBufferGraphics.setColor(Color.white);
				defaultMasterSoundmessage = SoundOption1;
			}
			if (MasterSoundchange == 2) {
				backBufferGraphics.setColor(Color.white);
				defaultMasterSoundmessage = SoundOption2;
			}
			if (MasterSoundchange == 3) {
				backBufferGraphics.setColor(Color.white);
				defaultMasterSoundmessage = SoundOption3;
			}
			if (MasterSoundchange == 4) {
				backBufferGraphics.setColor(Color.white);
				defaultMasterSoundmessage = SoundOption4;
			}
			if (MasterSoundchange == 5) {
				backBufferGraphics.setColor(Color.white);
				defaultMasterSoundmessage = SoundOption5;
			}
		} else
			backBufferGraphics.setColor(Color.darkGray);
		drawRightRegular2String(screen, defaultMasterSoundmessage,
				screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 2);

		// 추후 수정
		if (option == 400030) {
			if (MusicSoundchange == 1) {
				backBufferGraphics.setColor(Color.white);
				defaultMusicSoundmessage = SoundOption1;
			}
			if (MusicSoundchange == 2) {
				backBufferGraphics.setColor(Color.white);
				defaultMusicSoundmessage = SoundOption2;
			}
			if (MusicSoundchange == 3) {
				backBufferGraphics.setColor(Color.white);
				defaultMusicSoundmessage = SoundOption3;
			}
			if (MusicSoundchange == 4) {
				backBufferGraphics.setColor(Color.white);
				defaultMusicSoundmessage = SoundOption4;
			}
			if (MusicSoundchange == 5) {
				backBufferGraphics.setColor(Color.white);
				defaultMusicSoundmessage = SoundOption5;
			}
		} else
			backBufferGraphics.setColor(Color.darkGray);
		drawRightRegular2String(screen, defaultMusicSoundmessage,
				screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 4);

		// 추후 수정
		if (option == 400040) {
			if (EffectSoundchange == 1) {
				backBufferGraphics.setColor(Color.white);
				defaultEffectSoundmessage = SoundOption1;
			}
			if (EffectSoundchange == 2) {
				backBufferGraphics.setColor(Color.white);
				defaultEffectSoundmessage = SoundOption2;
			}
			if (EffectSoundchange == 3) {
				backBufferGraphics.setColor(Color.white);
				defaultEffectSoundmessage = SoundOption3;
			}
			if (EffectSoundchange == 4) {
				backBufferGraphics.setColor(Color.white);
				defaultEffectSoundmessage = SoundOption4;
			}
			if (EffectSoundchange == 5) {
				backBufferGraphics.setColor(Color.white);
				defaultEffectSoundmessage = SoundOption5;
			}
		} else
			backBufferGraphics.setColor(Color.darkGray);
		drawRightRegular2String(screen, defaultEffectSoundmessage,
				screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 6);
	}

	/**
	 * Draws game results.
	 *
	 * @param screen         Screen to draw on.
	 * @param score          Score obtained.
	 * @param livesRemaining Lives remaining when finished.
	 * @param shipsDestroyed Total ships destroyed.
	 * @param accuracy       Total accuracy.
	 * @param isNewRecord    If the score is a new high score.
	 */
	public void drawResults(final Screen screen, final int score,
			final int livesRemaining, final int shipsDestroyed,
			final float accuracy, final boolean isNewRecord) {
		String scoreString = String.format("score %04d", score);
		String livesRemainingString = "lives remaining " + livesRemaining;
		String shipsDestroyedString = "enemies destroyed " + shipsDestroyed;
		String accuracyString = String
				.format("accuracy %.2f%%", accuracy * 100);

		int height = isNewRecord ? 4 : 2;

		backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, scoreString, screen.getHeight()
				/ height);
		drawCenteredRegularString(screen, livesRemainingString,
				screen.getHeight() / height + fontRegularMetrics.getHeight()
						* 2);
		drawCenteredRegularString(screen, shipsDestroyedString,
				screen.getHeight() / height + fontRegularMetrics.getHeight()
						* 4);
		drawCenteredRegularString(screen, accuracyString, screen.getHeight()
				/ height + fontRegularMetrics.getHeight() * 6);
	}

	/**
	 * Draws interactive characters for name input.
	 *
	 * @param screen           Screen to draw on.
	 * @param name             Current name selected.
	 * @param nameCharSelected Current character selected for modification.
	 */
	public void drawNameInput(final Screen screen, final char[] name,
			final int nameCharSelected) {
		String newRecordString = "New Record!";
		String introduceNameString = "Introduce name:";

		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		drawCenteredRegularString(screen, newRecordString, screen.getHeight()
				/ 4 + fontRegularMetrics.getHeight() * 10);
		backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, introduceNameString,
				screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 12);

		// 3 letters name.
		int positionX = screen.getWidth()
				/ 2
				- (fontRegularMetrics.getWidths()[name[0]]
						+ fontRegularMetrics.getWidths()[name[1]]
						+ fontRegularMetrics.getWidths()[name[2]]
						+ fontRegularMetrics.getWidths()[' ']) / 2;

		for (int i = 0; i < 3; i++) {
			if (i == nameCharSelected)
				backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
			else
				backBufferGraphics.setColor(Color.WHITE);

			positionX += fontRegularMetrics.getWidths()[name[i]] / 2;
			positionX = i == 0 ? positionX
					: positionX
							+ (fontRegularMetrics.getWidths()[name[i - 1]]
									+ fontRegularMetrics.getWidths()[' ']) / 2;

			backBufferGraphics.drawString(Character.toString(name[i]),
					positionX,
					screen.getHeight() / 4 + fontRegularMetrics.getHeight()
							* 14);
		}
	}

	/**
	 * Draws basic content of game over screen.
	 *
	 * @param screen       Screen to draw on.
	 * @param acceptsInput If the screen accepts input.
	 * @param isNewRecord  If the score is a new high score.
	 */
	public void drawGameOver(final Screen screen, final boolean acceptsInput,
			final boolean isNewRecord) {
		String gameOverString = "Game Over";
		String continueOrExitString = "Press Space to play again, Escape to exit";

		int height = isNewRecord ? 4 : 2;

		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		drawCenteredBigString(screen, gameOverString, screen.getHeight()
				/ height - fontBigMetrics.getHeight() * 2);

		if (acceptsInput)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, continueOrExitString,
				screen.getHeight() / 2 + fontRegularMetrics.getHeight() * 10);
	}

	/**
	 * Draws high score screen title and instructions.
	 *
	 * @param screen Screen to draw on.
	 */
	public void drawHighScoreMenu(final Screen screen) {
		String highScoreString = "High Scores";
		String instructionsString = "Press Space to return";

		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		drawCenteredBigString(screen, highScoreString, screen.getHeight() / 8);

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString,
				screen.getHeight() / 5);
	}

	public void drawHighScores_submenu(final Screen screen) {
		String name = "Name";
		String score = "Score";
		String killed = "Killed";
		String bullet = "Bullets";
		String accuracy = "Accuracy";
		String stage = "Stage";

		backBufferGraphics.setColor(Color.gray);
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.fillRect(0, 105, 450, 35);

		backBufferGraphics.setColor(Color.red);
		backBufferGraphics.drawString(name, 13, 105 + 24);
		backBufferGraphics.drawString(score, 63, 129);
		backBufferGraphics.drawString(killed, 128, 129);
		backBufferGraphics.drawString(bullet, 203, 129);
		backBufferGraphics.drawString(accuracy, 290, 129);
		backBufferGraphics.drawString(stage, 388, 129);

	}

	/**
	 * Draws high scores.
	 *
	 * @param screen     Screen to draw on.
	 * @param highScores List of high scores.
	 */
	public void drawHighScores(final Screen screen,
			final List<Score> highScores) {
		backBufferGraphics.setColor(Color.WHITE);
		int i = 0;
		String nameString = "";
		String scoreString = "";
		String killedString = "";
		String bulletString = "";
		String accuracyString = "";
		String stageString = "";
		for (Score score : highScores) {
			scoreString = String.format("%s    %04d    %04d           %04d           %02.02f            %d   ",
					score.getName(),
					score.getScore(), score.getKilled(), score.getBullets(), score.getAccuracy(),
					score.getStage()); // need change 5th variables and score.getStage()
			drawCenteredRegularString(screen, scoreString, screen.getHeight()
					/ 4 + fontRegularMetrics.getHeight() * (i + 1) * 2);
			i++;
		}
	}

	public void drawHUDSettingMenu(final Screen screen, final int option) {
		String HUDString = "HUD Setting";
		String instructionsString = "Press Space to return";
		String option1 = "GREEN";
		String option2 = "RED";
		String option3 = "BLUE";

		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		drawCenteredBigString(screen, HUDString, screen.getHeight() / 8);

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString, screen.getHeight() / 5);

		if (option == 1)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, option1,
				screen.getHeight() / 3 * 2);

		if (option == 2)
			backBufferGraphics.setColor(Color.RED);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, option2, screen.getHeight()
				/ 3 * 2 + fontRegularMetrics.getHeight() * 2);

		if (option == 3)
			backBufferGraphics.setColor(Color.BLUE);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, option3, screen.getHeight() / 3
				* 2 + fontRegularMetrics.getHeight() * 4);

	}

	public void drawHelpMenu(final Screen screen) {
		String HelpString = "Help";
		String instructionsString = "Help menu";
		String[] help1 = { "Press the arrow keys <- / -> (or the A or D keys)", "to move your ship" };
		String[] help2 = { "Press space to shoot & hit an enemy ships" };
		String[] help3 = { "Dodge enemy missiles by pressing the key" };
		String[] help4 = { "The life to play the game is 3" };
		String[] help5 = { "Eliminate all enemies", "to advance to the next level" };
		String[] help6 = { "check your highscore in highscore menu" };
		String[][] helps = { help1, help2, help3, help4, help5, help6 };
		int i = 0;
		int j = 0;

		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		drawCenteredBigString(screen, HelpString, screen.getHeight() / 8);

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString, screen.getHeight() / 6);

		backBufferGraphics.setColor(Color.WHITE);
		for (String[] help : helps) {
			for (String helpString : help) {
				drawCenteredRegularString(screen, helpString,
						screen.getHeight() * 3 / 13 + fontRegularMetrics.getHeight() * i +
								fontRegularMetrics.getHeight() * 2 * j);
				i++;
			}
			j++;
		}
	}

	/**
	 * Draws a centered string on regular font.
	 *
	 * @param screen Screen to draw on.
	 * @param string String to draw.
	 * @param height Height of the drawing.
	 */
	public void drawCenteredRegular2String(final Screen screen,
			final String string, final int height) {
		backBufferGraphics.setFont(fontRegular2);
		backBufferGraphics.drawString(string, screen.getWidth() / 2 - fontRegular2Metrics.stringWidth(string) / 2,
				height);
	}

	public void drawCenteredRegularString(final Screen screen,
			final String string, final int height) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.drawString(string, screen.getWidth() / 2 - fontRegularMetrics.stringWidth(string) / 2,
				height);
	}

	public void drawLeftRegular2String(final Screen screen,
			final String string, final int height) {
		backBufferGraphics.setFont(fontRegular2);
		backBufferGraphics.drawString(string, screen.getWidth() / 4 - fontRegular2Metrics.stringWidth(string) / 2,
				height);
	}

	public void drawRightRegular2String(final Screen screen,
			final String string, final int height) {
		backBufferGraphics.setFont(fontRegular2);
		backBufferGraphics.drawString(string, 3 * screen.getWidth() / 4
				- fontRegular2Metrics.stringWidth(string) / 2, height);
	}

	/**
	 * Draws a centered string on big font.
	 *
	 * @param screen Screen to draw on.
	 * @param string String to draw.
	 * @param height Height of the drawing.
	 */
	public void drawCenteredBigString(final Screen screen, final String string,
			final int height) {
		backBufferGraphics.setFont(fontBig);
		backBufferGraphics.drawString(string, screen.getWidth() / 2 - fontBigMetrics.stringWidth(string) / 2, height);
	}

	/**
	 * Countdown to game start.
	 *
	 * @param screen    Screen to draw on.
	 * @param level     Game difficulty level.
	 * @param number    Countdown number.
	 * @param bonusLife Checks if a bonus life is received.
	 */
	public void drawCountDown(final Screen screen, final int level,
			final int number) {
		int rectWidth = screen.getWidth();
		int rectHeight = screen.getHeight() / 6;
		backBufferGraphics.setColor(Color.BLACK);
		backBufferGraphics.fillRect(0, screen.getHeight() / 2 - rectHeight / 2,
				rectWidth, rectHeight);
		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		if (number >= 4)
			drawCenteredBigString(screen, "Level " + level,
					screen.getHeight() / 2
							+ fontBigMetrics.getHeight() / 3);
		else if (number != 0)
			drawCenteredBigString(screen, Integer.toString(number),
					screen.getHeight() / 2 + fontBigMetrics.getHeight() / 3);
		else
			drawCenteredBigString(screen, "GO!", screen.getHeight() / 2
					+ fontBigMetrics.getHeight() / 3);
	}

	public int getshopgridcoordx(int c) {
		return 31 + 100 * c;
		// assumed grid size
	}

	public int getshopgridcoordy(int r) {
		return 130 + 130 * r;
		// assumed grid size
	}

	public void drawSelectIcon_ship(Screen screen, int x, int y) {
		backBufferGraphics.drawImage(imagemap.get("sel"), x, y, null, observer);
	}

	public void drawSelectIcon_bgm(Screen screen, int x, int y) {
		backBufferGraphics.drawImage(imagemap.get("sel"), x, y, null, observer);
	}

	private java.util.ArrayList<String> formatstr(String input, int linelen) {
		int frontdelim = 0;
		int backdelim = 0;
		var x = new ArrayList<String>();
		while ((input.length() - frontdelim > linelen) || ((input.indexOf('\n', frontdelim) != -1))) {
			if ((input.indexOf('\n', frontdelim) != -1) && ((input.indexOf('\n', frontdelim) - frontdelim) < linelen)) {
				backdelim = input.indexOf('\n', frontdelim);
				x.add(input.substring(frontdelim, backdelim));
				frontdelim = backdelim + 1;
			} else {
				backdelim = frontdelim + linelen;
				x.add(input.substring(frontdelim, backdelim));
				frontdelim = backdelim;
			}
		}
		x.add(input.substring(frontdelim, input.length()));
		return x;
	}

	private void drawmultiline(String input, int x, int y, int linelen, int maxlines) {
		int offset = 0;
		int c = 1;
		for (String istr : formatstr(input, 15)) {
			if (c++ > maxlines)
				return;
			backBufferGraphics.setColor((Color.WHITE));
			backBufferGraphics.setFont(fontRegular);
			backBufferGraphics.drawString((String) istr, x, offset + y);
			offset += fontRegularMetrics.getHeight();
		}
	}

	public void drawLevelMenu(final Screen screen, final int option) {
		String levelString = "LEVEL";
		String instructionsString = "Press Space to return";
		String level1 = "EASY:";
		String level2 = "NORMAL:";
		String level3 = "HARD:";
		String stage1 = "1";
		String stage2 = "2";
		String stage3 = "3";
		String stage4 = "4";
		String stage5 = "5";

		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		drawCenteredBigString(screen, levelString, screen.getHeight() / 8);

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString, screen.getHeight() / 5);

		if (option == 1)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);

		backBufferGraphics.drawString(level1, 13, 220);
		backBufferGraphics.drawString(stage1, 150, 220);
		backBufferGraphics.drawString(stage2, 210, 220);
		backBufferGraphics.drawString(stage3, 270, 220);
		backBufferGraphics.drawString(stage4, 330, 220);
		backBufferGraphics.drawString(stage5, 390, 220);

		if (option == 2)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		backBufferGraphics.drawString(level2, 13, 280);
		backBufferGraphics.drawString(stage1, 150, 280);
		backBufferGraphics.drawString(stage2, 210, 280);
		backBufferGraphics.drawString(stage3, 270, 280);
		backBufferGraphics.drawString(stage4, 330, 280);
		backBufferGraphics.drawString(stage5, 390, 280);

		if (option == 3)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		backBufferGraphics.drawString(level3, 13, 340);
		backBufferGraphics.drawString(stage1, 150, 340);
		backBufferGraphics.drawString(stage2, 210, 340);
		backBufferGraphics.drawString(stage3, 270, 340);
		backBufferGraphics.drawString(stage4, 330, 340);
		backBufferGraphics.drawString(stage5, 390, 340);

	}

	public void drawleveldiff(int sel) {
		int tenoffset = fontBigMetrics.stringWidth("123456789012345") / 4;
		backBufferGraphics.setColor(Color.WHITE);
		backBufferGraphics.setFont(fontBig);
		backBufferGraphics.drawString("easy", frame.getWidth() / 2 - 200 - fontBigMetrics.stringWidth("easy") / 2, 350);
		backBufferGraphics.drawString("normal", frame.getWidth() / 2 - fontBigMetrics.stringWidth("normal") / 2, 350);
		backBufferGraphics.drawString("hard", frame.getWidth() / 2 + 200 - fontBigMetrics.stringWidth("hard") / 2, 350);
		drawmultiline("easy is easy difficulty, do you need any more description?",
				frame.getWidth() / 2 - 200 - tenoffset, 370, 15, 5);
		drawmultiline("normal is normal difficulty, do you need any more description?",
				frame.getWidth() / 2 - tenoffset, 370, 15, 5);
		drawmultiline("hard is hard difficulty, do you need any more description?",
				frame.getWidth() / 2 + 200 - tenoffset, 370, 15, 5);
		backBufferGraphics.setColor(Color.GREEN);
		backBufferGraphics.setFont(fontBig);
		switch (sel) {
			case 0:
				backBufferGraphics.drawString("easy",
						frame.getWidth() / 2 - 200 - fontBigMetrics.stringWidth("easy") / 2, 350);
				break;
			case 1:
				backBufferGraphics.drawString("normal", frame.getWidth() / 2 - fontBigMetrics.stringWidth("normal") / 2,
						350);
				break;
			case 2:
				backBufferGraphics.drawString("hard",
						frame.getWidth() / 2 + 200 - fontBigMetrics.stringWidth("hard") / 2, 350);
				break;
		}
	}

	public void drawlevelchar(int sel) {
		String cstr = "";
		String lstr = "";
		String rstr = "";
		int twtoffset = fontBigMetrics.stringWidth("1234567890123456789012345") / 4;
		switch (sel) {
			case 0:
				cstr = "Midori";
				lstr = "Aris";
				rstr = "Uz";
				drawimg("midoriport", frame.getWidth() / 2 - 500 / 2, 50, 500, 500);
				drawimgtrans("arisport", 50 - 400 / 2, 50, 400, 400, 0.5f);
				drawimgtrans("uzport", frame.getWidth() - 50 - 400 / 2, 50, 400, 400, 0.5f);
				drawmultiline(
						"Using a bomb adds one life.\nRecommended for stable play or ones not familiar bullet hell genre.",
						frame.getWidth() / 2 - twtoffset, 670, 25, 6);
				break;
			case 1:
				cstr = "Uz";
				lstr = "Midori";
				rstr = "Aris";
				drawimg("uzport", frame.getWidth() / 2 - 500 / 2, 50, 500, 500);
				drawimgtrans("midoriport", 50 - 400 / 2, 50, 400, 400, 0.5f);
				drawimgtrans("arisport", frame.getWidth() - 50 - 400 / 2, 50, 400, 400, 0.5f);
				drawmultiline(
						"Least stable but fastest. Character with high risk and high return. Collects score and item at the top. For expreienced players.",
						frame.getWidth() / 2 - twtoffset, 670, 25, 8);
				break;
			case 2:
				cstr = "Aris";
				lstr = "Uz";
				rstr = "Midori";
				drawimg("arisport", frame.getWidth() / 2 - 500 / 2, 50, 500, 500);
				drawimgtrans("uzport", 50 - 400 / 2, 50, 400, 400, 0.5f);
				drawimgtrans("midoriport", frame.getWidth() - 50 - 400 / 2, 50, 400, 400, 0.5f);
				drawmultiline(
						"Strongest character, but bullets go straight.\nFor firepower lovers.",
						frame.getWidth() / 2 - twtoffset, 670, 25, 6);
				break;
		}
		backBufferGraphics.setColor(Color.WHITE);
		backBufferGraphics.setFont(fontBig);
		backBufferGraphics.drawString(lstr, 0 + 125 - (fontBigMetrics.stringWidth(lstr) / 2), 550);
		backBufferGraphics.drawString(rstr, frame.getWidth() - 125 - (fontBigMetrics.stringWidth(rstr) / 2), 550);

		backBufferGraphics.setColor(Color.GREEN);
		backBufferGraphics.setFont(fontBig);
		backBufferGraphics.drawString(cstr, frame.getWidth() / 2 - (fontBigMetrics.stringWidth(cstr) / 2), 600);
	}

	public void drawsquare(int x, int y, int w, int h, java.awt.Color c) {
		this.backBufferGraphics.setColor(c);
		this.backBufferGraphics.fillRect(x, y, w, h);
	}
}
