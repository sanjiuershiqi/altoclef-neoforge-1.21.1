package adris.altoclef.platform;

import net.minecraft.world.phys.Vec3;

/** Baritone-compatible goal heuristic without a direct loader dependency. */
public final class NavigationHeuristics {
    private NavigationHeuristics() {}

    public static double generic(Vec3 start, Vec3 target) {
        return generic(start.x, start.y, start.z, target.x, target.y, target.z);
    }

    public static double generic(double sx, double sy, double sz,
                                 double tx, double ty, double tz) {
        double dx = tx - sx;
        int dy = (int) ty - (int) sy;
        double dz = tz - sz;
        return Math.sqrt(dx * dx + dz * dz) + Math.abs(dy);
    }
}
