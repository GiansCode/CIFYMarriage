package com.codeitforyou.marriage.util;

import com.codeitforyou.lib.api.general.JSONMessage;
import com.codeitforyou.lib.api.general.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CommandUtil {
    public static void getCommandFormat(final CommandSender commandSender, final String mainCommand, final String command, final String commandTitle, final String commandDesc, String commandUsage, final String commandPerm) {
        final String commandStr = !command.isEmpty() ? " " + command : "";

        if (commandSender instanceof Player) {

            if (commandUsage.equalsIgnoreCase(command)) {
                commandUsage = "";
            } else if (!command.isEmpty()) {
                commandUsage = commandUsage.replace(command + " ", "");
            }

            List<String> helpTooltip = new LinkedList<>(Arrays.asList("&a&l" + commandTitle,
                    "&f" + commandDesc,
                    "&7",
                    "&7/" + mainCommand + commandStr + " &2" + commandUsage,
                    "&7"
            ));

            if (commandSender.hasPermission("marriage.admin")) {
                helpTooltip.add("&8Requires &n" + commandPerm + "&8 to use!");
            }

            JSONMessage.create(StringUtil.translate(" &a/" + mainCommand + commandStr))
                    .tooltip(StringUtil.translate(StringUtils.join(helpTooltip, "\n")))
                    .suggestCommand("/" + mainCommand + commandStr).send((Player) commandSender);
        } else {
            commandSender.sendMessage("/" + mainCommand + " " + commandUsage + " - " + commandDesc);
        }
    }
}
