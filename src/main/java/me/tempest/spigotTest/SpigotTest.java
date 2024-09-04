package me.tempest.spigotTest;

import me.tempest.spigotTest.commands.GodCommand;
import me.tempest.spigotTest.commands.MenuCommand;
import me.tempest.spigotTest.commands.SetSpawn;
import me.tempest.spigotTest.commands.SpawnCommand;
import me.tempest.spigotTest.listeners.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

// https://hub.spigotmc.org/javadocs/bukkit/

/**
**TODO
 * Look up custom inventories, custom items, how to make, etc.
 * Look up custom entities
 * Take a look at how holograms are done
 **/

public final class SpigotTest extends JavaPlugin implements Listener {

    private static SpigotTest plugin;

    @Override
    public void onEnable() {
        plugin = this;
        // config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Plugin startup logic
        System.out.println("First Plugin has started");
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new XPBottleBreakListener(), this);
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new SpawnListeners(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getCommand("god").setExecutor(new GodCommand());
        getCommand("setspawn").setExecutor(new SetSpawn());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("menu").setExecutor(new MenuCommand());


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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // /die = kill sending player
        if (command.getName().equalsIgnoreCase("die"))
        {
            if (sender instanceof Player player)
            {
                player.setHealth(0);
                player.sendMessage("The Stranger has come for you.");
            }
            else if (sender instanceof ConsoleCommandSender) {
                System.out.println("Die successfully ran by Console");
            }
        }
        return true;
    }

    public static SpigotTest getPlugin()
    {
        return plugin;
    }
}
