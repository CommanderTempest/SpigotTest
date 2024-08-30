package me.tempest.spigotTest;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

// https://hub.spigotmc.org/javadocs/bukkit/

public final class SpigotTest extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("First Plugin has started");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Plugin has shut down");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        playerJoinEvent.setJoinMessage("Hello, " + playerJoinEvent.getPlayer().getName());
        System.out.println("A player has joined");

    }

    @EventHandler
    public void onPlayerLeaveBed(PlayerBedLeaveEvent event) {
        String playerName = event.getPlayer().getName();
        getServer().broadcastMessage("Go back to sleep " + playerName + ", the night is dark and full of terrors.");
    }


}
