package com.codeitforyou.marriage.listener;

import com.codeitforyou.marriage.api.MarriageAPI;
import com.codeitforyou.marriage.api.event.CIFYDivorceEvent;
import com.codeitforyou.marriage.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PluginDivorceEvent implements Listener {
    @EventHandler
    public void onDivorce(CIFYDivorceEvent e) {
        if (e.isCancelled()) return;

        OfflinePlayer partner1 = Bukkit.getOfflinePlayer(e.getFirstPartner());
        OfflinePlayer partner2 = Bukkit.getOfflinePlayer(e.getSecondPartner());

        if (partner1.isOnline()) {
            Lang.MARRIAGE_DIVORCE.send(Bukkit.getPlayer(e.getFirstPartner()), Lang.PREFIX.asString(), partner2.getName());
        }

        if (partner2.isOnline()) {
            Lang.MARRIAGE_DIVORCE.send(Bukkit.getPlayer(e.getSecondPartner()), Lang.PREFIX.asString(), partner1.getName());
        }

        MarriageAPI.forceDivorce(Bukkit.getPlayer(e.getFirstPartner()));
    }

}
