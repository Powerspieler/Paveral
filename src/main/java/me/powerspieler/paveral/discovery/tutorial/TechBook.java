package me.powerspieler.paveral.discovery.tutorial;

import me.powerspieler.paveral.discovery.Discovery;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;

public class TechBook implements Discovery {
    @Override
    public ItemStack build() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookmeta = (BookMeta) book.getItemMeta();
        bookmeta.getPersistentDataContainer().set(Constant.DISCOVERY, PersistentDataType.STRING, "tech_book");
        bookmeta.setAuthor("Powerspieler");
        bookmeta.setTitle("Paveralicious Additions - Vol. 3");
        final Component page1 = GsonComponentSerializer.gson().deserialize("{\"italic\":true,\"color\":\"dark_green\",\"extra\":[{\"italic\":true,\"color\":\"light_purple\",\"text\":\"icious\n               Additions\n\n\n\"},{\"italic\":false,\"color\":\"black\",\"text\":\"          [Vol. 3]\"}],\"text\":\"\n\n\n\n\n   Paveral\"}");
        final Component page2 = GsonComponentSerializer.gson().deserialize("{\"text\":\"\uEE01\",\"color\":\"white\"}");
        bookmeta.addPages(page1, page2);
        book.setItemMeta(bookmeta);
        return book;
    }
}
