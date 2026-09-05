package adris.altoclef.eventbus.events;

import net.minecraft.client.player.LocalPlayer;

public record ClientConnectedEvent(LocalPlayer player) { }
