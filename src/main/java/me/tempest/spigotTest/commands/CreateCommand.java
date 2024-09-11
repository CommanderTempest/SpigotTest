package me.tempest.spigotTest.commands;

import com.mojang.authlib.GameProfile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CreateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player player)
        {
            CraftPlayer craftPlayer = (CraftPlayer) player;
            ServerPlayer sp = craftPlayer.getHandle();

            MinecraftServer server = sp.getServer();
            ServerLevel level = sp.getLevel();
            GameProfile profile = new GameProfile(UUID.randomUUID(), "Tom");

            ServerPlayer npc = new ServerPlayer(server, level, profile);

            ServerGamePacketListenerImpl ps = sp.connection;

            ps.send(new ClientboundPlayerInfoPacket(Action.ADD_PLAYER, npc));
            ps.send(new ClientboundAddPlayerPacket(sp));
        }

        return true;
    }
}
