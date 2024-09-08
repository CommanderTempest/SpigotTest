package me.tempest.spigotTest.listeners;

import me.tempest.spigotTest.events.GameEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameListeners implements Listener {
    @EventHandler
    public void onGameEnd(GameEndEvent e)
    {
        Bukkit.getServer().broadcastMessage("Game has ended, winner is " + e.getPlayer().getName());
    }
}
