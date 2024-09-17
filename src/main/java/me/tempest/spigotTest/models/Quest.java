package me.tempest.spigotTest.models;

public abstract class Quest {
    private String name;
    private String description;
    private double reward;

    private long whenStarted; // epoch time

    public Quest(String name, String description, double reward) {
        this.name = name;
        this.description = description;
        this.reward = reward;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public long getWhenStarted() {
        return whenStarted;
    }

    public void setWhenStarted(long whenStarted) {
        this.whenStarted = whenStarted;
    }
}
