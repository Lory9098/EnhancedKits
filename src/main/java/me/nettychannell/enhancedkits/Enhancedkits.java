package me.nettychannell.enhancedkits;

import co.aikar.commands.BukkitCommandManager;
import lombok.Getter;
import me.nettychannell.enhancedkits.commands.KitsCommand;
import me.nettychannell.enhancedkits.service.CooldownService;
import me.nettychannell.enhancedkits.service.KitService;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Enhancedkits extends JavaPlugin {
    @Getter
    private static Enhancedkits instance;

    private KitService kitService;
    private CooldownService coolDownService;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;

        kitService = new KitService(getConfig().getConfigurationSection("kits"));
        coolDownService = new CooldownService(getConfig().getConfigurationSection("cooldowns"));

        BukkitCommandManager bukkitCommandManager = new BukkitCommandManager(this);

        bukkitCommandManager.registerCommand(new KitsCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
