package entity;

import java.awt.Color;

import engine.DrawManager.SpriteType;
import screen.GameScreen;

/**
 * Implements a bullet that moves vertically up or down.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Bullet extends Entity {
	private double accuposX;
	private double accuposY;

	/**
	 * Speed of the bullet, positive or negative depending on direction -
	 * positive is down.
	 */
	public Boolean auto = true;
	public Boolean deleteoob = true;
	public int shooter;
	public int Damage;

	public double speedx;
	public double speedy;

	/**
	 * Constructor, establishes the bullet's properties.
	 * 
	 * @param positionX
	 *                  Initial position of the bullet in the X axis.
	 * @param positionY
	 *                  Initial position of the bullet in the Y axis.
	 * @param speed
	 *                  Speed of the bullet, positive or negative depending on
	 *                  direction - positive is down.
	 */
	public Bullet(final int positionX, final int positionY, final int speed) {
		super(positionX, positionY, 3 * 2, 5 * 3, Color.RED);
		this.speedx = 0;
		this.speedy = speed;
	}

	public Bullet(final int positionX, final int positionY, double spdx, double spdy, final int damage) {
		super(positionX, positionY, 3 * 2, 5 * 3, Color.RED);
		this.accuposX = positionX;
		this.accuposY = positionY;
		this.speedx = spdx;
		this.speedy = spdy;
		this.spriteType = SpriteType.EnemyBullet;
		this.Damage = damage;
	}

	public void moverel(double vx, double vy) {
		this.accuposX += vx;
		this.accuposY += vy;
		this.positionX = (int) accuposX;
		this.positionY = (int) accuposY;
	}

	/**
	 * Updates the bullet's position.
	 */
	public final void update() {
		if (this.auto) {
			this.moverel(speedx, speedy);
		}
	}

	/**
	 * Setter of the speed of the bullet.
	 * 
	 * @param speed
	 *              New speed of the bullet.
	 */
	public final void setSpeed(final int speed) {
		this.speedy = speed;
	}

	/**
	 * Getter for the speed of the bullet.
	 * 
	 * @return Speed of the bullet.
	 */
	public final int getSpeed() {
		return (int) speedy;
	}
}
