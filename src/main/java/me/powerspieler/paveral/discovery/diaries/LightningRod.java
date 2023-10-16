package me.powerspieler.paveral.discovery.diaries;

import me.powerspieler.paveral.discovery.Discovery;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;

public class LightningRod implements Discovery {
    @Override
    public ItemStack build() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookmeta = (BookMeta) book.getItemMeta();
        bookmeta.getPersistentDataContainer().set(Constant.DISCOVERY, PersistentDataType.STRING, "diary_17");
        bookmeta.getPersistentDataContainer().set(Constant.IS_DIARY, PersistentDataType.INTEGER, 1);
        bookmeta.setAuthor("");
        bookmeta.setTitle("Diary [#17]");
        bookmeta.setGeneration(BookMeta.Generation.TATTERED);
        bookmeta.addPages(Component.text("Day ??: \nI still don't know what day this is.\nBut at least i can start writing a new journal again since the book of my last one got full.\nNothing happend today, i just wanted to write again. So maybe there will be some interesting tomorrow.."));
        bookmeta.addPages(Component.text("Day 01: \nTo not get confused with the order of my pages I'm just going to name this \"Day 01\" and tomorrow will be \"Day 02\". Wow, so exciting - A new start.\n\nToday i was out in the ocean to fish, like the other 6 days of the week. Beside the"));
        bookmeta.addPages(Component.text("beautiful sunset and the big bobo fish I have catched, there were pretty high waves. My boat almost fell over, not gonna lie."));
        bookmeta.addPages(Component.text("Day 03: \nThe waves are as high as never before. I had no time writing an \"Day 02\" entry yesterday, because i needed to save my house from the waves.\nI hope the ocean can chill down a bit the next week."));
        bookmeta.addPages(Component.text("Day 04: \nIt's pretty interesting what junk the ocean is washing to my house. Ok, it's not only junk. There is a lot of fish - it's like I can work from home.\nBut back to the junk: Kelp, some interesting looking Eggs, and a lot of shells. Oh, i almost forgot, there was"));
        bookmeta.addPages(Component.text("some kind of stone plate before.\nThere was something drawn on it. I think it was like a staff with three sharp parts on the top of it and a book. There was also something written on the book but I can't remember..."));
        bookmeta.addPages(Component.text("Day 07: \nThere is a storm outside. The waves are becoming higher and higher. I hope this nightmare will end soon. At least I saved this weird stone plate from a few days ago.\n\nI can't read the text written on that book.\nI have never seen this language before,"));
        bookmeta.addPages(Component.text("even the letters seem odd. The only thing I can do is copying them. Maybe someone will understand this, someday:\n\n\n\nŀリ〒ᔑリᓵŀ↸ ᓵ〒ᔑリリŀꖎ╎リㅓ"));
        bookmeta.addPages(Component.text("Day 12: \nThe ocean has reached into my house. I think this will not end. I have to move out and leave my home behind.\nI will ask my friend if I could stay a while at his house.\nNew adventures are waiting for me!"));
        book.setItemMeta(bookmeta);
        return book;
    }
}
