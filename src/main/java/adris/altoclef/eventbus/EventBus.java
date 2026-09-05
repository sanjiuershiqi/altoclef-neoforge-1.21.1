package adris.altoclef.eventbus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/** Thread-confined internal bus; NeoForge events are translated into this bus. */
public final class EventBus {
    private static final Map<Class<?>, List<Subscription<?>>> topics = new HashMap<>();
    private static final List<Map.Entry<Class<?>, Subscription<?>>> pending = new ArrayList<>();
    private static boolean dispatching;
    private EventBus() {}

    public static synchronized <T> Subscription<T> subscribe(Class<T> type, Consumer<T> callback) {
        Subscription<T> subscription = new Subscription<>(callback);
        if (dispatching) pending.add(Map.entry(type, subscription));
        else topics.computeIfAbsent(type, ignored -> new ArrayList<>()).add(subscription);
        return subscription;
    }

    public static synchronized <T> void publish(T event) {
        flushPending();
        List<Subscription<?>> subscribers = topics.get(event.getClass());
        if (subscribers == null) return;
        dispatching = true;
        try {
            subscribers.removeIf(Subscription::shouldDelete);
            for (Subscription<?> raw : List.copyOf(subscribers)) publishOne(raw, event);
        } finally { dispatching = false; flushPending(); }
    }

    @SuppressWarnings("unchecked")
    private static <T> void publishOne(Subscription<?> raw, T event) {
        ((Subscription<T>) raw).accept(event);
    }

    private static void flushPending() {
        for (Map.Entry<Class<?>, Subscription<?>> entry : pending)
            topics.computeIfAbsent(entry.getKey(), ignored -> new ArrayList<>()).add(entry.getValue());
        pending.clear();
    }
}
