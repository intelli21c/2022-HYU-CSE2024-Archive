package scripts;

import java.util.ArrayList;
import engine.GameContext;
import engine.BulletUtil;
import engine.Cooldown;
import engine.Core;
import engine.Script;
import engine.DrawManager.SpriteType;
import entity.EnemyShip;

public class stage1 extends Script {
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

    EnemyShip e;
    EnemyShip e2;
    EnemyShip e3;
    EnemyShip e4;

    Cooldown c;
    Cooldown d;
    Cooldown f;
    int fctr = 0;

    private int field1(GameContext context) {
        if (!entrycount.checkFinished())
            return 0;
        e = new EnemyShip(600, 100, SpriteType.EnemyShipA1);
        context.enemys.add(e);
        c = Core.getCooldown(1000);
        c.reset();
        d = Core.getCooldown(500);
        d.reset();
        f = Core.getCooldown(50);
        f.reset();
        state = states.field2;
        return 0;
    }

    private int field2(GameContext context) {
        if (e.isDestroyed() == false) {
            if (c.checkFinished()) {
                // Core.getLogger().info("fired");
                context.bullets.addAll(BulletUtil.circularadial(600, 100, 200, 100, 0, 5));
                c.reset();
            }
            return 0;
        }
        e2 = new EnemyShip(500, 300, SpriteType.EnemyShipC1);
        e3 = new EnemyShip(800, 200, SpriteType.EnemyShipA1);
        context.enemys.add(e2);
        context.enemys.add(e3);
        state = states.field3;
        return 0;
    }

    double t = 0;

    private int field3(GameContext context) {
        e2.moverel((int) (10 * Math.sin(t)), (int) (-10 * Math.cos(t)));
        t += 0.05;
        if (!e3.isDestroyed()) {
            if (c.checkFinished()) {
                if (f.checkFinished() && fctr < 5) {
                    context.bullets.addAll(BulletUtil.circularadial(e3.getPositionX(),
                            e3.getPositionY(), 150, 30, (10 * 3.14 / 180) * fctr, 5));
                    fctr++;
                    f.reset();
                }
                if (fctr == 5) {
                    context.bullets.addAll(BulletUtil.circularadial(e3.getPositionX(),
                            e3.getPositionY(), 150, 30, (10 * 3.14 / 180) * fctr, 5));
                    fctr = 0;
                    f.reset();
                    c.reset();
                }
            }
        }
        if (!e2.isDestroyed()) {
            if (d.checkFinished()) {
                context.bullets.add(BulletUtil.aimto(e2.getPositionX(), e2.getPositionY(),
                        context.player.getPositionX(), context.player.getPositionY(), 5));
                d.reset();
            }
        }
        if (e2.isDestroyed() && e3.isDestroyed()) {
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
