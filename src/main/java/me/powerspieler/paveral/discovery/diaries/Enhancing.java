package me.powerspieler.paveral.discovery.diaries;

import me.powerspieler.paveral.discovery.DiscoveryBook;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class Enhancing extends DiscoveryBook {
    private static List<Component> pages(){
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("1. Foreword\n\n1.1 Potions\n1.1.1 Positive Effects\n1.1.2 Negative Effects\n1.1.3 Ethically\n       reprehensible?\n\n2.1 Enchantments\n2.1.1 Ench. for Armor\n2.1.2 Ench. for Tools\n2.1.3 Ench. for Misc.\n2.1.4 Overpowered\n       Enchantments "));
        pages.add(Component.text("1. Foreword\n\nThis documentation teaches you the basic of magic. Regarding Potions and also Enchantments. How careful you have to be with related stuff\nand which benefits will\nbe offered.\n\ncopyright 2021\nby merlin"));
        pages.add(Component.text("1.1 Potions\nPotions can manipulate the strength and perception of humans.\nBoth in a positive or a negative ways."));
        pages.add(Component.text("1.1.1 Positive Potions\n\n* Speed\n* Leaping\n* Haste\n* Resistance\n*\n*\n*\n* (add more... later)"));
        pages.add(Component.text("1.1.2 Negative Potions\n\n* Poison\n* Hunger\n* Blindness\n* Slowness\n*\n*\n\n[Transition]\n\n* Wither\n* Levitation"));
        pages.add(Component.text("1.1.3 Ethically\n       repreh..?\n\n[Text here]\n\n\n\nNobody care.. blah blah ..."));
        pages.add(Component.text("2.1 Enchantments\nEnchantments can improve your armor and gear beyond its physical boundaries.\n\nThere are no negative\nEnchantments!\n(Except for curses,\nbut they are called \"curses\" so they are not \"negative\" Enchantments"));
        pages.add(Component.text("2.1.1 Ench. for Armor\n\n* Protection\n* Mending\n* Unbreaking\n* Thorns"));
        pages.add(Component.text("2.1.2 Ench. for Tools\n\n* Efficiency\n* Unbreaking\n* Mending\n*\n*\n*\n*"));
        pages.add(Component.text("2.1.3 Ench. for Misc\n\nPickaxe/Axe/Shovel:\n* Silk Touch\n* Fortune\n\nSword:\n* Fire Aspect\n* Looting\n* Knockback\n\nTrident:\n* Channeling\n*"));
        pages.add(Component.text("Thanks for reading my documentation, I hope you learned something new and you will be more careful with the magic out there.\nSee ya!!\n\n-[Magic: Beautiful and\n           Dangerous]-\n.. Concept by merlin\n\n  copyright 2021"));
        pages.add(Component.text(""));
        pages.add(Component.text(""));
        pages.add(Component.text(""));
        pages.add(Component.text(""));
        pages.add(Component.text("2.1.4 Overpowered\n       Enchantments\n\nEvery Enchantment\nhas an \"Enhanced\" - Variant.\nThose are used to create items that are out of this world.\nTo upgrade an Enchantment, combine it with a Netherite  Scrap to create its \"Enhanced\" - Variant"));
        return pages;
    }

    public Enhancing() {
        super("diary_84", "", "Diary [#84]", BookMeta.Generation.TATTERED, pages(), true, true);
    }
}
