package com.codeitforyou.marriage.commands;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.marriage.CIFYMarriage;
import com.codeitforyou.marriage.api.Marriage;
import com.codeitforyou.marriage.api.MarriageAPI;
import com.codeitforyou.marriage.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DenyCommand {

    @Command(aliases = {"deny"}, usage = "deny", permission = "marriage.use", title = "Deny Marriage", about = "Deny a marriage request.")
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

        plugin.getMarriageManager().getRequests().invalidate(sender.getUniqueId());
        Lang.MARRIAGE_DENIED_SENT.send(sender, Lang.PREFIX.asString(), target.getName());
        Lang.MARRIAGE_DENIED_RECEIVED.send(target, Lang.PREFIX.asString(), sender.getName());

    }
}
