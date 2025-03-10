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

public class EchoContainer extends PaveralItem {
    private static Component itemName(){
        return Component.text("Echo Container", NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false);
    }

    public EchoContainer() {
        super(Material.JIGSAW, "echo_container", Constant.ITEMTYPE, "echo_container", itemName(), null);
    }

    @Override
    public PaveralRecipe recipe() {
        return null;
    }

    // --- Item Logic ---

    private static final NamespacedKey recipeKey = new NamespacedKey(Paveral.getPlugin(), "echo_container");
    public static ShapedRecipe registerRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new EchoContainer().build());
        recipe.shape("NIN","G N","NNN");
        recipe.setIngredient('N', Material.NETHER_BRICKS);
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('G', Material.GLASS_PANE);
        return recipe;
    }
}
