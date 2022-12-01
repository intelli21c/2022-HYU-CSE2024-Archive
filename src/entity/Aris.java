package entity;

import engine.Cooldown;
import engine.Sound;

import java.util.Set;
import java.util.ArrayList;

public class Aris extends Ship {
    public Aris(int positionX, int positionY) {
        super(positionX, positionY, null);
    }

    @Override
    public boolean shoot(final Set<Bullet> bullets) {
        if (this.shootingCooldown.checkFinished()) {
            new Sound().bulletsound();
            this.shootingCooldown.reset();
            bullets.add(new Bullet(positionX + this.width / 2, positionY, 0, BULLET_SPEED));
        }
        return true;
    }
}
