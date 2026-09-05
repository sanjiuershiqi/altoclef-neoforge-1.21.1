package adris.altoclef.eventbus.events;

import net.minecraft.client.gui.GuiGraphics;

public record GameOverlayEvent(GuiGraphics graphics, float partialTick) { }
