package me.nettychannell.enhancedkits.user;

import it.ytnoos.loadit.api.UserData;
import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class KitsUser extends UserData {

    private final HashMap<String, Long> cooldownsUnlocks;

    public KitsUser(UUID uuid, String name, HashMap<String, Long> cooldownsUnlocks) {
        super(uuid, name);

        this.cooldownsUnlocks = cooldownsUnlocks;
    }

    public void setCooldownsUnlocks(String kitName, long unlockWhen) {
        cooldownsUnlocks.put(kitName, unlockWhen);
    }

    public boolean isCooldownFinished(String kitName) {
        return !cooldownsUnlocks.containsKey(kitName) || System.currentTimeMillis() - cooldownsUnlocks.get(kitName) >= 0;
    }

    public void eraseCooldown(String kitName) {
        cooldownsUnlocks.remove(kitName);
    }

}
