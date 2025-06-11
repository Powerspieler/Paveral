package me.powerspieler.paveral.discovery.guide.entries;

import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;

public class AltarEntry implements EntryPage{
    @Override
    public List<Component> getEntryPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("FORMING ALTAR ENTRY"));
        return pages;
    }
}
