package adris.altoclef.platform;

import net.minecraft.core.BlockPos;

/** Narrow Baritone integration point; implementation can bind to official NeoForge JAR. */
public interface BaritoneFacade {
    void setGoal(BlockPos target);
    void cancelGoal();
    boolean isPathing();
}
