package me.powerspieler.paveral.discovery.guide.entries;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class MusicHoe implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("Crops broken with the ")
                .append(Component.text("Scythe of Harmony", NamedTextColor.GOLD))
                .append(Component.text(" are instantly "))
                .append(Component.text("replanted", NamedTextColor.GOLD))
                .append(Component.text(" onto the soil if you have the needed seed in your inventory. This covers all basic crops, melon, pumpkin, sniffer plants and nether warts.\n\nIn addition, farmland created with this scythe "))
                .append(Component.text("cannot", NamedTextColor.GOLD))
                .append(Component.text(" be")));
        pages.add(Component.text("trampled", NamedTextColor.GOLD)
                .append(Component.text(".\n\nThis tool acts like a regular Netherite Hoe, but can only be enchanted with ", NamedTextColor.BLACK))
                .append(Component.text("Unbreaking", NamedTextColor.GOLD))
                .append(Component.text(", ", NamedTextColor.BLACK))
                .append(Component.text("Mending", NamedTextColor.GOLD))
                .append(Component.text(" and ", NamedTextColor.BLACK))
                .append(Component.text("Fortune", NamedTextColor.GOLD))
                .append(Component.text(".\n\nThis item also ", NamedTextColor.BLACK))
                .append(Component.text("disables", NamedTextColor.GOLD))
                .append(Component.text(" your ", NamedTextColor.BLACK))
                .append(Component.text("Totem of Undying", NamedTextColor.GOLD))
                .append(Component.text(" when in inventory.", NamedTextColor.BLACK)));
        pages.add(Component.text("Combine a ")
                .append(Component.text("Netherite Hoe", NamedTextColor.GOLD))
                .append(Component.text(" with a "))
                .append(Component.text("Music Core", NamedTextColor.GOLD))
                .append(Component.text(" on the altar to create it.\n\n"))
                .append(Component.text("\uEE15", NamedTextColor.WHITE)));
        return pages;
    }
}
