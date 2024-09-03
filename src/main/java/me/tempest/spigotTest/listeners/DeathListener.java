package me.tempest.spigotTest.listeners;
import me.tempest.spigotTest.SpigotTest;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathListener implements Listener {

    // this exists in the main SpigotTest file already, just trying things
    private final SpigotTest plugin = new SpigotTest();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        new BukkitRunnable() {
            @Override
            public void run() {
                System.out.println("Dying");
            }
        }.runTaskLater(SpigotTest.getPlugin(), 5);
    }
}
