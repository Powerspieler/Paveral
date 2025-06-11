package me.powerspieler.paveral.discovery.guide.hints;

import net.kyori.adventure.text.Component;

import java.util.List;

@FunctionalInterface
public interface HintPage {
    List<Component> getHintPages();
}
