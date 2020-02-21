package com.codeitforyou.marriage.api;

import com.codeitforyou.marriage.CIFYMarriage;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class MarriageAPI {
    private static final CIFYMarriage PLUGIN = CIFYMarriage.getPlugin();

    public static Marriage getMarriage(Player player) {

        Set<Marriage> marriages = PLUGIN.getMarriageManager().getMarriages();

        for (Marriage marriage : marriages) {
            if (marriage.getPartner1().equals(player.getUniqueId()) || marriage.getPartner2().equals(player.getUniqueId())) {
                return marriage;
            }
        }

        return null;
    }


    /**
     * Check if a player is married.
     *
     * @param player
     * @return whether player is married or not.
     */
    public static boolean isMarried(Player player) {
        return getMarriage(player) != null;
    }

    /**
     * Get the players current partner (if they have one).
     *
     * @param player
     * @return Current partner.
     */
    public static UUID getPartner(Player player) {
        Marriage marriage = getMarriage(player);
        if (marriage != null) {
            if (marriage.getPartner1().equals(player.getUniqueId())) {
                return marriage.getPartner2();
            } else if (marriage.getPartner2().equals(player.getUniqueId())) {
                return marriage.getPartner1();
            }
        }

        return null;
    }

    /**
     * Get the time the player got married at.
     *
     * @param player
     * @return Time of marriage.
     */
    public static Long getTimeMarried(Player player) {
        Set<Marriage> marriages = PLUGIN.getMarriageManager().getMarriages();

        for (Marriage marriage : marriages) {

            if (marriage.getPartner1().equals(player.getUniqueId()) || marriage.getPartner2().equals(player.getUniqueId())) {
                return marriage.getDate();
            }
        }

        return null;
    }

    /**
     * Create a marriage.
     *
     * @param player
     * @param target
     */
    public static void setMarried(Player player, Player target) {
        if (getPartner(player) == null && getPartner(target) == null) {
            PLUGIN.getMarriageManager().getMarriages().add(new Marriage(player.getUniqueId(), target.getUniqueId(), null, System.currentTimeMillis()));
        }
    }

    /**
     * Create a marriage.
     *
     * @param player
     * @param target
     * @param time
     */
    public static void setMarried(Player player, Player target, Long time) {
        if (getPartner(player) == null && getPartner(target) == null) {
            PLUGIN.getMarriageManager().getMarriages().add(new Marriage(player.getUniqueId(), target.getUniqueId(), null, time));
        }
    }

    /**
     * Force a divorce.
     *
     * @param player
     */
    public static void forceDivorce(Player player) {
        Marriage marriage = getMarriage(player);

        if (marriage != null) {
            PLUGIN.getMarriageManager().getMarriages().remove(marriage);
        }
    }
}
