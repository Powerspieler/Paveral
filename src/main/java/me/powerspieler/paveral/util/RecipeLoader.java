package me.powerspieler.paveral.util;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.discovery.Discovery;
import me.powerspieler.paveral.discovery.tutorial.AltarBook;
import me.powerspieler.paveral.items.Worldalterer;
import me.powerspieler.paveral.items.parts.MusicCore;
import me.powerspieler.paveral.items.parts.worldalterer.AlterationCore;
import me.powerspieler.paveral.items.parts.worldalterer.AmethystLaser;
import me.powerspieler.paveral.items.parts.worldalterer.EchoContainer;
import me.powerspieler.paveral.items.parts.worldalterer.SculkCircuit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeLoader {

    public static final NamespacedKey altarbookrecipekey = new NamespacedKey(Paveral.getPlugin(), "altar_book");
    public static void registerRecipes(){
        Discovery altar_book = new AltarBook();
        ShapedRecipe al_Recipe = new ShapedRecipe(altarbookrecipekey, altar_book.build());
        al_Recipe.shape(" C ","S+S","#S#");
        al_Recipe.setIngredient('C', Material.AMETHYST_CLUSTER);
        al_Recipe.setIngredient('S', Material.AMETHYST_SHARD);
        al_Recipe.setIngredient('+', Material.BOOK);
        al_Recipe.setIngredient('#', Material.AMETHYST_BLOCK);
        Bukkit.addRecipe(al_Recipe);

        Bukkit.addRecipe(AlterationCore.registerRecipe());
        Bukkit.addRecipe(AmethystLaser.registerRecipe());
        Bukkit.addRecipe(SculkCircuit.registerRecipe());
        Bukkit.addRecipe(EchoContainer.registerRecipe());

        Bukkit.addRecipe(Worldalterer.registerRecipe());

        Bukkit.addRecipe(MusicCore.registerRecipe());
    }
}
