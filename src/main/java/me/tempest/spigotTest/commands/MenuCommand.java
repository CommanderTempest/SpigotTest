package me.tempest.spigotTest.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MenuCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player player)
        {
            ItemStack flowers = new ItemStack(Material.FLOWERING_AZALEA, 2);
            player.getInventory().addItem(flowers);

            ItemStack food = new ItemStack(Material.BEEF, 8);
            ItemMeta foodMeta = food.getItemMeta();
            foodMeta.setDisplayName(ChatColor.RED + "The Red Meat of Glory");
            List<String> foodLore = new ArrayList<>();
            foodLore.add("Primeval Meat hunted from the great Devilsaur");
            foodLore.add("The Devilsaur is a great creature from the depths of the Crater");
            foodMeta.setLore(foodLore);
            food.setItemMeta(foodMeta);
            player.getInventory().addItem(food);

        }

        return true;
    }
}
