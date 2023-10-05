package me.powerspieler.paveral.commands;

import me.powerspieler.paveral.items.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ItemsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(player.isOp()){
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
                    if(args[0].equalsIgnoreCase("bb")){
                        Items item = new BedrockBreaker();
                        player.getInventory().addItem(item.build());
                    }
                    if(args[0].equalsIgnoreCase("ls")){
                        Items item = new LightStaff();
                        player.getInventory().addItem(item.build());
                    }
                    if(args[0].equalsIgnoreCase("w")){
                        Items item = new Wrench();
                        player.getInventory().addItem(item.build());
                    }
                    if(args[0].equalsIgnoreCase("wa")){
                        Items item = new Worldalterer();
                        player.getInventory().addItem(item.build());
                    }
                }
            } else player.sendMessage(Component.text("ERROR: No Op", NamedTextColor.RED));
        }
        return false;
    }
}
