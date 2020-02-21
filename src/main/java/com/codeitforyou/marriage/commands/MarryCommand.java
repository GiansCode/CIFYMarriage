package com.codeitforyou.marriage.commands;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.lib.api.general.StringUtil;
import com.codeitforyou.marriage.CIFYMarriage;
import com.codeitforyou.marriage.api.Marriage;
import com.codeitforyou.marriage.api.MarriageAPI;
import com.codeitforyou.marriage.manager.PluginCmdManager;
import com.codeitforyou.marriage.util.CommandUtil;
import com.codeitforyou.marriage.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MarryCommand {

    @Command(usage = "marry [player]", permission = "marriage.use", title = "Marry a Player", about = "Marry a player.")
    public static void execute(Player sender, CIFYMarriage plugin, String[] args) {

        if (args.length == 0) {
            final List<Method> commandMethods = PluginCmdManager.getCommandManager().getCommands().values().stream().distinct().collect(Collectors.toList());

            sender.sendMessage(" ");
            sender.sendMessage(StringUtil.translate("&a[&l" + "Marriage" + "&a] &7v" + plugin.getDescription().getVersion() + " &8- &aHelp Menu"));
            sender.sendMessage(" ");

            if (sender instanceof ConsoleCommandSender) sender.sendMessage(" ");
            for (final Method commandMethod : commandMethods) {
                final Command command = commandMethod.getAnnotation(Command.class);
                if (!sender.hasPermission(command.permission())) continue;
                CommandUtil.getCommandFormat(sender, "marry", command.aliases()[0], command.title(), command.about(), command.usage(), command.permission());

            }
            CommandUtil.getCommandFormat(sender, "marry", "", "Help Menu / Marriage Request", "View help menu or send a marry request.", "[player]", "marriage.use");

            sender.sendMessage(" ");
            sender.sendMessage(StringUtil.translate("&a[&l" + "Marriage" + "&a] &7You're married to &a" + (MarriageAPI.isMarried(sender) ? Bukkit.getOfflinePlayer(MarriageAPI.getPartner(sender)).getName() : "Nobody") + "&7."));
            return;
        }

        Marriage marriage = MarriageAPI.getMarriage(sender);

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
