package me.powerspieler.paveral.discovery.guide.hints;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class MusicCore implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("Use the ")
                .append(Component.text("Forming Altar", NamedTextColor.GOLD))
                .append(Component.text(" at least once to unlock this entry")));
        pages.add(generateAchievementResetPage("first_forming"));
        return pages;
    }
}
