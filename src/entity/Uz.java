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
            new Sound().bulletsound();
            this.shootingCooldown.reset();
            bullets.add(new Bullet(positionX + this.width / 2, positionY, 0, BULLET_SPEED, BULLET_POWER));
            return true;
        }
        return false;
    }

}
