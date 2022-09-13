package me.powerspieler.paveral.util;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.discovery.Discovery;
import me.powerspieler.paveral.discovery.tutorial.altar_book;
import me.powerspieler.paveral.items.Chunkloader;
import me.powerspieler.paveral.items.Items;
import me.powerspieler.paveral.items.Wrench;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeLoader {
    public void registerRecipes(){
        Items wrench = new Wrench();
        ShapedRecipe wrenchRecipe = new ShapedRecipe(new NamespacedKey(Paveral.getPlugin(), "wrench"), wrench.build());
        wrenchRecipe.shape(" % "," %%","%  ");
        wrenchRecipe.setIngredient('%', Material.IRON_INGOT);
        Bukkit.addRecipe(wrenchRecipe);

        Items cl = new Chunkloader();
        ShapedRecipe clRecipe = new ShapedRecipe(new NamespacedKey(Paveral.getPlugin(), "chunkloader"), cl.build());
        clRecipe.shape(" S ","S%S","OEO");
        clRecipe.setIngredient('S', Material.NETHER_STAR);
        clRecipe.setIngredient('%', Material.LODESTONE);
        clRecipe.setIngredient('O', Material.OBSIDIAN);
        clRecipe.setIngredient('E', Material.ENCHANTING_TABLE);
        Bukkit.addRecipe(clRecipe);

        Discovery altar_book = new altar_book();
        ShapedRecipe al_Recipe = new ShapedRecipe(new NamespacedKey(Paveral.getPlugin(), "altar_book"), altar_book.build());
        al_Recipe.shape(" C ","S+S","#S#");
        al_Recipe.setIngredient('C', Material.AMETHYST_CLUSTER);
        al_Recipe.setIngredient('S', Material.AMETHYST_SHARD);
        al_Recipe.setIngredient('+', Material.BOOK);
        al_Recipe.setIngredient('#', Material.AMETHYST_BLOCK);
        Bukkit.addRecipe(al_Recipe);
    }
}
