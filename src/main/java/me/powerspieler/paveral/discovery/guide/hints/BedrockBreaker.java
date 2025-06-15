package me.powerspieler.paveral.discovery.guide.hints;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class BedrockBreaker implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("To unlock this entry you have to ")
                .append(Component.text("tame a cat", NamedTextColor.GOLD))
                .append(Component.text(" and have it nearby when you go to sleep.\n\nWhen you wake up, the cat might have brought you a gift.\n\nWith a chance of "))
                .append(Component.text("20%", NamedTextColor.GOLD))
                .append(Component.text(" it's the book you seek.")));
        pages.add(generateAchievementResetPage("sleep_with_cat"));
        return pages;
    }
}
