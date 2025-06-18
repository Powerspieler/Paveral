package me.powerspieler.paveral.discovery.guide.entries;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class Chunkloader implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("As the name suggests the ")
                .append(Component.text("Chunkloader", NamedTextColor.GOLD))
                .append(Component.text(" loads chunks.\nPlacing this block into a chunk sets "))
                .append(Component.text("this", NamedTextColor.GOLD))
                .append(Component.text(" and the other "))
                .append(Component.text("8 chunks", NamedTextColor.GOLD))
                .append(Component.text(" surrounding it to being "))
                .append(Component.text("forceloaded", NamedTextColor.GOLD))
                .append(Component.text(". In fact, it does utilize the build-in "))
                .append(Component.text("/forceload", NamedTextColor.GOLD))
                .append(Component.text(" command and therefore "))
                .append(Component.text("does not support random ticks", NamedTextColor.GOLD))
                .append(Component.text("!\nThis means things like ")));
        pages.add(Component.text("natural growth of crops and other plants or saplings, dripstones filling cauldrons, budding amethyst growing a crystal to the next stage or oxidization of copper blocks.\nBasically everything that has some kind of ")
                .append(Component.text("random factor", NamedTextColor.GOLD))
                .append(Component.text(" regarding "))
                .append(Component.text("time", NamedTextColor.GOLD))
                .append(Component.text(" to it, is "))
                .append(Component.text("not forceloaded", NamedTextColor.GOLD))
                .append(Component.text("!")));
        pages.add(Component.text("Notably, the chunkloader does keep ")
                .append(Component.text("redstone contraptions", NamedTextColor.GOLD))
                .append(Component.text(" loaded!\n\nWhen "))
                .append(Component.text("holding", NamedTextColor.GOLD))
                .append(Component.text(" another Chunkloader the action bar status tells if the chunk you are being in is already forceloaded and the total amount of forceloaded chunks in the current dimension.")));

        pages.add(Component.text("Create the chunkloader with\n")
                .append(Component.text("3 Netherstars", NamedTextColor.GOLD))
                .append(Component.text(",\n"))
                .append(Component.text("2 Obsidian", NamedTextColor.GOLD))
                .append(Component.text(", one "))
                .append(Component.text("Enchanting Table", NamedTextColor.GOLD))
                .append(Component.text(" and one "))
                .append(Component.text("Lodestone", NamedTextColor.GOLD))
                .append(Component.text(" on the forge.\n\n"))
                .append(Component.text("\uEE03",  NamedTextColor.WHITE)));
        return pages;
    }
}
