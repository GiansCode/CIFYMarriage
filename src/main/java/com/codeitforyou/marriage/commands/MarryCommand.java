package com.codeitforyou.marriage.commands;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.marriage.CIFYMarriage;
import com.codeitforyou.marriage.api.Marriage;
import com.codeitforyou.marriage.api.MarriageAPI;
import com.codeitforyou.marriage.util.Lang;
import com.extendedclip.deluxemenus.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MarryCommand {

    @Command(usage = "marry [player]", permission = "marriage.use", title = "Marry a Player", about = "Marry a player.")
    public static void execute(Player sender, CIFYMarriage plugin, String[] args) {
        Marriage marriage = MarriageAPI.getMarriage(sender);
        
        if (args.length == 0) {
            if (marriage == null) {
                Menu menu = Menu.getMenu(plugin.getConfig().getString("menus.notMarried"));
                if (menu != null)
                    menu.openMenu(sender, sender, null);
                return;
            }

            UUID partner = marriage.getPartner1().equals(sender.getUniqueId()) ? marriage.getPartner2() : marriage.getPartner1();
            Player partnerPlayer = Bukkit.getPlayer(partner);
            
            Menu menu = Menu.getMenu(plugin.getConfig().getString("menus.married"));
            if (menu != null)
                menu.openMenu(sender, partnerPlayer != null ? partnerPlayer : sender, null);
            return;
        }
        
        if (marriage != null) {
            // Already married!
            Lang.ERROR_ALREADY_MARRIED.send(sender, Lang.PREFIX.asString());
            return;
        }


        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            // Invalid player!
            Lang.ERROR_INVALID_PLAYER.send(sender, Lang.PREFIX.asString());
            return;
        }

        if (MarriageAPI.getPartner(target) != null) {
            // Already married!
            Lang.ERROR_TARGET_ALREADY_MARRIED.send(sender, Lang.PREFIX.asString(), target.getName());
            return;
        }

        if (sender.getUniqueId() == target.getUniqueId()) {
            // Can't marry self!
            Lang.ERROR_CANNOT_MARRY_SELF.send(sender, Lang.PREFIX.asString());
            return;
        }

        if (plugin.getMarriageManager().getRequests().getIfPresent(target.getUniqueId()) != null) {
            // Already sent a request!
            Lang.ERROR_ALREADY_SENT_REQUEST.send(sender, Lang.PREFIX.asString());
            return;
        }
        
        // Can marry.
        Lang.MARRIAGE_REQUEST_SENT.send(sender, Lang.PREFIX.asString(), target.getName(), plugin.getConfig().getInt("settings.time"));
        Lang.MARRIAGE_REQUEST_RECEIVED.send(target, Lang.PREFIX.asString(), sender.getName(), plugin.getConfig().getInt("settings.time"));
        plugin.getMarriageManager().getRequests().put(target.getUniqueId(), sender.getUniqueId());
    }
}
