package me.powerspieler.paveral.discovery.guide.entries;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class MusicCore implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("The ")
                .append(Component.text("Music Core", NamedTextColor.GOLD))
                .append(Component.text(" is the basic ingredient for all tools and weapons of the \""))
                .append(Component.text("Weapons of Melody", NamedTextColor.GOLD))
                .append(Component.text("\"-Set.\n\n"))
                .append(generateRecipeGivingComponent(me.powerspieler.paveral.items.parts.MusicCore.recipeKey, "\uEE17", true)));
        return pages;
    }
}
