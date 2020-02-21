package com.codeitforyou.marriage.commands;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.marriage.CIFYMarriage;
import com.codeitforyou.marriage.api.Marriage;
import com.codeitforyou.marriage.api.MarriageAPI;
import com.codeitforyou.marriage.api.event.CIFYDivorceEvent;
import com.codeitforyou.marriage.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class DivorceCommand {

    @Command(aliases = {"divorce"}, usage = "divorce", permission = "marriage.use", title = "Divorce a Player", about = "Divorce a player.")
    public static void execute(Player sender, CIFYMarriage plugin, String[] args) {
        Marriage marriage = MarriageAPI.getMarriage(sender);

        if (marriage == null) {
            // Not married!
            Lang.ERROR_NOT_MARRIED.send(sender, Lang.PREFIX.asString());
            return;
        }

        OfflinePlayer partner1 = Bukkit.getOfflinePlayer(marriage.getPartner1());
        OfflinePlayer partner2 = Bukkit.getOfflinePlayer(marriage.getPartner2());

        MarriageAPI.forceDivorce(sender);
        CIFYDivorceEvent cifyDivorceEvent = new CIFYDivorceEvent(partner1.getUniqueId(), partner2.getUniqueId(), System.currentTimeMillis());
        Bukkit.getServer().getPluginManager().callEvent(cifyDivorceEvent);
    }
}
