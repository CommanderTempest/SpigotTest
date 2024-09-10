package me.tempest.spigotTest.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SpawnHologram implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player)
        {
            ArmorStand hologram = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
            hologram.setVisible(false);
            hologram.setCustomNameVisible(true);
            hologram.setCustomName(ChatColor.RED + "Test Hologram");
            hologram.setGravity(false);
        }

        return true;
    }
}
