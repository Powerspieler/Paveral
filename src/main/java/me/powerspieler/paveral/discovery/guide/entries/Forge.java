package me.powerspieler.paveral.discovery.guide.entries;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class Forge implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("If you see this entry, you already have formed the ")
                .append(Component.text("third volume", NamedTextColor.GOLD))
                .append(Component.text(" of Paveralicious Addititons.\n\nAll details and the blueprint regarding "))
                .append(Component.text("The Forge", NamedTextColor.GOLD))
                .append(Component.text(" are found in there.")));
        pages.add(Component.text("If you need the book again, just combine the ")
                .append(Component.text("second volume", NamedTextColor.GOLD))
                .append(Component.text(" with a "))
                .append(Component.text("single netherite scrap", NamedTextColor.GOLD))
                .append(Component.text(" on the forming altar.\n\n"))
                .append(Component.text("\uEE04", NamedTextColor.WHITE)));
        return pages;
    }
}
