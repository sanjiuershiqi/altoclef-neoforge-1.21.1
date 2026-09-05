package adris.altoclef;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

/** NeoForge entry point. Gameplay code is initialized only on the client. */
@Mod(AltoClef.MOD_ID)
public final class AltoClef {
    public static final String MOD_ID = "altoclef";

    public AltoClef(IEventBus modBus) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientBootstrap.register(modBus);
        }
    }
}
