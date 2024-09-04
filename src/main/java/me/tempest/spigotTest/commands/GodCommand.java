package me.tempest.spigotTest.commands;

import me.tempest.spigotTest.SpigotTest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class GodCommand implements CommandExecutor {

    // key == UUID of player
    // long = epoch time of when they ran the command
    private final HashMap<UUID, Long> cooldown;

    private final long duration = 2000;

    public GodCommand()
    {
        this.cooldown = new HashMap<>();
    }

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
                if (!this.cooldown.containsKey(player.getUniqueId()))
                {
                    this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                    setInvuln(player, player);
                }
                else {
                    long timeElapsed = System.currentTimeMillis() - this.cooldown.get(player.getUniqueId());
                    if (timeElapsed > this.duration)
                    {
                        setInvuln(player, player);
                        this.cooldown.replace(player.getUniqueId(), System.currentTimeMillis());
                    }
                    else {
                        player.sendMessage("You cannot use that command again for another " + (this.duration - timeElapsed) + " milliseconds!");
                    }
                }
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
