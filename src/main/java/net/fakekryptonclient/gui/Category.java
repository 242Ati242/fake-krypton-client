package net.fakekryptonclient.gui;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private final String name;
    private final List<Module> modules = new ArrayList<>();
    private boolean expanded = true;

    // Panelin ekrandaki konumu; kullanıcı sürükleyebilsin diye ayrı tutuluyor.
    public float x;
    public float y;

    public Category(String name, float x, float y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public Category addModule(Module module) {
        modules.add(module);
        return this;
    }

    public String getName() {
        return name;
    }

    public List<Module> getModules() {
        return modules;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void toggleExpanded() {
        expanded = !expanded;
    }
}
