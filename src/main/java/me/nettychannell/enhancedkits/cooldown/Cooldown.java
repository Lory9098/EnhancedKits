package me.nettychannell.enhancedkits.cooldown;

import org.bukkit.entity.Player;

public interface Cooldown {

    int getSecondsCoolDown(Player player);

    default long getMillisCoolDown(Player player) {
        return getSecondsCoolDown(player) * 1000L;
    }

}
