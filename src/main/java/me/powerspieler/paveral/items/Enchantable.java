package me.powerspieler.paveral.items;

import me.powerspieler.paveral.util.Constant;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.Set;

public interface Enchantable extends Listener {
    default void onEnchantingAttempt(PrepareAnvilEvent event, String keyString, Set<Enchantment> enchants) {
        if(event.getInventory().getFirstItem() != null && event.getResult() != null && event.getInventory().getFirstItem().getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE)) {
            if (Objects.equals(event.getInventory().getFirstItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), keyString)) {
                ItemStack result = event.getResult();
                ItemMeta resultmeta = result.getItemMeta();
                for(Enchantment enchantment : enchants ) {
                    if(event.getResult().containsEnchantment(enchantment)) {
                        resultmeta.removeEnchant(enchantment);
                    }
                }
                result.setItemMeta(resultmeta);
                event.setResult(result);

                if (event.getInventory().getSecondItem() != null && event.getInventory().getSecondItem().getType() == Material.ENCHANTED_BOOK) {
                    if(event.getInventory().getFirstItem().getEnchantments().equals(event.getResult().getEnchantments())){
                        event.setResult(new ItemStack(Material.AIR));
                    }
                }
            }
        }
    }

    @EventHandler
    void onEnchantingAttempt(PrepareAnvilEvent event);
}
