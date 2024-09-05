package me.tempest.spigotTest.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class SpawnEntityCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Entity entity = Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), 0, 0,0 ), EntityType.TURTLE);
        entity.setCustomName("Green Rock");
        entity.setCustomNameVisible(true);
        return true;
    }
}
