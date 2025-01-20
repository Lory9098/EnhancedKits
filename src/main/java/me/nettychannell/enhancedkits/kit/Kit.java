package me.nettychannell.enhancedkits.kit;

import lombok.Getter;
import me.nettychannell.enhancedkits.utils.cooldown.type.CooldownType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

@Getter
public class Kit {
    private final String name, permission;
    private final CooldownType coolDownType;
    private final boolean showInGui;
    private final HashMap<Integer, ItemStack> items;

    public Kit(String name, String permission, CooldownType coolDownType, boolean showInGui, HashMap<Integer, ItemStack> items) {
        this.name = name;
        this.permission = permission;
        this.coolDownType = coolDownType;
        this.showInGui = showInGui;
        this.items = items;
    }

}
