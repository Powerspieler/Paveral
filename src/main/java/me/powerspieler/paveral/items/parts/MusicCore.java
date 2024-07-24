package me.powerspieler.paveral.items.parts;

import com.destroystokyo.paper.MaterialTags;
import me.powerspieler.paveral.Paveral;
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

import java.util.ArrayList;
import java.util.List;

public class MusicCore {
    private static ItemStack build() {
        ItemStack item = new ItemStack(Material.JIGSAW);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setCustomModelData(7);
        itemmeta.getPersistentDataContainer().set(Constant.ITEMTYPE, PersistentDataType.STRING, "music_core");
        itemmeta.itemName(Component.text("Music Core", NamedTextColor.GOLD));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Combined with ", NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Netherite Tool", NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false))
                .append(Component.text(" on the altar", NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false)));
        lore.add(Component.text("will form corresponding ", NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Melodic Tool", NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)));
        lore.add(Component.empty());
        lore.add(Component.text("Combined with ", NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Netherite Sword, 16 Quartz and 8 Blackstone", NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false)));
        lore.add(Component.text("on the altar will form ", NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Rhytms Awakening", NamedTextColor.DARK_PURPLE).decoration(TextDecoration.ITALIC, false)));
        lore.add(Component.text("Combined with ", NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Netherite Sword and any dye", NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false)));
        lore.add(Component.text("on the altar will form ", NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false)
                .append(Component.text("String Blade", NamedTextColor.DARK_PURPLE).decoration(TextDecoration.ITALIC, false)));
        itemmeta.lore(lore);

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
