package me.powerspieler.paveral.discovery.diaries;

import me.powerspieler.paveral.discovery.DiscoveryBook;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class Worldalterer extends DiscoveryBook {
    private static List<Component> pages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("The last few days we’ve been busy building our research facility underground. Things aren’t going according to plan, though – we’ve ran into some trouble, so we put the construction site on hold. One of my researchers found something weird, something I can’t even"));
        pages.add(Component.text("explain. It’s like a weird, blueish weed that grows on top of the stone – none of us has ever seen something like it before. It almost seems… alive? It makes a screeching sound when you get too close. One, two, three times – then a growl is heard somewhere in the"));
        pages.add(Component.text("distance. We have no idea what’s happening, but it’s best to not take any risks."));
        pages.add(Component.text("It’s been a few days since one of my researchers has made the weird discovery on the construction site of our research facility. Since then, I’ve sent a few of my best men further down into the cave to get a closer look and hopefully come back with answers."));
        pages.add(Component.text("Sending my researchers further into the cave has been a success. Well, partly. The men returned with some samples, which we’re able to examine in the facility. The thing is, though, that they returned with less people in their group. I was horrified when they told me what had"));
        pages.add(Component.text("happened while they were away. A creature, which they strangely enough call “the Warden”, deep down in the cave, killed them! They had found ruins of some sort – leftovers from before our time. One guy said that he’d noticed the creature had been attracted by sounds and that the"));
        pages.add(Component.text("group of researchers simply had been too loud while working on the ruins, which then had triggered the weird thing growing on top of the stone. So, it seems there is a connection between the sounds the “weed” makes and the Warden. Maybe the samples will give us some more"));
        pages.add(Component.text("information…"));
        pages.add(Component.text("As we slowly took up our building, we changed the name of the weed to “sculk”. Since we continued losing workers the further we went down into the cave, we finally concluded that you must work quietly and without any sounds as to not wake the Warden and prevent any more"));
        pages.add(Component.text("deaths. It seems the creature works as a kind of defense mechanism for the ancient ruins, as if he wants to guard them, but we can’t be sure yet…"));
        pages.add(Component.text("Our researchers made some progress, though it may seem small to others. But we finally have our confirmation!They discovered the “Sculk Catalyst” – with its help, the biomass of other living organisms is incorporated into the sculk to continue spreading out in the wild and in this way,"));
        pages.add(Component.text("survive. The screeching sounds we heard were so-called “Sculk Sensors”, meaning they basically sense danger when hearing sounds close to them and then send a warning signal to a “Sculk Shrieker”. After 4 calls, they summon the Warden to keep the sculk safe. In the process of"));
        pages.add(Component.text("researching, my experts found out that wool muffled the sounds, so we instantly provided wool on all our important ways into the cave, so we wouldn’t summon the Warden by accident. Nothing can stop us now from exploring the cave in its fullest."));
        pages.add(Component.text("As it turns out, the ruins in the cave hold treasure chests, which haven’t been looted at all – so it’s an extremely big win for us! Who knows how far down unfortunate explorers before us got before they got turned into sculk and became a part of the system. What kind of civilization lived"));
        pages.add(Component.text("here…. We have so much more to discover. Anyways, what we found in the chest, when we managed to pry one open, was astounding. An Echo Shard! It consists of concentrated energy pressed into the size of a crystal – the perfect energy storage, which works"));
        pages.add(Component.text("independently from the sculk system."));
        pages.add(Component.text("Apparently, sculk sensors are able to differentiate between different kinds of sounds. The calibrated sculk sensor, on the other hand, makes sure that this ability is compatible with Redstone – with the help of frequencies."));
        pages.add(Component.empty());
        pages.add(Component.text("?????"));
        pages.add(Component.empty());
        pages.add(Component.text("Weeks passed and we haven’t made any progress. Almost seems like a dead end. It’s been hard on me and my researchers, but we must stay positive. We’re here for a reason."));
        pages.add(Component.empty());
        pages.add(Component.text("???"));
        return pages;
    }

    public Worldalterer() {
        super("worldalterer", "", "Sculk Research", BookMeta.Generation.TATTERED, pages(), true, true);
    }
}
