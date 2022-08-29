package de.powerspieler.paveral.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class CooldownCommand implements CommandExecutor {

    // You can make a class "CooldownCommand" and then inherit from it. So you don't have to write that code every time a command needs cooldown.
    private final HashMap<UUID, Long> cooldown;

    public CooldownCommand(){
        cooldown = new HashMap<>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(!this.cooldown.containsKey(player.getUniqueId()) || (System.currentTimeMillis() - cooldown.get(player.getUniqueId())) >= 10000){
                cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                player.sendMessage(Component.text("You just farted!"));
            } else {
                player.sendMessage(Component.text("" + (10000 - (System.currentTimeMillis() - cooldown.get(player.getUniqueId())))));
            }
        }
        return false;
    }
}
