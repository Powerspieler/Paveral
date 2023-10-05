package me.powerspieler.paveral.items.parts.worldalterer;

import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class SonicEssence implements Listener {
    private static ItemStack build() {
        ItemStack item = new ItemStack(Material.JIGSAW);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setCustomModelData(2);
        itemmeta.getPersistentDataContainer().set(Constant.ITEMTYPE, PersistentDataType.STRING, "echo_container");
        itemmeta.displayName(Component.text("Sonic Essense", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false)); // Name by Raphilius
        item.setItemMeta(itemmeta);
        return item;
    }

    @EventHandler
    public void onWardenSonic(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player player && event.getCause() == EntityDamageEvent.DamageCause.SONIC_BOOM){
            ItemStack bottle = player.getInventory().getItemInMainHand();
            if(bottle.getType() == Material.GLASS_BOTTLE){
                bottle.setAmount(bottle.getAmount() - 1);
                HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(SonicEssence.build());
                if(!leftover.isEmpty()){
                    for(ItemStack item : leftover.values()){
                        player.getWorld().dropItemNaturally(player.getLocation(), item);
                    }
                }
            }
        }
    }
}
