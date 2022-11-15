package engine;

import entity.Bullet;
import entity.Ship;
import java.util.Set;

public class GameContext {
    java.awt.Graphics graphics;
    Ship player;
    Set<Bullet> bullets;
    GameSettings gsettings;

    public GameContext() {
    }
}
