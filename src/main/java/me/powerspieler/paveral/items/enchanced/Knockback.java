package me.powerspieler.paveral.items.enchanced;

import me.powerspieler.paveral.crafting.EnchantmentIngredient;
import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.crafting.StandardIngredient;
import me.powerspieler.paveral.items.Dismantable;
import me.powerspieler.paveral.items.PaveralItem;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class Knockback extends PaveralItem implements Listener, Dismantable {
    private static Component itemName(){
        return Component.text("Enhanced Book", NamedTextColor.LIGHT_PURPLE)
                .decoration(TextDecoration.ITALIC, false);
    }

    private static List<Component> lore(){
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Compatible with: ")
                .decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Stick", NamedTextColor.YELLOW)
                        .decoration(TextDecoration.ITALIC, false)));
        return lore;
    }

    public Knockback() {
        super(Material.ENCHANTED_BOOK, 0, Constant.ITEMTYPE, "enhanced_knockback", itemName(), lore());
    }

    @Override
    protected ItemStack build() {
        ItemStack item = super.build();
        EnchantmentStorageMeta itemMeta = (EnchantmentStorageMeta) item.getItemMeta();
        itemMeta.addStoredEnchant(Enchantment.KNOCKBACK, 5, true);
        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    public PaveralRecipe recipe() {
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(Enchantment.KNOCKBACK, 2);

        Set<StandardIngredient> ingredients = new HashSet<>();
        ingredients.add(new EnchantmentIngredient(enchantments));
        ingredients.add(new StandardIngredient(Material.NETHERITE_SCRAP, 1));
        return new PaveralRecipe(ingredients, this.build());
    }

    @Override
    public List<ItemStack> parts() {
        List<ItemStack> parts = new ArrayList<>();
        parts.add(new ItemStack(Material.BOOK));
        return parts;
    }

    // --- Item Logic ---

    @EventHandler
    private void onAnvilUse(PrepareAnvilEvent event){
        if(event.getInventory().getFirstItem() != null && event.getInventory().getFirstItem().getType() == Material.STICK && event.getInventory().getSecondItem() != null && Objects.equals(event.getInventory().getSecondItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), keyString)){
            ItemStack item = new ItemStack(Material.STICK);
            ItemMeta itemmeta = item.getItemMeta();
            itemmeta.getPersistentDataContainer().set(Constant.ITEMTYPE, PersistentDataType.STRING, "bonk");
            itemmeta.addEnchant(Enchantment.KNOCKBACK, 5, true);
            itemmeta.itemName(Component.text("Bonk", NamedTextColor.RED)
                    .decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Hornyjail certified", NamedTextColor.GOLD)
                    .decoration(TextDecoration.ITALIC, false));
            itemmeta.lore(lore);
            item.setItemMeta(itemmeta);
            event.getView().setRepairCost(0);
            event.setResult(item);

        }
    }
}
