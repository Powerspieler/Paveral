package me.powerspieler.paveral.discovery.diaries;

import me.powerspieler.paveral.discovery.DiscoveryBook;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class BedrockBreaker extends DiscoveryBook {
    private static List<Component> pages(){
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("There was a really huge mystery about one single block. A block that took almost forever to mine. Well it was forever. Every type of pickaxe had failed miserably. From wooden all the way up to diamond. Even the new discovered netherite made from ancient debris wasn't"));
        pages.add(Component.text("able to break this hard block.\nIt seemed impossible. But many smart people have found a way to break this block without even using a pickaxe at all. They used only two pistons, two blocks of tnt and an obsidian block, one oak trapdoor and of course a lever to ignite the tnt. The way"));
        pages.add(Component.text("to works is actually really simple. One tnt is placed directly under the lever but the second tnt is placed behind the block the lever is attached to. So both tnt are powered at the same time but because of the way redstone works the tnt below the lever is powered a bit earlier."));
        pages.add(Component.text("And because of this the first tnt blows up the lever to remove the power source and the second tnt destroys the piston. A guy is trying to place a second piston into the same block where the old piston sits. And the rest is really self explanatory."));
        pages.add(Component.text("A few scientists have managed to combine this technic with those materials to form a tool to remove the \"unbreakable\" block. They just added 4 of these weird blocks which netherite is made of.\n\nThis staff looks almost perfect, but it seems like the hard"));
        pages.add(Component.text("block is trying to defend against the tool."));
        return pages;
    }

    public BedrockBreaker() {
        super("bedrock_breaker", "", "A tale about an unbreakable myth", BookMeta.Generation.TATTERED, pages(), true);
    }
}
