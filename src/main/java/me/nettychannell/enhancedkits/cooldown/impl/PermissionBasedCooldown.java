package me.nettychannell.enhancedkits.cooldown.impl;

import lombok.RequiredArgsConstructor;
import me.nettychannell.enhancedkits.cooldown.Cooldown;
import org.bukkit.entity.Player;

import java.util.HashMap;

@RequiredArgsConstructor
public class PermissionBasedCooldown implements Cooldown {
    private final int defaultCoolDown;
    private final HashMap<Integer, String> permissions;
    private final HashMap<Integer, Integer> coolDowns;

    @Override
    public int getSecondsCoolDown(Player player) {
        int size = permissions.size();
        if (size != coolDowns.size()) {
            throw new IllegalStateException("Permissions can't be less than cooldown! Skibidi situation");
        }

        while (size >= 0) {
            int i = permissions.size() - size;
            if (player.hasPermission(permissions.get(i))) {
                return coolDowns.get(i);
            }
            size--;
        }
        return defaultCoolDown;
    }
}
