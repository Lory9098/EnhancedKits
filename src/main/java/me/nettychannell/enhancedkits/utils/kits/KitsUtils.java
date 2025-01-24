package me.nettychannell.enhancedkits.utils.kits;

import lombok.experimental.UtilityClass;
import me.nettychannell.enhancedkits.kit.Kit;
import me.nettychannell.enhancedkits.kit.item.behavior.ItemBehavior;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@UtilityClass
public class KitsUtils {

    public void give(Kit kit, Player player) {
        PlayerInventory inv = player.getInventory();

        kit.getItems().forEach((slot, kitItem) -> {
            ItemStack itemStack = kitItem.getItemStack();
            ItemBehavior itemBehavior = kitItem.getItemBehavior();

            if (inv.getItem(slot) == null || inv.getItem(slot).getType() == Material.AIR) {
                int firstAvailableSlot = inv.firstEmpty();

                if (firstAvailableSlot != -1) {
                    //TODO: no space handling
                    return;
                }

                switch (itemBehavior) {
                    case FIND_FIRST:
                        inv.setItem(firstAvailableSlot, itemStack);
                        break;
                    case OVERRIDE:
                        ItemStack cachedItem = inv.getItem(slot);

                        inv.setItem(slot, itemStack);
                        inv.setItem(firstAvailableSlot, cachedItem);
                        break;
                }
            }
        });
    }

}
