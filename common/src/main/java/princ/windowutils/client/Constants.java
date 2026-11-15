package princ.windowutils.client;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import princ.windowutils.client.config.WindowUtilsConfig;
import princ.windowutils.client.util.Handler;

public class Constants {
    public static final String NAMESPACE = "windowutils";
    public static final String NAME = "Window Utils";
    public static final String CONFIG_TRANSLATION_PREFIX = "config." + NAMESPACE;
    public static final Logger LOG = LoggerFactory.getLogger(NAME);
    public static final Handler handler = new Handler();
    public static final WindowUtilsConfig config = ConfigApiJava.registerAndLoadConfig(WindowUtilsConfig::new, RegisterType.CLIENT);

    public static ResourceLocation withDefaultNamespace(final String string) {
        return ResourceLocation.fromNamespaceAndPath(NAMESPACE, string);
    }
}