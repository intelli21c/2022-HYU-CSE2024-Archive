package scripts;

import java.util.ArrayList;
import engine.GameContext;
import engine.BulletUtil;
import engine.Cooldown;
import engine.Core;
import engine.Script;
import engine.DrawManager.SpriteType;
import entity.Bullet;
import entity.EnemyShip;

public class stage3 extends Script {
    /**
     * Java really doesn't work well with higher order functions, so we use
     * switch-case instead.
     */
    private enum states {
        init, int1, zf, int2, se, int3, nm
    } // I intentinoally omitted last spell.

    states state = states.init;

    @Override
    public int run(GameContext context) {
        int ret = 0;
        switch (state) {
            case init:
                ret = scriptentry(context);
                break;
            case int1:
                ret = int1(context);
                break;
            case zf:
                ret = zf(context);
                break;
            case int2:
                ret = int2(context);
                break;
            case se:
                ret = se(context);
                break;
            case int3:
                ret = int3(context);
                break;
            case nm:
                ret = nm(context);
                break;
            default:
                ret = fallback(context);
        }
        return ret;
    }

    BulletUtil bUtil;

    @Override
    public void prep(GameContext context) {
        new BulletUtil();
        bUtil = BulletUtil.BUTIL;
    }

    Cooldown entrycount;

    @Override
    public int scriptentry(GameContext context) {
        entrycount = Core.getCooldown(5000);
        entrycount.reset();
        boss = new EnemyShip(2000, 200, SpriteType.Boss);
        context.enemys.add(boss);
        state = states.int1;
        return 0;
    }

    @Override
    public int fallback(GameContext context) {
        return -1;
    }

    EnemyShip boss;

    int int1state = 0;
    ArrayList<Bullet> intholdccw;
    ArrayList<Bullet> intholdcw;
    // Cooldown int1timeout = Core.getCooldown(20000);
    Cooldown int1timeout = Core.getCooldown(2000);
    Cooldown intshootintv = Core.getCooldown(1500);
    Cooldown intspawnintv = Core.getCooldown(500);
    double intcirclectr;

