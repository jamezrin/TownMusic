package me.jaimemartz.townmusic;

import com.palmergames.bukkit.towny.object.Town;
import me.jaimemartz.faucet.ConfigFactory;
import net.mcjukebox.plugin.bukkit.api.JukeboxAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TownMusic extends JavaPlugin {
    private ConfigFactory factory;
    private TownMedia media;

    @Override
    public void onEnable() {
        if (factory == null) {
            factory = new ConfigFactory(this);
            factory.register(0, "config.yml");
            factory.submit(ConfigEntries.class);
        }

        loadPlugin();

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getCommand("townmusic").setExecutor(new TownMusicExecutor(this));
    }

    @Override
    public void onDisable() {
        factory.save(0);
    }

    public void loadPlugin() {
        factory.load(0, false);
        media = ConfigEntries.TOWN_MEDIA.get();
    }

    public boolean isValidLink(String link) {
        if (link == null) {
            return false;
        }
        return link.matches("(?:([^:/?#]+):)?(?://([^/?#]*))?([^?#]*\\.(?:mp3))(?:\\?([^#]*))?(?:#(.*))?");
    }

    public void playMusic(Player player, Town town) {
        if (town != null) {
            if (media.hasSong(town.getName())) {
                JukeboxAPI.play(player, media.getSong(town.getName()));
            }
        } else if (media.hasSong(null)) {
            JukeboxAPI.play(player, media.getSong(null));
        } else {
            JukeboxAPI.stopMusic(player);
        }
    }

    public TownMedia getMedia() {
        return media;
    }
}
