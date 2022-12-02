package entity;

import engine.Sound;

import java.util.Set;
import java.util.ArrayList;

public class Midori extends Ship{

    public Midori(int positionX, int positionY) {
        super(positionX, positionY, null);
    }

    @Override
    public boolean shoot(final Set<Bullet> bullets) {
        if (this.shootingCooldown.checkFinished()) {
            if (BULLET_POWER < 8) {
                new Sound().bulletsound();
                this.shootingCooldown.reset();
                bullets.add(new Bullet(positionX + this.width / 2, positionY, 0, BULLET_SPEED, BULLET_POWER));
            } else if (BULLET_POWER < 24) {
                new Sound().bulletsound();
                this.shootingCooldown.reset();
                bullets.add(new Bullet(positionX + this.width / 2, positionY, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 10, positionY - 5, -5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 10, positionY - 5, 5, BULLET_SPEED, BULLET_POWER));
            } else if (BULLET_POWER < 48) {
                new Sound().bulletsound();
                this.shootingCooldown.reset();
                bullets.add(new Bullet(positionX + this.width / 2, positionY, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2, positionY, -0.5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2, positionY, 0.5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 10, positionY - 5, -5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 10, positionY - 5, 5, BULLET_SPEED, BULLET_POWER));
            } else if (BULLET_POWER < 80) {
                new Sound().bulletsound();
                this.shootingCooldown.reset();
                bullets.add(new Bullet(positionX + this.width / 2, positionY, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 3, positionY, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 6, positionY, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2, positionY, -0.5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 3, positionY, -0.5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 6, positionY, -0.5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2, positionY, 0.5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 3, positionY, 0.5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 6, positionY, 0.5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 10, positionY - 5, -5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 10, positionY - 5, 5, BULLET_SPEED, BULLET_POWER));
            } else {
                new Sound().bulletsound();
                this.shootingCooldown.reset();
                bullets.add(new Bullet(positionX + this.width / 2, positionY, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 3, positionY, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 6, positionY, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2, positionY, -0.5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 3, positionY, -0.5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 6, positionY, -0.5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2, positionY, 0.5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 3, positionY, 0.5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 6, positionY, 0.5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 10, positionY - 5, -5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 10, positionY - 5, 5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 10, positionY - 5, -3, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 10, positionY - 5, 3, BULLET_SPEED, BULLET_POWER));
            }
            // bullets.add(BulletPool.getBullet(positionX + this.width / 2, positionY,
            // BULLET_SPEED, 0));
            return true;
        }
        return false;
    }
}
