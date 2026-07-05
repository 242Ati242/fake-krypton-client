package net.fakekryptonclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fakekryptonclient.gui.ClickGuiScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

/**
 * Client-side entry point. Tek yaptigi sey Right Shift tusuna basildiginda
 * ClickGuiScreen'i acmak/kapatmaktir. Baska hicbir hook, mixin veya event
 * kaydi yoktur; dolayisiyla oyun mekanigine dokunulmaz.
 */
public class FakeKryptonClientClient implements ClientModInitializer {

    private static KeyBinding openGuiKey;

    @Override
    public void onInitializeClient() {
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.fake-krypton-client.open_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.fake-krypton-client.general"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new ClickGuiScreen());
                } else if (client.currentScreen instanceof ClickGuiScreen) {
                    client.setScreen(null);
                }
            }
        });
    }
}
