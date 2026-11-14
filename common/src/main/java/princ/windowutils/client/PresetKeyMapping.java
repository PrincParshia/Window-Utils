package princ.windowutils.client;

import net.minecraft.client.Minecraft;
import princ.windowutils.client.config.entries.Preset;

import java.util.HashMap;
import java.util.Map;

import static princ.windowutils.client.Constants.*;

public class PresetKeyMapping {
    static final Map<String, State> STATES = new HashMap<>();

    public static void tick(Minecraft minecraft) {
        for (Preset preset : config.presets) {
            State state = STATES.computeIfAbsent(preset.identifier, identifier -> new State());
            boolean isKeyPressed = preset.key.isPressed();
            if (isKeyPressed && !state.isKeyPressed) handler.togglePreset(minecraft, preset);
            state.isKeyPressed = isKeyPressed;
        }
    }

    static class State {
        public boolean isKeyPressed;
    }
}
