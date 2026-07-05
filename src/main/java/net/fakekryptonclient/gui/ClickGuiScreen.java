package net.fakekryptonclient.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * KryptonClient ClickGUI.
 *
 * Bu ekran %100 kozmetiktir:
 *  - Modul kutucuklarina tiklamak sadece kendi "enabled" bayragini cevirir.
 *  - Hicbir modul; oyuncunun konumunu, saglik degerini, envanterini,
 *    paketlerini veya render pipeline'ini DEGISTIRMEZ.
 *  - Burada gorunen "ESP", "Fly", "KillAura" vb. isimler sadece metindir.
 *
 * ESC veya Right Shift ile kapanir (Right Shift keybinding KryptonClientClient
 * icinde tanimli).
 */
public class ClickGuiScreen extends Screen {

    private static final int PANEL_WIDTH = 170;
    private static final int HEADER_HEIGHT = 22;
    private static final int ROW_HEIGHT = 20;
    private static final int PANEL_GAP = 10;

    private final List<Category> categories = new ArrayList<>();

    // Sürükleme durumu
    private Category draggingCategory = null;
    private float dragOffsetX, dragOffsetY;

    public ClickGuiScreen() {
        super(Text.literal("Fake Krypton Client"));
        buildCategories();
    }

    private void buildCategories() {
        float startX = 20;
        float startY = 20;

        Category combat = new Category("COMBAT", startX, startY);
        String[] combatModules = {
                "Kill Aura", "Mob Aura", "Crystal Aura", "Auto Clicker",
                "Hitbox Expander", "Velocity", "Auto Totem", "Totem Pop",
                "PvP Info", "Anti Knockback", "Triggerbot", "Auto Gapple",
                "Auto Pot", "Auto Armor", "Auto Weapon", "Criticals",
                "Hit Sounds", "Damage Particles", "Anti Weapon Switch", "Bow Aimbot"
        };
        for (String m : combatModules) {
            combat.addModule(new Module(m).withSlider("Range", 0.5f).withCheckbox("Ignore Walls", false));
        }
        categories.add(combat);

        startX += PANEL_WIDTH + PANEL_GAP;
        Category move = new Category("MOVE", startX, startY);
        String[] moveModules = {
                "Speed", "Fly", "Target Strafe", "Long Jump", "High Jump",
                "No Slow", "Step", "Inventory Move", "Keep Sprint", "Anti Void",
                "Safe Walk", "Water Walk", "Ladder Boost", "Elytra Boost",
                "Glide", "No Fall", "Phase", "Teleport", "No Clip", "Boat Fly"
        };
        for (String m : moveModules) {
            move.addModule(new Module(m).withSlider("Speed", 0.3f));
        }
        categories.add(move);

        startX += PANEL_WIDTH + PANEL_GAP;
        Category render = new Category("RENDER", startX, startY);
        String[] renderModules = {
                "ESP", "Outline ESP", "Box ESP", "Tracers", "Name Tags",
                "Health Indicator", "Item ESP", "Armor Status", "Skeleton ESP",
                "X-Ray", "Fullbright", "Chunk Borders", "Entity Control",
                "Free Camera", "Zoom", "No Fog", "Hand View", "Crosshair",
                "Hit Marker", "View Clip"
        };
        for (String m : renderModules) {
            render.addModule(new Module(m).withCheckbox("Through Walls", true));
        }
        categories.add(render);

        startX += PANEL_WIDTH + PANEL_GAP;
        Category exploit = new Category("EXPLOIT", startX, startY);
        String[] exploitModules = {
                "Clean Inventory", "Disconnect", "Anti Ban", "Hypixel Bypass",
                "Rejoin", "Alt Manager", "Auto Play", "Chat Exploit",
                "Crash Server", "Dupe Exploit"
        };
        for (String m : exploitModules) {
            exploit.addModule(new Module(m));
        }
        categories.add(exploit);

        startX += PANEL_WIDTH + PANEL_GAP;
        Category player = new Category("PLAYER", startX, startY);
        String[] playerModules = {
                "No Rotate", "Anti AFK", "Auto Fish", "Fast Eat", "Anti Fire",
                "Freeze", "Anti Hunger", "No Interact", "Auto Mend"
        };
        for (String m : playerModules) {
            player.addModule(new Module(m));
        }
        categories.add(player);

        startX += PANEL_WIDTH + PANEL_GAP;
        Category world = new Category("WORLD", startX, startY);
        String[] worldModules = {"Nuker", "Scaffold", "Tower", "Auto Build", "Block Reach"};
        for (String m : worldModules) {
            world.addModule(new Module(m).withSlider("Reach", 0.6f));
        }
        categories.add(world);

        startX += PANEL_WIDTH + PANEL_GAP;
        Category misc = new Category("MISC", startX, startY);
        String[] miscModules = {
                "Discord RPC", "Client Sounds", "HUD", "Click GUI",
                "Theme Editor", "Client Settings", "Custom Fonts", "Client Capes"
        };
        for (String m : miscModules) {
            misc.addModule(new Module(m));
        }
        categories.add(misc);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Arka planı karartmadan oyun dünyasını arkada göster.
        this.renderBackground(context, mouseX, mouseY, delta);

        for (Category category : categories) {
            renderCategory(context, category, mouseX, mouseY);
        }

        context.drawTextWithShadow(this.textRenderer, "Fake Krypton Client v1.7.3 (no-op)",
                12, this.height - 24, 0xFF9B5CFF);
        context.drawTextWithShadow(this.textRenderer, "Right Shift to close",
                12, this.height - 13, 0xFF7A5CFF);

        super.render(context, mouseX, mouseY, delta);
    }

