package me.powerspieler.paveral.util;

import me.powerspieler.paveral.Paveral;
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
    }
}
