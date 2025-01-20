package me.nettychannell.enhancedkits.utils;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class ChatUtils {

    public Component colorComponent(String old) {
        return Component.text(color(old));
    }

    public String color(String old) {
        return ChatColor.translateAlternateColorCodes('&', old);
    }

}
