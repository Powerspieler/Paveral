package me.powerspieler.paveral.commands;


import me.powerspieler.paveral.discovery.Discovery;
import me.powerspieler.paveral.discovery.tutorial.dis_book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(player.isOp()){

                /*NamespacedKey key = ro;
                AdvancementProgress progress = player.getAdvancementProgress(Bukkit.getAdvancement(key));
                for(String criteria : progress.getRemainingCriteria())
                    progress.awardCriteria(criteria);*/



                /*NamespacedKey soos = Bukkit.advancementIterator().next().getKey();
                Bukkit.broadcast(Component.text("soos: " + soos));

                Advancement foof = new Test();
                Bukkit.broadcast(Component.text("Adv: " + foof));
                Bukkit.broadcast(Component.text("Executed!"));
                player.getAdvancementProgress(foof).getRemainingCriteria();
                for(String criteria : player.getAdvancementProgress(foof).getRemainingCriteria())
                    player.getAdvancementProgress(foof).awardCriteria(criteria);*/


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
            } else player.sendMessage(Component.text("ERROR: No Op", NamedTextColor.RED));
        }
        return false;
    }
}
