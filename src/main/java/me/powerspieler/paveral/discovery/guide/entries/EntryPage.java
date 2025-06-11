package me.powerspieler.paveral.discovery.guide.entries;

import net.kyori.adventure.text.Component;

import java.util.List;

@FunctionalInterface
public interface EntryPage {
    List<Component> getEntryPages();
}
