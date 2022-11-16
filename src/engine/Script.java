package engine;

public abstract class Script {
    public int level;

    public abstract int run(GameContext context);

    public abstract void prep(GameContext context);

    public abstract int scriptentry(GameContext context);

    public abstract int fallback(GameContext context);
}
