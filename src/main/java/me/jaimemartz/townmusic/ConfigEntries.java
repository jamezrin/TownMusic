package me.jaimemartz.townmusic;

import me.jaimemartz.faucet.ConfigEntry;
import me.jaimemartz.faucet.ConfigEntryHolder;

public class ConfigEntries implements ConfigEntryHolder {
    public static final ConfigEntry<Integer> FADE_DURATION = new ConfigEntry<>(0, "fade-duration", -1);
    public static final ConfigEntry<TownMedia> TOWN_MEDIA = new ConfigEntry<>(0, "town-media", new TownMedia());
}
