package me.tempest.spigotTest.menus;

import me.tempest.spigotTest.SpigotTest;
import me.tempest.spigotTest.managers.QuestManager;
import me.tempest.spigotTest.models.KillQuest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class QuestMenu{

    private final QuestManager questManager;

    public QuestMenu(QuestManager questManager) {
        this.questManager = SpigotTest.getPlugin().getQuestManager();
    }

    public void createInventory(Player e)
    {
        Inventory inventory = Bukkit.createInventory(e, this.getSlots(), ChatColor.BLUE + "Quest Menu");
    }

    public int getSlots()
    {
        return 54;
    }

    public void handleMenu(InventoryClickEvent e)
    {

    }

    public void setMenuItems()
    {
        questManager.getAvailableQuests().forEach(quest -> {
            ItemStack item;
            if (quest instanceof KillQuest killQuest)
            {
                boolean isOnQuest = false;

                item = makeItem(Material.DIAMOND_SWORD,
                        killQuest.getName(),
                        killQuest.getDescription(),
                        "", killQuest.getReward(),
                        "",
                        (isOnQuest ? "&cYou are on this quest!" : "&aClick to accept!"));
            }
        });
    }
}
