package entity;

import engine.Sound;
import screen.GameScreen;

import java.util.Set;
import java.util.ArrayList;

public class Uz extends Ship{
    public Uz(int positionX, int positionY) {
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

    @Override
    public boolean checkCollision(Entity b) {
        return super.checkCollision(b);
    }
}
