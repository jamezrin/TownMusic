package me.jaime29010.townmusic;

import com.palmergames.bukkit.towny.object.Town;
import me.jaime29010.townmusic.commands.TownMusicExecutor;
import me.jaime29010.townmusic.listeners.PlayerChangePlotListener;
import me.jaime29010.townmusic.listeners.PlayerJoinListener;
import me.jaime29010.townmusic.listeners.PlayerQuitListener;
import me.jaime29010.townmusic.utils.ConfigurationManager;
import net.mcjukebox.plugin.bukkit.api.ResourceType;
import net.mcjukebox.plugin.bukkit.api.models.Media;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Main extends JavaPlugin {
    private Permission provider = null;
    private FileConfiguration config = null;
    private ConfigurationSection section = null;
    private Media defaultSong = null;
    private final Map<String, Media> songs = Collections.synchronizedMap(new HashMap<String, Media>());

    @Override
    public void onEnable() {
        config = ConfigurationManager.loadConfig("config.yml", this);
        section = config.getConfigurationSection("songs");
        for (String key : section.getKeys(false)) {
            songs.put(key, new Media(ResourceType.MUSIC, section.getString(key)));
        }

        String link = config.getString("default-song");
        if (isValidLink(link)) {
            defaultSong = new Media(ResourceType.MUSIC, link);
        }

        ServicesManager manager = getServer().getServicesManager();
        RegisteredServiceProvider<Permission> service = manager.getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (service != null) {
            provider = service.getProvider();
        }

        getServer().getPluginManager().registerEvents(new PlayerChangePlotListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getCommand("townmusic").setExecutor(new TownMusicExecutor(this));
    }

    @Override
    public void onDisable() {
        for (Entry<String, Media> entry : songs.entrySet()) {
            Media media = entry.getValue();
            section.set(entry.getKey(), media.getURL());
        }
        ConfigurationManager.saveConfig(config, "config.yml", this);
    }

    public boolean isValidLink(String link) {
        if (link == null) {
            return false;
        }
        return link.matches("(?:([^:/?#]+):)?(?://([^/?#]*))?([^?#]*\\.(?:mp3))(?:\\?([^#]*))?(?:#(.*))?");
    }

    public boolean hasSong(Town town) {
        return songs.containsKey(town.getName());
    }

    public Media getSong(Town town) {
        return songs.get(town.getName());
    }

    public void setSong(Town town, String link) {
        songs.put(town.getName(), new Media(ResourceType.MUSIC, link));
    }

    public void removeSong(Town town) {
        songs.remove(town.getName());
    }

    public Media getDefaultSong() {
        return defaultSong;
    }

    public boolean hasPermission(Player player, String permission) {
        return provider.has(player, permission);
    }

    public void reloadPlugin() {
        config = ConfigurationManager.loadConfig("config.yml", this);

        String link = config.getString("default-song");
        if (isValidLink(link)) {
            defaultSong = new Media(ResourceType.MUSIC, link);
        }
    }

    public boolean hasDefaultSong() {
        return defaultSong != null;
    }
}
