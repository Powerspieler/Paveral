package me.powerspieler.paveral.discovery.guide.hints;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class Dis implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("Unlock this entry by ")
                .append(Component.text("fishing", NamedTextColor.GOLD))
                .append(Component.text(".\n\nThe fishing spot "))
                .append(Component.text("doesn't have to", NamedTextColor.GOLD))
                .append(Component.text(" meet the requirements for obtaining items from the treasure category.\n\n\nThis literature has a drop rate of "))
                .append(Component.text("2%", NamedTextColor.GOLD))
        );
        pages.add(generateAchievementResetPage("fishing"));
        return pages;
    }
}
