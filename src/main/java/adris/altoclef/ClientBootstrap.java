package adris.altoclef;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

/** Platform bridge; Altoclef's internal event bus will be connected here. */
public final class ClientBootstrap {
    private static boolean initialized;

    private ClientBootstrap() {}

    public static void register(IEventBus modBus) {
        NeoForge.EVENT_BUS.addListener(ClientBootstrap::onClientTick);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        if (!initialized) {
            initialized = true;
            // TODO: instantiate task runner, trackers and navigation adapter.
        }
    }
}
