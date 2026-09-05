package adris.altoclef;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ClientChatEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

/** Platform bridge; Altoclef's internal event bus will be connected here. */
public final class ClientBootstrap {
    private static boolean initialized;
    private static final KeyMapping TOGGLE_KEY = new KeyMapping(
            "key.altoclef.toggle", GLFW.GLFW_KEY_K, "key.categories.altoclef");

    private ClientBootstrap() {}

    public static void register(IEventBus modBus) {
        NeoForge.EVENT_BUS.addListener(ClientBootstrap::onClientTick);
        NeoForge.EVENT_BUS.addListener(ClientBootstrap::onClientChat);
        NeoForge.EVENT_BUS.addListener(ClientBootstrap::onRenderGui);
        modBus.addListener(ClientBootstrap::registerKeys);
        NeoForge.EVENT_BUS.addListener(ClientBootstrap::onLoggingIn);
        NeoForge.EVENT_BUS.addListener(ClientBootstrap::onLoggingOut);
        NeoForge.EVENT_BUS.addListener(ClientBootstrap::registerClientCommands);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        if (!initialized) {
            initialized = true;
            // TODO: instantiate task runner, trackers and navigation adapter.
        }
    }

    private static void onClientChat(ClientChatEvent event) {
        // TODO: forward client commands to AltoClef's CommandExecutor.
    }

    private static void onRenderGui(RenderGuiEvent.Post event) {
        // TODO: render CommandStatusOverlay through GuiGraphics.
    }

    private static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_KEY);
    }

    private static void onLoggingIn(ClientPlayerNetworkEvent.LoggingIn event) {
        initialized = false;
    }

    private static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        initialized = false;
    }

    private static void registerClientCommands(RegisterClientCommandsEvent event) {
        // CommandExecutor/TabCompleter will be registered here during command migration.
    }
}
