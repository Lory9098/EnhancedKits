package me.nettychannell.enhancedkits.user.load;

import it.ytnoos.loadit.api.DataLoader;
import me.nettychannell.enhancedkits.user.KitsUser;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class PlayerLoader implements DataLoader<KitsUser> {
    @Override
    public Optional<KitsUser> getOrCreate(UUID uuid, String s) {
        KitsUser kitsUser = null; // get from the db

        if (kitsUser == null) {
            kitsUser = new KitsUser(uuid, s, new HashMap<>());
            //TODO: save into the db
        }

        return Optional.of(kitsUser);
    }

    @Override
    public Optional<KitsUser> load(UUID uuid) {
        return Optional.empty(); // TODO: get from the db
    }

    @Override
    public Optional<KitsUser> load(String s) {
        return Optional.empty(); // TODO: get from the db
    }
}
