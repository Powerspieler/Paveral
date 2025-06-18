package me.powerspieler.paveral.discovery.guide.entries;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class Wrench implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("The ")
                .append(Component.text("Wrench", NamedTextColor.GOLD))
                .append(Component.text(" is a recreation of the "))
                .append(Component.text("Debug Stick", NamedTextColor.GOLD))
                .append(Component.text(", "))
                .append(Component.text("without cheating", NamedTextColor.GOLD))
                .append(Component.text(" some water or some other things.\n\nIn case you don't know, the Debug Stick allows you to "))
                .append(Component.text("modify", NamedTextColor.GOLD))
                .append(Component.text(" certain blocks to be in a certain state that might be "))
                .append(Component.text("impossible", NamedTextColor.GOLD))
                .append(Component.text(" to achieve in survival mode. For example, ")));
        pages.add(Component.text("a glass pane next to a block, but it doesn't connect with it, fences not connecting with each other, modifying stairs to be in L-Shape without the need of another or setting iron doors into an open state without the help of redstone.\n\n")
                .append(Component.text("Block updates", NamedTextColor.GOLD))
                .append(Component.text(" next to the modified block ")));
        pages.add(Component.text("might ")
                .append(Component.text("undo", NamedTextColor.GOLD))
                .append(Component.text(" some changes and set it back to a "))
                .append(Component.text("legal state", NamedTextColor.GOLD))
                .append(Component.text(".\n\nTo create it you just need to throw "))
                .append(Component.text("4 iron ingots", NamedTextColor.GOLD))
                .append(Component.text(" onto the Forge.\n\n"))
                .append(Component.text("\uEE01", NamedTextColor.WHITE)));
        pages.add(Component.text("""
                /!\\ WORK IN PROGRESS
                
                At least thats the plan. The Wrench currently is only able to toggle iron trapdoors. The rest will be implemented in the future.
                And there will be no need to re-craft the Wrench again."""));
        return pages;
    }
}
