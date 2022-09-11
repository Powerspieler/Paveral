package me.powerspieler.paveral.commands;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.items.BedrockBreaker;
import me.powerspieler.paveral.items.Chunkloader;
import me.powerspieler.paveral.items.Items;
import me.powerspieler.paveral.items.LightningRod;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
    private static final NamespacedKey CHUNKLOADS = new NamespacedKey(Paveral.getPlugin(), "chunkloads");
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){

            Items lr = new LightningRod();
            player.getInventory().addItem(lr.build());

/*            if(args.length == 1){
                if(args[0].equalsIgnoreCase("get")){
                    Items test = new Chunkloader();
                    player.getInventory().addItem(test.build());
                }
                if(args[0].equalsIgnoreCase("remove")){
                    player.getChunk().getPersistentDataContainer().remove(CHUNKLOADS);
                }

            } else {
                int value = player.getChunk().getPersistentDataContainer().get(CHUNKLOADS, PersistentDataType.INTEGER);
                Bukkit.broadcast(Component.text("Is Chunkforce loaded?: " + player.getChunk().isForceLoaded()));
                Bukkit.broadcast(Component.text("Chunkloads Value: " + value));
            }*/

            /*
            // Creeperitem Give
            Items anticreeperitem = new AntiCreeperGrief();
            player.getInventory().addItem(anticreeperitem.build());

            // Lightstaff Give
            Items lightStaff = new LightStaff();
            player.getInventory().addItem(lightStaff.build());



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
