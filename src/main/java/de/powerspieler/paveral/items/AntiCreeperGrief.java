package de.powerspieler.paveral.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Random;

public class AntiCreeperGrief implements Listener {
    // Convert Random Int to ColorType
    private Color getColor(int i) {
        Color c = null;
        if(i==1){
            c=Color.AQUA;
        }
        if(i==2){
            c=Color.BLACK;
        }
        if(i==3){
            c=Color.BLUE;
        }
        if(i==4){
            c=Color.FUCHSIA;
        }
        if(i==5){
            c=Color.GRAY;
        }
        if(i==6){
            c=Color.GREEN;
        }
        if(i==7){
            c=Color.LIME;
        }
        if(i==8){
            c=Color.MAROON;
        }
        if(i==9){
            c=Color.NAVY;
        }
        if(i==10){
            c=Color.OLIVE;
        }
        if(i==11){
            c=Color.ORANGE;
        }
        if(i==12){
            c=Color.PURPLE;
        }
        if(i==13){
            c=Color.RED;
        }
        if(i==14){
            c=Color.SILVER;
        }
        if(i==15){
            c=Color.TEAL;
        }
        if(i==16){
            c=Color.WHITE;
        }
        if(i==17){
            c=Color.YELLOW;
        }

        return c;
    }

    // handle Creeperexplosion
    @EventHandler
    public void onCreeperExplosion(EntityExplodeEvent event) {
        if (!(event.getEntityType() == EntityType.CREEPER)) return;
        Entity creeper = event.getEntity();

        // Check for Player in 8x8x8 Box
        List<Entity> playernearby = creeper.getNearbyEntities(8D,8D,8D);
        for(Entity entity : playernearby){
            if(entity instanceof Player player){
                ItemStack creeperitem = new ItemStack(Material.JIGSAW);
                ItemMeta creeperitemmeta = creeperitem.getItemMeta();
                creeperitemmeta.setCustomModelData(1);
                creeperitem.setItemMeta(creeperitemmeta);

                if(player.getInventory().contains(creeperitem)){
                    Firework fw = (Firework) creeper.getWorld().spawnEntity(creeper.getLocation(), EntityType.FIREWORK);
                    FireworkMeta fwm = fw.getFireworkMeta();
                    Random r = new Random();
                    int r1i = r.nextInt(17) + 1;
                    int r2i = r.nextInt(17) + 1;
                    Color c1 = getColor(r1i);
                    Color c2 = getColor(r2i);
                    FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(FireworkEffect.Type.BURST).trail(r.nextBoolean()).build();
                    fwm.addEffect(effect);
                    fw.setFireworkMeta(fwm);
                    fw.detonate();
                    event.setCancelled(true);
                    return;
                }
            }
        }
        // Check of Itemframe in 50x50x50 Box
        List<Entity> itemframenearby = creeper.getNearbyEntities(50D,50D,50D);
        for(Entity entity : itemframenearby){
            if(entity instanceof ItemFrame itemframe){
                ItemStack creeperitem = new ItemStack(Material.JIGSAW);
                ItemMeta creeperitemmeta = creeperitem.getItemMeta();
                creeperitemmeta.setCustomModelData(1);
                creeperitem.setItemMeta(creeperitemmeta);

                if(itemframe.getItem().equals(creeperitem)){
                    Firework fw = (Firework) creeper.getWorld().spawnEntity(creeper.getLocation(), EntityType.FIREWORK);
                    FireworkMeta fwm = fw.getFireworkMeta();
                    Random r = new Random();
                    int r1i = r.nextInt(17) + 1;
                    int r2i = r.nextInt(17) + 1;
                    Color c1 = getColor(r1i);
                    Color c2 = getColor(r2i);
                    FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(FireworkEffect.Type.BURST).trail(r.nextBoolean()).build();
                    fwm.addEffect(effect);
                    fw.setFireworkMeta(fwm);
                    fw.detonate();
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
