package me.powerspieler.paveral.discovery.guide.entries;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class CreeperDefuser implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("The ")
                .append(Component.text("Creeper Defuser", NamedTextColor.GOLD))
                .append(Component.text(" is probably the item you have needed the most!\n\nWhile having this item in your "))
                .append(Component.text("inventory", NamedTextColor.GOLD))
                .append(Component.text(", creepers "))
                .append(Component.text("won't do any block damage", NamedTextColor.GOLD))
                .append(Component.text(" around you in "))
                .append(Component.text("8 block", NamedTextColor.GOLD))
                .append(Component.text(" distance. In fact, they produce a little spark dealing only a "))
                .append(Component.text("little bit", NamedTextColor.GOLD))
                .append(Component.text(" of damage. ")));
        pages.add(Component.text("Furthermore, you can place the Creeper Defuser into an ")
                .append(Component.text("itemframe", NamedTextColor.GOLD))
                .append(Component.text(" to protect a whole area around it.\nThe protected area is a cube with the size of "))
                .append(Component.text("100 blocks", NamedTextColor.GOLD))
                .append(Component.text(" while the itemframe being in the center.\n\nWhen holding another creeper defuser in "))
                .append(Component.text("hand", NamedTextColor.GOLD))
                .append(Component.text(", the action bar ")));
        pages.add(Component.text("status tells you if you are in a ")
                .append(Component.text("protected area", NamedTextColor.GOLD))
                .append(Component.text(" right now.\n\nYou can craft this item on "))
                .append(Component.text("The Forge", NamedTextColor.GOLD))
                .append(Component.text("! Combine a "))
                .append(Component.text("Creeper Head", NamedTextColor.GOLD))
                .append(Component.text(" with "))
                .append(Component.text("any firework star", NamedTextColor.GOLD))
                .append(Component.text(" and a "))
                .append(Component.text("sculk sensor", NamedTextColor.GOLD))
                .append(Component.text(".\n\n"))
                .append(Component.text("\uEE02",  NamedTextColor.WHITE)));
        return pages;
    }
}
