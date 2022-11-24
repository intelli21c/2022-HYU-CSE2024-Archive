package scripts;

import java.util.ArrayList;
import engine.GameContext;
import engine.BulletUtil;
import engine.Cooldown;
import engine.Core;
import engine.Script;
import engine.DrawManager.SpriteType;
import entity.EnemyShip;

public class stage2 extends Script {
    /**
     * Java really doesn't work well with higher order functions, so we use
     * switch-case instead.
     */
    private enum states {
        init, field1, field2, field3, field4, boss
    }

    states state = states.init;

    @Override
    public int run(GameContext context) {
        int ret = 0;
        switch (state) {
            case init:
                ret = scriptentry(context);
                break;
            case field1:
                ret = field1(context);
                break;
            case field2:
                ret = field2(context);
                break;
            case field3:
                ret = field3(context);
                break;
            case field4:
                ret = field4(context);
                break;
            default:
                ret = fallback(context);
        }
        return ret;
    }

    @Override
    public void prep(GameContext context) {
        new BulletUtil();
    }

    Cooldown entrycount;

    @Override
    public int scriptentry(GameContext context) {
        entrycount = Core.getCooldown(5000);
        entrycount.reset();
        state = states.field1;
        return 0;
    }

    @Override
    public int fallback(GameContext context) {
        return -1;
    }

    ArrayList<EnemyShip> p1six;
    ArrayList<EnemyShip> p2six;
    EnemyShip e3;
    EnemyShip e4;

    Cooldown c;
    Cooldown d;
    Cooldown f;
    int fctr = 0;

    private int field1(GameContext context) {
        if (!entrycount.checkFinished())
            return 0;
        p1six = new ArrayList<EnemyShip>();
        p1six.add(new EnemyShip(600, 100, SpriteType.EnemyShipA1));
        p1six.add(new EnemyShip(500, 200, SpriteType.EnemyShipA1));
        p1six.add(new EnemyShip(400, 300, SpriteType.EnemyShipA1));
        p1six.add(new EnemyShip(900, 100, SpriteType.EnemyShipA1));
        p1six.add(new EnemyShip(1000, 200, SpriteType.EnemyShipA1));
        p1six.add(new EnemyShip(1100, 300, SpriteType.EnemyShipA1));
        context.enemys.addAll(p1six);
        c = Core.getCooldown(1000);
        c.reset();
        d = Core.getCooldown(10000);
        d.reset();
        f = Core.getCooldown(2000);
        f.reset();
        state = states.field2;
        return 0;
    }

    private int field2(GameContext context) {
        boolean alldead = true;
        for (EnemyShip e : p1six) {
            alldead = alldead && e.isDestroyed();
        }
        if (!alldead) {
            if (c.checkFinished()) {
                // Core.getLogger().info("fired");
                // context.bullets.addAll(BulletUtil.circularadial(600, 100, 200, 100, 0, 5));
                for (EnemyShip e : p1six) {
                    if (!e.isDestroyed())
                        context.bullets.add(
                                BulletUtil.aimto(e.getCPositionX(), e.getCPositionY(), context.player.getCPositionX(),
                                        context.player.getCPositionY(), 10));
                }
                c.reset();
            }
            return 0;
        }
        p2six = new ArrayList<EnemyShip>();
        p2six.add(new EnemyShip(400, 100, SpriteType.EnemyShipB1));
        p2six.add(new EnemyShip(250, 200, SpriteType.EnemyShipC1));
        p2six.add(new EnemyShip(400, 300, SpriteType.EnemyShipB1));
        p2six.add(new EnemyShip(600, 100, SpriteType.EnemyShipB1));
        p2six.add(new EnemyShip(750, 200, SpriteType.EnemyShipC1));
        p2six.add(new EnemyShip(600, 300, SpriteType.EnemyShipB1));
        state = states.field3;
        context.enemys.removeAll(p1six);
        p1six = null;
        context.enemys.addAll(p2six);
        d.reset();
        return 0;
    }

    double t = 0;

    private int field3(GameContext context) {
        boolean alldead = true;
        for (EnemyShip e : p2six) {
            alldead = alldead && e.isDestroyed();
        }
        if (d.checkFinished()) {
            for (EnemyShip e : p2six) {
                e.moverel(0, -10);
            }
            boolean allout = true;
            for (EnemyShip e : p2six) {
                allout = allout && e.checkoob(1000, 1000);
            }
            if (allout) {
                e4 = new EnemyShip(500, 500, SpriteType.EnemyShipSpecial);
                cirs = new ArrayList<BulletUtil.BulletController>();
                cirdels = new ArrayList<BulletUtil.BulletController>();
                context.enemys.removeAll(p2six);
                p2six = null;
                context.enemys.add(e4);
                this.state = states.field4;
                c.reset();
            }
        }
        if (alldead) {
            e4 = new EnemyShip(500, 500, SpriteType.EnemyShipSpecial);
            cirs = new ArrayList<BulletUtil.BulletController>();
            cirdels = new ArrayList<BulletUtil.BulletController>();
            context.enemys.add(e4);
            this.state = states.field4;
            c.reset();
        }

        return 0;
    }

    ArrayList<BulletUtil.BulletController> cirs;
    ArrayList<BulletUtil.BulletController> cirdels;

    private int field4(GameContext context) {

        if (e4.isDestroyed())
            return 1;
        if (c.checkFinished()) {
            context.bullets.addAll(
                    BulletUtil.varyingspeedline(e4.getPositionX(), e4.getPositionY(), context.player.getPositionX(),
                            context.player.getPositionY(), 5, 2, 5));
            cirs.add(BulletUtil.BUTIL.new PolarRProp(e4.getPositionX(), e4.getPositionY(), 10,
                    20, 0.005, 0.001));
            context.bullets.addAll(cirs.get(cirs.size() - 1).list);
            c.reset();
        }
        for (BulletUtil.BulletController bc : cirs) {
            if (((BulletUtil.PolarRProp) bc).update() == 1)
                cirdels.add(bc);
        }
        cirs.removeAll(cirdels);
        return 0;
    }
}
