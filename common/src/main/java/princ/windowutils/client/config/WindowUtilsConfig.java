package princ.windowutils.client.config;

import com.mojang.blaze3d.platform.InputConstants;
import me.fzzyhmstrs.fzzy_config.annotations.Translation;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.screen.context.FzzyKeybind;
import me.fzzyhmstrs.fzzy_config.validation.ValidatedField;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedAny;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedKeybind;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import princ.windowutils.client.config.entries.Preset;

import java.util.List;

import static princ.windowutils.client.Constants.*;

@Translation(prefix = CONFIG_TRANSLATION_PREFIX)
public class WindowUtilsConfig extends Config {
    final Minecraft minecraft;

    public WindowUtilsConfig() {
        super(withDefaultNamespace("general"), "", "", NAMESPACE);

        this.minecraft = Minecraft.getInstance();

        ValidatedAny<Preset> presetEntry = new ValidatedAny<>(new Preset());
        ValidatedField.Companion.attachProvider(presetEntry, Provider.Companion.getWIDGET_TITLE(), (preset, component) -> Component.literal(preset.identifier));

        this.presets = presetEntry.toList(List.of(
                new Preset(new ValidatedKeybind(new FzzyKeybind.Builder().keyboard(InputConstants.UNKNOWN.getValue()).build()), "Windowed Fullscreen", true, false, true, false, 854, 480, false, 0, 0),
                new Preset(new ValidatedKeybind(new FzzyKeybind.Builder().keyboard(InputConstants.UNKNOWN.getValue()).build()), "Borderless Maximized", true, true, false, false, 854, 480, false, 0, 0)
        ));
    }

    public ValidatedList<Preset> presets;
    public ValidatedBoolean borderless = new ValidatedBoolean(false);
    public ValidatedBoolean maximize = new ValidatedBoolean(false);
    public ValidatedBoolean fullscreen = new ValidatedBoolean(false);
    public ValidatedBoolean size = new ValidatedBoolean(false);
    public ValidatedInt width = new ValidatedInt(854);
    public ValidatedInt height = new ValidatedInt(480);
    public ValidatedBoolean position = new ValidatedBoolean(false);
    public ValidatedInt x = new ValidatedInt(0);
    public ValidatedInt y = new ValidatedInt(0);

    @Override
    public void onUpdateClient() {
        handler.apply(this.minecraft.getWindow(), this.borderless.get(), this.maximize.get(), this.fullscreen.get(), this.size.get(), this.width.get(), this.height.get(), this.position.get(), this.x.get(), this.y.get());
    }
}
