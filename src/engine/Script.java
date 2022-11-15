package engine;

public abstract class Script {
    public class ScriptContext {

    }

    public int level;

    public abstract int run(ScriptContext context);

    public abstract void prep(ScriptContext context);

    public abstract int scriptentry(ScriptContext context);

    public abstract int fallback(ScriptContext context);
}
