package me.powerspieler.paveral.discovery.guide.hints;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class MusicStringBlade implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("Craft the ")
                .append(Component.text("MusicCore", NamedTextColor.GOLD))
                .append(Component.text(" to unlock this entry.")));
        pages.add(generateAchievementResetPage("music_core"));
        return pages;
    }
}
