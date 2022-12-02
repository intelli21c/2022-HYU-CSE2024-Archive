package entity;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager;
import engine.Sound;

import java.util.Set;
import java.util.ArrayList;

public class Aris extends Ship {
    public Aris(int positionX, int positionY) {
        super(positionX, positionY, null);
        this.shootingCooldown = Core.getCooldown(100);
    }

    @Override
    public boolean shoot(final Set<Bullet> bullets) {
        if (this.shootingCooldown.checkFinished()) {
            if (BULLET_POWER < 8) {
                new Sound().bulletsound();
                this.shootingCooldown.reset();
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY, 0, BULLET_SPEED, BULLET_POWER));
            } else if (BULLET_POWER < 24) {
                new Sound().bulletsound();
                this.shootingCooldown.reset();
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY + 15, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY + 15, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY, 0, BULLET_SPEED, BULLET_POWER));
            } else if (BULLET_POWER < 48) {
                new Sound().bulletsound();
                this.shootingCooldown.reset();
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY + 30, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY + 30, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY + 15, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY + 15, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY, 0, BULLET_SPEED, BULLET_POWER));

            } else if (BULLET_POWER < 80) {
                new Sound().bulletsound();
                this.shootingCooldown.reset();
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY + 45, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY + 45, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY + 30, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY + 30, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY + 15, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY + 15, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY, 0, BULLET_SPEED, BULLET_POWER));
            } else if (BULLET_POWER <= 128) {
                new Sound().bulletsound();
                this.shootingCooldown.reset();
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY + 60, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY + 60, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY + 45, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY + 45, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY + 30, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY + 30, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY + 15, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY + 15, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY, 0, BULLET_SPEED, BULLET_POWER));
            } else if (BULLET_POWER == 192) {
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY + 75, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY + 75, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY + 60, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY + 60, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY + 45, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY + 45, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY + 30, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY + 30, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY + 15, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY + 15, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 - 15, positionY, 0, BULLET_SPEED, BULLET_POWER));
                bullets.add(new Bullet(positionX + this.width / 2 + 15, positionY, 0, BULLET_SPEED, BULLET_POWER));
             }
            return true;
        }
        return false;
    }


}
