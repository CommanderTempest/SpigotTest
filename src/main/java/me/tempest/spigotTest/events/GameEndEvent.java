package me.tempest.spigotTest.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Player player2;
    private int finalScore;

    public GameEndEvent(Player player, Player player2, int finalScore)
    {
        this.player = player;
        this.player2 = player2;
        this.finalScore = finalScore;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getFinalScore() {
        return finalScore;
    }
}
