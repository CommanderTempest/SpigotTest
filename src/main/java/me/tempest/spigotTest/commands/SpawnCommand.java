package me.tempest.spigotTest.commands;

import me.tempest.spigotTest.SpigotTest;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player)
        {
            Location location = SpigotTest.getPlugin().getConfig().getLocation("spawn");
            if (location != null)
            {
                player.teleport(location);
                player.sendMessage("Teleported to " + location);
            }
            else {
                player.sendMessage("No spawn point, use /setspawn to set it first");
            }
        }
        return true;
    }
}
