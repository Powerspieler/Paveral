package me.powerspieler.paveral.discovery.guide.entries;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class MusicPianoSword implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("The first sword of the \"Weapons of Melody\" Set:\n")
                .append(Component.text("Rhythms Awakening", NamedTextColor.GOLD))
                .append(Component.text(".\n\nThis sword can be used as a regular netherite sword, but comes also with the feature of sending out a "))
                .append(Component.text("wave of music notes", NamedTextColor.GOLD))
                .append(Component.text(" to damage other entites. ("))
                .append(Component.text("5 Hearts", NamedTextColor.GOLD))
                .append(Component.text(")\nPlayers receive only"))
                .append(Component.text("", NamedTextColor.GOLD))
                .append(Component.text("")));
        pages.add(Component.text("half", NamedTextColor.GOLD)
                .append(Component.text(" of that damage.\n\n", NamedTextColor.BLACK))
                .append(Component.text("While any ", NamedTextColor.BLACK))
                .append(Component.text("tamed companion", NamedTextColor.GOLD))
                .append(Component.text(" from any player is completely ", NamedTextColor.BLACK))
                .append(Component.text("excluded", NamedTextColor.GOLD))
                .append(Component.text(".\n\nJust like all other weapons and tools of this set, this sword ", NamedTextColor.BLACK))
                .append(Component.text("disables", NamedTextColor.GOLD))
                .append(Component.text(" your ", NamedTextColor.BLACK))
                .append(Component.text("Totem of Undying", NamedTextColor.GOLD))
                .append(Component.text(" while this item is in your ", NamedTextColor.BLACK))
                .append(Component.text("inventory", NamedTextColor.GOLD))
                .append(Component.text(". ", NamedTextColor.BLACK))
                .append(Component.text("Be careful!", NamedTextColor.GOLD)));
        pages.add(Component.text("Combine a ")
                .append(Component.text("netherite sword", NamedTextColor.GOLD))
                .append(Component.text(", "))
                .append(Component.text("one music core", NamedTextColor.GOLD))
                .append(Component.text(", "))
                .append(Component.text("16 nether quartz", NamedTextColor.GOLD))
                .append(Component.text(" and "))
                .append(Component.text("8 blackstone", NamedTextColor.GOLD))
                .append(Component.text(" on the altar to create this sword.\n\n"))
                .append(Component.text("\uEE10",  NamedTextColor.WHITE)));
        return pages;
    }
}
