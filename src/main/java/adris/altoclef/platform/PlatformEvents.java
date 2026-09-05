package adris.altoclef.platform;

import adris.altoclef.eventbus.EventBus;
import adris.altoclef.eventbus.events.ChunkLoadEvent;
import adris.altoclef.eventbus.events.ChunkUnloadEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;

/** Adapts NeoForge world events to the internal Altoclef event model. */
public final class PlatformEvents {
    private PlatformEvents() { }

    public static void onChunkLoad(ChunkEvent.Load event) {
        if (event.getChunk() instanceof net.minecraft.world.level.chunk.LevelChunk chunk)
            EventBus.publish(new ChunkLoadEvent(chunk));
    }

    public static void onChunkUnload(ChunkEvent.Unload event) {
        if (event.getChunk() instanceof net.minecraft.world.level.chunk.LevelChunk chunk)
            EventBus.publish(new ChunkUnloadEvent(chunk));
    }
}
