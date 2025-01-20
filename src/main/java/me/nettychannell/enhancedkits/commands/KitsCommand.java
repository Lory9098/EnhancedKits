package me.nettychannell.enhancedkits.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.HelpCommand;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import me.nettychannell.enhancedkits.Enhancedkits;
import me.nettychannell.enhancedkits.utils.ChatUtils;
import org.bukkit.entity.Player;

@CommandAlias("kit|kits")
@RequiredArgsConstructor
public class KitsCommand extends BaseCommand {
    private final Enhancedkits instance;

    @Default
    @HelpCommand
    public void onHelp(Player player) {
        switch (instance.getConfig().getString("command.default").toUpperCase()) {
            case "MESSAGE":
                instance.getConfig().getStringList("kits-message")
                        .forEach(message -> {
                            player.sendMessage(ChatUtils.colorComponent(PlaceholderAPI.setPlaceholders(player, message)));
                        });
                break;
            case "GUI":
                //TODO: open GUI
                return;
        }
    }

}
