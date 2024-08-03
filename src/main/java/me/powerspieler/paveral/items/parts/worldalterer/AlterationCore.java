package me.powerspieler.paveral.items.parts.worldalterer;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class AlterationCore {
    private static ItemStack build() {
        ItemStack item = new ItemStack(Material.JIGSAW);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setCustomModelData(5);
        itemmeta.getPersistentDataContainer().set(Constant.ITEMTYPE, PersistentDataType.STRING, "alteration_core");
        itemmeta.itemName(Component.text("Alteration Core", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        item.setItemMeta(itemmeta);
        return item;

    }

    private static final NamespacedKey recipeKey = new NamespacedKey(Paveral.getPlugin(), "alteration_core");
    public static ShapedRecipe registerRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, build());
        recipe.shape("DGD","G#G","DGD");
        recipe.setIngredient('D', Material.DEEPSLATE_TILES);
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('#', SculkCircuit.build());
        return recipe;
    }
}
