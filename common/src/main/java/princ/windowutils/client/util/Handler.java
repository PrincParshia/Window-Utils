package princ.windowutils.client.util;

import com.mojang.blaze3d.platform.Monitor;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import princ.windowutils.client.config.entries.Preset;
import princ.windowutils.client.state.WindowState;

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
        this.handle = window.getWindow();

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
        this.handle = window.getWindow();
        boolean borderless = GLFW.glfwGetWindowAttrib(this.handle, GLFW.GLFW_DECORATED) == GLFW.GLFW_FALSE;
        boolean maximized = GLFW.glfwGetWindowAttrib(this.handle, GLFW.GLFW_MAXIMIZED) == GLFW.GLFW_TRUE;
        return new WindowState(borderless, maximized, window.getWidth(), window.getHeight(), window.getX(), window.getY());
    }

    public void apply(Window window, Preset preset) {
        this.apply(window, preset.borderless, preset.maximize, preset.fullscreen, preset.size, preset.width, preset.height, preset.position, preset.x, preset.y);
    }

    public void apply(Window window, boolean borderless, boolean maximize, boolean fullscreen, boolean size, int width, int height, boolean position, int x, int y) {
        this.handle = window.getWindow();

        this.borderless(borderless || fullscreen);
        this.maximize(maximize && !fullscreen);

        if (fullscreen) {
            this.fullscreen(window);
        } else if (size && !maximize) {
            this.size(width, height);
        }

        if (position && !maximize && !fullscreen) {
            this.position(x, y);
        }
    }

    public void borderless(boolean borderless) {
        GLFW.glfwSetWindowAttrib(this.handle, GLFW.GLFW_DECORATED, borderless ? GLFW.GLFW_FALSE : GLFW.GLFW_TRUE);
    }

    public boolean maximized() {
        return GLFW.glfwGetWindowAttrib(this.handle, GLFW.GLFW_MAXIMIZED) == GLFW.GLFW_TRUE;
    }

    public void maximize(boolean maximize) {
        if (maximize == this.maximized()) return;

        if (maximize) {
            GLFW.glfwMaximizeWindow(this.handle);
        } else {
            GLFW.glfwRestoreWindow(this.handle);
        }
    }

    public void fullscreen(Window window) {
        Monitor monitor = window.findBestMonitor();
        if (monitor == null) return;
        this.size(monitor.getCurrentMode().getWidth(), monitor.getCurrentMode().getHeight());
        this.position(monitor.getX(), monitor.getY());
    }

    public void size(int width, int height) {
        GLFW.glfwSetWindowSize(this.handle, width, height);
    }

    public void position(int x, int y) {
        GLFW.glfwSetWindowPos(this.handle, x, y);
    }
}
