package me.tempest.spigotTest.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Pair;
import me.tempest.spigotTest.SpigotTest;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.EquipmentSlot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_21_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CreateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player player)
        {
            CraftPlayer craftPlayer = (CraftPlayer) player;
            ServerPlayer sp = craftPlayer.getHandle();

            MinecraftServer server = sp.getServer();
            ServerLevel level = sp.serverLevel();
            GameProfile profile = new GameProfile(UUID.randomUUID(), "Tom");

            ServerPlayer npc = new ServerPlayer(server, level, profile, sp.clientInformation());
            npc.setPos(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());

            String signature = "D0uWzO3V+VVvcOTQgP6JRIAbqtkyDwbBr2pYwy44XSr5mGftXn9IwHHnQZ5/3aXFUH3m1ldTZ7Gjtf2RXkTPxZ45h7WOSYE/sksdyMff0s/HNCR5UmbkbTveioT+irvy1Lw++1a2aNdKa+Hp61KUw14byd5Bmsiv1rf+ULW9NIcQmUKi0fQZwZzGZO12w8Kzmyygj6dEcz6VaCxHDJpyAKt0mQFlql1ANOcCJ7zB5hiHHTW/F72jfORZWq3nLikVccG4yYgZH7Lsfkzvc0PRgccNHVz6LDS519fljEi3N9hdq1AhwKLiMoxDFbRFI7aLVTuY6yX9+dQLdlWx2xsk3KPeslK1smXHbuauO9cfOr60TyhgW60PrvhxvH0pV4BlO9r5PbUFxszyGmos7VB2cINlf+iol7tl9tYWeLhmsW7Fmx8uN02p8rMIinaJahx6m4w8GW4NWY4snkDjC9AUSeMpLCd5GzChflf0EQQCyDoBDyrVLiMM0L/yTfgg2ba3KWmsYVqtTu0lPJDyihNpk9YoBpAgHAkq4/NxdHk9nXksrXWT/3JLY4JY2oEP1wkHrWX3bXVyuQbBBA3oT5imXf73CwO6Whe3nWPGkjESDaHK5WPLXeWkTiJ7RozMigTKu6c+MYLlNh/s4JaqpjY1XMiKnLDw2Jpu5gfTXI+YCpg=";
            String texture = "ewogICJ0aW1lc3RhbXAiIDogMTcyNjI0MDcwODMyNSwKICAicHJvZmlsZUlkIiA6ICIwNjkxOGIxNjVhNjQ0MjdlYjE3N2Y2YmUwYmViYjMzMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJTYWx0ZWRfIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2RmODFlNTY2YTQ5ZTk0MzczM2QxYmRkMjY5MDM2MzVkYmNjZGU2ZjdiMDMyMjE5YjYzNzZjZjU2ZWYyZjFiMTAiCiAgICB9CiAgfQp9";

            profile.getProperties().put("textures", new Property("textures", texture, signature));

            SynchedEntityData synchedEntityData = npc.getEntityData();

            ServerGamePacketListenerImpl ps = sp.connection;

            ps.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, npc));

            //sendPacket(new ClientboundAddEntityPacket(npc), player);
            sendPacket(new ClientboundSetEntityDataPacket(npc.getId(), synchedEntityData.getNonDefaultValues()), player);
            Bukkit.getScheduler().runTaskLaterAsynchronously(SpigotTest.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    sendPacket(new ClientboundPlayerInfoRemovePacket(Collections.singletonList(npc.getUUID())), player);
                }
            }, 40);
            //ps.send(new ClientboundAddEntityPacket(sp));
            //ps.send(new ClientboundAddEntityPacket(npc));

            ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
            ps.send(new ClientboundSetEquipmentPacket(npc.getBukkitEntity().getEntityId(), List.of(
                    new Pair<>(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(sword)),
                    new Pair<>(EquipmentSlot.BODY, CraftItemStack.asNMSCopy(new ItemStack(Material.DIAMOND_CHESTPLATE)))
                )
            ));

            SpigotTest.getPlayerList().add(npc);
        }

        return true;
    }
    public void sendPacket(Packet<?> packet, Player player) {
        ((CraftPlayer) player).getHandle().connection.send(packet);
    }
}
