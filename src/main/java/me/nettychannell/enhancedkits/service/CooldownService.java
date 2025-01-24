package me.nettychannell.enhancedkits.service;

import me.nettychannell.enhancedkits.kit.Kit;
import me.nettychannell.enhancedkits.cooldown.Cooldown;
import me.nettychannell.enhancedkits.cooldown.impl.GlobalCooldown;
import me.nettychannell.enhancedkits.cooldown.impl.PermissionBasedCooldown;
import me.nettychannell.enhancedkits.cooldown.type.CooldownType;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CooldownService {
    private final HashMap<CooldownType, HashMap<String, Cooldown>> coolDowns = new HashMap<>();

    public CooldownService(ConfigurationSection section) {
        for (CooldownType coolDownType : CooldownType.values()) {
            ConfigurationSection deepSection = section.getConfigurationSection(coolDownType.name().toLowerCase());

            switch (coolDownType) {
                case PERMISSIONBASED:
                    deepSection.getKeys(false)
                            .forEach(key -> {
                                AtomicInteger defaultCoolDown = new AtomicInteger();
                                HashMap<Integer, String> permissions = new HashMap<>();
                                HashMap<Integer, Integer> cooldowns = new HashMap<>();

                                AtomicInteger atomicInteger = new AtomicInteger(0);

                                deepSection.getConfigurationSection(key)
                                        .getKeys(false)
                                        .forEach(permission -> {
                                            int coolDown = deepSection.getInt(key + "." + permission);

                                            if (permission.equalsIgnoreCase("default")) {
                                                defaultCoolDown.set(coolDown);
                                                return;
                                            }

                                            permissions.put(atomicInteger.get(), permission);
                                            cooldowns.put(atomicInteger.get(), coolDown);

                                            atomicInteger.incrementAndGet();
                                        });

                                Cooldown coolDown = new PermissionBasedCooldown(defaultCoolDown.get(), permissions, cooldowns);

                                addCoolDown(coolDownType, key, coolDown);
                            });
                    break;
                case GLOBAL:
                    deepSection.getKeys(false)
                            .forEach(key -> {
                                int coolDownValue = deepSection.getInt(key);

                                Cooldown coolDown = new GlobalCooldown(coolDownValue);

                                addCoolDown(coolDownType, key, coolDown);
                            });
            }
        }
    }

    public Cooldown getCoolDown(Kit kit) {
        return coolDowns.get(kit.getCoolDownType()).get(kit.getName());
    }

    public Cooldown getCoolDown(CooldownType coolDownType, String kitName) {
        return coolDowns.get(coolDownType).get(kitName);
    }

    public void addCoolDown(CooldownType coolDownType, String kit, Cooldown coolDown) {
        HashMap<String, Cooldown> map = coolDowns.getOrDefault(coolDownType, new HashMap<>());

        map.put(kit, coolDown);

        coolDowns.put(coolDownType, map);
    }

}
