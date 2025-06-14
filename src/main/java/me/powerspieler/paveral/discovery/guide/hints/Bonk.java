package me.powerspieler.paveral.discovery.guide.hints;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class Bonk implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("The book unlocking this entry can be found in ")
                .append(Component.text("Woodland Mansion", NamedTextColor.GOLD))
                .append(Component.text(" Chests with a chance of "))
                .append(Component.text("25%", NamedTextColor.GOLD)));
        return pages;
    }
}
