package scripts;

import java.io.Console;

import engine.Script;

public class stage1 extends Script {
    /**
     * Java really doesn't work well with higher order functions, so we use
     * switch-case instead.
     */
    private enum states {
        init, field1, field2, boss
    }

    states state = states.init;

    @Override
    public int run(ScriptContext context) {
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
            default:
                ret = fallback(context);
        }
        return ret;
    }

    @Override
    public void prep(ScriptContext context) {

    }

    @Override
    public int scriptentry(ScriptContext context) {
        state = states.field1;
        return 0;
    }

    @Override
    public int fallback(ScriptContext context) {
        return -1;
    }

    private int field1(ScriptContext context) {
        state = states.field2;
        return 0;
    }

    private int field2(ScriptContext context) {
        return 0;
    }
}
