package me.tempest.spigotTest.commands;

import me.tempest.spigotTest.managers.NPCManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuestNPCCommand implements CommandExecutor {

    private final NPCManager npcManager;

    public QuestNPCCommand(NPCManager npcManager) {
        this.npcManager = npcManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player p)
        {
            npcManager.spawnQuestNPC(p);
        }

        return true;
    }
}
