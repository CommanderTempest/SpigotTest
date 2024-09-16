package me.tempest.spigotTest.managers;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

// manages quest NPCs
public class NPCManager {
    private ServerPlayer questNPC;

    public void spawnQuestNPC(Player p)
    {
        CraftPlayer craftPlayer = (CraftPlayer) p;

        MinecraftServer server = craftPlayer.getHandle().getServer();
        ServerLevel level = craftPlayer.getHandle().serverLevel();

        ServerPlayer npc = new ServerPlayer(server, level, new GameProfile(UUID.randomUUID(), "Tom"), craftPlayer.getHandle().clientInformation());

        ServerGamePacketListenerImpl ps = craftPlayer.getHandle().connection;

        ps.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, npc));

        this.questNPC = npc;
    }

    public int getID()
    {
        return (this.questNPC == null) ? 0 : this.questNPC.getId();
    }
}
