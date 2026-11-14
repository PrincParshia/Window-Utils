package princ.windowutils.client.config.entries;

import me.fzzyhmstrs.fzzy_config.annotations.ClientModifiable;
import me.fzzyhmstrs.fzzy_config.annotations.Translation;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedKeybind;
import princ.windowutils.client.Constants;

@ClientModifiable
@Translation(prefix = Constants.CONFIG_TRANSLATION_PREFIX + ".preset")
public class Preset {
    public ValidatedKeybind key = new ValidatedKeybind();
    public String identifier = "";
    public boolean borderless;
    public boolean maximize;
    public boolean fullscreen;
    public boolean size ;
    public int width = 854;
    public int height = 480;
    public boolean position;
    public int x;
    public int y;

    public Preset() {
    }

    public Preset(ValidatedKeybind key, String identifier, boolean borderless, boolean maximize, boolean fullscreen, boolean size, int width, int height, boolean position, int x, int y) {
        this.key = key;
        this.identifier = identifier;
        this.borderless = borderless;
        this.maximize = maximize;
        this.fullscreen = fullscreen;
        this.size = size;
        this.width = width;
        this.height = height;
        this.position = position;
        this.x = x;
        this.y = y;
    }
}
