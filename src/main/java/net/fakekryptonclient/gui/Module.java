package net.fakekryptonclient.gui;

import java.util.ArrayList;
import java.util.List;

/**
 * Tamamen kozmetik bir "modul" temsili.
 *
 * ONEMLI: Bu sinif hicbir oyun mantigina baglanmaz. "enabled" alani
 * yalnizca GUI'de yanip sonen bir toggle gostermek icindir. Herhangi bir
 * tick, event veya mixin bu degeri okumaz; dolayisiyla oyun oynanisinda
 * hicbir degisiklik olusturmaz.
 */
public class Module {
    private final String name;
    private boolean enabled = false;
    private boolean settingsOpen = false;
    private final List<FakeSetting> settings = new ArrayList<>();

    public Module(String name) {
        this.name = name;
    }

    public Module withSlider(String label, float defaultValue) {
        settings.add(FakeSetting.slider(label, defaultValue));
        return this;
    }

    public Module withCheckbox(String label, boolean defaultValue) {
        settings.add(FakeSetting.checkbox(label, defaultValue));
        return this;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    /** Sadece görsel toggle; gerçek dünyada hiçbir şeyi açıp kapatmaz. */
    public void toggle() {
        enabled = !enabled;
    }

    public boolean isSettingsOpen() {
        return settingsOpen;
    }

    public void toggleSettings() {
        settingsOpen = !settingsOpen;
    }

    public List<FakeSetting> getSettings() {
        return settings;
    }

    /** Ayarlar panelinde gösterilecek sahte (işlevsiz) bir ayar. */
    public static class FakeSetting {
        public enum Type { SLIDER, CHECKBOX }

        public final Type type;
        public final String label;
        public float sliderValue; // 0.0 - 1.0 arasi normalize
        public boolean checkboxValue;

        private FakeSetting(Type type, String label) {
            this.type = type;
            this.label = label;
        }

        static FakeSetting slider(String label, float defaultValue) {
            FakeSetting s = new FakeSetting(Type.SLIDER, label);
            s.sliderValue = defaultValue;
            return s;
        }

        static FakeSetting checkbox(String label, boolean defaultValue) {
            FakeSetting s = new FakeSetting(Type.CHECKBOX, label);
            s.checkboxValue = defaultValue;
            return s;
        }
    }
}
