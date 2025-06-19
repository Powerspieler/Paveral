package me.powerspieler.paveral.discovery.guide.entries;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class Lightstaff implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("The ")
                .append(Component.text("Lightstaff", NamedTextColor.GOLD))
                .append(Component.text(" can be used to light up dark areas "))
                .append(Component.text("without", NamedTextColor.GOLD))
                .append(Component.text(" the need of a physical light source.\n\nUse the "))
                .append(Component.text("right button", NamedTextColor.GOLD))
                .append(Component.text(" to place or remove an existing lightblock.\nYou can also "))
                .append(Component.text("choose the lightlevel", NamedTextColor.GOLD))
                .append(Component.text(" from 1 to 15 by using the "))
                .append(Component.text("left button", NamedTextColor.GOLD))
                .append(Component.text(".\n(Hold shift to inverse)")));
        pages.add(Component.text("Once you placed a lightblock you can see a ")
                .append(Component.text("lightbulb particle",  NamedTextColor.GOLD))
                .append(Component.text(" effect indicating its position. This particle is only shown if a player nearby is "))
                .append(Component.text("holding a lightstaff",  NamedTextColor.GOLD))
                .append(Component.text(".\nSo, by simply taking the staff out of your hand will remove the lightbulb and the light source will be "))
                .append(Component.text("invisible",  NamedTextColor.GOLD))
                .append(Component.text(".")));
        pages.add(Component.text("The lightstaff has a ")
                .append(Component.text("limited", NamedTextColor.GOLD))
                .append(Component.text(" amount of uses, but those can be extended by the use of the "))
                .append(Component.text("Unbreaking", NamedTextColor.GOLD))
                .append(Component.text(" and "))
                .append(Component.text("Mending enchantments", NamedTextColor.GOLD))
                .append(Component.text(".\n\nBuild it by combining "))
                .append(Component.text("two iron ingots", NamedTextColor.GOLD))
                .append(Component.text(", "))
                .append(Component.text("one copper ingot", NamedTextColor.GOLD))
                .append(Component.text(", "))
                .append(Component.text("one redstone lamp", NamedTextColor.GOLD))
                .append(Component.text(" and "))
                .append(Component.text("two wither roses", NamedTextColor.GOLD))
                .append(Component.text(" on the altar.")));
        pages.add(Component.text("\n\uEE08",  NamedTextColor.WHITE));
        return pages;
    }
}
