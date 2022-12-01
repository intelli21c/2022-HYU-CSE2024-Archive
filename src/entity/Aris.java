package entity;

import java.util.Set;
import java.util.ArrayList;

public class Aris extends Ship {
    public Aris(int positionX, int positionY) {
        super(positionX, positionY, null);
    }

    @Override
    public boolean shoot(final Set<Bullet> bullets) {
        return true;
    }
}
