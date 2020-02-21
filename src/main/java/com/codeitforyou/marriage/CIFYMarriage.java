package com.codeitforyou.marriage;

import com.codeitforyou.marriage.manager.ListenerManager;
import com.codeitforyou.marriage.manager.MarriageManager;
import com.codeitforyou.marriage.manager.PluginCmdManager;
import com.codeitforyou.marriage.util.Lang;
import org.bukkit.plugin.java.JavaPlugin;

public class CIFYMarriage extends JavaPlugin {
    private MarriageManager marriageManager;
    private static CIFYMarriage plugin;

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        Lang.init(this);
        marriageManager = new MarriageManager();
        marriageManager.loadMarriages();
        new PluginCmdManager().register();
        ListenerManager.register();
    }

    @Override
    public void onDisable() {
        marriageManager.unloadMarriages();
        marriageManager = null;
    }

    public MarriageManager getMarriageManager() {
        return marriageManager;
    }

    public static CIFYMarriage getPlugin() {
        return plugin;
    }
}
