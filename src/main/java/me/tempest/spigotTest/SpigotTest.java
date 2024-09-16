package me.tempest.spigotTest;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import me.tempest.spigotTest.commands.*;
import me.tempest.spigotTest.listeners.*;
import me.tempest.spigotTest.managers.NPCManager;
import me.tempest.spigotTest.tasks.MyTask;
import net.minecraft.server.level.ServerPlayer;
import org.bson.Document;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

// https://hub.spigotmc.org/javadocs/bukkit/

/**
**TODO
 * (COMPLETED) Look up custom inventories, custom items, how to make, etc.
 * Look up custom entities
 * Take a look at how holograms are done
 * (COMPLETED) Look at MongoDB
 **/

public final class SpigotTest extends JavaPlugin implements Listener {

    private static List<ServerPlayer> playerList = new ArrayList<>();
    private static SpigotTest plugin;
    public static JedisPool pool;

    private NPCManager npcManager;

    @Override
    public void onEnable() {
        plugin = this;
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        // config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // MongoDB Connection
        MongoClient mongoClient = MongoClients.create("mongodb+srv://krukovtempest:tEAjiygHEc25AoA4@spigotlearn.o0vh7.mongodb.net/?retryWrites=true&w=majority&appName=SpigotLearn");
        MongoCollection<Document> collection = mongoClient.getDatabase("SpigotLearning").getCollection("users");

        // Redis
        pool = new JedisPool("127.0.0.1", 80);

        System.out.println("Connected to Database");
        Document document1 = new Document("name", "Tempest")
                .append("level", 105)
                .append("kills", 42875);
        collection.insertOne(document1);

        // Plugin startup logic
        System.out.println("First Plugin has started");
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new XPBottleBreakListener(), this);
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new SpawnListeners(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new MovementListener(), this);
        getCommand("god").setExecutor(new GodCommand());
        getCommand("setspawn").setExecutor(new SetSpawn());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("menu").setExecutor(new MenuCommand());
        getCommand("spawnentity").setExecutor(new SpawnEntityCommand());
        getCommand("gameover").setExecutor(new GameOverCommand());
        getCommand("boom").setExecutor(new BoomCommand());
        getCommand("spawnhologram").setExecutor(new SpawnHologram());
        getCommand("questnpc").setExecutor(new QuestNPCCommand(npcManager));

        // Tasks
        BukkitTask task = new MyTask().runTaskLater(this, 200);
        BukkitScheduler schedule = getServer().getScheduler();
        schedule.runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                System.out.println("Anon task");
            }
        }, 20L);

        // Redis Practice Connection
        Jedis j = null;
        try {
            j = pool.getResource();
            j.auth("123456789");
            j.set("key", "value");
            System.out.println(j.get("key"));
        } finally {
            j.close();
        }

        // Listening to incoming packet client -> server
        manager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.POSITION) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player p = event.getPlayer();

                double x = packet.getDoubles().read(0);
                double y = packet.getDoubles().read(1);
                double z = packet.getDoubles().read(2);
                boolean isOnGround = packet.getBooleans().read(0);

                p.sendMessage("Inbound: x: " + x + " y: " + y + " z: " + z);
                p.sendMessage("On ground? " + isOnGround);

            }
        });
        manager.addPacketListener(new PacketAdapter(this, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                int entityID = packet.getIntegers().read(0);
                if (entityID == npcManager.getID() )
                {
                    EnumWrappers.Hand hand = packet.getEnumEntityUseActions().read(0).getHand();
                    EnumWrappers.EntityUseAction action = packet.getEnumEntityUseActions().read(0).getAction();

                    if (hand == EnumWrappers.Hand.MAIN_HAND && action == EnumWrappers.EntityUseAction.INTERACT)
                    {

                    }
                }

            }
        });
        // server -> client
        manager.addPacketListener(new PacketAdapter(this, PacketType.Play.Server.REL_ENTITY_MOVE) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player p = event.getPlayer();
                short x = packet.getShorts().read(0);
                short y = packet.getShorts().read(1);
                short z = packet.getShorts().read(2);
                int entityID = packet.getIntegers().read(0);

                Entity entity = manager.getEntityFromID(p.getWorld(), entityID);

                // hehe
                //entity.teleport(p.getLocation());

                p.sendMessage("Outbound: x: " + x + " y: " + y + " z: " + z);
            }
        });

        manager.addPacketListener(new PacketAdapter(this, PacketType.Play.Client.CHAT) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                event.setCancelled(true);
            }
        });

    }

    public static List<ServerPlayer> getPlayerList() {
        return playerList;
    }

    @Override
    public void onDisable() {

        // Redis close
        pool.close();
        // Plugin shutdown logic
        System.out.println("Plugin has shut down");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        playerJoinEvent.setJoinMessage("Hello, " + playerJoinEvent.getPlayer().getName());
        System.out.println("A player has joined");

    }

    @EventHandler
    public void onPlayerLeaveBed(PlayerBedLeaveEvent event) {
        String playerName = event.getPlayer().getName();
        getServer().broadcastMessage("Go back to sleep " + playerName + ", the night is dark and full of terrors.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // /die = kill sending player
        if (command.getName().equalsIgnoreCase("die"))
        {
            if (sender instanceof Player player)
            {
                player.setHealth(0);
                player.sendMessage("The Stranger has come for you.");
            }
            else if (sender instanceof ConsoleCommandSender) {
                System.out.println("Die successfully ran by Console");
            }
        }
        return true;
    }

    public static SpigotTest getPlugin()
    {
        return plugin;
    }
}
