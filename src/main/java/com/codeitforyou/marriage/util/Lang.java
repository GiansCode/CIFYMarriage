package com.codeitforyou.marriage.util;

import com.codeitforyou.marriage.CIFYMarriage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public enum Lang {
    PREFIX("&a[&lMarriage&a]"),

    MAIN_COMMAND("{0} &7You're running &f{1} &7version &av{2} &7by &f{3}&7."),

    CHANNEL_COMMAND_USAGE("{0} &7Usage: &a/marry &7<&f{1}&7>."),

    ERROR_NO_PERMISSION_COMMAND("{0} &7You don't have permission to that command."),
    ERROR_PLAYER_ONLY("{0} &7That's a player only command."),
    ERROR_INVALID_COMMAND("{0} &7That's an invalid command, use &f/marriage help&7."),
    ERROR_INVALID_PLAYER("{0} &7That player couldn't be found."),
    ERROR_CANNOT_MARRY_SELF("{0} &7You cannot marry yourself!"),
    ERROR_ALREADY_MARRIED("{0} &7You're already married!"),
    ERROR_ALREADY_SENT_REQUEST("{0} &7Another player has already requested to marry them!"),
    ERROR_TARGET_ALREADY_MARRIED("{0} &7Eek, &a{1} &7is already married!"),
    ERROR_NOT_MARRIED("{0} &7You don't have a partner!"),

    MARRIAGE_REQUEST_SENT("{0} &7You've sent a marriage request to &a{1}&7. They have &2{2} &7seconds to accept!"),
    MARRIAGE_REQUEST_RECEIVED("{0} &7You've received a marriage request from &a{1}&7. You have &2{2} &7seconds to &f/marry [accept/deny]&7!"),
    MARRIAGE_HOME_SET("{0} &7Your marriage home has been set to your current location."),
    MARRIAGE_HOME_SET_OTHER("{0} &f{1} &7has changed your marriage home."),
    MARRIAGE_DIVORCE("{0} &7You and &a{1} &7are no longer married!"),
    MARRIAGE_ACCEPTED("{0} &7You and &a{1} &7are now married!"),
    MARRIAGE_DENIED_SENT("{0} &7You've denied a marriage request from &a{1}&7."),
    MARRIAGE_DENIED_RECEIVED("{0} &7Your married request to &a{1} &7has been denied."),
    MARRIAGE_HOME("{0} &7You've teleported to your marriage home."),
    MARRIAGE_NO_HOME_SET("{0} &7You don't have a marriage home set."),

    RELOADED("{0} &7Successfully reloaded config and lang files.")
    ;

    private static FileConfiguration c;
    private static File f;
    private String message;

    Lang(final String... def) {
        this.message = String.join("\n", def);
    }

    public static String format(String s, final Object... objects) {
        for (int i = 0; i < objects.length; ++i) {
            s = s.replace("{" + i + "}", String.valueOf(objects[i]));
        }
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static boolean init(CIFYMarriage marriage) {
        Lang.f = new File(marriage.getDataFolder() + "/" + "messages.yml");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Lang.c = YamlConfiguration.loadConfiguration(f);
        for (final Lang value : values()) {
            if (value.getMessage().split("\n").length == 1) {
                Lang.c.addDefault(value.getPath().toLowerCase(), value.getMessage());
            } else {
                Lang.c.addDefault(value.getPath().toLowerCase(), value.getMessage().split("\n"));
            }
        }
        Lang.c.options().copyDefaults(true);
        try {
            Lang.c.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private String getMessage() {
        return this.message;
    }

    public String getPath() {
        return this.name().toLowerCase().toLowerCase();
    }

    public void send(final Player player, final Object... args) {
        final String message = this.asString(args);
        Arrays.stream(message.split("\n")).forEach(player::sendMessage);
    }

    public void send(final CommandSender sender, final Object... args) {
        if (sender instanceof Player) {
            this.send((Player) sender, args);
        } else {
            Arrays.stream(this.asString(args).split("\n")).forEach(sender::sendMessage);
        }
    }

    public String asString(final Object... objects) {
        Optional<String> opt = Optional.empty();
        if (Lang.c.contains(this.getPath())) {
            if (Lang.c.isList(getPath())) {
                opt = Optional.of(String.join("\n", Lang.c.getStringList(this.getPath())));
            } else if (Lang.c.isString(this.getPath())) {
                opt = Optional.ofNullable(Lang.c.getString(this.getPath()));
            }
        }
        return this.format(opt.orElse(this.message), objects);
    }
}

