package entity;

import java.awt.Color;

import engine.DrawManager.SpriteType;

/**
 * Implements a item that moves vertically up or down.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Item extends Entity {

	/**
	 * Speed of the item, positive or negative depending on direction -
	 */
	private int speed;

	public Entity hometgt;

	public enum itemtype {
		score, power, bomb
	}

	public static itemtype type;

	/**
	 * Constructor, establishes the item's properties.
	 * 
	 * @param positionX
	 *                  Initial position of the item in the X axis.
	 * @param positionY
	 *                  Initial position of the item in the Y axis.
	 * @param speed
	 *                  Speed of the bullet, positive or negative depending on
	 *                  direction - positive is down.
	 */
	public Item(final int positionX, final int positionY, final int speed, itemtype type) {
		super(positionX, positionY, 3 * 2, 5 * 2, Color.WHITE);
		this.type = type;
		this.speed = speed;
		setSprite();
	}

	public void use() {
	}

	/**
	 * Sets correct sprite for the item.
	 */
	public final void setSprite() {
		this.spriteType = SpriteType.Item;
	}

	/**
	 * Updates the item's position.
	 */
	public final void update() {
		if (hometgt != null) {
			this.speed = 20;
			int dx = (hometgt.getCPositionX() - positionX);
			int dy = (hometgt.getCPositionY() - positionY);
			double l = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
			this.moverel((int) (speed * dx / l), (int) (speed * dy / l));
		} else
			this.positionY += this.speed;
	}

	public final itemtype getItemType() {
		return this.type;
	}

	/**
	 * Setter of the speed of the item.
	 * 
	 * @param speed
	 *              New speed of the item.
	 */
	public final void setSpeed(final int speed) {
		this.speed = speed;
	}

	/**
	 * Getter for the speed of the item.
	 * 
	 * @return Speed of the item.
	 */
	public final int getSpeed() {
		return this.speed;
	}
}
