package me.jaime29010.townmusic.listeners;

import me.jaime29010.townmusic.Main;
import net.mcjukebox.plugin.bukkit.api.JukeboxAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final Main main;
    public PlayerQuitListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        JukeboxAPI.stopMusic(event.getPlayer());
    }
}
