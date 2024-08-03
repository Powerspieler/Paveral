package me.powerspieler.paveral.items.enchanced;

import me.powerspieler.paveral.items.Items;
import me.powerspieler.paveral.items.LightningRod;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Channeling implements Listener, Items {
    @Override
    public ItemStack build() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta itemmeta = (EnchantmentStorageMeta)  item.getItemMeta();
        itemmeta.addStoredEnchant(Enchantment.CHANNELING, 10, true);
        itemmeta.getPersistentDataContainer().set(Constant.ITEMTYPE, PersistentDataType.STRING, "enhanced_channeling");

        itemmeta.itemName(Component.text("Enhanced Book", NamedTextColor.LIGHT_PURPLE)
                .decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Compatible with: ")
                .decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Trident", NamedTextColor.YELLOW)
                        .decoration(TextDecoration.ITALIC, false)));
        itemmeta.lore(lore);

        item.setItemMeta(itemmeta);
        return item;
    }

    @Override
    public List<ItemStack> parts() {
        List<ItemStack> parts = new ArrayList<>();
        ItemStack book = new ItemStack(Material.BOOK);
        // Skipped 1 Netherite Scrap
        parts.add(book);
        return parts;
    }

    @EventHandler
    public void onAnvilUse(PrepareAnvilEvent event){
        if(event.getInventory().getFirstItem() != null && event.getInventory().getFirstItem().getType() == Material.TRIDENT && event.getInventory().getSecondItem() != null && Objects.equals(event.getInventory().getSecondItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "enhanced_channeling")){
            Items lr = new LightningRod();
            event.setResult(lr.build());
        }
    }
}
