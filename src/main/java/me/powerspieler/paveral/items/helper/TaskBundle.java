package me.powerspieler.paveral.items.helper;

import org.bukkit.scheduler.BukkitTask;

public class TaskBundle {
    private final BukkitTask task;
    private final String key;
    private final int priority;

    public TaskBundle(final BukkitTask task, final String key, final int priority) {
        this.task = task;
        this.key = key;
        this.priority = priority;
    }

    public String getKey() {
        return key;
    }

    public BukkitTask getTask() {
        return task;
    }

    public int getPriority() {
        return priority;
    }
}
