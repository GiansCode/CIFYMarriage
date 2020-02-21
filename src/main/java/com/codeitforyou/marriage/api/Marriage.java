package com.codeitforyou.marriage.api;

import org.bukkit.Location;

import java.util.UUID;

public class Marriage {

    private UUID partner1;
    private UUID partner2;
    private Location home;
    private Long date;

    public Marriage(final UUID partner1, final UUID partner2, final Location home, final Long date) {
        this.partner1 = partner1;
        this.partner2 = partner2;
        this.home = home;
        this.date = date;
    }

    public UUID getPartner1() {
        return partner1;
    }

    public UUID getPartner2() {
        return partner2;
    }

    public Location getHome() {
        return home;
    }

    public Long getDate() {
        return date;
    }

    public void setPartner1(final UUID partner1) {
        this.partner1 = partner1;
    }

    public void setPartner2(final UUID partner2) {
        this.partner2 = partner2;
    }

    public void setHome(final Location home) {
        this.home = home;
    }

    public void setDate(final Long date) {
        this.date = date;
    }
}
