package me.nettychannell.enhancedkits.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import me.nettychannell.enhancedkits.EnhancedKits;
import me.nettychannell.enhancedkits.kit.Kit;
import me.nettychannell.enhancedkits.kit.item.KitItem;
import me.nettychannell.enhancedkits.kit.item.behavior.ItemBehavior;
import me.nettychannell.enhancedkits.user.KitsUser;
import me.nettychannell.enhancedkits.utils.ChatUtils;
import me.nettychannell.enhancedkits.utils.kits.KitsUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

@CommandAlias("kit|kits")
@RequiredArgsConstructor
public class KitsCommand extends BaseCommand {
    private final EnhancedKits instance;

    @Default
    @HelpCommand
    public void onHelp(Player player) {
        switch (instance.getConfig().getString("command.default").toUpperCase()) {
            case "MESSAGE":
                instance.getConfig().getStringList("kits-message")
                        .forEach(message -> {
                            player.sendMessage(ChatUtils.color(PlaceholderAPI.setPlaceholders(player, message)));
                        });
                break;
            case "GUI":
                //TODO: open GUI
                return;
        }
    }

    @Default
    @HelpCommand
    @CommandCompletion("kits")
    public void onGive(Player player, String kitName) {
        if (!instance.getKitService().exists(kitName)) {
            player.sendMessage(ChatUtils.color(instance.getConfig().getString("messages.kit-not-found").replace("%kit%", kitName)));
            return;
        }

        Kit kit = instance.getKitService().getKit(kitName);

        if (kit == null) {
            player.sendMessage(ChatUtils.color(instance.getConfig().getString("messages.kit-not-found").replace("%kit%", kitName)));
            return;
        }

        if (!player.hasPermission(kit.getPermission())) {
            player.sendMessage(ChatUtils.color(instance.getConfig().getString("messages.no-permission").replace("%kit%", kitName)));
            return;
        }

        KitsUser user = instance.getLoadit().getContainer().getCached(player);

        if (!user.isCooldownFinished(kitName)) {
            player.sendMessage(ChatUtils.color(instance.getConfig().getString("messages.cooldown").replace("%kit%", kitName)));
            return;
        }

        user.setCooldownsUnlocks(kitName, System.currentTimeMillis() + instance.getCoolDownService().getCoolDown(kit).getMillisCoolDown(player));

        KitsUtils.give(kit, player);
        player.sendMessage(ChatUtils.color(instance.getConfig().getString("messages.kit-given").replace("%kit%", kitName)));
    }

    @Subcommand("save")
    @CommandPermission("enhancedkits.editor.save")
    @CommandCompletion("kits")
    public void onSave(Player player, String kitName) {
        if (!instance.getKitService().exists(kitName)) {
            player.sendMessage(ChatUtils.color(instance.getConfig().getString("messages.kit-not-found").replace("%kit%", kitName)));
            return;
        }

        Kit kit = instance.getKitService().getKit(kitName);

        if (kit == null) {
            player.sendMessage(ChatUtils.color(instance.getConfig().getString("messages.kit-not-found").replace("%kit%", kitName)));
            return;
        }

        HashMap<Integer, KitItem> items = new HashMap<>();

        for (ItemStack itemStack : player.getInventory().getContents()) {
            int slot = player.getInventory().first(itemStack);

            items.put(slot, new KitItem(itemStack, ItemBehavior.valueOf(EnhancedKits.getInstance().getConfig().getString("kit-item-behavior-default").toUpperCase())));
        }

        kit.getItems().clear();
        kit.getItems().putAll(items);

        ConfigurationSection section = instance.getConfig().getConfigurationSection("kits." + kitName + ".items");

        section.getKeys(false)
                        .forEach(key -> {
                            section.set(key, null);
                        });

        items.forEach((slot, kitItem) -> {
            section.set(slot + ".item", kitItem.getItemStack());
        });

        instance.saveConfig();

        player.sendMessage(ChatUtils.color(instance.getConfig().getString("messages.kit-saved").replace("%kit%", kitName)));
    }

}
