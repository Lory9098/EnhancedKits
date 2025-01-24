package me.nettychannell.enhancedkits.hooks.impl;

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.nettychannell.enhancedkits.EnhancedKits;
import me.nettychannell.enhancedkits.hooks.Hookable;
import me.nettychannell.enhancedkits.kit.Kit;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PlaceholderAPIHook extends PlaceholderExpansion implements Hookable {
    private final EnhancedKits instance;

    @Override
    public String getIdentifier() {
        return "ek";
    }

    @Override
    public String getAuthor() {
        return "NettyChannell";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    public String onPlaceholderRequest(final Player player, final String params) {
        switch (params) {
            case "availablekits":
                return instance.getKitService()
                        .getAvailableKits(player)
                        .stream()
                        .map(Kit::getName)
                        .collect(Collectors.joining(", "));
            case "unavailablekits":
                return instance.getKitService()
                        .getUnavailableKits(player)
                        .stream()
                        .map(Kit::getName)
                        .collect(Collectors.joining(", "));
        }

        return "";
    }

    @Override
    public void registerHook() {
        register();
    }
}
