package me.powerspieler.paveral.discovery.guide.entries;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class MusicStringBlade implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("The ")
                .append(Component.text("String Blade", NamedTextColor.GOLD))
                .append(Component.text(" is like a regular Netherite Sword, but it has the feature of "))
                .append(Component.text("shooting", NamedTextColor.GOLD))
                .append(Component.text(" a single music note as a "))
                .append(Component.text("projectile", NamedTextColor.GOLD))
                .append(Component.text(".\nJust press "))
                .append(Component.text("right-click", NamedTextColor.GOLD))
                .append(Component.text(" to launch and the music note will deal "))
                .append(Component.text("3 Hearts", NamedTextColor.GOLD))
                .append(Component.text(" of damage. ("))
                .append(Component.text("Half", NamedTextColor.GOLD))
                .append(Component.text(" of that to players). "))
                .append(Component.text("Tamed animals", NamedTextColor.GOLD))
                .append(Component.text(" of any player")));
        pages.add(Component.text("are ")
                .append(Component.text("excluded", NamedTextColor.GOLD))
                .append(Component.text(".\n\nCombine a "))
                .append(Component.text("Netherite Sword", NamedTextColor.GOLD))
                .append(Component.text(", a "))
                .append(Component.text("Music Core", NamedTextColor.GOLD))
                .append(Component.text(" and "))
                .append(Component.text("any dye", NamedTextColor.GOLD))
                .append(Component.text(" on the altar to craft it.\n\n"))
                .append(Component.text("\uEE11",  NamedTextColor.WHITE))
                .append(Component.text("\n\n\n\nAny dye", NamedTextColor.GOLD))
                .append(Component.text(" can be used to set the "))
                .append(Component.text("appearance", NamedTextColor.GOLD))
                .append(Component.text(" of the sword.")));
        return pages;
    }
}
