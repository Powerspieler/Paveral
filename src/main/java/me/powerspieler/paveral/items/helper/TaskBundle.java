package me.powerspieler.paveral.items.helper;

import org.bukkit.scheduler.BukkitTask;

public class TaskBundle {
    private final BukkitTask task;
    private final String key;

    public TaskBundle(final BukkitTask task, final String key) {
        this.task = task;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public BukkitTask getTask() {
        return task;
    }
}
