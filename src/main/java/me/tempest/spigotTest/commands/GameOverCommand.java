package me.tempest.spigotTest.commands;

import me.tempest.spigotTest.events.GameEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameOverCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player)
        {
            if (strings.length == 1)
            {
                Player target = Bukkit.getPlayer(strings[0]);
                Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(player, target, 1));
            }
        }
        return true;
    }
}
