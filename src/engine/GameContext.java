package engine;

import entity.*;
import java.util.ArrayList;
import java.util.Set;

public class GameContext {
    public int difficulty;
    public Ship player;
    public ArrayList<EnemyShip> enemys;
    public ArrayList<Bullet> bullets;
    public ArrayList<UzBullet> uzBullets;
    public java.awt.Graphics canvas;

    public GameContext() {
        enemys = new ArrayList<EnemyShip>();
        bullets = new ArrayList<Bullet>();
        uzBullets = new ArrayList<UzBullet>();
    }
}
