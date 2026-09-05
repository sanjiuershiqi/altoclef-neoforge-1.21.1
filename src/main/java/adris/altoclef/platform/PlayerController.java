package adris.altoclef.platform;

import net.minecraft.core.BlockPos;

/** Platform-independent player actions required by migrated tasks. */
public interface PlayerController {
    void lookAt(BlockPos target);
    void breakBlock(BlockPos target);
    void placeBlock(BlockPos target);
}
