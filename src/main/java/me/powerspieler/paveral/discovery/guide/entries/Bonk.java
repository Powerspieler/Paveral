package me.powerspieler.paveral.discovery.guide.entries;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class Bonk implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("Bonk!", NamedTextColor.GOLD)
                .append(Component.text("\nThis is just a ", NamedTextColor.BLACK))
                .append(Component.text("Knockback V", NamedTextColor.GOLD))
                .append(Component.text(" Stick to mess with your friends.\n\nIt seems a bit out of place here, but this item was the initial thought. It all started with the idea of having a Knockback stick, but the first problem was how to craft it. Since", NamedTextColor.BLACK)));
        pages.add(Component.text("NBT-Crafting wasn't a thing back then, to add more complex crafting recipes, the simple idea of just throwing ingredients onto the ground has been given to me.\n" +
                "But I did not like it! I wanted to make it properly and clean. So the first attempt of making some kind of new way to craft this"));
        pages.add(Component.text("knockback stick lead to The Forming Altar!\n\nEnchant an ")
                .append(Component.text("stick", NamedTextColor.GOLD))
                .append(Component.text(" with "))
                .append(Component.text("Enhanced Knockback", NamedTextColor.GOLD))
                .append(Component.text(" inside an anvil to create your very own Bonk stick!\n\n"))
                .append(Component.text("\uEE06", NamedTextColor.WHITE)));
        return pages;
    }
}
