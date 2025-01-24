package me.nettychannell.enhancedkits.kit.item;

import lombok.Data;
import me.nettychannell.enhancedkits.kit.item.behavior.ItemBehavior;
import org.bukkit.inventory.ItemStack;

@Data
public class KitItem {

    private final ItemStack itemStack;
    private final ItemBehavior itemBehavior;

}
