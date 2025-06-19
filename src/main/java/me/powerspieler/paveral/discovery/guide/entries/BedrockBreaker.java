package me.powerspieler.paveral.discovery.guide.entries;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class BedrockBreaker implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("Did you ever break ")
                .append(Component.text("bedrock", NamedTextColor.GOLD))
                .append(Component.text(" in survival?\nDid you "))
                .append(Component.text("master", NamedTextColor.GOLD))
                .append(Component.text(" the art of breaking bedrock?\n\nEither way, with the "))
                .append(Component.text("Bedrock Breaker", NamedTextColor.GOLD))
                .append(Component.text(" you can easily remove bedrock blocks.\n\nThis is helpful for people having trouble or if a "))
                .append(Component.text("big area", NamedTextColor.GOLD))
                .append(Component.text(" has to be removed "))
                .append(Component.text("quickly", NamedTextColor.GOLD))
                .append(Component.text(".")));
        pages.add(Component.text("Use ")
                .append(Component.text("right-click on a bedrock block", NamedTextColor.GOLD))
                .append(Component.text(" while holding this tool to remove it.\n\nThe Bedrock Breaker has "))
                .append(Component.text("limited uses", NamedTextColor.GOLD))
                .append(Component.text(" and can "))
                .append(Component.text("only", NamedTextColor.GOLD))
                .append(Component.text(" be enchanted with "))
                .append(Component.text("Unbreaking", NamedTextColor.GOLD))
                .append(Component.text(" to increase them. To "))
                .append(Component.text("refill", NamedTextColor.GOLD))
                .append(Component.text(" this tool you have to "))
                .append(Component.text("right-click", NamedTextColor.GOLD))
                .append(Component.text(" on "))
                .append(Component.text("ancient debris", NamedTextColor.GOLD))
                .append(Component.text(" blocks,")));
        pages.add(Component.text("each restoring ")
                .append(Component.text("25%", NamedTextColor.GOLD))
                .append(Component.text(" of durability. (25 blocks; without Unbreaking)\n\nCombine the following on the forming altar to create the bedrock breaker:\n"))
                .append(Component.text("1x Obisidian\n2x Piston\n2x TNT\n1x Lever\n1x Oak Trapdoor\n4x Ancient Debris", NamedTextColor.GOLD)));
        pages.add(Component.text("\n\uEE09", NamedTextColor.WHITE));
        return pages;
    }
}
