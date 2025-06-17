package me.powerspieler.paveral.discovery.guide.entries;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class LightningRod implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("Phantoms", NamedTextColor.GOLD)
                .append(Component.text(" are ", NamedTextColor.BLACK))
                .append(Component.text("annoying", NamedTextColor.GOLD))
                .append(Component.text("!\nEspecially, when building and getting knocked off a ledge.\n\nYou can get rid of those creatures by using the ", NamedTextColor.BLACK))
                .append(Component.text("Lightning Rod", NamedTextColor.GOLD))
                .append(Component.text(". Simply ", NamedTextColor.BLACK))
                .append(Component.text("right-click", NamedTextColor.GOLD))
                .append(Component.text(" with it to cast a lightning which ", NamedTextColor.BLACK))
                .append(Component.text("kills phantoms instantly", NamedTextColor.GOLD))
                .append(Component.text(".\nKilling at least ", NamedTextColor.BLACK))
                .append(Component.text("three",  NamedTextColor.GOLD)));
        pages.add(Component.text("phantoms at once grands you ")
                .append(Component.text("Phantom Protection", NamedTextColor.GOLD))
                .append(Component.text(", which prevents phantoms from spawing for five minutes!\n\nCombine a "))
                .append(Component.text("trident", NamedTextColor.GOLD))
                .append(Component.text(" with "))
                .append(Component.text("Enhanced Channeling", NamedTextColor.GOLD))
                .append(Component.text(" in an anvil to craft it.\n\n"))
                .append(Component.text("\uEE07", NamedTextColor.WHITE)));
        return pages;
    }
}
