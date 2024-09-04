package me.powerspieler.paveral.items.parts.worldalterer;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.items.PaveralItem;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class AmethystLaser extends PaveralItem {
    private static Component itemName(){
        return Component.text("Amethyst Laser", NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false);
    }

    public AmethystLaser() {
        super(Material.JIGSAW, 3, Constant.ITEMTYPE, "amethyst_laser", itemName(), null);
    }

    @Override
    public PaveralRecipe recipe() {
        return null;
    }

    // --- Item Logic ---

    private static final NamespacedKey recipeKey = new NamespacedKey(Paveral.getPlugin(), "amethyst_laser");
    public static ShapedRecipe registerRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new AmethystLaser().build());
        recipe.shape(" I ","NAN","DSD");
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('A', Material.AMETHYST_CLUSTER);
        recipe.setIngredient('S', Material.SCULK);
        recipe.setIngredient('D', Material.DEEPSLATE_TILES);
        return recipe;
    }
}
