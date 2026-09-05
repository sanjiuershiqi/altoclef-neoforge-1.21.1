package adris.altoclef.platform;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;

/** Mojmap accessors replacing Fabric's MinecraftClient helpers. */
public final class ClientRuntime {
    private ClientRuntime() {}

    public static Minecraft client() {
        return Minecraft.getInstance();
    }

    public static LocalPlayer player() {
        return client().player;
    }

    public static ClientLevel level() {
        return client().level;
    }

    public static boolean inGame() {
        return player() != null && level() != null && client().getConnection() != null;
    }
}
