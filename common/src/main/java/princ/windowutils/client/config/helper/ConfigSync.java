package princ.windowutils.client.config.helper;

import me.fzzyhmstrs.fzzy_config.screen.internal.ConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jspecify.annotations.Nullable;
import princ.windowutils.client.state.WindowState;

import static princ.windowutils.client.Constants.config;
import static princ.windowutils.client.Constants.handler;

public class ConfigSync {
    static @Nullable WindowState previous;

    public static void sync(Minecraft minecraft, Screen screen) {
        if (screen instanceof ConfigScreen) {
            sync(minecraft);
        }
    }

    static void sync(Minecraft minecraft) {
        WindowState current = handler.snapshot(minecraft);
        if (current.equals(previous)) return;
        previous = current;

        config.maximize.validateAndSet(current.maximized());
        config.borderless.validateAndSet(current.borderless());
        if (!current.maximized()) {
            config.width.validateAndSet(current.width());
            config.height.validateAndSet(current.height());
            config.x.validateAndSet(current.x());
            config.y.validateAndSet(current.y());
        }
    }
}
