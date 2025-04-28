package me.powerspieler.paveral.items.enhanced;

import com.destroystokyo.paper.MaterialSetTag;
import me.powerspieler.paveral.crafting.EnchantmentIngredient;
import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.crafting.StandardIngredient;
import me.powerspieler.paveral.items.PaveralItem;
import me.powerspieler.paveral.items.helper.Dismantable;
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
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class Efficiency extends PaveralItem implements Listener, Dismantable {
    private static Component itemName(){
        return Component.text("Enhanced Book", NamedTextColor.LIGHT_PURPLE)
                .decoration(TextDecoration.ITALIC, false);
    }

    private static List<Component> lore(){
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Enhanced Efficiency", NamedTextColor.DARK_RED)
                .decoration(TextDecoration.ITALIC, false));
        return lore;
    }

    public Efficiency() {
        super(Material.ENCHANTED_BOOK, "", Constant.ITEMTYPE, "enhanced_efficiency", itemName(), lore());
    }

    @Override
    public PaveralRecipe recipe() {
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(Enchantment.EFFICIENCY, 5);

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
        if(event.getInventory().getSecondItem() != null && Objects.equals(event.getInventory().getSecondItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), keyString)){
            ItemStack firstItem = event.getInventory().getFirstItem();
            if(firstItem != null){
                Material material = firstItem.getType();
                if(MaterialSetTag.ITEMS_PICKAXES.isTagged(material)) {
                    ItemStack result = new ItemStack(firstItem);
                    result.addUnsafeEnchantment(Enchantment.EFFICIENCY, 8);
                    event.setResult(result);
                } else if(MaterialSetTag.ITEMS_AXES.isTagged(material)) {
                    ItemStack result = new ItemStack(firstItem);
                    result.addUnsafeEnchantment(Enchantment.EFFICIENCY, 6);
                    event.setResult(result);
                }
            }
        }
    }
}
