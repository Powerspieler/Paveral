package me.powerspieler.paveral.discovery.guide.hints;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;

public class Forming implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("Hey! You found a secret!\n\nI'd like to give you an cookie, but I think it's considered cheating"));
        return pages;
    }
}
