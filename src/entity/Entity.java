package entity;

import java.awt.Color;

import engine.DrawManager.SpriteType;

/**
 * Implements a generic game entity.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Entity {

	/** Position in the x-axis of the upper left corner of the entity. */
	protected int positionX;
	/** Position in the y-axis of the upper left corner of the entity. */
	protected int positionY;
	/** Width of the entity. */
	protected int width;
	/** Height of the entity. */
	protected int height;
	/** Color of the entity. */
	private Color color;
	/** Sprite type assigned to the entity. */
	protected SpriteType spriteType;

	/**
	 * Constructor, establishes the entity's generic properties.
	 * 
	 * @param positionX
	 *                  Initial position of the entity in the X axis.
	 * @param positionY
	 *                  Initial position of the entity in the Y axis.
	 * @param width
	 *                  Width of the entity.
	 * @param height
	 *                  Height of the entity.
	 * @param color
	 *                  Color of the entity.
	 */
	public Entity(final int positionX, final int positionY, final int width,
			final int height, final Color color) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	/**
	 * Getter for the color of the entity.
	 * 
	 * @return Color of the entity, used when drawing it.
	 */
	public final Color getColor() {
		return color;
	}

	// 아이템 먹었을 때 색깔 변할 수 있게 메서드 추가
	// Add a method so that the color changes when you eat an item
	public final void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Getter for the X axis position of the entity.
	 * 
	 * @return Position of the entity in the X axis.
	 */
	public final int getPositionX() {
		return this.positionX;
	}

	/**
	 * Getter for the Y axis position of the entity.
	 * 
	 * @return Position of the entity in the Y axis.
	 */
	public final int getPositionY() {
		return this.positionY;
	}

	/**
	 * Getter for the Centre X axis position of the entity.
	 * 
	 * @return Centre position of the entity in the X axis.
	 */
	public final int getCPositionX() {
		return this.positionX + this.width / 2;
	}

	/**
	 * Getter for the Centre Y axis position of the entity.
	 * 
	 * @return Centre position of the entity in the Y axis.
	 */
	public final int getCPositionY() {
		return this.positionY + this.height / 2;
	}

	/**
	 * Setter for the X axis position of the entity.
	 * 
	 * @param positionX
	 *                  New position of the entity in the X axis.
	 */
	public final void setPositionX(final int positionX) {
		this.positionX = positionX;
	}

	/**
	 * Setter for the Y axis position of the entity.
	 * 
	 * @param positionY
	 *                  New position of the entity in the Y axis.
	 */
	public final void setPositionY(final int positionY) {
		this.positionY = positionY;
	}

	/**
	 * Setter for the Y axis position of the entity.
	 * 
	 * @param positionY
	 *                  New position of the entity in the Y axis.
	 */
	public final void moverel(final int delX, final int delY) {
		this.positionX += delX;
		this.positionY += delY;
	}

	/**
	 * check out of bound
	 * 
	 * @param boundx
	 *               New position of the entity in the Y axis.
	 * @param boundy
	 *               New position of the entity in the Y axis.
	 * 
	 */
	public final Boolean checkoob(int boundx, int boundy) {
		return ((positionX < 0) || (positionX > boundx) || (positionY < 0) || (positionY > boundy));
	}

	/**
	 * Getter for the sprite that the entity will be drawn as.
	 * 
	 * @return Sprite corresponding to the entity.
	 */
	public final SpriteType getSpriteType() {
		return this.spriteType;
	}

	public final void setSpriteType(SpriteType newSprite) {
		this.spriteType = newSprite;
	}

	/**
	 * Getter for the width of the image associated to the entity.
	 * 
	 * @return Width of the entity.
	 */
	public final int getWidth() {
		return this.width;
	}

	/**
	 * Getter for the height of the image associated to the entity.
	 * 
	 * @return Height of the entity.
	 */
	public final int getHeight() {
		return this.height;
	}

	public static boolean checkCollision(final Entity a, final Entity b) {
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

	public boolean checkCollision(final Entity b) {
		// Calculate center point of the entities in both axis.
		int centerAX = this.getPositionX() + this.getWidth() / 2;
		int centerAY = this.getPositionY() + this.getHeight() / 2;
		int centerBX = b.getPositionX() + b.getWidth() / 2;
		int centerBY = b.getPositionY() + b.getHeight() / 2;
		// Calculate maximum distance without collision.
		int maxDistanceX = this.getWidth() / 2 + b.getWidth() / 2;
		int maxDistanceY = this.getHeight() / 2 + b.getHeight() / 2;
		// Calculates distance.
		int distanceX = Math.abs(centerAX - centerBX);
		int distanceY = Math.abs(centerAY - centerBY);

		return distanceX < maxDistanceX && distanceY < maxDistanceY;
	}
}
