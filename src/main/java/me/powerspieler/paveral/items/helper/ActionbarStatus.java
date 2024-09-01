package me.powerspieler.paveral.items.helper;

import me.powerspieler.paveral.Paveral;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class ActionbarStatus implements ActionbarStatusMessage {
    private static final HashMap<UUID, TaskBundle> tasks = new HashMap<>();

    /**
     * Every item is calling this method for itself.
     * If the player is holding "nothing" keyString is: ""
     * So every item removes the taskbundle and cancels the task.
     * <p>
     * If the player is holding e.g. Creeper Defuser. The call from e.g. chunkloader does nothing since keyString is not
     * chunkloader or "".
     * Creeper Defuser tries to override the taskbundle only if the key differs from creeper defuser. when overriding
     * the old taskbundle the task will be canceled.
     * @param event ItemHoldingControllerEvent containing player and currently held paveral item.
     * @param keyString source of call. (Which item called this method)
     */

    public void displayMessage(ItemHoldingControllerEvent event, String keyString){
        Player player = event.getPlayer();
        if(event.getItemType().equals(keyString)){ // only allow "isHolding-item" to continue
            if(!tasks.containsKey(player.getUniqueId()) || !tasks.get(player.getUniqueId()).getKey().equals(keyString)){ // keyString differs from existing key in bundle or is not available
                if(tasks.containsKey(player.getUniqueId())){ // cancel existing task if available.
                    tasks.get(player.getUniqueId()).getTask().cancel();
                }

                BukkitTask task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        message();
                    }
                }.runTaskTimer(Paveral.getPlugin(), 0L, 5L);
                tasks.put(player.getUniqueId(), new TaskBundle(task, keyString));
            }
        } else if(!allowItems().contains(event.getItemType())){ // holding nothing
            BukkitTask task = tasks.remove(player.getUniqueId()).getTask();
            if(task != null){
                task.cancel();
            }
        }
    }

    private static Set<String> allowItems(){
        Set<String> items = new HashSet<>();
        items.add("anti_creeper_grief");
        items.add("chunkloader");
        return items;
    }
}
