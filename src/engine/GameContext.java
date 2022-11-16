package engine;

import entity.*;
import java.util.ArrayList;
import java.util.Set;

public class GameContext {
    public Ship player;
    public ArrayList<EnemyShip> enemys;
    public Set<Bullet> bullets;
    public java.awt.Graphics canvas;

    public GameContext() {
        enemys = new ArrayList<EnemyShip>();
    }
}
