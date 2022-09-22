package me.powerspieler.paveral.discovery.tutorial;

import me.powerspieler.paveral.discovery.Discovery;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;

public class DisBook implements Discovery {

    @Override
    public ItemStack build() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookmeta = (BookMeta) book.getItemMeta();
        bookmeta.getPersistentDataContainer().set(Constant.DISCOVERY, PersistentDataType.STRING, "dis_book");
        bookmeta.setAuthor("Powerspieler");
        bookmeta.setTitle("Paveralicious Additions - Vol. 2");
        final Component page1 = GsonComponentSerializer.gson().deserialize("{\"italic\":true,\"color\":\"dark_green\",\"extra\":[{\"italic\":true,\"color\":\"light_purple\",\"text\":\"icious\n               Additions\n\n\n\"},{\"italic\":false,\"color\":\"black\",\"text\":\"          [Vol. 2]\"}],\"text\":\"\n\n\n\n\n   Paveral\"}");
        final Component page2 = GsonComponentSerializer.gson().deserialize("{\"color\":\"dark_purple\",\"extra\":[{\"color\":\"red\",\"text\":\"The Disassembling Table\"}],\"text\":\"I am glad you made it to the second volume of the documentation of Paveralicious Additions. Another structure is described on the following pages:\n\n\"}");
        final Component page3 = GsonComponentSerializer.gson().deserialize("{\"color\":\"dark_purple\",\"text\":\"You may have already found some new interesting items and tools laying in chests all around your world..\nWait you haven't?\nThen go and search for them!\n\nIf you already think these items are uncraftable, don't worry this is where\"}");
        final Component page4 = GsonComponentSerializer.gson().deserialize("{\"color\":\"dark_purple\",\"text\":\"the Disassembling Table comes into place. You can use it to take tools apart and learn which materials had been used for the original forming.\n\nThe Blueprint is shown on the following pages.\"}");
        final Component page5 = GsonComponentSerializer.gson().deserialize("{\"color\":\"dark_purple\",\"extra\":[{\"color\":\"dark_green\",\"text\":\"6x Wall\n2x Stair\n1x End Rod\n1x Iron Bars\n1x Smithing Table\"},{\"color\":\"dark_purple\",\"text\":\"\n\n\nThe type of wall or stair doesn\\'t matter.\nBuild it the way you like it!\"}],\"text\":\"Materials:\n\n\"}");
        final Component page6 = GsonComponentSerializer.gson().deserialize("{\"underlined\":true,\"color\":\"red\",\"extra\":[{\"underlined\":false,\"color\":\"dark_purple\",\"text\":\" Side View\n\n\n\"},{\"underlined\":false,\"color\":\"gold\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Stair (Connects With Iron Bars;\nOn Top of Wall)\"}},\"text\":\"⬛\"},{\"underlined\":false,\"color\":\"gray\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Iron Bars\"}},\"text\":\"⬛\"},{\"underlined\":false,\"color\":\"gold\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Stair (Connects With Iron Bars;\nOn Top of Wall)\"}},\"text\":\"⬛\n\"},{\"underlined\":false,\"color\":\"blue\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Wall\"}},\"text\":\"⬛\"},{\"underlined\":false,\"color\":\"light_purple\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"End Rod (Facing Down)\"}},\"text\":\"⬛\"},{\"underlined\":false,\"color\":\"blue\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Wall\"}},\"text\":\"⬛\n\"},{\"underlined\":false,\"color\":\"blue\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Wall\"}},\"text\":\"⬛\"},{\"underlined\":false,\"color\":\"white\",\"text\":\"⬛\"},{\"underlined\":false,\"color\":\"blue\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Wall\"}},\"text\":\"⬛\n\"},{\"underlined\":false,\"color\":\"blue\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Wall\"}},\"text\":\"⬛\"},{\"underlined\":false,\"color\":\"dark_gray\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Smithing Table\"}},\"text\":\"⬛\"},{\"underlined\":false,\"color\":\"blue\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Wall\"}},\"text\":\"⬛\n\n\"},{\"underlined\":false,\"color\":\"dark_purple\",\"text\":\"Hover for Details\"}],\"text\":\"[!]\"}");
        final Component page7 = GsonComponentSerializer.gson().deserialize("{\"color\":\"dark_purple\",\"text\":\"Throw your item to be disassembled onto the smiting table to start the process. This process cannot be undone or interrupted, so keep an eye out for the resulting materials to reform the item on the altar again.\"}");
        final Component page8 = GsonComponentSerializer.gson().deserialize("{\"text\":\"Due to the machines roughness enchantments cannot be extracted!\",\"color\":\"dark_purple\"}");
        bookmeta.addPages(page1, page2, page3, page4, page5, page6, page7, page8);
        book.setItemMeta(bookmeta);
        return book;
    }
}
