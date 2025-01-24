package me.nettychannell.enhancedkits.utils;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class ChatUtils {

    public String color(String old) {
        return ChatColor.translateAlternateColorCodes('&', old);
    }

}
