package com.codeitforyou.marriage.commands;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.marriage.CIFYMarriage;
import com.codeitforyou.marriage.api.Marriage;
import com.codeitforyou.marriage.api.MarriageAPI;
import com.codeitforyou.marriage.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class HomeCommand {

    @Command(aliases = {"home"}, usage = "home", permission = "marriage.use", title = "Teleport Home", about = "Move to your marriage home.")
    public static void execute(Player sender, CIFYMarriage plugin, String[] args) {
        Marriage marriage = MarriageAPI.getMarriage(sender);

        if (marriage == null) {
            // Not married!
            Lang.ERROR_NOT_MARRIED.send(sender, Lang.PREFIX.asString());
            return;
        }


        Location marriageHome = marriage.getHome();

        if (marriageHome == null) {
            Lang.MARRIAGE_NO_HOME_SET.send(sender, Lang.PREFIX.asString());
            return;
        }

        sender.teleport(marriageHome);
        Lang.MARRIAGE_HOME.send(sender, Lang.PREFIX.asString());
    }
}
