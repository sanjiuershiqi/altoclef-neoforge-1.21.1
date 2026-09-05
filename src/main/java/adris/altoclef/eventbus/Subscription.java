package adris.altoclef.eventbus;

import java.util.function.Consumer;

public final class Subscription<T> {
    private final Consumer<T> callback;
    private boolean deleted;
    Subscription(Consumer<T> callback) { this.callback = callback; }
    void accept(T event) { callback.accept(event); }
    public void delete() { deleted = true; }
    boolean shouldDelete() { return deleted; }
}
