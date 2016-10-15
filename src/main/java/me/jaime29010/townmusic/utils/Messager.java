package me.jaime29010.townmusic.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public final class Messager {
    private final CommandSender sender;
    public Messager(CommandSender sender) {
        this.sender = sender;
    }

    public Messager send(String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        return this;
    }

    public Messager send(String... messages) {
        for (String message : messages) {
            send(message);
        }
        return this;
    }
}