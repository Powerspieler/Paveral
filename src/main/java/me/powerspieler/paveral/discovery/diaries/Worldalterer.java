package me.powerspieler.paveral.discovery.diaries;

import me.powerspieler.paveral.discovery.Discovery;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;

public class Worldalterer implements Discovery {
    @Override
    public ItemStack build() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookmeta = (BookMeta) book.getItemMeta();
        bookmeta.getPersistentDataContainer().set(Constant.DISCOVERY, PersistentDataType.STRING, "worldalterer");
        bookmeta.getPersistentDataContainer().set(Constant.IS_DIARY, PersistentDataType.INTEGER, 1);
        bookmeta.setAuthor("");
        bookmeta.setTitle("EMI Research");
        bookmeta.setGeneration(BookMeta.Generation.TATTERED);
        bookmeta.addPages(Component.text("PLACEHOLDER"));
        bookmeta.addPages(Component.text("Day ??: \nI still don't know what day this is.\nBut at least i can start writing a new journal again since the book of my last one got full.\nNothing happend today, i just wanted to write again. So maybe there will be some interesting tomorrow.."));
        bookmeta.addPages(Component.text("Day 12: \nThe ocean has reached into my house. I think this will not end. I have to move out and leave my home behind.\nI will ask my friend if I could stay a while at his house.\nNew adventures are waiting for me!"));
        book.setItemMeta(bookmeta);
        return book;
    }
}
