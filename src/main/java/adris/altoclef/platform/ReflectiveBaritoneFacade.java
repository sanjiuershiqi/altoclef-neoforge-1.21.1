package adris.altoclef.platform;

import net.minecraft.core.BlockPos;

import java.lang.reflect.Method;

/**
 * Binds to the official Baritone NeoForge jar when present, without leaking
 * Baritone/Fabric classes into the compile classpath.
 */
public final class ReflectiveBaritoneFacade implements BaritoneFacade {
    private Object baritone;
    private Method setGoalAndPath;
    private Method isPathing;
    private Throwable failure;

    public ReflectiveBaritoneFacade() { resolve(); }

    public boolean available() { return baritone != null; }
    public String diagnostic() { return available() ? "Baritone API connected" : "Baritone unavailable: " + failure; }

    @Override public void setGoal(BlockPos target) {
        if (!available()) return;
        try {
            Class<?> goalBlock = Class.forName("baritone.api.pathing.goals.GoalBlock");
            Object goal = goalBlock.getConstructor(int.class, int.class, int.class)
                    .newInstance(target.getX(), target.getY(), target.getZ());
            setGoalAndPath.invoke(baritone, goal);
        } catch (ReflectiveOperationException e) { failure = e; }
    }

    @Override public void cancelGoal() {
        if (!available()) return;
        try { baritone.getClass().getMethod("getCustomGoalProcess").invoke(baritone).getClass()
                .getMethod("cancel").invoke(baritone.getClass().getMethod("getCustomGoalProcess").invoke(baritone)); }
        catch (ReflectiveOperationException e) { failure = e; }
    }

    @Override public boolean isPathing() {
        if (!available()) return false;
        try { return (boolean) isPathing.invoke(baritone.getClass().getMethod("getPathingBehavior").invoke(baritone)); }
        catch (ReflectiveOperationException e) { failure = e; return false; }
    }

    private void resolve() {
        try {
            Object provider = Class.forName("baritone.api.BaritoneAPI").getMethod("getProvider").invoke(null);
            baritone = provider.getClass().getMethod("getPrimaryBaritone").invoke(provider);
            Object process = baritone.getClass().getMethod("getCustomGoalProcess").invoke(baritone);
            setGoalAndPath = process.getClass().getMethod("setGoalAndPath",
                    Class.forName("baritone.api.pathing.goals.Goal"));
            isPathing = baritone.getClass().getMethod("getPathingBehavior").getReturnType()
                    .getMethod("isPathing");
        } catch (ReflectiveOperationException e) { failure = e; baritone = null; }
    }
}
