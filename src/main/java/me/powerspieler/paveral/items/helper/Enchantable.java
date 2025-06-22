package me.powerspieler.paveral.items.helper;

import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
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

    default void onEnchantingTableAttempt(PrepareItemEnchantEvent event, String keyString, Set<Enchantment> enchants) {
        if(event.getItem().getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE)) {
            if (Objects.equals(event.getItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), keyString)) {
                EnchantmentOffer[] offers = event.getOffers();
                for (int i = 0; i < offers.length; i++) {
                    if (offers[i] != null && enchants.contains(offers[i].getEnchantment())) {
                        offers[i] = null;
                    }
                }
                if(Arrays.stream(offers).allMatch(Objects::isNull)) {
                    event.getEnchanter().sendMessage(Component.text("No valid enchantments available for this item! Try rerolling by enchanting a book or other item.", NamedTextColor.RED));
                }
            }
        }
    }

    default void onEnchantingTableComplete(EnchantItemEvent event, String keyString, Set<Enchantment> enchants) {
        if(event.getItem().getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE)) {
            if (Objects.equals(event.getItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), keyString)) {
                event.getEnchantsToAdd().keySet().removeIf(enchants::contains);
            }
        }
    }

    @EventHandler
    void onEnchantingAttempt(PrepareAnvilEvent event);

    @EventHandler
    void onEnchantingTableAttempt(PrepareItemEnchantEvent event);

    @EventHandler
    void onEnchantingTableComplete(EnchantItemEvent event);
}
