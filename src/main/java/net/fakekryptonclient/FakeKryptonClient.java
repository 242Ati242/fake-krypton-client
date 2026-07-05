package net.fakekryptonclient;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FakeKryptonClient implements ModInitializer {
    public static final String MOD_ID = "fake-krypton-client";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Fake Krypton Client yuklendi (gercek bir hile client'i degildir) - sadece gorsel bir ClickGUI, hicbir gercek hile icermez.");
    }
}
