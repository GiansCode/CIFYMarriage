package com.codeitforyou.marriage.manager;

import com.codeitforyou.marriage.CIFYMarriage;
import com.codeitforyou.marriage.listener.PluginDivorceEvent;
import com.codeitforyou.marriage.listener.PluginMarriageEvent;
import org.bukkit.event.Listener;

public class ListenerManager {
    private static final Listener[] LISTENERS = {
            new PluginMarriageEvent(),
            new PluginDivorceEvent()
    };

    public static void register() {
        CIFYMarriage plugin = CIFYMarriage.getPlugin();
        for (Listener listener : LISTENERS) {
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
}
