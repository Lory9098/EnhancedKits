package me.nettychannell.enhancedkits;

import co.aikar.commands.BukkitCommandManager;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.ytnoos.loadit.Loadit;
import lombok.Getter;
import me.nettychannell.enhancedkits.commands.KitsCommand;
import me.nettychannell.enhancedkits.hooks.Hookable;
import me.nettychannell.enhancedkits.hooks.impl.PlaceholderAPIHook;
import me.nettychannell.enhancedkits.service.CooldownService;
import me.nettychannell.enhancedkits.service.KitService;
import me.nettychannell.enhancedkits.user.KitsUser;
import me.nettychannell.enhancedkits.user.load.PlayerLoader;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class EnhancedKits extends JavaPlugin {
    @Getter
    private static EnhancedKits instance;

    private Loadit<KitsUser> loadit;

    private KitService kitService;
    private CooldownService coolDownService;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;

        loadit = Loadit.createInstance(this, new PlayerLoader());

        kitService = new KitService(getConfig().getConfigurationSection("kits"));
        coolDownService = new CooldownService(getConfig().getConfigurationSection("cooldowns"));

        Lists.asList(
                new PlaceholderAPIHook(this)
        ).forEach(Hookable::registerHook);

        BukkitCommandManager bukkitCommandManager = new BukkitCommandManager(this);

        bukkitCommandManager.getCommandCompletions().registerAsyncCompletion("foo", c -> {
            return ImmutableList.of(kitService.getKitsNames());
        });

        bukkitCommandManager.registerCommand(new KitsCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
