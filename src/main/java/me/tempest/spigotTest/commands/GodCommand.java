package me.tempest.spigotTest.commands;

import me.tempest.spigotTest.SpigotTest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1)
        {
            // find if it matches player name
            String playerName = args[0];
            Player target = SpigotTest.getPlugin().getServer().getPlayerExact(playerName);

            if (target == null)
            {
                commandSender.sendMessage("Player is not online");
            }
            else
            {
                setInvuln(target, commandSender);
            }
        }
        else if (args.length == 0)
        {
            if (commandSender instanceof Player player) {
                setInvuln(player, player);
            }
        }
        else {
            commandSender.sendMessage("Invalid number of arguments. /god <player>");
        }

        return true;
    }

    private void setInvuln(Player player, CommandSender sender)
    {
        if (sender.hasPermission("spigottest.god")) {
            if (player.isInvulnerable()) {
                player.setInvulnerable(false);
                player.sendMessage(ChatColor.RED + "God mode has been disabled.");
            } else {
                player.setInvulnerable(true);
                player.sendMessage(ChatColor.RED + "God mode has been enabled.");
            }
        }
    }
}
