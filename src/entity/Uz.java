package entity;

import engine.Sound;
import screen.GameScreen;

import java.util.Set;
import java.util.ArrayList;

public class Uz extends Ship{
    public Uz(int positionX, int positionY) {
        super(positionX, positionY, null);
        // This character very fast but scoring is very good.
        this.SPEED = 15;
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
            } else if (BULLET_POWER < 48) {
                new Sound().bulletsound();
                this.shootingCooldown.reset();
                bullets.add(new Bullet(positionX + this.width / 2, positionY, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2, positionY, -0.5, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2, positionY, 0.5, BULLET_SPEED, BULLET_POWER));
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
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean UzShoot(Set<UzBullet> uzBullets) {
        if (this.uzshootingCooldown.checkFinished()) {
            if (BULLET_POWER < 8) {
                this.uzshootingCooldown.reset();
            }
            else if (BULLET_POWER < 24) {
                this.uzshootingCooldown.reset();
                uzBullets.add(new UzBullet(positionX + this.width / 2 - 10, positionY - 5, -5, BULLET_SPEED, SUB_BULLET_POWER));
                uzBullets.add(new UzBullet(positionX + this.width / 2 + 10, positionY - 5, 5, BULLET_SPEED, SUB_BULLET_POWER));
            } else if (BULLET_POWER < 48) {
                this.uzshootingCooldown.reset();
                uzBullets.add(new UzBullet(positionX + this.width / 2 - 10, positionY - 5, -5, BULLET_SPEED, SUB_BULLET_POWER));
                uzBullets.add(new UzBullet(positionX + this.width / 2 + 10, positionY - 5, 5, BULLET_SPEED, SUB_BULLET_POWER));
            } else if (BULLET_POWER < 80) {
                this.uzshootingCooldown.reset();
                uzBullets.add(new UzBullet(positionX + this.width / 2 - 10, positionY - 5, -5, BULLET_SPEED, SUB_BULLET_POWER));
                uzBullets.add(new UzBullet(positionX + this.width / 2 + 10, positionY - 5, 5, BULLET_SPEED, SUB_BULLET_POWER));
            } else {
                this.uzshootingCooldown.reset();
                uzBullets.add(new UzBullet(positionX + this.width / 2 - 10, positionY - 5, -5, BULLET_SPEED, SUB_BULLET_POWER));
                uzBullets.add(new UzBullet(positionX + this.width / 2 + 10, positionY - 5, 5, BULLET_SPEED, SUB_BULLET_POWER));
                uzBullets.add(new UzBullet(positionX + this.width / 2 - 10, positionY - 5, -3, BULLET_SPEED, SUB_BULLET_POWER));
                uzBullets.add(new UzBullet(positionX + this.width / 2 + 10, positionY - 5, 3, BULLET_SPEED, SUB_BULLET_POWER));
            }
            return true;
        }
        return false;
    }
}
