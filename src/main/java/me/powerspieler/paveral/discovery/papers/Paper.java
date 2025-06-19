package me.powerspieler.paveral.discovery.papers;

import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class Paper {

    private static ItemStack createPaper(List<Component> lore, String discoveryString) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(Constant.DISCOVERY, PersistentDataType.STRING, discoveryString);
        lore.add(Component.text(""));
        lore.add(Component.text("Can be combined with Paveral Guide", NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.ITALIC, false));
        itemMeta.lore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack disassemblePaper() {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Have you ever wondered if you could")
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("enhance the documentation book on the altar?")
                .decoration(TextDecoration.ITALIC, false));

        return createPaper(lore, "disassemble_paper");
    }

    public static ItemStack forgePaper() {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("This document contains valueable knowledge"));
        lore.add(Component.text("about the Forge and their products"));

        return createPaper(lore, "forge_paper");
    }

    public static ItemStack lightstaffPaper() {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("A blueprint having detailed explanation"));
        lore.add(Component.text("about the Lightstaff and its usage"));

        return createPaper(lore, "lightstaff_paper");
    }

    public static ItemStack musicCorePaper() {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("An interesting article about how everything"));
        lore.add(Component.text("may be used as a weapon, even music"));

        return createPaper(lore, "musiccore_paper");
    }

    public static ItemStack musicCoreItemsPaper() {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Some blueprints for tools and weapons"));
        lore.add(Component.text("(Weapons of Melody Set)").decoration(TextDecoration.ITALIC, false));

        return createPaper(lore, "musiccore_items_paper");
    }

}
