package princ.windowutils.client;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForge;
import princ.windowutils.client.config.helper.ConfigSync;

@Mod(value = Constants.NAMESPACE, dist = Dist.CLIENT)
public class WindowUtils {

    public WindowUtils(IEventBus eventBus) {
        NeoForge.EVENT_BUS.addListener((ClientTickEvent.Post event) -> PresetKeyMapping.tick(this.minecraft()));
        NeoForge.EVENT_BUS.addListener((ScreenEvent.Init.Pre event) -> ConfigSync.sync(this.minecraft(), event.getScreen()));
    }

    public Minecraft minecraft() {
        return Minecraft.getInstance();
    }
}