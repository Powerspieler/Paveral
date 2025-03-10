package me.powerspieler.paveral.items;

import me.powerspieler.paveral.items.helper.ActionbarStatus;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class CooldownItem extends PaveralItem {
    private final HashMap<UUID, Long> cooldownMap;
    private long cooldownMillis;
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.000");

    public CooldownItem(Material baseMaterial, String customModelDataString, NamespacedKey key, String keyString, Component itemName, List<Component> lore, long cooldownMillis) {
        super(baseMaterial, customModelDataString, key, keyString, itemName, lore);
        this.cooldownMap = new HashMap<>();
        this.cooldownMillis = cooldownMillis;
    }

    protected boolean notOnCooldown(Player player) {
        UUID uuid = player.getUniqueId();
        return !cooldownMap.containsKey(uuid) || (System.currentTimeMillis() - cooldownMap.get(uuid) > cooldownMillis);
    }

    /**
     * Get the amount of millisseconds past since start of the last cooldown
     * @param player Player
     * @return Milliseconds. 0, if no cooldown was running yet.
     */
    protected long getMillisPast(Player player){
        UUID uuid = player.getUniqueId();
        if(cooldownMap.containsKey(uuid)){
            return System.currentTimeMillis() - cooldownMap.get(uuid);
        }
        return 0;
    }

    /**
     * Apply cooldown on player for this item. Does not tamper with actionbar message.
     * @param player Which player to cooldown
     */
    protected void applyCooldown(Player player) {
        cooldownMap.put(player.getUniqueId(), System.currentTimeMillis());
    }

    /**
     * Apply cooldown on player for this item. This method will set cooldownMillis.
     * @param player Player affected
     * @param duration Set the cooldown for this item
     */
    protected void applyCooldown(Player player, long duration) {
        this.cooldownMillis = duration;
        cooldownMap.put(player.getUniqueId(), System.currentTimeMillis());
    }

    /**
     * Apply cooldown with cooldown message
     * @param player On which player
     * @param interrupting This flag determinse if this cooldown message is interrupting another message by the same item.
     */
    protected void applyCooldown(Player player, boolean interrupting){
        cooldownMap.put(player.getUniqueId(), System.currentTimeMillis());
        new ActionbarStatus(player, keyString, 1L, 5) {
            @Override
            public void message() {
                double millisLeft = ((cooldownMillis - (System.currentTimeMillis() - cooldownMap.get(player.getUniqueId()))) / 1000.0);
                player.sendActionBar(Component.text("[ ", NamedTextColor.GOLD)
                        .append(Component.text(decimalFormat.format(millisLeft), NamedTextColor.RED))
                        .append(Component.text(" ]",NamedTextColor.GOLD)));
                if(millisLeft <= 0){
                    cancel(interrupting);
                    player.sendActionBar(Component.empty());
                }
            }
        }.displayMessageRecoverable();
    }
}
