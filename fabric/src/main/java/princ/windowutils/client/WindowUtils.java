package princ.windowutils.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import princ.windowutils.client.config.helper.ConfigSync;

public class WindowUtils implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(PresetKeyMapping::tick);
        ScreenEvents.BEFORE_INIT.register((minecraft, screen, scaledWidth, scaledHeight) -> ConfigSync.sync(minecraft, screen));
    }
}
