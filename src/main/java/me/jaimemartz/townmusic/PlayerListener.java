package me.jaimemartz.townmusic;

import com.palmergames.bukkit.towny.event.PlayerChangePlotEvent;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import net.mcjukebox.plugin.bukkit.api.JukeboxAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final TownMusic plugin;
    public PlayerListener(TownMusic plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(PlayerChangePlotEvent event) {
        Player player = event.getPlayer();
        Town town = TownyUtils.getTown(event.getTo());
        if (town == null) return;

        if (TownyUtils.townEquals(TownyUtils.getTown(event.getFrom()), town)) return;
        plugin.startSong(player, town);
    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        TownBlock block = TownyUniverse.getTownBlock(player.getLocation());
        if (block == null) return;

        Town town = TownyUtils.getTown(block);
        plugin.startSong(player, town);
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        JukeboxAPI.stopMusic(event.getPlayer());
    }
}
