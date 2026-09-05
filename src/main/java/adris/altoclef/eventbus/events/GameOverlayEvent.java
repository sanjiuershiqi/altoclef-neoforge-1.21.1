package adris.altoclef.eventbus.events;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.DeltaTracker;

public record GameOverlayEvent(GuiGraphics graphics, DeltaTracker partialTick) { }
