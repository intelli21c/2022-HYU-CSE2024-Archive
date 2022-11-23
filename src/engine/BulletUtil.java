package engine;

import java.util.ArrayList;
import entity.Bullet;

public class BulletUtil {
    public static BulletUtil BUTIL;

    public BulletUtil() {
        BUTIL = this;
    }

    public class BulletController {
        public ArrayList<Bullet> list;

        public BulletController importb(ArrayList<Bullet> i) {
            list.addAll(i);
            return this;
        }

        public ArrayList<Bullet> exportb() {
            return list;
        }

        public int update() {
            if (this.checkoffscr())
                return 1;
            for (Bullet bullet : list) {
                bullet.update();
            }
            return 0;
        }

        public boolean checkoffscr() {
            boolean acc = true;
            for (Bullet bullet : list) {
                acc = acc && bullet.checkoob(2000, 1000);
            }
            return acc;
        }
    }

    /* Polar cordinate, theta is radius proportional. */
    public class PolarRProp extends BulletController {
        int origx;
        int origy;
        public double r;
        public double k;
        private int t;
        public double v;

        public PolarRProp(int posx, int posy, int initr, int count, double v, double k) {
            this.list = new ArrayList<Bullet>();
            this.t = 0;
            this.origx = posx;
            this.origy = posy;
            this.v = v;
            this.r = initr;
            this.k = k;
            int x, y;
            double offset = Math.PI / count;
            for (int i = 0; i < count; i++) {
                x = posx + (int) (initr * Math.cos(offset * i));
                y = posy + (int) (initr * Math.sin(offset * i));
                Bullet b = new Bullet(x, y, 0, 0);
                b.auto = false;
                list.add(b);
            }
        }

        @Override
        public int update() {
            int x, y;
            this.r = r + (v * t);
            double offset = 2 * Math.PI / list.size();
            for (int i = 0; i < list.size(); i++) {
                Bullet b = list.get(i);
                x = origx + (int) (r * Math.cos(r * k + offset * i));
                y = origy + (int) (r * Math.sin(r * k + offset * i));
                b.setPositionX(x);
                b.setPositionY(y);
            }
            t++;
            if (this.checkoffscr())
                return 1;
            return 0;
        }
    }

    public static ArrayList<Bullet> varyingspeedline(int positionX, int positionY, int destX, int destY, int count,
            int variance, int basespd) {
        ArrayList<Bullet> b = new ArrayList<Bullet>();
        for (int i = 0; i < count; i++) {
            b.add(aimto(positionX, positionY, destX, destY, basespd + variance * i));
        }
        return b;
    }

    public static ArrayList<Bullet> circularadial(int positionX, int positionY, int radius, int count, double ang,
            double v) {
        ArrayList<Bullet> b = new ArrayList<Bullet>();
        double theta = ang;
        double intv = (2 * Math.PI) / count;
        for (int i = 0; i < count; i++) {
            int posx = positionX + (int) (radius * Math.cos(theta));
            int posy = positionY + (int) (radius * Math.sin(theta));
            double velx = (v * Math.cos(theta));
            double vely = (v * Math.sin(theta));
            Bullet a = new Bullet(posx, posy, velx, vely);
            b.add(a);
            theta += intv;
        }
        return b;
    }

    public static Bullet aimto(int PositionX, int positionY, int destX, int destY, int speed) {
        double velx = destX - PositionX;
        double vely = destY - positionY;
        double l = Math.sqrt(Math.pow(velx, 2) + Math.pow(vely, 2)) / speed;
        velx = velx / l;
        vely = vely / l;
        Bullet b = new Bullet(PositionX, positionY, velx, vely);
        return b;
    }
}
