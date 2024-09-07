package me.tempest.spigotTest.tasks;
import org.bukkit.scheduler.BukkitRunnable;

public class MyTask extends BukkitRunnable {

    @Override
    public void run() {
        System.out.println("Task has been run");
    }
}