    private void renderCategory(DrawContext context, Category category, int mouseX, int mouseY) {
        int x = (int) category.x;
        int y = (int) category.y;

        int totalHeight = HEADER_HEIGHT;
        if (category.isExpanded()) {
            for (Module m : category.getModules()) {
                totalHeight += ROW_HEIGHT;
                if (m.isSettingsOpen()) {
                    totalHeight += m.getSettings().size() * ROW_HEIGHT;
                }
            }
        }

        // Panel arkaplanı
        context.fill(x, y, x + PANEL_WIDTH, y + totalHeight, 0xCC1A0F2E);
        context.fill(x, y, x + PANEL_WIDTH, y + HEADER_HEIGHT, 0xE62A1750);

        context.drawTextWithShadow(this.textRenderer, category.getName(),
                x + 8, y + 7, 0xFFB98CFF);
        context.drawTextWithShadow(this.textRenderer, category.isExpanded() ? "-" : "+",
                x + PANEL_WIDTH - 14, y + 7, 0xFFB98CFF);

        if (!category.isExpanded()) {
            return;
        }

        int rowY = y + HEADER_HEIGHT;
        for (Module module : category.getModules()) {
            boolean hovered = mouseX >= x && mouseX <= x + PANEL_WIDTH
                    && mouseY >= rowY && mouseY <= rowY + ROW_HEIGHT;

            context.fill(x, rowY, x + PANEL_WIDTH, rowY + ROW_HEIGHT,
                    hovered ? 0x992A1750 : 0x661A0F2E);

            int textColor = module.isEnabled() ? 0xFFFFFFFF : 0xFF8A7BA8;
            context.drawTextWithShadow(this.textRenderer, module.getName(),
                    x + 8, rowY + 6, textColor);

            // "+" ayar butonu (sadece görsel açılış)
            context.drawTextWithShadow(this.textRenderer, "+",
                    x + PANEL_WIDTH - 34, rowY + 6, 0xFF8A7BA8);

            // Sağdaki toggle anahtarı (sadece görsel)
            int toggleX = x + PANEL_WIDTH - 22;
            int toggleY = rowY + 4;
            int toggleColor = module.isEnabled() ? 0xFF9B5CFF : 0xFF3A2A55;
            context.fill(toggleX, toggleY, toggleX + 16, toggleY + 12, toggleColor);
            int knobX = module.isEnabled() ? toggleX + 9 : toggleX + 2;
            context.fill(knobX, toggleY + 2, knobX + 5, toggleY + 10, 0xFFFFFFFF);

            rowY += ROW_HEIGHT;

            if (module.isSettingsOpen()) {
                for (Module.FakeSetting setting : module.getSettings()) {
                    context.fill(x, rowY, x + PANEL_WIDTH, rowY + ROW_HEIGHT, 0x55000000);
                    context.drawTextWithShadow(this.textRenderer, setting.label,
                            x + 12, rowY + 6, 0xFF6E5C99);

                    if (setting.type == Module.FakeSetting.Type.SLIDER) {
                        int barX = x + PANEL_WIDTH - 70;
                        int barW = 60;
                        context.fill(barX, rowY + 8, barX + barW, rowY + 10, 0xFF3A2A55);
                        int filled = (int) (barW * setting.sliderValue);
                        context.fill(barX, rowY + 8, barX + filled, rowY + 10, 0xFF9B5CFF);
                    } else {
                        int boxX = x + PANEL_WIDTH - 24;
                        context.fill(boxX, rowY + 5, boxX + 10, rowY + 15,
                                setting.checkboxValue ? 0xFF9B5CFF : 0xFF3A2A55);
                    }
                    rowY += ROW_HEIGHT;
                }
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            for (Category category : categories) {
                int x = (int) category.x;
                int y = (int) category.y;

                // Başlığa tıklama: sürükleme başlat / genişlet-daralt
                if (mouseX >= x && mouseX <= x + PANEL_WIDTH && mouseY >= y && mouseY <= y + HEADER_HEIGHT) {
                    if (mouseX >= x + PANEL_WIDTH - 20) {
                        category.toggleExpanded();
                    } else {
                        draggingCategory = category;
                        dragOffsetX = (float) (mouseX - x);
                        dragOffsetY = (float) (mouseY - y);
                    }
                    return true;
                }

                if (!category.isExpanded()) continue;

                int rowY = y + HEADER_HEIGHT;
                for (Module module : category.getModules()) {
                    if (mouseX >= x && mouseX <= x + PANEL_WIDTH && mouseY >= rowY && mouseY <= rowY + ROW_HEIGHT) {
                        if (mouseX >= x + PANEL_WIDTH - 34 && mouseX < x + PANEL_WIDTH - 20) {
                            // Ayarları aç/kapat (yalnızca görsel)
                            module.toggleSettings();
                        } else {
                            // Modülü aç/kapat (yalnızca görsel, hiçbir gerçek etki yok)
                            module.toggle();
                        }
                        return true;
                    }
                    rowY += ROW_HEIGHT;

                    if (module.isSettingsOpen()) {
                        for (Module.FakeSetting setting : module.getSettings()) {
                            if (mouseX >= x && mouseX <= x + PANEL_WIDTH && mouseY >= rowY && mouseY <= rowY + ROW_HEIGHT) {
                                if (setting.type == Module.FakeSetting.Type.SLIDER) {
                                    int barX = x + PANEL_WIDTH - 70;
                                    int barW = 60;
                                    float ratio = (float) ((mouseX - barX) / barW);
                                    setting.sliderValue = Math.max(0f, Math.min(1f, ratio));
                                } else {
                                    setting.checkboxValue = !setting.checkboxValue;
                                }
                                return true;
                            }
                            rowY += ROW_HEIGHT;
                        }
                    }
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (draggingCategory != null && button == 0) {
            draggingCategory.x = (float) (mouseX - dragOffsetX);
            draggingCategory.y = (float) (mouseY - dragOffsetY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        draggingCategory = null;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        // Tek oyunculu dunyada oyunu duraklatmasin, GUI acikken de dunya donsun.
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void close() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            client.setScreen(null);
        }
    }
}
