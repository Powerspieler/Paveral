package me.powerspieler.paveral.items.parts;

import com.destroystokyo.paper.MaterialTags;
import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.items.PaveralItem;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;

public class MusicCore extends PaveralItem {
    private static Component itemName(){
        return Component.text("Music Core", NamedTextColor.GOLD);
    }

    public MusicCore() {
        super(Material.JIGSAW, "music_core", Constant.ITEMTYPE, "music_core", itemName(), new ArrayList<>());
    }

    @Override
    public PaveralRecipe recipe() {
        return null;
    }

    // --- Item Logic ---

    public static final NamespacedKey recipeKey = new NamespacedKey(Paveral.getPlugin(), "music_core");
    public static ShapedRecipe registerRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new MusicCore().build());
        recipe.shape(" * ","#B#","JIJ");
        recipe.setIngredient('*', Material.NETHER_STAR);
        recipe.setIngredient('B', Material.NOTE_BLOCK);
        recipe.setIngredient('J', Material.JUKEBOX);
        recipe.setIngredient('I', Material.NETHERITE_INGOT);
        recipe.setIngredient('#', new RecipeChoice.MaterialChoice(MaterialTags.MUSIC_DISCS));

        return recipe;
    }
}
