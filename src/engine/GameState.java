package engine;

import java.util.ArrayList;

/**
 * Implements an object that stores the state of the game between levels.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class GameState {

	/** Current game level. */
	private int level;
	/** Current score. */
	private int score;
	/** Lives currently remaining. */
	private int livesRemaining;
	/** clear times */
	public ArrayList<Long> cleartime;
	/** Bullets shot until now. */
	private int bulletsShot;
	/** Ships destroyed until now. */
	private int shipsDestroyed;
	/** Current coin. */
	private int coin;
	/** Current number of bomb. */
	private int bombNumber;
	/** Current power */
	private int power;
	private int character;
	private int difficulty;


	/**
	 * Constructor.
	 * 
	 * @param level
	 *                       Current game level.
	 * @param score
	 *                       Current score.
	 * @param livesRemaining
	 *                       Lives currently remaining.
	 * @param bulletsShot
	 *                       Bullets shot until now.
	 * @param shipsDestroyed
	 *                       Ships destroyed until now.
	 */
	public GameState(final int level, final int score,
			final int livesRemaining, final int bulletsShot,
			final int shipsDestroyed, final int bombNumber, final int power, int character, int difficulty) {
		this.level = level;
		this.score = score;
		this.livesRemaining = livesRemaining;
		this.bulletsShot = bulletsShot;
		this.shipsDestroyed = shipsDestroyed;
		this.bombNumber = bombNumber;
		this.power = power;
		this.character=character;
		this.difficulty=difficulty;
		this.cleartime = new ArrayList<Long>();
		cleartime.add(0L);
		cleartime.add(0L);
		cleartime.add(0L);
		cleartime.add(0L);
		cleartime.add(0L);
		cleartime.add(0L);
	}

	/**
	 * @return the level
	 */
	public final int getLevel() {
		return level;
	}

	/**
	 * @return the score
	 */
	public final int getScore() {
		return score;
	}

	/**
	 * @return the livesRemaining
	 */
	public final int getLivesRemaining() {
		return livesRemaining;
	}

	/**
	 * @return the bulletsShot
	 */
	public final int getBulletsShot() {
		return bulletsShot;
	}

	/**
	 * @return the shipsDestroyed
	 */
	public final int getShipsDestroyed() {
		return shipsDestroyed;
	}

	/**
	 * @return the coin
	 */
	public final int getCoin() {
		return coin;
	}

	/**
	 * @return the number of bomb
	 */
	public final int getBombNumber() {
		return bombNumber;
	}

	/**
	 * @return the power
	 */
	public final int getPower() {
		return power;
	}

	public final int getdiff() {
		return difficulty;
	}
	public final int getch() {
		return character;
	}
}
