package me.tempest.spigotTest.commands;

import me.tempest.spigotTest.SpigotTest;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player player)
        {
            Location location = player.getLocation();

            SpigotTest plugin = SpigotTest.getPlugin();
            plugin.getConfig().set("spawn", location);
            plugin.getConfig().set("spawn.worldName", location.getWorld().getName());
            plugin.saveConfig();
            player.sendMessage("Spawn location set!");
        }

        return true;
    }
}
