package me.tempest.spigotTest.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        if (e.getWhoClicked() instanceof Player player)
        {
            var clickedItem = e.getCurrentItem();

            if (clickedItem.getType() == Material.FLOWERING_AZALEA)
            {
                player.sendMessage("Clicked the flower");
            }
        }
    }
}
