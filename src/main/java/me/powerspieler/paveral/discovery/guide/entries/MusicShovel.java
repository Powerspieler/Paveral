package me.powerspieler.paveral.discovery.guide.entries;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class MusicShovel implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("Bardic Inspiration", NamedTextColor.GOLD)
                .append(Component.text("! Who did even come up with this name? Anyways, this Netherite Shovel works just like the ", NamedTextColor.BLACK))
                .append(Component.text("Resonating Pickaxe", NamedTextColor.GOLD))
                .append(Component.text(" but as a shovel!\n\nQuick Summary:\n* Mining ", NamedTextColor.BLACK))
                .append(Component.text("3x3", NamedTextColor.GOLD))
                .append(Component.text("\n* ", NamedTextColor.BLACK))
                .append(Component.text("Hardness", NamedTextColor.GOLD))
                .append(Component.text(" of mined      block dictates blocks\n  being broken             around", NamedTextColor.BLACK)));
        pages.add(Component.text("* Cannot be enchanted   with ")
                .append(Component.text("Unbreaking", NamedTextColor.GOLD))
                .append(Component.text("\n* "))
                .append(Component.text("Disables", NamedTextColor.GOLD))
                .append(Component.text(" Totem\n\nCombine a "))
                .append(Component.text("Netherite Shovel", NamedTextColor.GOLD))
                .append(Component.text(" with a "))
                .append(Component.text("Music Core", NamedTextColor.GOLD))
                .append(Component.text(" on the altar to create it.\n\n"))
                .append(Component.text("\uEE14", NamedTextColor.WHITE)));
        return pages;
    }
}
