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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class SculkCircuit extends PaveralItem {
    private static Component itemName(){
        return Component.text("Sculk Circuit", NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false);
    }

    public SculkCircuit() {
        super(Material.JIGSAW, 4, Constant.ITEMTYPE, "sculk_circuit", itemName(), null);
    }

    @Override
    protected ItemStack build() {
        return super.build();
    }

    @Override
    public PaveralRecipe recipe() {
        return null;
    }

    // --- Item Logic ---

    private static final NamespacedKey recipeKey = new NamespacedKey(Paveral.getPlugin(), "sculk_circuit");
    public static ShapedRecipe registerRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new SculkCircuit().build());
        recipe.shape("PDP","RWR","SNS");
        recipe.setIngredient('P', Material.PISTON);
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('W', new SonicEssence().build());
        recipe.setIngredient('S', Material.CALIBRATED_SCULK_SENSOR);
        recipe.setIngredient('N', Material.NETHERITE_BLOCK);
        return recipe;
    }
}
