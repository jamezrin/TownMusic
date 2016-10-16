package me.jaimemartz.townmusic;

import me.jaimemartz.faucet.ConfigObject;
import net.mcjukebox.plugin.bukkit.api.ResourceType;
import net.mcjukebox.plugin.bukkit.api.models.Media;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class TownMedia implements ConfigObject {
    private final Map<String, Media> map = new HashMap<>();

    @Override
    public void get(ConfigurationSection section) {
        section.getKeys(false).forEach(key -> map.put(key, createMedia(section.getString(key))));
    }

    @Override
    public void set(ConfigurationSection section, boolean first) {
        map.forEach((key, value) -> section.set(key, value.getURL()));
    }

    public boolean hasSong(String name) {
        return map.containsKey(name);
    }

    public Media getSong(String name) {
        return map.get(name);
    }

    public void setSong(String name, String link) {
        map.put(name, createMedia(link));
    }

    public void removeSong(String name) {
        map.remove(name);
    }

    private Media createMedia(String link) {
        Media media = new Media(ResourceType.MUSIC, link);
        media.setFadeDuration(ConfigEntries.FADE_DURATION.get());
        return media;
    }
}
