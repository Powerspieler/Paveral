package me.powerspieler.paveral.discovery.guide.entries;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class Dis implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("The next multiblock- structure is going to be the ")
                .append(Component.text("Disassembling Table", NamedTextColor.GOLD))
                .append(Component.text(".\n\nHere you can take items from Paveral apart.\n\nAll details about the table and the blueprint itself are described in the "))
                .append(Component.text("second volume", NamedTextColor.GOLD))
                .append(Component.text(" of")));
        pages.add(Component.text("the Paveralicious Additions book.\n\nCombine the ")
                .append(Component.text("original book", NamedTextColor.GOLD))
                .append(Component.text(" with a "))
                .append(Component.text("single netherite scrap", NamedTextColor.GOLD))
                .append(Component.text(" on the "))
                .append(Component.text("forming altar\n\n", NamedTextColor.GOLD))
                .append(Component.text("\uEE04", NamedTextColor.WHITE)));
        return pages;
    }
}
