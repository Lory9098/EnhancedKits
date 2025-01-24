package me.nettychannell.enhancedkits.service;

import me.nettychannell.enhancedkits.EnhancedKits;
import me.nettychannell.enhancedkits.kit.Kit;
import me.nettychannell.enhancedkits.cooldown.type.CooldownType;
import me.nettychannell.enhancedkits.kit.item.KitItem;
import me.nettychannell.enhancedkits.kit.item.behavior.ItemBehavior;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
                    HashMap<Integer, KitItem> items = new HashMap<>();

                    if (kitSection.getBoolean("permission.enabled")) {
                        permission = kitSection.getString("permission.value");
                    }

                    coolDownType = CooldownType.valueOf(kitSection.getString("cooldown").toUpperCase());
                    showInGui = kitSection.getBoolean("showInGui");

                    kitSection.getConfigurationSection("items")
                            .getKeys(false)
                            .forEach(slot -> {
                                ItemStack itemStack = kitSection.getItemStack("items." + slot + ".item");

                                ItemBehavior itemBehavior = ItemBehavior.valueOf(EnhancedKits.getInstance().getConfig().getString("kit-item-behavior-default").toUpperCase());

                                if (kitSection.contains("items." + slot + ".behavior")) {
                                    itemBehavior = ItemBehavior.valueOf(kitSection.getString("items." + slot + ".behavior").toUpperCase());
                                }

                                items.put(Integer.parseInt(slot), new KitItem(itemStack, itemBehavior));
                            });

                    addKit(new Kit(kitName, permission, coolDownType, showInGui, items));
                });
    }

    public void addKit(Kit kit) {
        this.kits.put(kit.getName(), kit);
    }

    public Kit getKit(String name) {
        return this.kits.get(name);
    }

    public boolean exists(String name) {
        return this.kits.containsKey(name);
    }

    public List<Kit> getAvailableKits(Player player) {
        return kits.values().stream().filter(kit -> kit.getPermission() == null || player.hasPermission(kit.getPermission())).collect(Collectors.toList());
    }

    public List<Kit> getUnavailableKits(Player player) {
        return kits.values().stream().filter(kit -> kit.getPermission() != null && !player.hasPermission(kit.getPermission())).collect(Collectors.toList());
    }

    public List<Kit> getKits() {
        return new ArrayList<>(kits.values());
    }

    public List<String> getKitsNames() {
        return new ArrayList<>(kits.keySet());
    }

    public void clear() {
        this.kits.clear();
    }


}
