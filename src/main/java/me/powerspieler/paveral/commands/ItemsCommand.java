package me.powerspieler.paveral.commands;

import me.powerspieler.paveral.items.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Breedable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ItemsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(player.isOp()){
                if(args.length == 1){
                    if(args[0].equalsIgnoreCase("cl")){
                        player.getInventory().addItem(new Chunkloader().recipe().result());
                    }
                    if(args[0].equalsIgnoreCase("acg")){
                        player.getInventory().addItem(new AntiCreeperGrief().recipe().result());
                    }
                    if(args[0].equalsIgnoreCase("lr")){
                        player.getInventory().addItem(new LightningRod().recipe().result());
                    }
                    if(args[0].equalsIgnoreCase("bb")){
                        player.getInventory().addItem(new BedrockBreaker().recipe().result());
                    }
                    if(args[0].equalsIgnoreCase("ls")){
                        player.getInventory().addItem(new LightStaff().recipe().result());
                    }
                    if(args[0].equalsIgnoreCase("w")){
                        player.getInventory().addItem(new Wrench().recipe().result());
                    }
                    if(args[0].equalsIgnoreCase("wa")){
                        //Items item = new Worldalterer();
                        player.getInventory().addItem(new Worldalterer().recipe().result());
                    }
                }
            } else player.sendMessage(Component.text("ERROR: No Op", NamedTextColor.RED));
        }
        return false;
    }
}
