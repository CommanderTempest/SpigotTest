package me.tempest.spigotTest.listeners;

import me.tempest.spigotTest.SpigotTest;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SpawnListeners implements Listener {
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e)
    {
        // when player dies, respawn at set spawn event that was set using /setspawn
        Location location = SpigotTest.getPlugin().getConfig().getLocation("spawn");
        if (location != null)
        {
            e.setRespawnLocation(location);
        }
    }
}