    public int int1(GameContext context) {
        switch (int1state) {
            case 0: // entry wait
                if (entrycount.checkFinished()) {
                    boss.Hp = 10000;
                    int1state = 1;
                }
                break;
            case 1:
                if (boss.getPositionX() > 500)
                    boss.moverel(-11, 0);
                else {
                    int1timeout.reset();
                    intholdccw = new ArrayList<Bullet>();
                    intholdcw = new ArrayList<Bullet>();
                    int1state = 2;
                }
                break;
            case 2:
                if (int1timeout.checkFinished() || boss.Hp == 0) {
                    int1state = 4;
                    break;
                }
                if (intspawnintv.checkFinished()) {
                    for (Bullet bullet : intholdcw) {
                        bullet.auto = true;
                    }
                    intholdcw = new ArrayList<Bullet>();
                }
                if (intshootintv.checkFinished()) {
                    intcirclectr = 0;
                    for (int i = 0; i < 13; i++) {
                        int r = i * 50 + 20;
                        int cirn = 30;// (int) (2 * Math.PI * r / 70);
                        intholdccw.addAll(BulletUtil.circularadial(boss.getCPositionX(), boss.getCPositionY(), r,
                                cirn, intcirclectr, 5));
                        intcirclectr += 0.05;
                    }

                    for (Bullet bullet : intholdccw) {
                        bullet.auto = false;
                    }
                    context.bullets.addAll(intholdccw);
                    intshootintv.reset();
                    intspawnintv.reset();
                    int1state = 3;
                }

                break;
            case 3:
                if (int1timeout.checkFinished() || boss.Hp == 0) {
                    int1state = 4;
                    break;
                }
                if (intspawnintv.checkFinished()) {
                    for (Bullet bullet : intholdccw) {
                        bullet.auto = true;
                    }
                    intholdccw = new ArrayList<Bullet>();
                }
                if (intshootintv.checkFinished()) {

                    intcirclectr = 0;
                    for (int i = 0; i < 13; i++) {
                        int r = i * 50 + 20;
                        int cirn = 30;// (int) (2 * Math.PI * r / 70);
                        intholdcw.addAll(BulletUtil.circularadial(boss.getCPositionX(), boss.getCPositionY(), r,
                                cirn, intcirclectr, 5));
                        intcirclectr -= 0.05;
                    }

                    for (Bullet bullet : intholdcw) {
                        bullet.auto = false;
                    }
                    context.bullets.addAll(intholdcw);
                    intshootintv.reset();
                    intspawnintv.reset();
                    int1state = 2;
                }
                break;
            case 4:
                context.bullets.removeAll(intholdccw);
                context.bullets.removeAll(intholdcw);
                intholdccw = null;
                intholdcw = null;
                boss.Hp = 20000;
                zfenemys = new ArrayList<EnemyShip>();
                EnemyShip tempe;
                tempe = new EnemyShip(boss.getCPositionX() - 100, boss.getCPositionY() + 100, SpriteType.EnemyShipC1);
                tempe.Hp = 1000000000;
                zfenemys.add(tempe);
                context.enemys.add(tempe);
                tempe = new EnemyShip(boss.getCPositionX() - 150, boss.getCPositionY() + 150, SpriteType.EnemyShipC1);
                tempe.Hp = 1000000000;
                zfenemys.add(tempe);
                context.enemys.add(tempe);
                tempe = new EnemyShip(boss.getCPositionX() - 200, boss.getCPositionY() + 200, SpriteType.EnemyShipC1);
                tempe.Hp = 1000000000;
                zfenemys.add(tempe);
                context.enemys.add(tempe);
                tempe = new EnemyShip(boss.getCPositionX() - 100, boss.getCPositionY() + 200, SpriteType.EnemyShipC1);
                tempe.Hp = 1000000000;
                zfenemys.add(tempe);
                context.enemys.add(tempe);
                tempe = new EnemyShip(boss.getCPositionX() - 150, boss.getCPositionY() + 250, SpriteType.EnemyShipC1);
                tempe.Hp = 1000000000;
                zfenemys.add(tempe);
                context.enemys.add(tempe);
                zftimeout.reset();
                zfcirclefirecd.reset();
                this.state = states.zf;
                break;
            default:
                break;
        }
        return 0;
    }

    double zfcirclectr = 0;
    Cooldown zfcirclefirecd = Core.getCooldown(25);
    // Cooldown zftimeout = Core.getCooldown(30000);
    Cooldown zftimeout = Core.getCooldown(3000);
    ArrayList<EnemyShip> zfenemys;
    int zfstate = 0;

    public int zf(GameContext context) {
        if (zftimeout.checkFinished() || boss.Hp == 0) {
            context.enemys.removeAll(zfenemys);
            zfenemys = null;
            this.state = states.int2;
            intholdcw = new ArrayList<Bullet>();
            intholdccw = new ArrayList<Bullet>();
            intshootintv=Core.getCooldown(700);
            intshootintv.reset();
            int2timeout.reset();
            return 0;
        }
        for (EnemyShip e : zfenemys) {
            int zx = (e.getPositionX() - context.player.getCPositionX() + 100 * ((int) Math.random()));
            int zy = (e.getPositionY() - context.player.getCPositionY() + 100 * ((int) Math.random()));
            double zl = Math.sqrt(Math.pow(zx, 2) + Math.pow(zy, 2));
            zx = (int) (zx * 2 / zl);
            zy = (int) (zy * 2 / zl);
            e.moverel(-zx, -zy);
        }

        if (zfcirclefirecd.checkFinished()) {
            context.bullets.addAll(
                    BulletUtil.circularadial(boss.getCPositionX(), boss.getCPositionY(), 1, 1, zfcirclectr, 4));
            zfcirclectr += 0.15;
            zfcirclefirecd.reset();
        }
        return 0;
    }

