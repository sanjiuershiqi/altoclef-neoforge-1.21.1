package adris.altoclef.eventbus.events;

public final class SendChatEvent {
    public final String message;
    private boolean cancelled;
    public SendChatEvent(String message) { this.message = message; }
    public void cancel() { cancelled = true; }
    public boolean isCancelled() { return cancelled; }
}
