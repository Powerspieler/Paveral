package me.powerspieler.paveral.discovery.guide.entries;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class MusicAxe implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("This Netherite Axe known as the ")
                .append(Component.text("Lumberjack's Bass", NamedTextColor.GOLD))
                .append(Component.text(" is able to mine a "))
                .append(Component.text("whole tree", NamedTextColor.GOLD))
                .append(Component.text(". This includes "))
                .append(Component.text("all", NamedTextColor.GOLD))
                .append(Component.text(" types of logs, warped and crimson and giant mushrooms.\nAll leaves attached to the chopped tree that would decay eventually, will do it "))
                .append(Component.text("directly", NamedTextColor.GOLD))
                .append(Component.text(".")));
        pages.add(Component.text("The block you are facing defines the ")
                .append(Component.text("height", NamedTextColor.GOLD))
                .append(Component.text(" at which the tree will be chopped off. So the blocks below will "))
                .append(Component.text("remain untouched", NamedTextColor.GOLD))
                .append(Component.text(".\n\nIn addition, you can also "))
                .append(Component.text("strip", NamedTextColor.GOLD))
                .append(Component.text(" the whole tree by "))
                .append(Component.text("right-clicking", NamedTextColor.GOLD))
                .append(Component.text(" on a "))
                .append(Component.text("stripped part", NamedTextColor.GOLD))
                .append(Component.text(" of the tree.")));
        pages.add(Component.text("This is tool works just like a regular Netherite Axe, but it can only enchanted with ")
                .append(Component.text("Unbreaking", NamedTextColor.GOLD))
                .append(Component.text(" and "))
                .append(Component.text("Mending", NamedTextColor.GOLD))
                .append(Component.text(". The block breaking speed is defined by the "))
                .append(Component.text("type", NamedTextColor.GOLD))
                .append(Component.text(" of tree you are chopping down.\n\nThis tool also "))
                .append(Component.text("disables", NamedTextColor.GOLD))
                .append(Component.text(" your "))
                .append(Component.text("Totem of Undying", NamedTextColor.GOLD))
                .append(Component.text(" when being in your")));
        pages.add(Component.text("inventory", NamedTextColor.GOLD)
                .append(Component.text(", just like all other tools of the \"Weapons of Melody\" - Set.\n\nCombine a "))
                .append(Component.text("Netherite Axe", NamedTextColor.GOLD))
                .append(Component.text(" with a "))
                .append(Component.text("Music Core", NamedTextColor.GOLD))
                .append(Component.text(" on the altar to create it."))
                .append(Component.text("\uEE13", NamedTextColor.WHITE)));
        return pages;
    }
}
