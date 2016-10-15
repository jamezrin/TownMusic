package me.jaime29010.townmusic.listeners;

import com.palmergames.bukkit.towny.event.PlayerChangePlotEvent;
import com.palmergames.bukkit.towny.object.Town;
import me.jaime29010.townmusic.Main;
import me.jaime29010.townmusic.utils.TownyUtils;
import net.mcjukebox.plugin.bukkit.api.JukeboxAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerChangePlotListener implements Listener {
    private final Main main;
    public PlayerChangePlotListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerChangePlot(PlayerChangePlotEvent event) {
        Player player = event.getPlayer();
        Town town = TownyUtils.getTown(event.getTo());
        if (TownyUtils.townEquals(TownyUtils.getTown(event.getFrom()), town)) return;
        if (town != null && main.hasSong(town)) {
            JukeboxAPI.play(player, main.getSong(town));
        } else if (main.hasDefaultSong()) {
            JukeboxAPI.play(player, main.getDefaultSong());
        } else {
            JukeboxAPI.stopMusic(player);
        }
    }
}
