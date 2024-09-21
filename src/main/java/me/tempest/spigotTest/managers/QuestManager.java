package me.tempest.spigotTest.managers;

import me.tempest.spigotTest.SpigotTest;
import me.tempest.spigotTest.commands.QuestNPCCommand;
import me.tempest.spigotTest.models.KillQuest;
import me.tempest.spigotTest.models.Quest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestManager {

    private final HashMap<Player, Quest> activeQuests;

    public QuestManager() {
        this.activeQuests = new HashMap<>();
    }

    public void giveQuest(Player p, Quest q)
    {
        q.setWhenStarted(System.currentTimeMillis());
        this.activeQuests.put(p,q);
    }

    public Quest getQuest(Player p)
    {
        return activeQuests.get(p);
    }

    public List<Quest> getAvailableQuests()
    {
        List<Quest> quests = new ArrayList<>();

        SpigotTest.getPlugin().getConfig().getConfigurationSection("quests.kill").getKeys(false).forEach(questName -> {
            String name = SpigotTest.getPlugin().getConfig().getString("quests.kill." + questName + ".name");
            String desc = SpigotTest.getPlugin().getConfig().getString("quests.kill." + questName + ".description");
            double reward = SpigotTest.getPlugin().getConfig().getDouble("quests.kill." + questName + ".reward");
            String entityType = SpigotTest.getPlugin().getConfig().getString("quests.kill." + questName + ".target.type");
            int count = SpigotTest.getPlugin().getConfig().getInt("quests.kill." + questName + ".target.count");

            EntityType entityTypeEnumerator = EntityType.valueOf(entityType);

            Quest quest = new KillQuest(name, desc, reward, entityTypeEnumerator, count);
            quests.add(quest);
        });
        return quests;
    }
}
