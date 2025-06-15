package me.powerspieler.paveral.discovery.guide.entries;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.ArrayList;
import java.util.List;

public class Enhanced implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("Every enchantment has an ")
                .append(Component.text("enhanced variant", NamedTextColor.GOLD))
                .append(Component.text(". The Forming Altar allows to push every enchanted book "))
                .append(Component.text("with a single enchantment at its maximum level", NamedTextColor.GOLD))
                .append(Component.text(" beyond this limit.\nOnly a "))
                .append(Component.text("netherite scrap", NamedTextColor.GOLD))
                .append(Component.text(" is needed.\n\n"))
                .append(Component.text("\uEE05", NamedTextColor.WHITE)));
        pages.add(Component.text("Note: ")
                .append(Component.text("Not every", NamedTextColor.GOLD))
                .append(Component.text(" enhanced variant is currently craftable, because only a few have actual purpose.\n"))
                .append(Component.text("All available", NamedTextColor.GOLD))
                .append(Component.text(" enhanced books are shown on the next pages.\n\nRemember: The enchanted book to be enhanced "))
                .append(Component.text("must have only one single max. level", NamedTextColor.GOLD))
                .append(Component.text(" enchantment.")));
        pages.add(Component.text("Enhanced Efficiency").decoration(TextDecoration.UNDERLINED, true)
                .append(Component.text("\n\nCan be applied onto pickaxes and axes inside an anvil.\nNetherite Pickaxes will be able to ").decoration(TextDecoration.UNDERLINED, false))
                .append(Component.text("instant-mine", NamedTextColor.GOLD).decoration(TextDecoration.UNDERLINED, false))
                .append(Component.text(" deepslate and Netherite axes will do the same with logs.\nA ").decoration(TextDecoration.UNDERLINED, false))
                .append(Component.text("Haste II",  NamedTextColor.GOLD).decoration(TextDecoration.UNDERLINED, false))
                .append(Component.text(" beacon is still required in both cases.").decoration(TextDecoration.UNDERLINED, false)));
        pages.add(Component.text("Enhanced Knockback").decoration(TextDecoration.UNDERLINED, true)
                .append(Component.text("\n\nCurrently only used as an ").decoration(TextDecoration.UNDERLINED, false))
                .append(Component.text("ingredient", NamedTextColor.GOLD).decoration(TextDecoration.UNDERLINED, false))
                .append(Component.text(" for other Forming Altar recipes.").decoration(TextDecoration.UNDERLINED, false)));
        pages.add(Component.text("Enhanced Channeling").decoration(TextDecoration.UNDERLINED, true)
                .append(Component.text("\n\nCurrently only used as an ").decoration(TextDecoration.UNDERLINED, false))
                .append(Component.text("ingredient", NamedTextColor.GOLD).decoration(TextDecoration.UNDERLINED, false))
                .append(Component.text(" for other Forming Altar recipes.").decoration(TextDecoration.UNDERLINED, false)));
        return pages;
    }
}
