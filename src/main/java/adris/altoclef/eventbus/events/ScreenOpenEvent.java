package adris.altoclef.eventbus.events;
import net.minecraft.client.gui.screens.Screen;
public record ScreenOpenEvent(Screen screen, boolean preOpen) { }
