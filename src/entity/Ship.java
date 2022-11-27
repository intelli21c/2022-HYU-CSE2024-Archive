package entity;

import java.awt.Color;
import java.util.Arrays;
import java.util.Set;

import engine.*;
import engine.DrawManager.SpriteType;

/**
 * Implements a ship, to be controlled by the player.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class Ship extends Entity {

	/** Time between shots. */
	private int SHOOTING_INTERVAL = 200;
	/** Time between shots. */
	private int BOMB_INTERVAL = 1000;
	/** Speed of the bullets shot by the ship. */
	private int BULLET_SPEED = -15;
	public int BULLET_POWER = 0;

	/** Movement of the ship for each unit of time. */
	private float SPEED;
	public boolean slowp = false;

	public int animctr = 1;

	/** Minimum time between shots. */
	public Cooldown shootingCooldown;
	/** Minimum time between shots. */
	private Cooldown bombCooldown;
	/** Time spent inactive between hits. */
	private Cooldown destructionCooldown;
	/** Movement of the ship for each unit of time. */
	private int destructCool = 500;
	/**
	 * Constructor, establishes the ship's properties.
	 *
	 * @param positionX
	 *                  Initial position of the ship in the X axis.
	 * @param positionY
	 *                  Initial position of the ship in the Y axis.
	 */

	private Color baseColor = Color.green;

	public Ship(final int positionX, final int positionY, Color color) {
		super(positionX, positionY, 10, 10, color);
		this.spriteType = SpriteType.Ship;
		this.shootingCooldown = Core.getCooldown(SHOOTING_INTERVAL);
		this.destructionCooldown = Core.getCooldown(destructCool);
		this.SPEED = 10;
		this.bombCooldown = Core.getCooldown(BOMB_INTERVAL);
	}

	/**
	 * Moves the ship speed units right, or until the right screen border is
	 * reached.
	 */
	public final void moveRight() {
		this.positionX += SPEED;
	}

	/**
	 * Moves the ship speed units left, or until the left screen border is
	 * reached.
	 */
	public final void moveLeft() {
		this.positionX -= SPEED;
	}

	/**
	 * Moves the ship speed units Up, or until the Up screen border is
	 * reached.
	 */
	public final void moveUp() {
		this.positionY -= SPEED;
	}

	/**
	 * Moves the ship speed units Down, or until the Down screen border is
	 * reached.
	 */
	public final void moveDown() {
		this.positionY += SPEED;
	}

	/**
	 * Shoots a bullet upwards.
	 *
	 * @param bullets
	 *                List of bullets on screen, to add the new bullet.
	 * @return Checks if the bullet was shot correctly.
	 */
	public final boolean shoot(final Set<Bullet> bullets) {
		if (this.shootingCooldown.checkFinished()) {
			if (BULLET_POWER < 8) {
				new Sound().bulletsound();
				this.shootingCooldown.reset();
				bullets.add(new Bullet(positionX + this.width / 2, positionY, 0, BULLET_SPEED));
			} else if (BULLET_POWER < 24) {
				new Sound().bulletsound();
				this.shootingCooldown.reset();
				bullets.add(new Bullet(positionX + this.width / 2, positionY, 0, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 - 10, positionY - 5, -5, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 + 10, positionY - 5, 5, BULLET_SPEED));
			} else if (BULLET_POWER < 48) {
				new Sound().bulletsound();
				this.shootingCooldown.reset();
				bullets.add(new Bullet(positionX + this.width / 2, positionY, 0, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2, positionY, -0.5, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2, positionY, 0.5, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 - 10, positionY - 5, -5, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 + 10, positionY - 5, 5, BULLET_SPEED));
			} else if (BULLET_POWER < 80) {
				new Sound().bulletsound();
				this.shootingCooldown.reset();
				bullets.add(new Bullet(positionX + this.width / 2, positionY, 0, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 + 3, positionY, 0, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 + 6, positionY, 0, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2, positionY, -0.5, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 + 3, positionY, -0.5, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 + 6, positionY, -0.5, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2, positionY, 0.5, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 + 3, positionY, 0.5, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 + 6, positionY, 0.5, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 - 10, positionY - 5, -5, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 + 10, positionY - 5, 5, BULLET_SPEED));
			} else if (BULLET_POWER >= 80) {
				new Sound().bulletsound();
				this.shootingCooldown.reset();
				bullets.add(new Bullet(positionX + this.width / 2, positionY, 0, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 + 3, positionY, 0, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 + 6, positionY, 0, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2, positionY, -0.5, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 + 3, positionY, -0.5, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 + 6, positionY, -0.5, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2, positionY, 0.5, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 + 3, positionY, 0.5, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 + 6, positionY, 0.5, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 - 10, positionY - 5, -5, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 + 10, positionY - 5, 5, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 - 10, positionY - 5, -3, BULLET_SPEED));
				bullets.add(new Bullet(positionX + this.width / 2 + 10, positionY - 5, 3, BULLET_SPEED));
			}
			// bullets.add(BulletPool.getBullet(positionX + this.width / 2, positionY,
			// BULLET_SPEED, 0));
			return true;
		}
		return false;
	}

	public final boolean bomb() {
		if (this.bombCooldown.checkFinished()) {
			new Sound().bombSound();
			this.bombCooldown.reset();
			return true;
		}
		return false;
	}

	/**
	 * Updates status of the ship.
	 */
	public final void update() {
		if (this.isDestroyed()) {
			if (this.destructionCooldown.checkFinished()) {
				this.spriteType = SpriteType.Ship;
			}
		}
	}

	/**
	 * Switches the ship to its destroyed state.
	 */
	public final void destroy() {
		new Sound().explosionsound();
		this.destructionCooldown.reset();

		new Sound().explosionsound();
	}

	/**
	 * Checks if the ship is destroyed.
	 *
	 * @return True if the ship is currently destroyed.
	 */
	public final boolean isDestroyed() {
		return !this.destructionCooldown.checkFinished();
	}

	/**
	 * Getter for the ship's speed.
	 *
	 * @return Speed of the ship.
	 */
	public final float getSpeed() {
		return SPEED;
	}

	public void setSHOOTING_COOLDOWN(int SHOOTING_INTERVAL) {
		this.shootingCooldown = Core.getCooldown(SHOOTING_INTERVAL);
	}

	public void setSHOOTING_INTERVAL(int SHOOTING_INTERVAL) {
		this.SHOOTING_INTERVAL = SHOOTING_INTERVAL;
	}

	public int getSHOOTING_INTERVAL() {
		return this.SHOOTING_INTERVAL;
	}

	public void setSPEED(float SPEED) {
		this.SPEED = SPEED;
	}

	public float getSPEED() {
		return SPEED;
	}

	public int getBULLET_SPEED() {
		return BULLET_SPEED;
	}
}
