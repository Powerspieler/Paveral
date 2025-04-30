package me.powerspieler.paveral.discovery;

import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public abstract class DiscoveryBook {
    protected String keyString;
    protected String author;
    protected String title;
    protected BookMeta.Generation generation;
    protected List<Component> pages;
    protected boolean isDiary;

    public DiscoveryBook(String keyString, String author, String title, BookMeta.Generation generation, List<Component> pages, boolean isDiary) {
        this.keyString = keyString;
        this.author = author;
        this.title = title;
        this.generation = generation;
        this.pages = pages;
        this.isDiary = isDiary;
    }

    public ItemStack build(){
        ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();
        bookMeta.getPersistentDataContainer().set(Constant.DISCOVERY, PersistentDataType.STRING, keyString);
        if(isDiary) bookMeta.getPersistentDataContainer().set(Constant.IS_DIARY, PersistentDataType.INTEGER, 1);
        bookMeta.setAuthor(author);
        bookMeta.setTitle(title);
        bookMeta.setGeneration(generation);
        for(Component page : pages){
            bookMeta.addPages(page);
        }
        itemStack.setItemMeta(bookMeta);
        return itemStack;
    }
}
