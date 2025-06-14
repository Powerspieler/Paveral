package me.powerspieler.paveral.discovery.guide.hints;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class Wrench implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("Unlock this entry by unlocking the ")
                .append(Component.text("third entry", NamedTextColor.GOLD))
                .append(Component.text(" of this guide.")));
        pages.add(generateAchievementResetPage("craft_tutorial_book_forge"));
        return pages;
    }
}
