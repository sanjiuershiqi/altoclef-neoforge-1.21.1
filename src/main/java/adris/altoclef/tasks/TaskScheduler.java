package adris.altoclef.tasks;

import java.util.ArrayDeque;
import java.util.Deque;

/** Small deterministic scheduler; legacy TaskRunner will be migrated onto this API. */
public final class TaskScheduler {
    private final Deque<Task> tasks = new ArrayDeque<>();
    private boolean enabled;

    public void enable() { enabled = true; }
    public void disable() {
        enabled = false;
        tasks.forEach(Task::stop);
        tasks.clear();
    }
    public void submit(Task task) { if (task != null) tasks.addLast(task); }
    public void tick() {
        if (!enabled || tasks.isEmpty()) return;
        Task task = tasks.peekFirst();
        task.tick();
        if (task.isStopped()) tasks.removeFirst();
    }
    public boolean isEnabled() { return enabled; }
    public int size() { return tasks.size(); }
}
