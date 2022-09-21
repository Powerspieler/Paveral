package me.powerspieler.paveral.discovery.diaries;

import me.powerspieler.paveral.discovery.Discovery;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;

public class BedrockBreaker implements Discovery {
    @Override
    public ItemStack build() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookmeta = (BookMeta) book.getItemMeta();
        bookmeta.getPersistentDataContainer().set(Constant.DISCOVERY, PersistentDataType.STRING, "bedrock_breaker");
        bookmeta.getPersistentDataContainer().set(Constant.IS_DIARY, PersistentDataType.INTEGER, 1);
        bookmeta.setAuthor("");
        bookmeta.setTitle("A tale about an unbreakable myth");
        bookmeta.setGeneration(BookMeta.Generation.TATTERED);
        bookmeta.addPages(Component.text("There was a really huge mystery about one single block. A block that took almost forever to mine. Well it was forever. Every type of pickaxe had failed miserably. From wooden all the way up to diamond. Even the new discovered netherite made from ancient debris wasn't"));
        bookmeta.addPages(Component.text("able to break this hard block.\nIt seemed impossible. But many smart people have found a way to break this block without even using a pickaxe at all. They used only two pistons, two blocks of tnt and an obsidian block, one oak trapdoor and of course a lever to ignite the tnt. The way"));
        bookmeta.addPages(Component.text("to works is actually really simple. One tnt is placed directly under the lever but the second tnt is placed behind the block the lever is attached to. So both tnt are powered at the same time but because of the way redstone works the tnt below the lever is powered a bit earlier."));
        bookmeta.addPages(Component.text("And because of this the first tnt blows up the lever to remove the power source and the second tnt destroys the piston. A guy is trying to place a second piston into the same block where the old piston sits. And the rest is really self explanatory."));
        bookmeta.addPages(Component.text("A few scientists have managed to combine this technic with those materials to form a tool to remove the \"unbreakable\" block. They just added 4 of these weird blocks which netherite is made of.\n\nThis staff looks almost perfect, but it seems like the hard"));
        bookmeta.addPages(Component.text("block is trying to defend against the tool."));
        book.setItemMeta(bookmeta);
        return book;
    }
}
