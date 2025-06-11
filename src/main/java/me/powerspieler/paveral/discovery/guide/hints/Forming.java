package me.powerspieler.paveral.discovery.guide.hints;

import me.powerspieler.paveral.discovery.guide.GuideEntryBook;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;

public class Forming implements GuideEntryBook {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("FORMING HINT PAGE"));
        return pages;
    }
}
