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

public class AmethystLaser {
    private static ItemStack build() {
        ItemStack item = new ItemStack(Material.JIGSAW);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setCustomModelData(3);
        itemmeta.getPersistentDataContainer().set(Constant.ITEMTYPE, PersistentDataType.STRING, "amethyst_laser");
        itemmeta.itemName(Component.text("Amethyst Laser", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        item.setItemMeta(itemmeta);
        return item;

    }

    private static final NamespacedKey recipeKey = new NamespacedKey(Paveral.getPlugin(), "amethyst_laser");
    public static ShapedRecipe registerRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, build());
        recipe.shape(" I ","NAN","DSD");
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('A', Material.AMETHYST_CLUSTER);
        recipe.setIngredient('S', Material.SCULK);
        recipe.setIngredient('D', Material.DEEPSLATE_TILES);
        return recipe;
    }
}
