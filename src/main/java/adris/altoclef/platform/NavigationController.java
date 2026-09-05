package adris.altoclef.platform;

import net.minecraft.core.BlockPos;

/** Minimal navigation contract used by migrated task code. */
public interface NavigationController {
    void pathTo(BlockPos target);
    void cancel();
    boolean isActive();
}
