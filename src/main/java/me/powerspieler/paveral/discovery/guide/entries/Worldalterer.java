package me.powerspieler.paveral.discovery.guide.entries;

import me.powerspieler.paveral.discovery.guide.GuideBookEntry;
import me.powerspieler.paveral.items.parts.worldalterer.AlterationCore;
import me.powerspieler.paveral.items.parts.worldalterer.AmethystLaser;
import me.powerspieler.paveral.items.parts.worldalterer.EchoContainer;
import me.powerspieler.paveral.items.parts.worldalterer.SculkCircuit;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class Worldalterer implements GuideBookEntry {
    @Override
    public List<Component> getPages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("The ")
                .append(Component.text("Worldalterer", NamedTextColor.GOLD))
                .append(Component.text(" is a very "))
                .append(Component.text("powerful", NamedTextColor.GOLD))
                .append(Component.text(" tool!\n\nIt has the ability to mark a certain area and "))
                .append(Component.text("move it", NamedTextColor.GOLD))
                .append(Component.text(" one block into a desired direction.\n\nA good thing, when fixing some issues on "))
                .append(Component.text("huge", NamedTextColor.GOLD))
                .append(Component.text(" building projects.")));
        pages.add(Component.text("The maximum amount of blocks allowed to move is ")
                .append(Component.text("32768 (=32³)", NamedTextColor.GOLD))
                .append(Component.text(". If you want to move "))
                .append(Component.text("more", NamedTextColor.GOLD))
                .append(Component.text(" than that, try to "))
                .append(Component.text("split", NamedTextColor.GOLD))
                .append(Component.text(" your desired area into smaller parts and move them separately.\n\n"))
                .append(Component.text("There are some blocks like bedrock or end portal frames that are "))
                .append(Component.text("excluded", NamedTextColor.GOLD))
                .append(Component.text(" from being moved.")));
        pages.add(Component.text("Marking an area:\n1. Position:\n  ")
                .append(Component.text("Sneaking + Leftclick", NamedTextColor.GOLD))
                .append(Component.text("\n2. Position:\n  "))
                .append(Component.text("Sneaking + Rightclick", NamedTextColor.GOLD))
                .append(Component.text("\n\nThose two positions are marking the cuboid to be moved.\nSet the desired direction by "))
                .append(Component.text("looking", NamedTextColor.GOLD))
                .append(Component.text(" into that direction and pressing "))
                .append(Component.text("leftclick", NamedTextColor.GOLD))
                .append(Component.text(".")));
        pages.add(Component.text("Commit the world alteration with ")
                .append(Component.text("rightclick", NamedTextColor.GOLD))
                .append(Component.text(".\n\nThe area in which the selection is being moved to has be "))
                .append(Component.text("completely empty", NamedTextColor.GOLD))
                .append(Component.text(". If there is a block in the way the alteration will be "))
                .append(Component.text("cancelled", NamedTextColor.GOLD))
                .append(Component.text(" and the\n"))
                .append(Component.text("interfering block", NamedTextColor.GOLD))
                .append(Component.text(" will be "))
                .append(Component.text("marked", NamedTextColor.GOLD))
                .append(Component.text(". If the selected area")));
        pages.add(Component.text("itself contains an ")
                .append(Component.text("illegal block", NamedTextColor.GOLD))
                .append(Component.text(" it will also be marked.\n\nWhen no interfering blocks are found the whole selection will be moved after a short time calculated by the "))
                .append(Component.text("amount of marked blocks", NamedTextColor.GOLD))
                .append(Component.text(".")));
        pages.add(Component.text("The ")
                .append(Component.text("Worldalterer", NamedTextColor.GOLD))
                .append(Component.text(" has a capacity for "))
                .append(Component.text("100", NamedTextColor.GOLD))
                .append(Component.text(" world alterations.\nYou can refill its tank by "))
                .append(Component.text("25 charges", NamedTextColor.GOLD))
                .append(Component.text(" using "))
                .append(Component.text("Echo Shards", NamedTextColor.GOLD))
                .append(Component.text(". You can combine them in a "))
                .append(Component.text("Smithing Table", NamedTextColor.GOLD))
                .append(Component.text(".\n\n"))
                .append(Component.text("\uEE18", NamedTextColor.WHITE)));
        pages.add(Component.text("Note that the worldalterer ")
                .append(Component.text("always", NamedTextColor.GOLD))
                .append(Component.text(" uses a charge when committing movements, even in case of a "))
                .append(Component.text("failure", NamedTextColor.GOLD))
                .append(Component.text(".\n\nAlso note, that "))
                .append(Component.text("entities", NamedTextColor.GOLD))
                .append(Component.text(" inside the marked area are "))
                .append(Component.text("not affected", NamedTextColor.GOLD))
                .append(Component.text(" and will not be moved.")));
        pages.add(Component.text("Since the Worldalterer is such a powerful tool ")
                .append(Component.text("only", NamedTextColor.GOLD))
                .append(Component.text(" players who crafted this tool themselve are "))
                .append(Component.text("allowed to use it", NamedTextColor.GOLD))
                .append(Component.text(".\n\n\nThe next pages will go into detail about how you can "))
                .append(Component.text("create", NamedTextColor.GOLD))
                .append(Component.text(" your very own "))
                .append(Component.text("Worldalterer", NamedTextColor.GOLD))
                .append(Component.text(".")));
        pages.add(Component.text("The most important component will be the ")
                .append(Component.text("Alteration Core", NamedTextColor.GOLD))
                .append(Component.text(".\n\n"))
                .append(generateRecipeGivingComponent(AlterationCore.registerRecipe().getKey(), "\uEE19", true)));
        pages.add(Component.text("Since we need such a huge amount of energy to alter the world, we have to go and get the ")
                .append(Component.text("most powerful", NamedTextColor.GOLD))
                .append(Component.text(" energy source known to players: "))
                .append(Component.text("A Sonic Boom", NamedTextColor.GOLD))
                .append(Component.text(".\n\nYou can gather some so called "))
                .append(Component.text("Sonic Essence", NamedTextColor.GOLD))
                .append(Component.text(" from "))
                .append(Component.text("Wardens", NamedTextColor.GOLD))
                .append(Component.text(" in Deep Dark biomes.")));
        pages.add(Component.text("In order to get some essence, you need to get ")
                .append(Component.text("shot", NamedTextColor.GOLD))
                .append(Component.text(" by the "))
                .append(Component.text("ranged sonic boom attack", NamedTextColor.GOLD))
                .append(Component.text(" of a warden while having an "))
                .append(Component.text("empty glass bottle", NamedTextColor.GOLD))
                .append(Component.text(" in your "))
                .append(Component.text("hand", NamedTextColor.GOLD))
                .append(Component.text(".\nA single bottle will be enough.\n"))
                .append(Component.text("Be careful!", NamedTextColor.GOLD))
                .append(Component.text(" Capturing the sonic boom "))
                .append(Component.text("does not decrease", NamedTextColor.GOLD))
                .append(Component.text(" the damage you will get.")));
        pages.add(Component.text("With the ")
                .append(Component.text("Sonic Essence", NamedTextColor.GOLD))
                .append(Component.text(" we can build the "))
                .append(Component.text("Sculk Circuit", NamedTextColor.GOLD))
                .append(Component.text(" and therefore the Alteration Core.\n\n"))
                .append(generateRecipeGivingComponent(SculkCircuit.registerRecipe().getKey(), "\uEE20", true)));
        pages.add(Component.text("The next component we need is the ")
                .append(Component.text("Amethyst Laser", NamedTextColor.GOLD))
                .append(Component.text(" to bundle the energy towards our blocks.\n\n"))
                .append(generateRecipeGivingComponent(AmethystLaser.registerRecipe().getKey(), "\uEE21", true)));
        pages.add(Component.text("And as the last component we need an ")
                .append(Component.text("Echo Container", NamedTextColor.GOLD))
                .append(Component.text(". This will store our "))
                .append(Component.text("charges", NamedTextColor.GOLD))
                .append(Component.text(".\n\n"))
                .append(generateRecipeGivingComponent(EchoContainer.registerRecipe().getKey(), "\uEE22", true)));
        pages.add(Component.text("Congratulations!", NamedTextColor.GOLD)
                .append(Component.text("\nYou have everything to ", NamedTextColor.BLACK))
                .append(Component.text("build", NamedTextColor.GOLD))
                .append(Component.text(" the ", NamedTextColor.BLACK))
                .append(Component.text("Worldalterer", NamedTextColor.GOLD))
                .append(Component.text(".\nTime to put the pieces together on ", NamedTextColor.BLACK))
                .append(Component.text("The Forge", NamedTextColor.GOLD))
                .append(Component.text(".\n\n", NamedTextColor.BLACK))
                .append(Component.text("\uEE23", NamedTextColor.WHITE)));
        return pages;
    }
}
