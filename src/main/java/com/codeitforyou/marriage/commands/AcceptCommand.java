package com.codeitforyou.marriage.commands;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.marriage.CIFYMarriage;
import com.codeitforyou.marriage.api.Marriage;
import com.codeitforyou.marriage.api.MarriageAPI;
import com.codeitforyou.marriage.api.event.CIFYMarriageEvent;
import com.codeitforyou.marriage.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class AcceptCommand {

    @Command(aliases = {"accept"}, usage = "accept", permission = "marriage.use", title = "Accept Marriage", about = "Accept a marriage request.")
    public static void execute(Player sender, CIFYMarriage plugin, String[] args) {
        Marriage marriage = MarriageAPI.getMarriage(sender);

        if (marriage != null) {
            // Not married!
            Lang.ERROR_ALREADY_MARRIED.send(sender, Lang.PREFIX.asString());
            return;
        }

        Player target = Bukkit.getPlayer(plugin.getMarriageManager().getRequests().getIfPresent(sender.getUniqueId()));

        if (target == null) {
            Lang.ERROR_INVALID_PLAYER.send(sender, Lang.PREFIX.asString());
            return;
        }

        CIFYMarriageEvent cifyMarriageEvent = new CIFYMarriageEvent(sender.getUniqueId(), target.getUniqueId(), System.currentTimeMillis());
        Bukkit.getServer().getPluginManager().callEvent(cifyMarriageEvent);
    }
}
