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

public class SculkCircuit {
    protected static ItemStack build() {
        ItemStack item = new ItemStack(Material.JIGSAW);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setCustomModelData(4);
        itemmeta.getPersistentDataContainer().set(Constant.ITEMTYPE, PersistentDataType.STRING, "sculk_circuit");
        itemmeta.itemName(Component.text("Sculk Circuit", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        item.setItemMeta(itemmeta);
        return item;

    }

    private static final NamespacedKey recipeKey = new NamespacedKey(Paveral.getPlugin(), "sculk_circuit");
    public static ShapedRecipe registerRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, build());
        recipe.shape("PDP","RWR","SNS");
        recipe.setIngredient('P', Material.PISTON);
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('W', SonicEssence.build());
        recipe.setIngredient('S', Material.CALIBRATED_SCULK_SENSOR);
        recipe.setIngredient('N', Material.NETHERITE_BLOCK);
        return recipe;
    }

}
