package me.powerspieler.paveral.discovery.guide;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.discovery.DiscoveryBook;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class BaseGuide extends DiscoveryBook {
    private static List<Component> pages(){
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("Titlepage"));
        pages.add(Component.text("Hint Page"));
        pages.add(Component.text("End Page"));
        return pages;
    }

    public BaseGuide() {
        super("guide_book", "You", "Paveral Guide", BookMeta.Generation.ORIGINAL, pages(), false);
    }

    @Override
    public ItemStack build() {
        return super.build();
    }

    public static final NamespacedKey recipeKey = new NamespacedKey(Paveral.getPlugin(), "guide_book");
    public static ShapedRecipe registerRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new BaseGuide().build());
        recipe.shape(" F ","PBI"," E ");
        recipe.setIngredient('F', Material.FEATHER);
        recipe.setIngredient('P', Material.PAPER);
        recipe.setIngredient('B', Material.BOOK);
        recipe.setIngredient('I', Material.GLOW_INK_SAC);
        recipe.setIngredient('E', Material.ECHO_SHARD);
        return recipe;
    }
}
