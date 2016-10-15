package me.jaime29010.townmusic.listeners;

import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import me.jaime29010.townmusic.Main;
import me.jaime29010.townmusic.utils.TownyUtils;
import net.mcjukebox.plugin.bukkit.api.JukeboxAPI;
import net.mcjukebox.plugin.bukkit.api.models.Media;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final Main main;
    public PlayerJoinListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        TownBlock block = TownyUniverse.getTownBlock(player.getLocation());
        if (block == null) return;

        Town town = TownyUtils.getTown(block);
        if (town != null && main.hasSong(town)) {
            Media media = main.getSong(town);
            JukeboxAPI.play(player, media);
        } else if (main.hasDefaultSong()) {
            JukeboxAPI.play(player, main.getDefaultSong());
        } else {
            JukeboxAPI.stopMusic(player);
        }
    }
}
