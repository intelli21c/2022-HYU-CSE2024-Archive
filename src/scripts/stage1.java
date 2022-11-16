package scripts;

import java.io.Console;

import engine.GameContext;
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

    @Override
    public int scriptentry(GameContext context) {
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

    private int field1(GameContext context) {
        e = new EnemyShip(600, 100, SpriteType.EnemyShipA1);
        context.enemys.add(e);
        state = states.field2;
        return 0;
    }

    private int field2(GameContext context) {
        if (e.isDestroyed() == false)
            return 0;
        e2 = new EnemyShip(500, 100, SpriteType.EnemyShipA1);
        e3 = new EnemyShip(800, 100, SpriteType.EnemyShipA1);
        context.enemys.add(e2);
        context.enemys.add(e3);
        state = states.field3;
        return 0;
    }

    private int field3(GameContext context) {
        if (e2.isDestroyed() && e3.isDestroyed())
            return 1;
        return 0;
    }
}
