package adris.altoclef;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ClientChatEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.ChunkEvent;
import adris.altoclef.platform.PlatformEvents;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import adris.altoclef.platform.ClientRuntime;
import adris.altoclef.platform.NeoSettingsStore;
import adris.altoclef.tasks.TaskScheduler;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import adris.altoclef.eventbus.EventBus;
import adris.altoclef.eventbus.events.SendChatEvent;
import adris.altoclef.eventbus.events.GameOverlayEvent;
import adris.altoclef.eventbus.events.ScreenOpenEvent;

/** Platform bridge; Altoclef's internal event bus will be connected here. */
public final class ClientBootstrap {
    private static boolean initialized;
    private static boolean enabled;
    private static final TaskScheduler scheduler = new TaskScheduler();
    private static final NeoSettingsStore settings = new NeoSettingsStore();
    private static final KeyMapping TOGGLE_KEY = new KeyMapping(
            "key.altoclef.toggle", GLFW.GLFW_KEY_K, "key.categories.altoclef");

    private ClientBootstrap() {}

    public static void register(IEventBus modBus) {
        enabled = settings.enabledByDefault();
        if (enabled) scheduler.enable();
        NeoForge.EVENT_BUS.addListener(ClientBootstrap::onClientTick);
        NeoForge.EVENT_BUS.addListener(ClientBootstrap::onClientChat);
        NeoForge.EVENT_BUS.addListener(ClientBootstrap::onRenderGui);
        NeoForge.EVENT_BUS.addListener(ClientBootstrap::onScreenOpening);
        modBus.addListener(ClientBootstrap::registerKeys);
        NeoForge.EVENT_BUS.addListener(ClientBootstrap::onLoggingIn);
        NeoForge.EVENT_BUS.addListener(ClientBootstrap::onLoggingOut);
        NeoForge.EVENT_BUS.addListener(ClientBootstrap::registerClientCommands);
        NeoForge.EVENT_BUS.addListener(PlatformEvents::onChunkLoad);
        NeoForge.EVENT_BUS.addListener(PlatformEvents::onChunkUnload);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        if (!ClientRuntime.inGame()) {
            return;
        }
        while (TOGGLE_KEY.consumeClick()) {
            setEnabled(!enabled);
        }
        if (!initialized) {
            initialized = true;
            // TODO: instantiate task runner, trackers and navigation adapter.
        }
        EventBus.publish(new adris.altoclef.eventbus.events.ClientTickEvent());
    }

    private static void onClientChat(ClientChatEvent event) {
        SendChatEvent translated = new SendChatEvent(event.getMessage());
        EventBus.publish(translated);
        if (translated.isCancelled()) event.setCanceled(true);
    }

    private static void onRenderGui(RenderGuiEvent.Post event) {
        EventBus.publish(new GameOverlayEvent(event.getGuiGraphics(), event.getPartialTick()));
    }

    private static void onScreenOpening(ScreenEvent.Opening event) {
        ScreenOpenEvent translated = new ScreenOpenEvent(event.getScreen(), true);
        EventBus.publish(translated);
        if (event.isCanceled()) return;
        EventBus.publish(new ScreenOpenEvent(event.getScreen(), false));
    }

    private static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_KEY);
    }

    private static void onLoggingIn(ClientPlayerNetworkEvent.LoggingIn event) {
        initialized = false;
        EventBus.publish(new adris.altoclef.eventbus.events.ClientConnectedEvent(event.getPlayer()));
    }

    private static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        initialized = false;
        EventBus.publish(new adris.altoclef.eventbus.events.ClientDisconnectedEvent());
        scheduler.disable();
    }

    private static void registerClientCommands(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("altoclef")
                .then(Commands.literal("status").executes(ctx -> {
                    ctx.getSource().sendSystemMessage(Component.literal(
                            "Alto Clef NeoForge: " + (enabled ? "enabled" : "disabled")));
                    return 1;
                }))
                .then(Commands.literal("toggle").executes(ctx -> {
                    setEnabled(!enabled);
                    ctx.getSource().sendSystemMessage(Component.literal(
                            "Alto Clef " + (enabled ? "enabled" : "disabled")));
                    return 1;
                })));
    }

    private static void setEnabled(boolean value) {
        enabled = value;
        settings.setEnabled(enabled);
        if (enabled) scheduler.enable(); else scheduler.disable();
    }
}


