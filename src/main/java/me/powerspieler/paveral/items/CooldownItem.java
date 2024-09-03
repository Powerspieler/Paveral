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
    private final long cooldownMillis;
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.000");

    public CooldownItem(Material baseMaterial, int customModelData, NamespacedKey key, String keyString, Component itemName, List<Component> lore, long cooldownMillis) {
        super(baseMaterial, customModelData, key, keyString, itemName, lore);
        this.cooldownMap = new HashMap<>();
        this.cooldownMillis = cooldownMillis;
    }

    protected boolean notOnCooldown(Player player) {
        UUID uuid = player.getUniqueId();
        return !cooldownMap.containsKey(uuid) || (System.currentTimeMillis() - cooldownMap.get(uuid) >= cooldownMillis);
    }

    /**
     * Apply cooldown
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
