package de.powerspieler.paveral.commands;

import de.powerspieler.paveral.Paveral;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Jigsaw;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            ItemStack creeperitem = new ItemStack(Material.JIGSAW);
            NamespacedKey creeperitemkey = new NamespacedKey(Paveral.getPlugin(), "creeperitem");
            ItemMeta creeperitemmeta = creeperitem.getItemMeta();
            creeperitemmeta.getPersistentDataContainer().set(creeperitemkey, PersistentDataType.INTEGER, 1);
            creeperitemmeta.setCustomModelData(1);
            creeperitem.setItemMeta(creeperitemmeta);
            player.getInventory().addItem(creeperitem);


            /*
            ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta itemMeta = item.getItemMeta();
            TextComponent test = Component.text("This is one too");
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("This is a test"));
            lore.add(test);
            itemMeta.lore(lore);
            item.setItemMeta(itemMeta);
            Inventory inventory = Bukkit.createInventory(null, 9*3);
            inventory.addItem(item);
            player.openInventory(inventory);
            */
        }
        return false;
    }
}
