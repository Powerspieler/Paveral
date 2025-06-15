package me.powerspieler.paveral.discovery.guide.hints;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class Lightstaff implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("To unlock this entry you have to get yourself a ")
                .append(Component.text("lightstaff", NamedTextColor.GOLD))
                .append(Component.text(". These can be obtained in two ways:\n\nFishing ("))
                .append(Component.text("1%", NamedTextColor.GOLD))
                .append(Component.text(" Chance)\n\n(No treasure water required and only if the player hasn't already an lightstaff in their inventory)")));
        pages.add(Component.text("Stronghold Crossing", NamedTextColor.GOLD)
                .append(Component.text(" or ", NamedTextColor.BLACK))
                .append(Component.text("Stronghold Corridor", NamedTextColor.GOLD))
                .append(Component.text(" chests with a chance of ", NamedTextColor.BLACK))
                .append(Component.text("25%", NamedTextColor.GOLD))
                .append(Component.text(".\n\n\nOnce you have gained your lightstaff, you can throw it into the multi-structure which is introduced in the ", NamedTextColor.BLACK))
                .append(Component.text("second entry of this guide.", NamedTextColor.GOLD)));
        return pages;
    }
}
