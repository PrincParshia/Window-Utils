package princ.windowutils.client.util;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import org.jspecify.annotations.Nullable;
import org.lwjgl.sdl.SDL_Rect;
import org.lwjgl.system.MemoryStack;
import princ.windowutils.client.config.entries.Preset;
import princ.windowutils.client.state.WindowState;

import static org.lwjgl.sdl.SDLVideo.*;

public class Handler {
    private long handle;
    private @Nullable WindowState saved;
    private @Nullable String preset;

    public void togglePreset(Minecraft minecraft, Preset preset) {
        if (minecraft.options.fullscreen().get()) return;

        if (preset.identifier.equals(this.preset)) {
            this.revert(minecraft);
            return;
        }

        if (this.saved == null) this.saved = this.snapshot(minecraft);
        this.preset = preset.identifier;
        this.apply(minecraft.getWindow(), preset);
    }

    public void revert(Minecraft minecraft) {
        WindowState state = this.saved;
        this.saved = null;
        this.preset = null;
        if (state == null) return;

        Window window = minecraft.getWindow();
        this.handle = window.handle();

        this.borderless(state.borderless());

        if (state.maximized()) {
            this.maximize(true);
        } else {
            this.maximize(false);
            this.size(state.width(), state.height());
            this.position(state.x(), state.y());
        }
    }

    public WindowState snapshot(Minecraft minecraft) {
        Window window = minecraft.getWindow();
        this.handle = window.handle();
        long flags = SDL_GetWindowFlags(this.handle);
        boolean borderless = (flags & SDL_WINDOW_BORDERLESS) != 0;
        boolean maximized = (flags & SDL_WINDOW_MAXIMIZED) != 0;
        return new WindowState(borderless, maximized, window.getWidth(), window.getHeight(), window.getX(), window.getY());
    }

    public void apply(Window window, Preset preset) {
        this.apply(window, preset.borderless, preset.maximize, preset.fullscreen, preset.size, preset.width, preset.height, preset.position, preset.x, preset.y);
    }

    public void apply(Window window, boolean borderless, boolean maximize, boolean fullscreen, boolean size, int width, int height, boolean position, int x, int y) {
        this.handle = window.handle();

        this.borderless(borderless || fullscreen);
        this.maximize(maximize && !fullscreen);

        if (fullscreen) {
            this.fullscreen();
        } else if (size && !maximize) {
            this.size(width, height);
        }

        if (position && !maximize && !fullscreen) {
            this.position(x, y);
        }
    }

    public void borderless(boolean borderless) {
        SDL_SetWindowBordered(this.handle, !borderless);
        SDL_SyncWindow(this.handle);
    }

    public boolean maximized() {
        return (SDL_GetWindowFlags(this.handle) & SDL_WINDOW_MAXIMIZED) != 0;
    }

    public void maximize(boolean maximize) {
        if (maximize == this.maximized()) return;

        if (maximize) {
            SDL_MaximizeWindow(this.handle);
        } else {
            SDL_RestoreWindow(this.handle);
        }
        SDL_SyncWindow(this.handle);
    }

    public void fullscreen() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            SDL_Rect rect = SDL_Rect.malloc(stack);
            if (SDL_GetDisplayBounds(SDL_GetDisplayForWindow(this.handle), rect)) {
                this.size(rect.w(), rect.h());
                this.position(rect.x(), rect.y());
            }
        }
    }

    public void size(int width, int height) {
        SDL_SetWindowSize(this.handle, width, height);
        SDL_SyncWindow(this.handle);
    }

    public void position(int x, int y) {
        SDL_SetWindowPosition(this.handle, x, y);
    }
}
