package entity;

import java.util.HashSet;
import java.util.Set;

import engine.DrawManager.SpriteType;

/**
 * Implements a pool of recyclable bullets.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public final class UzBulletPool {

    /** Set of already created bullets. */
    private static Set<UzBullet> pool = new HashSet<UzBullet>();

    /**
     * Constructor, not called.
     */
    private UzBulletPool() {

    }

    /**
     * Returns a bullet from the pool if one is available, a new one if there
     * isn't.
     *
     * @param positionX
     *                  Requested position of the bullet in the X axis.
     * @param positionY
     *                  Requested position of the bullet in the Y axis.
     * @param speed
     *                  Requested speed of the bullet, positive or negative
     *                  depending
     *                  on direction - positive is down.
     * @return Requested bullet.
     */
    public static UzBullet getBullet(final int positionX,
                                   final int positionY, int speed, int movingPattern) {
        UzBullet uzBullet;
        if (!pool.isEmpty()) {
            uzBullet = pool.iterator().next();
            pool.remove(uzBullet);
            uzBullet.setPositionX(positionX - uzBullet.getWidth() / 2);
            uzBullet.setPositionY(positionY);
            uzBullet.setSpeed(speed);
            uzBullet.spriteType=SpriteType.Bullet;
        } else {
            uzBullet = new UzBullet(positionX, positionY, speed, movingPattern, Ship.BULLET_POWER);
            uzBullet.setPositionX(positionX - uzBullet.getWidth() / 2);
        }
        return uzBullet;
    }

    /**
     * Adds one or more bullets to the list of available ones.
     *
     * @param bullet
     *               Bullets to recycle.
     */
    public static void recycle(final Set<UzBullet> bullet) {
        pool.addAll(bullet);
    }
}

