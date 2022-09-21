package me.powerspieler.paveral.discovery.diaries;

import me.powerspieler.paveral.discovery.Discovery;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;

public class AntiCreeperGrief implements Discovery {
    @Override
    public ItemStack build() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookmeta = (BookMeta) book.getItemMeta();
        bookmeta.getPersistentDataContainer().set(Constant.DISCOVERY, PersistentDataType.STRING, "diary_35");
        bookmeta.getPersistentDataContainer().set(Constant.IS_DIARY, PersistentDataType.INTEGER, 1);
        bookmeta.setAuthor("");
        bookmeta.setTitle("Diary [#35]");
        bookmeta.setGeneration(BookMeta.Generation.TATTERED);
        bookmeta.addPages(Component.text("Who likes these exploding green creatures? Seriously?!\nI hate wasting my time filling up creeper holes!\n\nBut I've got a solution!\nI just need to combine a creeper head, a firework star and sculk sensor. But how am I supposed to get two of these three"));
        bookmeta.addPages(Component.text("items? And how can I combine them together?\n\n\nThis could be the best thing in the world! Imagine ignoring these sneaky little ..."));
        book.setItemMeta(bookmeta);
        return book;
    }
}
