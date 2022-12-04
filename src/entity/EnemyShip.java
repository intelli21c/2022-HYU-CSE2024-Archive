package entity;

import java.awt.Color;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;
import entity.Item.itemtype;

/**
 * Implements a enemy ship, to be destroyed by the player.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class EnemyShip extends Entity {

	/** Point value of a type A enemy. */
	private static final int A_TYPE_POINTS = 50;
	/** Point value of a type B enemy. */
	private static final int B_TYPE_POINTS = 50;
	/** Point value of a type C enemy. */
	private static final int C_TYPE_POINTS = 100;
	/** Point value of a bonus enemy. */
	private static final int BONUS_TYPE_POINTS = 100;

	/** Cooldown between sprite changes. */
	private Cooldown animationCooldown;
	/** Checks if the ship has been hit by a bullet. */
	private boolean isDestroyed;
	/** Values of the ship, in points, when destroyed. */
	private int pointValue;
	public int Hp;
	public itemtype droptype;

	/** lives of the enemyship. */
	public int enemyLives;

	/**
	 * Constructor, establishes the ship's properties.
	 * 
	 * @param positionX
	 *                   Initial position of the ship in the X axis.
	 * @param positionY
	 *                   Initial position of the ship in the Y axis.
	 * @param spriteType
	 *                   Sprite type, image corresponding to the ship.
	 */
	public EnemyShip(final int positionX, final int positionY,
			final SpriteType spriteType) {
		super(positionX, positionY, 12 * 2, 8 * 2, Color.WHITE);

		this.spriteType = spriteType;
		this.animationCooldown = Core.getCooldown(500);
		this.isDestroyed = false;
		switch (this.spriteType) {
			case EnemyShipA1:
			case EnemyShipA2:
				this.pointValue = A_TYPE_POINTS;
				this.Hp = 0;
				break;
			case EnemyShipB1:
			case EnemyShipB2:
				this.pointValue = B_TYPE_POINTS;
				this.Hp = 0;
				break;
			case EnemyShipC1:
			case EnemyShipC2:
				this.pointValue = C_TYPE_POINTS;
				this.Hp = 0;
				break;
			case EnemyShipSpecial:
				this.pointValue = C_TYPE_POINTS;
				this.Hp = 100000;
				break;
			default:
				this.pointValue = 0;
				this.Hp = 0;
				break;
		}
	}

	/**
	 * Constructor, establishes the ship's properties for a special ship, with
	 * known starting properties.
	 */
	public EnemyShip() {
		super(-32, 60, 16 * 2, 7 * 2, Color.RED);

		this.spriteType = SpriteType.EnemyShipSpecial;
		this.isDestroyed = false;
		this.pointValue = BONUS_TYPE_POINTS;
		this.Hp = 1000;
	}

	/**
	 * Getter for the score bonus if this ship is destroyed.
	 * 
	 * @return Value of the ship.
	 */
	public final int getPointValue() {
		return this.pointValue;
	}

	/** Setter enemyLives. */
	public void setenemyLives(int life) {
		this.enemyLives = life;
	}

	/** Getter enemyLives. */
	public int getEnemyLives() {
		return enemyLives;
	}

	/**
	 * Updates attributes, mainly used for animation purposes.
	 */
	public final void update() {
		if (this.animationCooldown.checkFinished()) {
			this.animationCooldown.reset();
			if (this.isDestroyed) {
				this.setColor(new java.awt.Color(0, 0, 0, 0));
			}
			switch (this.spriteType) {
				case EnemyShipA1:
					this.spriteType = SpriteType.EnemyShipA2;
					break;
				case EnemyShipA2:
					this.spriteType = SpriteType.EnemyShipA1;
					break;
				case EnemyShipB1:
					this.spriteType = SpriteType.EnemyShipB2;
					break;
				case EnemyShipB2:
					this.spriteType = SpriteType.EnemyShipB1;
					break;
				case EnemyShipC1:
					this.spriteType = SpriteType.EnemyShipC2;
					break;
				case EnemyShipC2:
					this.spriteType = SpriteType.EnemyShipC1;
					break;
				default:
					break;
			}
		}
	}

	/**
	 * Destroys the ship, causing an explosion.
	 */
	public final void destroy() {
		this.isDestroyed = true;
		int random = (int) (Math.random() * 4);
		switch (random) {
			case 0:
				this.spriteType = SpriteType.Explosion;
				break;
			case 1:
				this.spriteType = SpriteType.Explosion2;
				break;
			case 2:
				this.spriteType = SpriteType.Explosion3;
				break;
			case 3:
				this.spriteType = SpriteType.Explosion4;
				break;
		}
	}

	/**
	 * Checks if the ship has been destroyed.
	 * 
	 * @return True if the ship has been destroyed.
	 */
	public final boolean isDestroyed() {
		return this.isDestroyed;
	}

}
