package me.powerspieler.paveral.discovery;

import net.kyori.adventure.text.Component;

import java.util.List;

@FunctionalInterface
public interface Literature {
    List<Component> getGuidePages();
}
