package me.powerspieler.paveral.commands;

import me.powerspieler.paveral.items.AntiCreeperGrief;
import me.powerspieler.paveral.items.Chunkloader;
import me.powerspieler.paveral.items.Items;
import me.powerspieler.paveral.items.LightningRod;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ItemsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("cl")){
                    Items item = new Chunkloader();
                    player.getInventory().addItem(item.build());
                }
                if(args[0].equalsIgnoreCase("acg")){
                    Items item = new AntiCreeperGrief();
                    player.getInventory().addItem(item.build());
                }
                if(args[0].equalsIgnoreCase("lr")){
                    Items item = new LightningRod();
                    player.getInventory().addItem(item.build());
                }
            }
        }
        return false;
    }
}
