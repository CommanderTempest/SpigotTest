package me.tempest.spigotTest.listeners;

import me.tempest.spigotTest.SpigotTest;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovementListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e)
    {
        SpigotTest.getPlayerList().stream()
                .forEach(npc -> {
                    Location location = npc.getBukkitEntity().getLocation();
                    location.setDirection(e.getPlayer().getLocation().subtract(location).toVector());
                    float yaw = location.getYaw();
                    float pitch = location.getPitch();

                    ServerGamePacketListenerImpl ps = ((CraftPlayer) e.getPlayer()).getHandle().connection;

                    ps.send(new ClientboundRotateHeadPacket(npc, (byte) ((yaw%360)*256/360)));
                    ps.send(new ClientboundMoveEntityPacket.Rot(
                            npc.getBukkitEntity().getEntityId(),
                            (byte) ((yaw%360)*256/360),
                            (byte) ((pitch%360)*256/360),
                            false));
                });
    }
}
