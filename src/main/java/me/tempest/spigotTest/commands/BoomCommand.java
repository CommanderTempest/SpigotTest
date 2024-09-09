package me.tempest.spigotTest.commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class BoomCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {


        if (commandSender instanceof Player player)
        {

            ProtocolManager manager = ProtocolLibrary.getProtocolManager();

            player.getLineOfSight(null, 50).stream()
                    .filter(block -> block.getType() != Material.AIR)
                    .forEach(block -> {
                        Location blockLocation = block.getLocation();

                        PacketContainer container = manager.createPacket(PacketType.Play.Server.EXPLOSION);
                        container.getDoubles().write(0, blockLocation.getX());
                        container.getDoubles().write(1, blockLocation.getY());
                        container.getDoubles().write(2, blockLocation.getZ());

                        container.getFloat().write(0, 5.0f);
                        container.getFloat().write(1, 0f);
                        container.getFloat().write(2, 3.0f);
                        container.getFloat().write(3, 0f);

                        try {
                            manager.sendServerPacket(player, container);
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    });
        }

        return true;
    }
}
