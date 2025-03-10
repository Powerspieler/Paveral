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

public class AlterationCore extends PaveralItem {
    private static Component itemName(){
        return Component.text("Alteration Core", NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false);
    }

    public AlterationCore() {
        super(Material.JIGSAW, "alteration_core", Constant.ITEMTYPE, "alteration_core", itemName(), null);
    }

    @Override
    public PaveralRecipe recipe() {
        return null;
    }

    private static final NamespacedKey recipeKey = new NamespacedKey(Paveral.getPlugin(), "alteration_core");
    public static ShapedRecipe registerRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new AlterationCore().build());
        recipe.shape("DGD","G#G","DGD");
        recipe.setIngredient('D', Material.DEEPSLATE_TILES);
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('#', new SculkCircuit().build());
        return recipe;
    }
}
