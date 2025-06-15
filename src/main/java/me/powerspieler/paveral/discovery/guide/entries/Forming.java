package me.powerspieler.paveral.discovery.guide.entries;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import me.powerspieler.paveral.discovery.tutorial.AltarBook;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class Forming implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("Your first step is going to be the construction of\n")
                .append(Component.text("The Forming Altar", NamedTextColor.GOLD))
                .append(Component.text(".\n\nThis multiblock- structure will be the main method to \"form\" certain items, at least for now.\n\nAll details and the blueprint for building the altar are part of")));
        pages.add(Component.text("the Paveralicious Additions book.\n\n")
                .append(generateRecipeGivingComponent(AltarBook.registerRecipe().getKey(), true)));
        pages.add(Component.text("Explore other entries of ")
                .append(Component.text("this guide",  NamedTextColor.GOLD))
                .append(Component.text(" to learn which items you can form.")));
        return pages;
    }
}
