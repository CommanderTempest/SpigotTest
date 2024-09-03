package me.tempest.spigotTest.listeners;

import me.tempest.spigotTest.SpigotTest;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();

        e.setQuitMessage(ChatColor.GREEN + "" + ChatColor.BOLD + player.getDisplayName() + " has left.");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();

        if (player.hasPlayedBefore())
        {
            // load last played class woh

            player.sendMessage(ChatColor.YELLOW + "Welcome back " + player.getDisplayName());
            Location location = SpigotTest.getPlugin().getConfig().getLocation("spawn");
            if (location != null)
            {
                player.teleport(location);
            }
        }
        else
        {
            // load class creation/selection screen
        }
    }
}
