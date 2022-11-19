package engine;

import java.util.ArrayList;
import entity.Bullet;
import java.math.*;
import java.lang.Math.*;

public class BulletUtil {
    public static ArrayList<Bullet> circularadial(int positionX, int positionY, int radius, int count) {
        ArrayList<Bullet> b = new ArrayList<Bullet>();
        double theta = 0;
        double intv = (2 * Math.PI) / count;
        for (int i = 0; i < count; i++) {
            int posx = positionX + (int) (radius * Math.cos(theta));
            int posy = positionY + (int) (radius * Math.sin(theta));
            int velx = (int) (5 * Math.cos(theta));
            int vely = (int) (5 * Math.sin(theta));
            Bullet a = new Bullet(posx, posy, velx, vely);
            b.add(a);
            theta += intv;
        }
        return b;
    }

    public static Bullet aimto(int PositionX, int positionY, int destX, int destY) {
        double velx = destX - PositionX;
        double vely = destY - positionY;
        double l = Math.sqrt(Math.pow(velx, 2) + Math.pow(vely, 2)) / 5;
        velx = velx / l;
        vely = vely / l;
        Bullet b = new Bullet(PositionX, positionY, (int) velx, (int) vely);
        return b;
    }
}
