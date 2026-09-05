package adris.altoclef.eventbus.events;
import adris.altoclef.tasks.Task;
public record TaskFinishedEvent(double durationSeconds, Task lastTaskRan) { }
