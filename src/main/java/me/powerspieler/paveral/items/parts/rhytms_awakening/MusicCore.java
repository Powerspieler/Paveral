package me.powerspieler.paveral.items.parts.rhytms_awakening;

import com.destroystokyo.paper.MaterialTags;
import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.items.parts.worldalterer.SculkCircuit;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class MusicCore {
    private static ItemStack build() {
        ItemStack item = new ItemStack(Material.JIGSAW);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setCustomModelData(6);
        itemmeta.getPersistentDataContainer().set(Constant.ITEMTYPE, PersistentDataType.STRING, "music_core");
        itemmeta.itemName(Component.text("Music Core", NamedTextColor.GOLD));
        item.setItemMeta(itemmeta);
        return item;
    }

    private static final NamespacedKey recipeKey = new NamespacedKey(Paveral.getPlugin(), "music_core");
    public static ShapedRecipe registerRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, build());
        recipe.shape(" * ","#B#","JIJ");
        recipe.setIngredient('*', Material.NETHER_STAR);
        recipe.setIngredient('B', Material.NOTE_BLOCK);
        recipe.setIngredient('J', Material.JUKEBOX);
        recipe.setIngredient('I', Material.NETHERITE_INGOT);
        recipe.setIngredient('#', new RecipeChoice.MaterialChoice(MaterialTags.MUSIC_DISCS));

        return recipe;
    }
}
