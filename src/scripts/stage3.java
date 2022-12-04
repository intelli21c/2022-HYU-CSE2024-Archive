package scripts;

import java.util.ArrayList;
import engine.GameContext;
import engine.BulletUtil;
import engine.Cooldown;
import engine.Core;
import engine.Countdown;
import engine.Script;
import engine.BulletUtil.SemiStaticRadial;
import engine.DrawManager.SpriteType;
import entity.EnemyShip;

public class stage3 extends Script {
    /**
     * Java really doesn't work well with higher order functions, so we use
     * switch-case instead.
     */
    private enum states {
        init, int1, zf, int2, se, int3, nm, sos
    }

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
    SemiStaticRadial bcon1;
    double circlectr = 0;
    Cooldown zfcirclefirecd = Core.getCooldown(30);

    public int int1(GameContext context) {
        switch (int1state) {
            case 0: // entry wait
                if (entrycount.checkFinished())
                    int1state = 1;
                break;
            case 1:
                if (boss.getPositionX() > 500)
                    boss.moverel(-11, 0);
                else {
                    int1state = 2;
                    zfcirclefirecd.reset();
                }
                break;
            case 2:
                if (zfcirclefirecd.checkFinished()) {
                    context.bullets.addAll(
                            BulletUtil.circularadial(boss.getCPositionX(), boss.getCPositionY(), 1, 1, circlectr, 4));
                    circlectr += 0.15;
                    zfcirclefirecd.reset();
                }
                break;
            default:
                break;
        }
        return 0;
    }

    public int zf(GameContext context) {
        return 0;
    }

    public int int2(GameContext context) {
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
