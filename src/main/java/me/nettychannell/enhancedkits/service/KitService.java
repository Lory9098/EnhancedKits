package me.nettychannell.enhancedkits.service;

import me.nettychannell.enhancedkits.kit.Kit;
import me.nettychannell.enhancedkits.utils.cooldown.type.CooldownType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class KitService {
    private final HashMap<String, Kit> kits = new HashMap<>();

    public KitService(ConfigurationSection section) {
        section.getKeys(false)
                .forEach(kitName -> {
                    ConfigurationSection kitSection = section.getConfigurationSection(kitName);

                    if (kitSection == null) return;

                    String permission = null;
                    CooldownType coolDownType;
                    boolean showInGui;
                    HashMap<Integer, ItemStack> items = new HashMap<>();

                    if (kitSection.getBoolean("permission.enabled")) {
                        permission = kitSection.getString("permission.value");
                    }

                    coolDownType = CooldownType.valueOf(kitSection.getString("cooldown").toUpperCase());
                    showInGui = kitSection.getBoolean("showInGui");

                    kitSection.getConfigurationSection("items")
                            .getKeys(false)
                            .forEach(slot -> {
                                ItemStack itemStack = kitSection.getItemStack("items." + slot);

                                items.put(Integer.parseInt(slot), itemStack);
                            });

                    addKit(new Kit(kitName, permission, coolDownType, showInGui, items));
                });
    }

    public void addKit(Kit kit) {
        this.kits.put(kit.getName(), kit);
    }

    private Kit getKit(String name) {
        return this.kits.get(name);
    }

    public boolean exists(String name) {
        return this.kits.containsKey(name);
    }

    public void clear() {
        this.kits.clear();
    }


}
