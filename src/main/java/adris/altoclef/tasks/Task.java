package adris.altoclef.tasks;

/** Platform-neutral task primitive used while migrating Altoclef chains. */
public abstract class Task {
    private boolean started;
    private boolean stopped;

    public final void tick() {
        if (stopped) return;
        if (!started) { started = true; onStart(); }
        onTick();
    }

    public final void stop() {
        if (!stopped) { stopped = true; onStop(); }
    }

    public final boolean isStopped() { return stopped; }
    protected void onStart() {}
    protected abstract void onTick();
    protected void onStop() {}
}
