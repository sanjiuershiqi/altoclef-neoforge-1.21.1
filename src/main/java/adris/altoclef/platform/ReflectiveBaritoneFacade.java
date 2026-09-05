package adris.altoclef.platform;

import net.minecraft.core.BlockPos;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalBlock;

/**
 * Binds to the official Baritone NeoForge jar when present, without leaking
 * Baritone/Fabric classes into the compile classpath.
 */
public final class ReflectiveBaritoneFacade implements BaritoneFacade {
    private final IBaritone baritone;
    private Throwable failure;

    public ReflectiveBaritoneFacade() { IBaritone value; try { value = BaritoneAPI.getProvider().getPrimaryBaritone(); } catch (Throwable t) { failure = t; value = null; } baritone = value; }

    public boolean available() { return baritone != null; }
    public String diagnostic() { return available() ? "Baritone API connected" : "Baritone unavailable: " + failure; }

    @Override public void setGoal(BlockPos target) {
        if (!available()) return;
        try {
            baritone.getCustomGoalProcess().setGoalAndPath(new GoalBlock(target));
        } catch (Throwable e) { failure = e; }
    }

    @Override public void cancelGoal() {
        if (!available()) return;
        try { baritone.getCustomGoalProcess().onLostControl(); }
        catch (Throwable e) { failure = e; }
    }

    @Override public boolean isPathing() {
        if (!available()) return false;
        try { return baritone.getPathingBehavior().isPathing(); }
        catch (Throwable e) { failure = e; return false; }
    }
}
