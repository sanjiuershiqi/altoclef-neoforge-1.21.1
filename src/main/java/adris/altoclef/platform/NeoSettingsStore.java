package adris.altoclef.platform;

import net.neoforged.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Loader-owned configuration bridge used until the full Settings model is mapped. */
public final class NeoSettingsStore {
    private static final Path FILE = FMLPaths.CONFIGDIR.get().resolve("altoclef.properties");
    private final Properties values = new Properties();

    public NeoSettingsStore() { load(); }
    public boolean enabledByDefault() { return Boolean.parseBoolean(values.getProperty("enabled", "false")); }
    public void setEnabled(boolean enabled) {
        values.setProperty("enabled", Boolean.toString(enabled));
        try { Files.createDirectories(FILE.getParent()); values.store(Files.newOutputStream(FILE), "Alto Clef NeoForge settings"); }
        catch (IOException ignored) { }
    }
    private void load() {
        if (!Files.exists(FILE)) return;
        try (var in = Files.newInputStream(FILE)) { values.load(in); } catch (IOException ignored) { }
    }
}
