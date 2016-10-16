package me.jaimemartz.townmusic;

import com.palmergames.bukkit.towny.object.Town;
import me.jaimemartz.faucet.ConfigObject;
import net.mcjukebox.plugin.bukkit.api.ResourceType;
import net.mcjukebox.plugin.bukkit.api.models.Media;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class TownMedia implements ConfigObject {
    private final Map<String, Media> map = new HashMap<>();

    @Override
    public void load(ConfigurationSection section) {
        section.getKeys(false).forEach(key -> map.put(key, createMedia(section.getString(key))));
    }

    @Override
    public void save(ConfigurationSection section, boolean first) {
        map.forEach((key, value) -> section.set(key, value.getURL()));
    }

    public boolean hasSong(Town town) {
        return map.containsKey(town.getName());
    }

    public Media getSong(Town town) {
        return map.get(town.getName());
    }

    public void setSong(Town town, String link) {
        map.put(town.getName(), createMedia(link));
    }

    public void removeSong(Town town) {
        map.remove(town.getName());
    }

    public Media getDefaultSong() {
        return map.get(null);
    }

    public boolean hasDefaultSong() {
        return map.get(null) != null;
    }

    private Media createMedia(String link) {
        Media media = new Media(ResourceType.MUSIC, link);
        media.setFadeDuration(ConfigEntries.FADE_DURATION.get());
        return media;
    }
}
