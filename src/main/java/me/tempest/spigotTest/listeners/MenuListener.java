package me.tempest.spigotTest.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        // Player clicking custom 'Menu' inventory
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Custom UI"))
        {
            if (e.isRightClick()) {return;}

            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();
            switch (e.getCurrentItem().getType())
            {
                case BREAD:
                    player.setFoodLevel(20);
                    player.sendMessage("Capped Hunger Level");
                    break;
                case TNT:
                    player.setHealth(0);
                    player.sendMessage("The reaper has come to claim your soul");
                    break;
            }
        }
    }
}
