package com.codeitforyou.marriage.listener;

import com.codeitforyou.marriage.CIFYMarriage;
import com.codeitforyou.marriage.api.MarriageAPI;
import com.codeitforyou.marriage.api.event.CIFYMarriageEvent;
import com.codeitforyou.marriage.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PluginMarriageEvent implements Listener {

    @EventHandler
    public void onMarriage(CIFYMarriageEvent e) {
        if (e.isCancelled()) return;

        Player sender = Bukkit.getPlayer(e.getFirstPartner());
        Player target = Bukkit.getPlayer(e.getSecondPartner());

        MarriageAPI.setMarried(sender, target, e.getTime());
        CIFYMarriage.getPlugin().getMarriageManager().getRequests().invalidate(sender.getUniqueId());
        Lang.MARRIAGE_ACCEPTED.send(sender, Lang.PREFIX.asString(), target.getName());
        Lang.MARRIAGE_ACCEPTED.send(target, Lang.PREFIX.asString(), sender.getName());
    }
}
