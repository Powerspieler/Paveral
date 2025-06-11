package me.powerspieler.paveral.discovery.guide;

import net.kyori.adventure.text.Component;

import java.util.List;

@FunctionalInterface
public interface GuideEntryBook {
    List<Component> getPages();
}
