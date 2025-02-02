package screen;

import engine.*;
import engine.DrawManager.SpriteType;
import entity.*;
import scripts.stage1;
import scripts.stage2;
import scripts.stage3;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Implements the game screen, where the action happens.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class GameScreen extends Screen {

	GameContext context;

	/**
	 * Milliseconds until the screen accepts user input.
	 */
	private static final int INPUT_DELAY = 6000;
	/**
	 * Bonus score for each life remaining at the end of the level.
	 */
	private static final int LIFE_SCORE = 100;
	/**
	 * Height of the interface separation line.
	 */
	private static final int SEPARATION_LINE_HEIGHT = 40;
	/**
	 * Current difficulty level number.
	 */
	public int level;
	/**
	 * Player's ship.
	 */
	private Ship ship;
	/**
	 * Set of all bullets fired by on screen ships.
	 */
	private Set<Bullet> bullets;
	private Set<UzBullet> uzBullets;

	/** Current score. */
	private int score;
	/** Current coin. */
	private int difficulty;
	private int character;
	/** Current Number of bomb. */
	private int bombNumber;
	/** Current power */
	public int power;
	/** Player lives left. */
	public int lives;
	/**
	 * Total bullets shot by the player.
	 */
	private int bulletsShot;
	/**
	 * Total ships destroyed by the player.
	 */
	private int shipsDestroyed;
	/**
	 * Moment the game starts.
	 */
	private long gameStartTime;

	public int enemyLives;
	/**
	 * Set of all items dropped by on screen enemyships.
	 */

	public int accumulated_score;

	private ArrayList<entity.Item> items;

	private float originSpeed;

	private final int itemcolborder = 300;

	private boolean bordercrossed = false;
	private boolean slowp = false;

	/**
	 * Currently loaded script
	 */
	Script stage;

	/**
	 * Constructor, establishes the properties of the screen.
	 *
	 * @param gameState
	 *                     Current game state.
	 * @param gameSettings
	 *                     Current game settings.
	 * @param bonusLife
	 *                     Checks if a bonus life is awarded this level.
	 * @param width
	 *                     Screen width.
	 * @param height
	 *                     Screen height.
	 * @param fps
	 *                     Frames per second, frame rate at which the game is run.
	 */
	public GameScreen(final GameState gameState, final int width, final int height, final int fps) {
		super(width, height, fps);
		this.level = gameState.getLevel();
		this.score = gameState.getScore();
		this.lives = gameState.getLivesRemaining();
		this.bulletsShot = gameState.getBulletsShot();
		this.shipsDestroyed = gameState.getShipsDestroyed();
		this.bombNumber = gameState.getBombNumber();
		this.power = gameState.getPower();
		this.character = gameState.getch();
		this.difficulty = gameState.getdiff();
	}

	/**
	 * Initializes basic screen properties, and adds necessary elements.
	 */
	public final void initialize() {
		super.initialize();
		this.context = new GameContext();

		switch (this.level) {
			case 1:
				this.stage = new stage1();
				break;
			case 2:
				this.stage = new stage2();
				break;
			case 3:
				this.stage = new stage3();
				break;
			default:
				this.stage = new stage1();
				break;
		}

		stage.prep(null);
		this.ship = switch (character) {
			case 0 -> new Midori(this.width / 2, this.height - 30);
			case 1 -> new Uz(this.width / 2, this.height - 30);
			case 2 -> new Aris(this.width / 2, this.height - 30);
			default -> new Ship(this.width / 2, this.height - 30, Color.GREEN);
		};
		Ship.BULLET_POWER = power;
		context.player = ship;
		context.difficulty = difficulty;
		// Appears each 10-30 seconds.
		this.bullets = new HashSet<Bullet>();
		this.uzBullets = new HashSet<UzBullet>();
		this.items = new ArrayList<entity.Item>();
		// Special input delay / countdown.
		this.gameStartTime = System.currentTimeMillis();
		this.inputDelay = Core.getCooldown(INPUT_DELAY);
		this.inputDelay.reset();
		originSpeed = this.ship.getSpeed();
		engine.Countdown.countDown(99);
	}

	/**
	 * Starts the action.
	 *
	 * @return Next screen code.
	 */
	public final int run() {
		super.run();

		// after program cleanup
		// this.score += LIFE_SCORE * (this.lives - 1);
		this.logger.info("Screen cleared with a score of " + this.score);
		this.accumulated_score += this.score;

		return this.returnCode;
	}

	/**
	 * Updates the elements on screen and checks for events.
	 */
	protected final void update() {
		super.update();

		if (stage.run(context) == 1) {
			this.isRunning = false;
			return;
		}

		boolean moveRight = inputManager.isKeyDown(KeyEvent.VK_RIGHT)
				|| inputManager.isKeyDown(KeyEvent.VK_D);
		boolean moveLeft = inputManager.isKeyDown(KeyEvent.VK_LEFT)
				|| inputManager.isKeyDown(KeyEvent.VK_A);

		boolean moveUp = inputManager.isKeyDown(KeyEvent.VK_UP);
		boolean moveDown = inputManager.isKeyDown(KeyEvent.VK_DOWN);
		boolean moveSlow = inputManager.isKeyDown(KeyEvent.VK_SHIFT);
		boolean openfire = (inputManager.isKeyDown(KeyEvent.VK_SPACE) || inputManager.isKeyDown(KeyEvent.VK_Z));
		// 사실 키가 눌리는 순간(rising edge)만 필요함...
		boolean bomb = (inputManager.isKeyDown(KeyEvent.VK_C) || inputManager.isKeyDown(KeyEvent.VK_X));

		boolean isRightBorder_ship = this.ship.getPositionX()
				+ this.ship.getWidth() + this.ship.getSpeed() > this.width - 1;
		boolean isLeftBorder_ship = this.ship.getPositionX()
				- this.ship.getSpeed() < 1;
		boolean isUpBorder_ship = this.ship.getPositionY()
				- this.ship.getSpeed() < SEPARATION_LINE_HEIGHT - 1;
		boolean isDownBorder_ship = this.ship.getPositionY()
				+ this.ship.getHeight() + this.ship.getSpeed() > this.height - 10;

		if (moveRight && !isRightBorder_ship) {
			this.ship.moveRight();
		}
		if (moveLeft && !isLeftBorder_ship) {
			this.ship.moveLeft();
		}
		if (moveUp && !isUpBorder_ship) {
			this.ship.moveUp();
		}
		if (moveDown && !isDownBorder_ship) {
			this.ship.moveDown();
		}
		if (moveSlow) {
			if (character == 1)
				this.ship.setSPEED(8);
			else
				this.ship.setSPEED(4);
		}
		if (!moveSlow) {
			this.ship.setSPEED(originSpeed);

		}
		if (bomb) {
			if (this.ship.bomb()) {
				if (bombNumber > 0) {
					bombNumber--;
					if (character == 0) {
						lives++;
						for (EnemyShip e : context.enemys) {
							if (!e.isDestroyed()&&e.getSpriteType()!=SpriteType.Boss) {
								e.destroy();
								score += e.getPointValue();
								if (e.droptype != null)
									items.add(new Item(e.getCPositionX(), e.getCPositionY(), 2, e.droptype));
							}
						}
						context.bullets=new ArrayList<Bullet>();
					} else {
						for (EnemyShip e : context.enemys) {
							if (!e.isDestroyed()) {
								e.destroy();
								score += e.getPointValue();
								if (e.droptype != null)
									items.add(new Item(e.getCPositionX(), e.getCPositionY(), 2, e.droptype));
							}
						}
					}
				} else
					bombNumber = 0;
			}
		}

		if (openfire) {
			if (character == 1) {
				if (this.ship.shoot(this.bullets) && this.ship.UzShoot(this.uzBullets)) {
					this.bulletsShot++;
				}
			} else {
				if (this.ship.shoot(this.bullets)) {
					this.bulletsShot++;
				}
			}
		}

		if (moveLeft)
			ship.animctr = 2;
		else if (moveRight)
			ship.animctr = 3;
		else
			ship.animctr = 1;

		this.slowp = moveSlow;
		for (EnemyShip e : context.enemys) {
			e.update();
		}
		Countdown.update();
		this.ship.update();
		manageCollisions();
		manageCollisionsItem();
		cleanBullets();
		cleanUzBullets();
		if (Countdown.endp) {
			this.isRunning = false;
			return;
		}

		if (this.lives <= 0) {
			this.isRunning = false;
			return;
		}
		draw();
	}

	Cooldown tempblinkinner = Core.getCooldown(100);

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.backgroundDrawing(this);
		// TODO this is temporary!!!
		String shn = "";
		if (character == 0) {
			shn = switch (ship.animctr) {
				case 1 -> "MidoriBackSprite";
				case 2 -> "MidoriBackSpriteLeft";
				case 3 -> "MidoriBackSpriteRight";
				default -> shn;
			};
		}
		if (character == 1) {
			shn = switch (ship.animctr) {
				case 1 -> "UzBackSprite";
				case 2 -> "UzBackSpriteLeft";
				case 3 -> "UzBackSpriteRight";
				default -> shn;
			};
		}
		if (character == 2) {
			shn = switch (ship.animctr) {
				case 1 -> "ArisBackSprite";
				case 2 -> "ArisBackSpriteLeft";
				case 3 -> "ArisBackSpriteRight";
				default -> shn;
			};
		}
		if (ship.isDestroyed()) {
			if (tempblinkinner.checkFinished()) {
				drawManager.drawimgtrans(shn, ship.getCPositionX() - 30, ship.getCPositionY() - 30, 60, 60, 0.5f);
				tempblinkinner.reset();
			} else {
				drawManager.drawimg(shn, ship.getCPositionX() - 30, ship.getCPositionY() - 30, 60, 60);
			}
		} else {
			drawManager.drawimg(shn, ship.getCPositionX() - 30, ship.getCPositionY() - 30, 60, 60);
		}
		if (slowp)
			drawManager.drawsquare(ship.getPositionX(), ship.getPositionY(), ship.getWidth(), ship.getHeight(),
					java.awt.Color.WHITE);
		for (Bullet bullet : context.bullets) {
			drawManager.drawEBullet(bullet, bullet.getPositionX(), bullet.getPositionY());
		}
		if (bombNumber > 0 && inputManager.isKeyDown(KeyEvent.VK_C) || inputManager.isKeyDown(KeyEvent.VK_X)) {
			drawManager.drawBombEffect(this, this.width / 2 - 500, this.height / 2 - 500);
		}
		for (EnemyShip e : context.enemys) {
			drawManager.drawEnemy(e, e.getPositionX(), e.getPositionY());
		}
		for (Bullet bullet : this.bullets) {
			drawManager.drawPBullet(bullet, bullet.getPositionX(), bullet.getPositionY(), character);
		}
		if (character == 1) {
			for (UzBullet uzBullet : this.uzBullets) {
				drawManager.drawUBullet(uzBullet, uzBullet.getPositionX(), uzBullet.getPositionY());
			}
		}
		for (entity.Item item : this.items)
			drawManager.drawItem(item, item.getPositionX(),
					item.getPositionY());

		// Interface.
		drawManager.drawScore(this, this.score);
		drawManager.drawTimer(this, engine.Countdown.count);
		drawManager.drawBomb(this, this.bombNumber);
		drawManager.drawLives(this, this.lives);
		drawManager.drawPower(this, Ship.BULLET_POWER);
		drawManager.drawHorizontalLine(this, SEPARATION_LINE_HEIGHT - 1);

		// Countdown to game start.
		if (!this.inputDelay.checkFinished()) {
			// int countdown = (int) ((INPUT_DELAY
			// - (System.currentTimeMillis()
			// - this.gameStartTime))
			// / 1000);
			drawManager.drawCountDown(this, this.level, 4);
			drawManager.drawHorizontalLine(this, this.height / 2 - this.height
					/ 12);
			drawManager.drawHorizontalLine(this, this.height / 2 + this.height
					/ 12);
		}

		drawManager.completeDrawing(this);
	}

	/**
	 * Cleans bullets that go off screen.
	 */
	private void cleanBullets() {
		ArrayList<Bullet> oobs = new ArrayList<Bullet>();
		for (Bullet bullet : context.bullets) {
			if (bullet.checkoob(this.getWidth(), this.getHeight()) && bullet.deleteoob) {
				oobs.add(bullet);
			}
		}
		context.bullets.removeAll(oobs);
		Set<Bullet> recyclable = new HashSet<Bullet>();
		for (Bullet bullet : this.bullets) {
			bullet.update();
			if (bullet.getPositionY() < SEPARATION_LINE_HEIGHT
					|| bullet.getPositionY() > this.height)
				recyclable.add(bullet);
		}
		this.bullets.removeAll(recyclable);
		BulletPool.recycle(recyclable);
	}

	/**
	 * Cleans bullets that go off screen.
	 */
	private void cleanUzBullets() {
		ArrayList<UzBullet> oobs = new ArrayList<UzBullet>();
		for (UzBullet uzBullet : context.uzBullets) {
			if (uzBullet.checkoob(this.getWidth(), this.getHeight()) && uzBullet.deleteoob) {
				oobs.add(uzBullet);
			}
		}
		context.uzBullets.removeAll(oobs);
		Set<UzBullet> recyclable = new HashSet<UzBullet>();
		for (UzBullet uzBullet : this.uzBullets) {
			uzBullet.update();
			if (uzBullet.getPositionY() < SEPARATION_LINE_HEIGHT
					|| uzBullet.getPositionY() > this.height)
				recyclable.add(uzBullet);
		}
		this.uzBullets.removeAll(recyclable);
		UzBulletPool.recycle(recyclable);
	}

	/**
	 * Manages collisions between bullets and ships.
	 */
	private void manageCollisions() {
		for (Bullet bullet : context.bullets) {
			bullet.update();
			if (checkCollision(this.ship, bullet) && !this.ship.isDestroyed()) {
				this.lives--;
				ship.destroy();
			}
		}
		Set<Bullet> recyclable = new HashSet<Bullet>();
		for (EnemyShip e : context.enemys) {
			if (checkCollision(e, this.ship))
				if (!this.ship.isDestroyed()) {
					this.ship.destroy();
					this.lives--;
				}
		}
		for (Bullet bullet : this.bullets) {
			for (EnemyShip e : context.enemys) {
				if (!e.isDestroyed() && checkCollision(bullet, e)) {
					if (e.attack(ship.BULLET_POWER)) {
						score += e.getPointValue();
						e.destroy();
						if (e.droptype != null)
							items.add(new Item(e.getCPositionX(), e.getCPositionY(), 2, e.droptype));
					}
				}
			}
		}
		for (UzBullet uzBullet : this.uzBullets) {
			for (EnemyShip e : context.enemys) {
				uzBullet.hometgt = e;
				if (!e.isDestroyed() && checkCollision(uzBullet, e)) {
					if (e.Hp > 0) {
						e.Hp -= Ship.BULLET_POWER;
					} else {
						score += e.getPointValue();
						e.destroy();
						if (e.droptype != null)
							items.add(new Item(e.getCPositionX(), e.getCPositionY(), 2, e.droptype));
					}

				}
			}
		}
		this.bullets.removeAll(recyclable);
		BulletPool.recycle(recyclable);
	}

	/**
	 * Returns a GameState object representing the status of the game.
	 *
	 * @return Current game state.
	 */
	public GameState getGameState() {
		var gs = new GameState(this.level, this.score, this.lives,
				this.bulletsShot, this.shipsDestroyed, this.bombNumber, Ship.BULLET_POWER, this.character,
				this.difficulty);
		gs.cleartime.set(this.level, System.currentTimeMillis() - gameStartTime);
		return gs;
	}

	/**
	 * Manages collisions between items and ships.
	 */
	private void manageCollisionsItem() {
		if (ship.getPositionY() < itemcolborder && !bordercrossed) {
			switch (character) {
				case 0, 1 -> {
					if (ship.BULLET_POWER == 128) {
						bordercrossed = true;
						for (Item itm : this.items) {
							itm.hometgt = ship;
						}
					}
				}
				case 2 -> {
					if (ship.BULLET_POWER == 192) {
						bordercrossed = true;
						for (Item itm : this.items) {
							itm.hometgt = ship;
						}
					}
				}
			}
		}
		if ((ship.getPositionY() > itemcolborder) && bordercrossed) {
			bordercrossed = false;
		}
		ArrayList<Item> delitm = new ArrayList<Item>();
		for (entity.Item itm : this.items) {
			itm.update();
			if (itm.checkoob(width, height))
				delitm.add(itm);
			if (checkCollision(ship, itm)) {
				switch (itm.getItemType()) {
					case power -> {
						if (character == 0 || character == 2) {
							if (ship.BULLET_POWER == 128)
								score += 30;
							else {
								ship.BULLET_POWER += 10;
							}
						} else {
							if (ship.BULLET_POWER == 192)
								score += 30;
							else {
								ship.BULLET_POWER += 10;
							}
						}
					}
					case bomb -> {
						if (bombNumber == 5) {
							score += 70;
						} else
							bombNumber++;
					}
					case score -> {
						if (character == 1) {
							score += ship.getHeight() * 50;
						} else
							score += 50;
					}
				}
				itm.use();
				delitm.add(itm);
			}
		}
		items.removeAll(delitm);
		delitm = null;

	}

	/**
	 * Checks if two entities are colliding.
	 *
	 * @param a
	 *          First entity, the bullet or item.
	 * @param b
	 *          Second entity, the ship.
	 * @return Result of the collision test.
	 */
	private boolean checkCollision(final Entity a, final Entity b) {
		// Calculate center point of the entities in both axis.
		int centerAX = a.getPositionX() + a.getWidth() / 2;
		int centerAY = a.getPositionY() + a.getHeight() / 2;
		int centerBX = b.getPositionX() + b.getWidth() / 2;
		int centerBY = b.getPositionY() + b.getHeight() / 2;
		// Calculate maximum distance without collision.
		int maxDistanceX = a.getWidth() / 2 + b.getWidth() / 2;
		int maxDistanceY = a.getHeight() / 2 + b.getHeight() / 2;
		// Calculates distance.
		int distanceX = Math.abs(centerAX - centerBX);
		int distanceY = Math.abs(centerAY - centerBY);

		return distanceX < maxDistanceX && distanceY < maxDistanceY;
	}
}

