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

public class EchoContainer {
    private static ItemStack build() {
        ItemStack item = new ItemStack(Material.JIGSAW);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setCustomModelData(6);
        itemmeta.getPersistentDataContainer().set(Constant.ITEMTYPE, PersistentDataType.STRING, "echo_container");
        itemmeta.displayName(Component.text("Echo Container", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        item.setItemMeta(itemmeta);
        return item;

    }

    private static final NamespacedKey recipeKey = new NamespacedKey(Paveral.getPlugin(), "echo_container");
    public static ShapedRecipe registerRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, build());
        recipe.shape("NIN","G N","NNN");
        recipe.setIngredient('N', Material.NETHER_BRICKS);
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('G', Material.GLASS_PANE);
        return recipe;
    }
}
