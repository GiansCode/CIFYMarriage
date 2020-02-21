package com.codeitforyou.marriage.commands;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.marriage.CIFYMarriage;
import com.codeitforyou.marriage.api.Marriage;
import com.codeitforyou.marriage.api.MarriageAPI;
import com.codeitforyou.marriage.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class SetHomeCommand {

    @Command(aliases = {"sethome"}, usage = "sethome", permission = "marriage.use", title = "Set Home", about = "Set a marriages home.")
    public static void execute(Player sender, CIFYMarriage plugin, String[] args) {
        Marriage marriage = MarriageAPI.getMarriage(sender);

        if (marriage == null) {
            // Not married!
            Lang.ERROR_NOT_MARRIED.send(sender, Lang.PREFIX.asString());
            return;
        }

        OfflinePlayer partner1 = Bukkit.getOfflinePlayer(marriage.getPartner1());
        OfflinePlayer partner2 = Bukkit.getOfflinePlayer(marriage.getPartner2());

        if (partner1.isOnline()) {
            if (partner1.getUniqueId() == sender.getUniqueId()) {
                Lang.MARRIAGE_HOME_SET.send(Bukkit.getPlayer(marriage.getPartner1()), Lang.PREFIX.asString());
            } else {
                Lang.MARRIAGE_HOME_SET_OTHER.send(Bukkit.getPlayer(marriage.getPartner1()), Lang.PREFIX.asString(), partner2.getName());
            }
        }

        if (partner2.isOnline()) {
            if (partner2.getUniqueId() == sender.getUniqueId()) {
                Lang.MARRIAGE_HOME_SET.send(Bukkit.getPlayer(marriage.getPartner2()), Lang.PREFIX.asString());
            } else {
                Lang.MARRIAGE_HOME_SET_OTHER.send(Bukkit.getPlayer(marriage.getPartner2()), Lang.PREFIX.asString(), partner1.getName());
            }
        }


        marriage.setHome(sender.getLocation());
    }
}
