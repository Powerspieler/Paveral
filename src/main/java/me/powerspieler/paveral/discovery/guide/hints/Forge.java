package me.powerspieler.paveral.discovery.guide.hints;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class Forge implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("Unlock this entry by doing the same thing again from the last entry.\n\nIt's ")
                .append(Component.text("not", NamedTextColor.GOLD))
                .append(Component.text(" the fishing part")));
        pages.add(generateAchievementResetPage("craft_tutorial_book_forge"));
        return pages;
    }
}
