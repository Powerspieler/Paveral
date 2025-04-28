package me.powerspieler.paveral.items.enhanced;

import me.powerspieler.paveral.crafting.EnchantmentIngredient;
import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.crafting.StandardIngredient;
import me.powerspieler.paveral.items.LightningRod;
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

public class Channeling extends PaveralItem implements Listener, Dismantable {
    private static Component itemName(){
        return Component.text("Enhanced Book", NamedTextColor.LIGHT_PURPLE)
                .decoration(TextDecoration.ITALIC, false);
    }

    private static List<Component> lore(){
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Enhanced Channeling", NamedTextColor.DARK_RED)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Compatible with: ")
                .decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Trident", NamedTextColor.YELLOW)
                        .decoration(TextDecoration.ITALIC, false)));
        return lore;
    }

    public Channeling() {
        super(Material.ENCHANTED_BOOK, "", Constant.ITEMTYPE, "enhanced_channeling", itemName(), lore());
    }

    @Override
    public PaveralRecipe recipe() {
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(Enchantment.CHANNELING, 1);

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
        if(event.getInventory().getFirstItem() != null && event.getInventory().getFirstItem().getType() == Material.TRIDENT && event.getInventory().getSecondItem() != null && Objects.equals(event.getInventory().getSecondItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), keyString)){
            event.setResult(new LightningRod().build());
        }
    }
}
