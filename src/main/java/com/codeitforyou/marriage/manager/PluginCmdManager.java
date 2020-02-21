package com.codeitforyou.marriage.manager;

import com.codeitforyou.lib.api.command.CommandManager;
import com.codeitforyou.marriage.CIFYMarriage;
import com.codeitforyou.marriage.commands.*;
import com.codeitforyou.marriage.util.Lang;

import java.util.Arrays;

public class PluginCmdManager {
    private static CommandManager commandManager;

    public void register() {
        commandManager = new CommandManager(Arrays.asList(
                DivorceCommand.class,
                SetHomeCommand.class,
                AcceptCommand.class,
                DenyCommand.class,
                HomeCommand.class,
                ReloadCommand.class
        ), CIFYMarriage.getPlugin().getName().toLowerCase(), CIFYMarriage.getPlugin());
        commandManager.setMainCommand(MarryCommand.class);
        commandManager.setMainCommandArgs(true);


        for (String alias : Arrays.asList("marry", "marriages", "date")) {
            commandManager.addAlias(alias);
        }

        commandManager.register();
        commandManager.getLocale().setNoPermission(Lang.ERROR_NO_PERMISSION_COMMAND.asString(Lang.PREFIX.asString()));
        commandManager.getLocale().setUnknownCommand("&a[&l" + "Marriage" + "&a] &7Unknown sub-command, use &a/marriage help&7.");
        commandManager.getLocale().setPlayerOnly(Lang.ERROR_PLAYER_ONLY.asString(Lang.PREFIX.asString()));
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }
}
