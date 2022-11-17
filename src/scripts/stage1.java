package scripts;

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
        init, field1, field2, field3, boss
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
            default:
                ret = fallback(context);
        }
        return ret;
    }

    @Override
    public void prep(GameContext context) {

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

    Cooldown c;
    Cooldown d;

    private int field1(GameContext context) {
        if (!entrycount.checkFinished())
            return 0;
        e = new EnemyShip(600, 100, SpriteType.EnemyShipA1);
        context.enemys.add(e);
        c = Core.getCooldown(1000);
        c.reset();
        d = Core.getCooldown(500);
        d.reset();
        state = states.field2;
        return 0;
    }

    private int field2(GameContext context) {
        if (e.isDestroyed() == false) {
            if (c.checkFinished()) {
                // Core.getLogger().info("fired");
                context.bullets.addAll(BulletUtil.circularadial(600, 100, 70, 100));
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
                context.bullets.addAll(BulletUtil.circularadial(e3.getPositionX(),
                        e3.getPositionY(), 70, 70));
                c.reset();
            }
        }
        if (!e2.isDestroyed()) {
            if (d.checkFinished()) {
                context.bullets.add(BulletUtil.aimto(e2.getPositionX(), e2.getPositionY(),
                        context.player.getPositionX(), context.player.getPositionY()));
                d.reset();
            }
        }
        if (e2.isDestroyed() && e3.isDestroyed())
            return 1;
        return 0;
    }
}
