package com.codeitforyou.marriage.util;

import com.codeitforyou.marriage.CIFYMarriage;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MarriageConfig {

    private final File marriageFile = new File(CIFYMarriage.getPlugin().getDataFolder() + "/" + "marriages.yml");
    private YamlConfiguration marriageConfig;

    public MarriageConfig() {
        if (!marriageFile.exists()) {
            try {
                marriageFile.createNewFile();
                loadConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            loadConfig();
        }
    }

    public void loadConfig() {
        marriageConfig = YamlConfiguration.loadConfiguration(marriageFile);
    }

    public YamlConfiguration getConfig() {
        return marriageConfig;
    }

    public File getFile() {
        return marriageFile;
    }
}
