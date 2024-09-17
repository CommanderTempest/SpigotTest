package me.tempest.spigotTest.models;
import org.bukkit.entity.EntityType;

public class KillQuest extends Quest{

    private EntityType entityType;
    private int amountToKill;

    public KillQuest(String name, String description, double reward, EntityType entityType, int amount) {
        super(name, description, reward);
        this.entityType = entityType;
        this.amountToKill = amount;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public int getAmountToKill() {
        return amountToKill;
    }

    public void setAmountToKill(int amountToKill) {
        this.amountToKill = amountToKill;
    }
}