/*
 * private void manageCollisionsItem() {
 * Set<entity.Item> recyclable = new HashSet<entity.Item>(); // ItemPool
 * for (entity.Item item : this.items) {
 * if (checkCollision(item, this.ship) && !this.levelFinished) {
 * recyclable.add(item);
 * Random random = new Random();
 * int per = random.nextInt(6);
 * 
 * if (per == 0) {
 * if (this.lives < 3) {
 * this.lives++;
 * this.logger.info("Acquire a item_lifePoint," + this.lives +
 * " lives remaining.");
 * this.ship.item_number = 1;
 * this.ship.itemimgGet();
 * } else {
 * if (ship.getSHOOTING_INTERVAL() > 300) {
 * int shootingSpeed = (int) (ship.getSHOOTING_INTERVAL() - 100);
 * ship.setSHOOTING_INTERVAL(shootingSpeed);
 * ship.setSHOOTING_COOLDOWN(shootingSpeed);
 * this.logger
 * .info("Acquire a item_shootingSpeedUp," + shootingSpeed +
 * " Time between shots.");
 * } else {
 * this.logger.info("Acquire a item_shootingSpeedUp, MAX SHOOTING SPEED!");
 * }
 * this.ship.item_number = 2;
 * this.ship.itemimgGet();
 * }
 * } else if (per == 1) {
 * if (ship.getSHOOTING_INTERVAL() > 300) {
 * int shootingSpeed = (int) (ship.getSHOOTING_INTERVAL() - 100);
 * ship.setSHOOTING_INTERVAL(shootingSpeed);
 * ship.setSHOOTING_COOLDOWN(shootingSpeed);
 * this.logger.info("Acquire a item_shootingSpeedUp," + shootingSpeed +
 * " Time between shots.");
 * } else {
 * this.logger.info("Acquire a item_shootingSpeedUp, MAX SHOOTING SPEED!");
 * }
 * this.ship.item_number = 2;
 * this.ship.itemimgGet();
 * } else if (per == 2) {
 * int shipSpeed = (int) (ship.getSPEED() + 1);
 * ship.setSPEED(shipSpeed);
 * this.logger.info(
 * "Acquire a item_shipSpeedUp," + shipSpeed +
 * " Movement of the ship for each unit of time.");
 * this.ship.item_number = 3;
 * this.ship.itemimgGet();
 * } else if (per == 3) {
 * bullets.add(BulletPool.getBullet(ship.getPositionX(),
 * ship.getPositionY(), ship.getBULLET_SPEED(), 0));
 * bullets.add(BulletPool.getBullet(ship.getPositionX() + shipWidth / 2,
 * ship.getPositionY(), ship.getBULLET_SPEED(), 0));
 * bullets.add(BulletPool.getBullet(ship.getPositionX() + shipWidth,
 * ship.getPositionY(), ship.getBULLET_SPEED(), 0));
 * this.logger.info("Three bullets");
 * } else if (per == 4) {
 * bullets.add(BulletPool.getBullet(ship.getPositionX() + shipWidth / 2,
 * ship.getPositionY(), ship.getBULLET_SPEED(), 0));
 * bullets.add(BulletPool.getBullet(ship.getPositionX() + shipWidth / 2,
 * ship.getPositionY() + shipWidth / 2, ship.getBULLET_SPEED(), 0));
 * bullets.add(BulletPool.getBullet(ship.getPositionX() + shipWidth / 2,
 * ship.getPositionY() + shipWidth, ship.getBULLET_SPEED(), 0));
 * this.logger.info("Three bullets");
 * } else {
 * bullets.add(BulletPool.getBullet(ship.getPositionX() - shipWidth / 2,
 * ship.getPositionY(), ship.getBULLET_SPEED(), 0));
 * bullets.add(BulletPool.getBullet(ship.getPositionX(),
 * ship.getPositionY() - shipWidth / 3, ship.getBULLET_SPEED(), 0));
 * bullets.add(BulletPool.getBullet(ship.getPositionX() + shipWidth / 2,
 * ship.getPositionY() - shipWidth / 2, ship.getBULLET_SPEED(), 0));
 * bullets.add(BulletPool.getBullet(ship.getPositionX() + shipWidth,
 * ship.getPositionY() - shipWidth / 3, ship.getBULLET_SPEED(), 0));
 * bullets.add(BulletPool.getBullet(ship.getPositionX() + shipWidth + shipWidth
 * / 2,
 * ship.getPositionY(), ship.getBULLET_SPEED(), 0));
 * this.logger.info("Five bullets");
 * }
 * this.ship.getItem();
 * }
 * }
 * this.items.removeAll(recyclable);
 * ItemPool.recycle(recyclable);
 * }
 */
