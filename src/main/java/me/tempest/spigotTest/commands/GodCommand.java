package me.tempest.spigotTest.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {

            if (player.hasPermission("spigottest.god")) {
                if (player.isInvulnerable()) {
                    player.setInvulnerable(false);
                    player.sendMessage(ChatColor.RED + "God mode has been disabled.");
                } else {
                    player.setInvulnerable(true);
                    player.sendMessage(ChatColor.RED + "God mode has been enabled.");
                }
            }
        }

        return true;
    }
}
