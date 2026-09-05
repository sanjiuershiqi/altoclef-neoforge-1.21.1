package adris.altoclef.platform;

import net.minecraft.core.BlockPos;

/** Shared navigation service used by tasks and command handlers. */
public final class NavigationService implements NavigationController {
    private final BaritoneFacade baritone;
    private BlockPos target;

    public NavigationService(BaritoneFacade baritone) { this.baritone = baritone; }
    @Override public void pathTo(BlockPos target) { this.target = target; baritone.setGoal(target); }
    @Override public void cancel() { target = null; baritone.cancelGoal(); }
    @Override public boolean isActive() { return target != null && baritone.isPathing(); }
    public BlockPos target() { return target; }
}
