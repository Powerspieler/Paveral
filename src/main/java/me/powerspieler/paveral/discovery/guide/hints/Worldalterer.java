package me.powerspieler.paveral.discovery.guide.hints;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class Worldalterer implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("Unlock this entry by searching for an ")
                .append(Component.text("Ancient City", NamedTextColor.GOLD))
                .append(Component.text(".\n\nEvery Ancient City has one center point, which may generate with a "))
                .append(Component.text("chest containing a golden apple", NamedTextColor.GOLD))
                .append(Component.text(" in front of the portal. ("))
                .append(Component.text("33%", NamedTextColor.GOLD))
                .append(Component.text(" Chance).\n\nIn this chest you will find what you need.")));
        return pages;
    }
}