    int int2state = 0;
    Cooldown int2timeout = Core.getCooldown(20000);
    int int2tgtx = 650;

    public int int2(GameContext context) {

        if (int2timeout.checkFinished() || boss.Hp == 0) {
            int2state = 3;
        }

        if (intspawnintv.checkFinished()) {
            for (Bullet bullet : intholdcw) {
                bullet.auto = true;
            }
            for (Bullet bullet : intholdccw) {
                bullet.auto = true;
            }
            intholdcw = new ArrayList<Bullet>();
            intholdccw = new ArrayList<Bullet>();
        }
        if ((!intshootintv.checkFinished()) && (int2state != 0)) {
            return 0;
        }
        switch (int2state) {
            case 0: // transient
                if (boss.getPositionX() > (int2tgtx + 10))
                    boss.moverel(-15, 0);
                else if (boss.getPositionX() < (int2tgtx - 10))
                    boss.moverel(15, 0);
                else {
                    switch (int2tgtx) {
                        case 100:
                            int2state = 4;
                            break;
                        case 550:
                            int2state = 1;
                            break;
                        case 650:
                            int2state = 2;
                            break;
                        case 1100:
                            int2state = 4;
                            break;
                    }
                }
                break;
            case 1:
                intcirclectr = 0;
                for (int i = 0; i < 13; i++) {
                    int r = i * 50 + 20;
                    int cirn = 30;// (int) (2 * Math.PI * r / 70);
                    intholdccw.addAll(BulletUtil.circularadial(boss.getCPositionX(), boss.getCPositionY(), r,
                            cirn, intcirclectr, 5));
                    intcirclectr += 0.05;
                }
                for (Bullet bullet : intholdccw) {
                    bullet.auto = false;
                }
                context.bullets.addAll(intholdccw);
                intspawnintv.reset();
                int2state = 0;
                int2tgtx = 100;
                break;
            case 2:
                intcirclectr = 0;
                for (int i = 0; i < 13; i++) {
                    int r = i * 50 + 20;
                    int cirn = 30;// (int) (2 * Math.PI * r / 70);
                    intholdcw.addAll(BulletUtil.circularadial(boss.getCPositionX(), boss.getCPositionY(), r,
                            cirn, intcirclectr, 5));
                    intcirclectr -= 0.05;
                }

                for (Bullet bullet : intholdcw) {
                    bullet.auto = false;
                }
                context.bullets.addAll(intholdcw);
                int2state = 0;
                int2tgtx = 1100;
                break;
            case 3:
                context.bullets.removeAll(intholdccw);
                context.bullets.removeAll(intholdcw);
                intholdccw = null;
                intholdcw = null;
                boss.Hp = 20000;
                this.state = states.se;
                break;
            case 4:
                for (Bullet b : BulletUtil.circularadial(boss.getCPositionX(), boss.getCPositionY(), 90, 5, 0, 7)) {
                    context.bullets
                            .addAll(BulletUtil.circularadial(b.getCPositionX(), b.getCPositionY(), 20, 12, 0, 7));
                    context.bullets
                            .addAll(BulletUtil.circularadial(b.getCPositionX(), b.getCPositionY(), 40, 12, 0, 7));
                    context.bullets
                            .addAll(BulletUtil.circularadial(b.getCPositionX(), b.getCPositionY(), 60, 12, 0, 7));
                    context.bullets
                            .addAll(BulletUtil.circularadial(b.getCPositionX(), b.getCPositionY(), 80, 12, 0, 7));
                }

                if (this.int2tgtx == 100) {
                    int2state = 0;
                    int2tgtx = 650;
                } else {// 1100
                    int2state = 0;
                    int2tgtx = 550;
                }
                break;
            default:
                break;
        }
        if (int2state != 0)
            intshootintv.reset();
        return 0;
    }

    public int se(GameContext context) {
        return 0;
    }

    public int int3(GameContext context) {
        return 0;
    }

    public int nm(GameContext context) {
        return 0;
    }

}
