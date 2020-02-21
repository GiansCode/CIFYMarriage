package com.codeitforyou.marriage.commands;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.marriage.CIFYMarriage;
import com.codeitforyou.marriage.api.Marriage;
import com.codeitforyou.marriage.api.MarriageAPI;
import com.codeitforyou.marriage.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class ReloadCommand {

    @Command(aliases = {"reload"}, usage = "reload", permission = "marriage.reload", title = "Reload Plugin", about = "Update the plugin config.")
    public static void execute(Player sender, CIFYMarriage plugin, String[] args) {
        plugin.reloadConfig();
        Lang.init(plugin);
        Lang.RELOADED.send(sender, Lang.PREFIX.asString());
    }
}
