package me.powerspieler.paveral.discovery.guide.entries;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class MusicPickaxe implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("The ")
                .append(Component.text("Resonating Pickaxe", NamedTextColor.GOLD))
                .append(Component.text(" allows you to mine a "))
                .append(Component.text("3x3", NamedTextColor.GOLD))
                .append(Component.text(" area, surrounding the block you are directly looking at.\nThe block breaking speed of the "))
                .append(Component.text("mined block", NamedTextColor.GOLD))
                .append(Component.text(" defines which blocks are mined around it, so only the blocks with "))
                .append(Component.text("higher or equal", NamedTextColor.GOLD))
                .append(Component.text(" block breaking speed will actually")));
        pages.add(Component.text("getting mined.\nAs a ")
                .append(Component.text("trade-off", NamedTextColor.GOLD))
                .append(Component.text(" to this feature, this Netherite Pickaxe "))
                .append(Component.text("cannot", NamedTextColor.GOLD))
                .append(Component.text(" be enchanted with "))
                .append(Component.text("Unbreaking", NamedTextColor.GOLD))
                .append(Component.text(", but besides that, it's just a "))
                .append(Component.text("regular", NamedTextColor.GOLD))
                .append(Component.text(" one.\n\n"))
                .append(Component.text("Beware!", NamedTextColor.GOLD))
                .append(Component.text(" Like with all other tools of this set, this item will "))
                .append(Component.text("disable", NamedTextColor.GOLD))
                .append(Component.text(" your "))
                .append(Component.text("Totem of Undying", NamedTextColor.GOLD))
                .append(Component.text(" when in")));
        pages.add(Component.text("inventory", NamedTextColor.GOLD)
                .append(Component.text(".\n\nCombine a ", NamedTextColor.BLACK))
                .append(Component.text("Netherite Pickaxe", NamedTextColor.GOLD))
                .append(Component.text(" with a ",  NamedTextColor.BLACK))
                .append(Component.text("Music Core", NamedTextColor.GOLD))
                .append(Component.text(" on the altar to create it.\n\n",  NamedTextColor.BLACK))
                .append(Component.text("\uEE12", NamedTextColor.WHITE)));
        return pages;
    }
}
