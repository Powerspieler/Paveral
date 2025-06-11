package me.powerspieler.paveral.discovery.guide;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.discovery.DiscoveryBook;
import me.powerspieler.paveral.discovery.diaries.AntiCreeperGrief;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BookMeta;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

public class BaseGuide extends DiscoveryBook {
    private static List<Component> pages(){
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("Titlepage"));
        pages.add(Component.text("Welcome!\n\nThis book will assist you in collecting all recipes added by Paveral.\n\nYou can find various diaries and documents in your world, which can be added to this guide."));
        pages.add(Component.text("You can do this by combining the found literature with this guide via crafting.\n\n Note: This two introduction pages will be removed once you have added your first diary or document"));

        // Access via children
        pages.add(Component.text("Index: \n\n")
                .append(Component.text("The Forming Altar\n", NamedTextColor.AQUA).clickEvent(ClickEvent.runCommand("/guide entry A")))
                .append(Component.text("???\n", NamedTextColor.AQUA).clickEvent(ClickEvent.changePage(5))) // Dis
                .append(Component.text("???\n", NamedTextColor.AQUA).clickEvent(ClickEvent.changePage(6))) // Forge

                .append(Component.text("???\n", NamedTextColor.LIGHT_PURPLE).clickEvent(ClickEvent.changePage(7))) // Enhanced
                .append(Component.text("???\n", NamedTextColor.LIGHT_PURPLE).clickEvent(ClickEvent.changePage(8))) // Bonk
                .append(Component.text("???\n", NamedTextColor.LIGHT_PURPLE).clickEvent(ClickEvent.changePage(9))) // Lightning Rod

                .append(Component.text("???\n", NamedTextColor.DARK_PURPLE).clickEvent(ClickEvent.changePage(10))) // Lightstaff
                .append(Component.text("???\n", NamedTextColor.DARK_PURPLE).clickEvent(ClickEvent.changePage(11))) // Bedrock Breaker

                .append(Component.text("???\n").clickEvent(ClickEvent.changePage(12))) // Music Core
                .append(Component.text("???\n").clickEvent(ClickEvent.changePage(13))) // Piano Sword
                .append(Component.text("???\n").clickEvent(ClickEvent.changePage(14))) // String Blade
                .append(Component.text("???\n").clickEvent(ClickEvent.changePage(15))) // Pickaxe
                .append(Component.text("???\n").clickEvent(ClickEvent.changePage(16))) // Axe
                .append(Component.text("???\n").clickEvent(ClickEvent.changePage(17))) // Shovel
                .append(Component.text("???\n").clickEvent(ClickEvent.changePage(18))) // Hoe

                .append(Component.text("???\n").clickEvent(ClickEvent.changePage(19))) // creeper defuser
                .append(Component.text("???\n").clickEvent(ClickEvent.changePage(20))) // chunkloader
                .append(Component.text("???\n").clickEvent(ClickEvent.changePage(21))) // Wrench
                .append(Component.text("???\n").clickEvent(ClickEvent.changePage(22))) // Worldalterer
        );

        pages.add(Component.text("A way of ").clickEvent(ClickEvent.runCommand("/guide"))); //4
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
