package adris.altoclef.eventbus.events;
import net.minecraft.world.phys.BlockHitResult;
public record BlockInteractEvent(BlockHitResult hitResult) { }
