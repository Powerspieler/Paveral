package me.powerspieler.paveral.items.helper;

import me.powerspieler.paveral.Paveral;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public abstract class ActionbarStatus implements ActionbarStatusMessage {
    public static final HashMap<UUID, TaskBundle> tasks = new HashMap<>();
    public static final HashMap<UUID, HashMap<String, ActionbarStatus>> statusMessageVault = new HashMap<>();

    private final UUID uuid;
    private final String itemType;
    private final long period;
    private final int priority;

    /**
     * Create a new ActionbarStatus to display a message() in a players actionbar.
     * Priority defaults to 5
     * @param player Player to display actionbar
     * @param itemType Corresponding item
     * @param period Interval of sending the actionbar. Useful when a message needs high refresh rate when the content of the message depends on players location or is a timer
     */
    public ActionbarStatus(Player player, String itemType, long period) {
        this.uuid = player.getUniqueId();
        this.itemType = itemType;
        this.period = period;
        this.priority = 5;
    }

    /**
     * Create a new ActionbarStatus to display a message() in a players actionbar.
     * Use this constructor to set a priortiy for the message. This will be useful when an
     * item has two messages and both are displayed at the same time.
     * This has only effect when calling displayMessageRecoverable() on this.
     * Priority defaults to 5
     * @param player Player to display actionbar
     * @param itemType Corresponding item
     * @param period Interval of sending the actionbar. Useful when a message needs high refresh rate when the content of the message depends on players location or is a timer
     * @param priority Priorty of message. Higher will be prioritized
     */
    public ActionbarStatus(Player player, String itemType, long period, int priority) {
        this.uuid = player.getUniqueId();
        this.itemType = itemType;
        this.period = period;
        this.priority = priority;
    }

    /**
     * Use this to display a message once.
     * This will also clear / cancel the running actionbar status message.
     */
    public void displayMessageOnce(){
        cancelPreviousTaskIfPresent(uuid);
        message();
    }

    /**
     * This method displays the given message continously (controlled by period field)
     * If a message is already displayed it will be replaced.
     */
    public void displayMessage(){
        if(!tasks.containsKey(uuid) || !tasks.get(uuid).getKey().equals(itemType) || tasks.get(uuid).getPriority() < priority){
            cancelPreviousTaskIfPresent(uuid);

            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    message();
                }
            }.runTaskTimer(Paveral.getPlugin(), 0L, period);
            tasks.put(uuid, new TaskBundle(task, itemType, priority));
        }
    }

    /**
     * This method is designed for things like cooldown.
     * If called the message will be displayed and stored.
     * So if a message from another item replaces this one, it can later be resumed.
     * <p>
     * Usually this method is used when the message is not a permanant one by an item.
     * rather when displaying something temporiarly after doing something (e.g. cooldowns)
     * <p>
     * If a recoverable message is already displayed it will only be overridden if this message has a higher priority than the present one.
     * In case of overriding, the old message will be lost.
     * If this message has a lower priority, the currently displayed message will be paused and this message will be shown.
     * Note that this message does not use the vault to store itself, so scrolling through the hotbar makes this message disappear.
     * Once this message is done (cancelled). The stored message inside the vault will be recovered.
     */
    public void displayMessageRecoverable(){
        if(!statusMessageVault.containsKey(uuid)){
            statusMessageVault.put(uuid, new HashMap<>());
        }
        HashMap<String, ActionbarStatus> personalVault = statusMessageVault.get(uuid);
        if(!personalVault.containsKey(itemType)){
            personalVault.put(itemType, this);
            displayMessage();
        } else {
            if(personalVault.get(itemType).priority < this.priority){
                personalVault.put(itemType, this);
                displayMessage();
            } else {
                displayMessageInterupt();
            }
        }
    }

    /**
     * This method interupts the running task.
     * Then shows the interrupting message, until it finishes and
     * restores the original message from the vault
     */
    private void displayMessageInterupt(){
        cancelPreviousTaskIfPresent(uuid);
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                message();
            }
        }.runTaskTimer(Paveral.getPlugin(), 0L, period);
        tasks.put(uuid, new TaskBundle(task, itemType, priority));

        new BukkitRunnable() {

            @Override
            public void run() {
                if(task.isCancelled()){
                    cancel();
                    recoverDisplayMessage(uuid, itemType);
                }
            }
        }.runTaskTimer(Paveral.getPlugin(), 0L, 1L);
    }

    protected static void recoverDisplayMessage(UUID uuid, String itemType){
        if(statusMessageVault.containsKey(uuid)){
            HashMap<String, ActionbarStatus> personalVault = statusMessageVault.get(uuid);
            if(personalVault.containsKey(itemType)){
                ActionbarStatus status = personalVault.remove(itemType);
                status.displayMessageRecoverable();
            }
        }
    }

    private void cancelPreviousTaskIfPresent(UUID uuid){
        if(tasks.containsKey(uuid)){
            tasks.remove(uuid).getTask().cancel();
        }
    }

    /**
     * Use this method inside of recoverable messages to indicate that
     * this message is no longer needed and can be removed from the tasks.
     *
     * @param interrupting Whether this cancel-Method should NOT clear the stored message of this itemType. Use this as an indicator if this message was interrupting another.
     */
    protected void cancel(boolean interrupting){
        tasks.remove(uuid).getTask().cancel();

        if(!interrupting && statusMessageVault.containsKey(uuid)){
            HashMap<String, ActionbarStatus> personalVault = statusMessageVault.get(uuid);
            personalVault.remove(itemType);
        }
    }
}
