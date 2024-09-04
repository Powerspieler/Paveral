package me.powerspieler.paveral.items.helper;

import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TotemDisabler implements Listener {
    private static Set<String> taggedItems(){
        Set<String> tags = new HashSet<>();
        tags.add("bardic_inspiration");
        tags.add("lumberjacks_bass");
        tags.add("piano_sword");
        tags.add("resonating_pickaxe");
        tags.add("scythe_of_harmony");
        tags.add("string_blade");
        return tags;
    }

    public static List<Component> loreAddition(){
        List<Component> lore = new ArrayList<>();
        lore.add(Component.empty());
        lore.add(Component.text("When in Inventory:", NamedTextColor.GRAY)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text(" Disables Totem of Undying", NamedTextColor.RED)
                .decoration(TextDecoration.ITALIC, false));
        return lore;
    }


    private static boolean hasTaggedItemInInventory(Player player){
        Set<String> taggedItems = taggedItems();
        for (final ItemStack stack : player.getInventory().getContents()) {
            if (stack != null && stack.hasItemMeta() && stack.getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE)){
                if(taggedItems.contains(stack.getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING))){
                    return true;
                }
            }
        }
        return false;
    }

    @EventHandler
    private void onPlayerDeath(EntityResurrectEvent event) {
        if(event.getEntity() instanceof Player player){
            if(hasTaggedItemInInventory(player)){
                player.playSound(Sound.sound(Key.key("entity.villager.no"), Sound.Source.MASTER, 1f, 0.2f), Sound.Emitter.self());
                player.playSound(Sound.sound(Key.key("block.respawn_anchor.deplete"), Sound.Source.MASTER, 1f, 0.2f), Sound.Emitter.self());
                player.spawnParticle(Particle.TRIAL_SPAWNER_DETECTION, player.getLocation().add(0,1,0), 30, 0,0,0, 0.05);
                event.setCancelled(true);
            }
        }
    }
}
