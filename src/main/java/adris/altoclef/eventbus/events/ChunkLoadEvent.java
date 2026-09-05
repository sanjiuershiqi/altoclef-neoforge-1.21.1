package adris.altoclef.eventbus.events;
import net.minecraft.world.level.chunk.LevelChunk;
public record ChunkLoadEvent(LevelChunk chunk) { }
