package me.powerspieler.paveral.discovery.papers;

import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class Paper {







    
    public static ItemStack disassemblePaper() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(Constant.DISCOVERY, PersistentDataType.STRING, "disassemble_paper");
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Have you ever wondered if you could")
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("enhance the documentation book on the altar?")
                .decoration(TextDecoration.ITALIC, false));
        itemMeta.lore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }


}
