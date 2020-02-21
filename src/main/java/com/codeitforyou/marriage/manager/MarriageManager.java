package com.codeitforyou.marriage.manager;

import com.codeitforyou.lib.api.exception.InvalidLocationException;
import com.codeitforyou.lib.api.serializer.LocationSerializer;
import com.codeitforyou.marriage.CIFYMarriage;
import com.codeitforyou.marriage.api.Marriage;
import com.codeitforyou.marriage.util.MarriageConfig;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MarriageManager {

    private MarriageConfig marriageConfig;
    private Set<Marriage> marriages;
    private Cache<UUID, UUID> marriageRequests;

    public MarriageManager() {
        marriageConfig = new MarriageConfig();
        marriages = new HashSet<>();
        marriageRequests = CacheBuilder.newBuilder()
                .expireAfterWrite(CIFYMarriage.getPlugin().getConfig().getInt("settings.limit", 30), TimeUnit.SECONDS)
                .maximumSize(500)
                .build();
    }

    public void loadMarriages() {
        YamlConfiguration config = marriageConfig.getConfig();

        if (config.getConfigurationSection("marriage") == null) {
            return;

        }
        for (String marriagePartner1 : config.getConfigurationSection("marriage").getKeys(false)) {
            String marriagePartner2 = config.getString("marriage." + marriagePartner1 + ".partner");
            String marriageHome = config.getString("marriage." + marriagePartner1 + ".home", null);
            long marriageTime = config.getLong("marriage." + marriagePartner1 + ".time", System.currentTimeMillis());
            try {
                Marriage marriage = new Marriage(UUID.fromString(marriagePartner1),
                        UUID.fromString(marriagePartner2),
                        marriageHome != null ? LocationSerializer.fromString(marriageHome) : null,
                        marriageTime
                );
                marriages.add(marriage);
            } catch (InvalidLocationException e) {
                CIFYMarriage.getPlugin().getLogger().warning("Failed to fetch location!");
            }
        }
    }

    public void unloadMarriages() {
        YamlConfiguration config = marriageConfig.getConfig();

        config.set("marriage", null);
        for (Marriage marriage : marriages) {
            config.set("marriage." + marriage.getPartner1() + ".partner", marriage.getPartner2().toString());
            config.set("marriage." + marriage.getPartner1() + ".time", marriage.getDate());
            try {


                config.set("marriage." + marriage.getPartner1() + ".home", marriage.getHome() != null ? LocationSerializer.toString(marriage.getHome()) : null);
            } catch (InvalidLocationException e) {
                CIFYMarriage.getPlugin().getLogger().warning("Failed to save location!");
            }
        }

        try {
            config.save(marriageConfig.getFile());
        } catch (IOException e) {
            CIFYMarriage.getPlugin().getLogger().info("Failed to save marriages.yml file. Reason: " + e.getLocalizedMessage() + "!");
        }
    }

    public Cache<UUID, UUID> getRequests() {
        return marriageRequests;
    }

    public Set<Marriage> getMarriages() {
        return marriages;
    }
}
