package me.nettychannell.enhancedkits.cooldown.impl;

import lombok.RequiredArgsConstructor;
import me.nettychannell.enhancedkits.cooldown.Cooldown;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class GlobalCooldown implements Cooldown {
    private final int coolDown;

    @Override
    public int getSecondsCoolDown(Player player) {
        return coolDown;
    }
}
