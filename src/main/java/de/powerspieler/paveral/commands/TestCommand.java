package de.powerspieler.paveral.commands;

import de.powerspieler.paveral.Paveral;
import de.powerspieler.paveral.items.AntiCreeperGrief;
import de.powerspieler.paveral.items.Items;
import de.powerspieler.paveral.items.LightStaff;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Jigsaw;
import org.bukkit.block.data.type.Light;
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
            // Creeperitem Give
            ItemStack creeperitem = new ItemStack(Material.JIGSAW);
            NamespacedKey creeperitemkey = new NamespacedKey(Paveral.getPlugin(), "creeperitem");
            ItemMeta creeperitemmeta = creeperitem.getItemMeta();
            creeperitemmeta.getPersistentDataContainer().set(creeperitemkey, PersistentDataType.INTEGER, 1);
            creeperitemmeta.setCustomModelData(1);
            creeperitem.setItemMeta(creeperitemmeta);
            player.getInventory().addItem(creeperitem);

            // Lightstaff Give
            /*
            ItemStack lightstaff = new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK);
            NamespacedKey lightstaffkey = new NamespacedKey(Paveral.getPlugin(), "lightstaff");
            NamespacedKey lightblocklevel = new NamespacedKey(Paveral.getPlugin(), "lightlevel");
            ItemMeta lightstaffmeta = lightstaff.getItemMeta();
            lightstaffmeta.getPersistentDataContainer().set(lightstaffkey, PersistentDataType.INTEGER, 1);
            lightstaffmeta.getPersistentDataContainer().set(lightblocklevel, PersistentDataType.INTEGER, 15);
            lightstaffmeta.setCustomModelData(4);
            lightstaff.setItemMeta(lightstaffmeta);
             */
            Items lightStaff = new LightStaff();
            player.getInventory().addItem(lightStaff.build());


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
