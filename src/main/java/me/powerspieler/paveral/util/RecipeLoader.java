package me.powerspieler.paveral.util;

import me.powerspieler.paveral.discovery.guide.BaseGuide;
import me.powerspieler.paveral.discovery.tutorial.AltarBook;
import me.powerspieler.paveral.items.Worldalterer;
import me.powerspieler.paveral.items.parts.MusicCore;
import me.powerspieler.paveral.items.parts.worldalterer.AlterationCore;
import me.powerspieler.paveral.items.parts.worldalterer.AmethystLaser;
import me.powerspieler.paveral.items.parts.worldalterer.EchoContainer;
import me.powerspieler.paveral.items.parts.worldalterer.SculkCircuit;
import org.bukkit.Bukkit;

public class RecipeLoader {


    public static void registerRecipes(){
        Bukkit.addRecipe(BaseGuide.registerRecipe());
        Bukkit.addRecipe(AltarBook.registerRecipe());

        Bukkit.addRecipe(AlterationCore.registerRecipe());
        Bukkit.addRecipe(AmethystLaser.registerRecipe());
        Bukkit.addRecipe(SculkCircuit.registerRecipe());
        Bukkit.addRecipe(EchoContainer.registerRecipe());

        Bukkit.addRecipe(Worldalterer.registerRecipe());

        Bukkit.addRecipe(MusicCore.registerRecipe());
    }
}
