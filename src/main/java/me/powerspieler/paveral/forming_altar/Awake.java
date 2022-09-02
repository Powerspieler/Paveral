package me.powerspieler.paveral.forming_altar;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.forming_altar.events.FormingItemOnAltar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Awake implements Listener {

    private static HashSet<Material> itemlist;
    public static final NamespacedKey FORMING_CANDIDATE = new NamespacedKey(Paveral.getPlugin(), "forming_candidate");
    public static final NamespacedKey ALREADY_FORMING = new NamespacedKey(Paveral.getPlugin(), "already_forming");

    public Awake(){
        itemlist = new HashSet<>(20);
        itemlist.add(Material.WARPED_FUNGUS_ON_A_STICK);
        itemlist.add(Material.STICK);
        itemlist.add(Material.ENCHANTED_BOOK);
        itemlist.add(Material.NETHERITE_SCRAP);
        // CreeperItem (3)
        itemlist.add(Material.CREEPER_HEAD);
        itemlist.add(Material.FIREWORK_STAR);
        itemlist.add(Material.SCULK_SENSOR);
        // Lightstaff (4)
        itemlist.add(Material.IRON_INGOT);
        itemlist.add(Material.COPPER_INGOT);
        itemlist.add(Material.REDSTONE_LAMP);
        itemlist.add(Material.WITHER_ROSE);
        // Bedrock Breaker (6)
        itemlist.add(Material.OBSIDIAN);
        itemlist.add(Material.PISTON);
        itemlist.add(Material.TNT);
        itemlist.add(Material.LEVER);
        itemlist.add(Material.OAK_TRAPDOOR);
        itemlist.add(Material.ANCIENT_DEBRIS);
    }



    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        Item itementity = event.getItemDrop();
        if(itemlist.contains(itementity.getItemStack().getType())){
            new BukkitRunnable(){
                @Override
                public void run() {
                    if(itementity.isOnGround()){
                        Location layingon = itementity.getLocation();
                        layingon.add(0,-1,0);
                        if(layingon.getBlock().getType() == Material.LODESTONE){
                            if(onValidAltar(layingon)){
                                if(isAltarAvailable(itementity.getLocation())){
                                    itementity.getPersistentDataContainer().set(FORMING_CANDIDATE, PersistentDataType.INTEGER, 1);
                                    Bukkit.getPluginManager().callEvent(new FormingItemOnAltar(itementity, itementity.getLocation().getBlock().getLocation().add(0.5,-0.5,0.5)));
                                }
                            }
                        }
                        cancel();
                    }
                }
            }.runTaskTimer(Paveral.getPlugin(), 0L, 5L);
        }
    }

    private boolean onValidAltar(Location lodestone){
        lodestone.add(0,-1,0);
        Block center = lodestone.getBlock();
        int x;
        int y;
        int z;

        for(y = 1; y <= 3; y++){
            for(x = -1; x <= 1; x++){
                for(z = -1; z <= 1; z++){
                    if(y == 1 && Math.abs(x * 2) == 2 && Math.abs(z * 2) == 2){
                        if(!(center.getRelative(x * 2,y,z * 2).getType() == Material.CRYING_OBSIDIAN)){
                            return false;
                        }
                    }
                    if(y == 2 && Math.abs(x * 2) == 2 && Math.abs(z * 2) == 2){
                        if(!(center.getRelative(x * 2,y,z * 2).getType() == Material.RESPAWN_ANCHOR)){
                            return false;
                        }
                    }
                    if(y == 3 && Math.abs(x * 2) == 2 && Math.abs(z * 2) == 2){
                        if(!(center.getRelative(x * 2,y,z * 2).getType() == Material.AMETHYST_CLUSTER)){
                            return false;
                        }
                    }
                }
            }
        }

        for(x = -3; x <= 3; x++){
            for(z = -3; z <= 3; z++){
                if((Math.abs(x) == 3 && Math.abs(z) == 0) || (Math.abs(x) == 0 && Math.abs(z) == 3)){
                    if(!(Tag.STAIRS.getValues().contains(center.getRelative(x,0,z).getType()))){
                        return false;
                    }
                }
                if(Math.abs(x) == 2 && Math.abs(z) == 2){
                    if(!(center.getRelative(x,0,z).getType() == Material.CRYING_OBSIDIAN)){
                        return false;
                    }
                }
                if((Math.abs(x) == 2 && Math.abs(z) == 1) || (Math.abs(x) == 1 && Math.abs(z) == 2)){
                    if(!(center.getRelative(x,0,z).getType() == Material.END_PORTAL_FRAME)){
                        return false;
                    }
                }
                if((Math.abs(x) == 2 && Math.abs(z) == 0) || (Math.abs(x) == 0 && Math.abs(z) == 2)){
                    if(!(center.getRelative(x,0,z).getType() == Material.END_PORTAL_FRAME)){
                        return false;
                    }
                }
                if((Math.abs(x) == 1 && Math.abs(z) == 0) || (Math.abs(x) == 0 && Math.abs(z) == 1) || (Math.abs(x) == 0 && Math.abs(z) == 0)){
                    if(!(center.getRelative(x,0,z).getType() == Material.PURPUR_PILLAR)){
                        return false;
                    }
                }
                if(Math.abs(x) == 1 && Math.abs(z) == 1){
                    if(!(center.getRelative(x,0,z).getType() == Material.CUT_COPPER_SLAB)){
                        if(!(center.getRelative(x,0,z).getType() == Material.AIR)){
                            center.getWorld().spawnParticle(Particle.WAX_ON, center.getLocation().add(x + 0.5,0.5,z + 0.5), 50, 0.25,0,0.25, 1);
                            center.getWorld().playSound(Sound.sound(Key.key("item.axe.scrape"), Sound.Source.BLOCK, 1f, 1f));
                        }
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isAltarAvailable(Location lodestone){
        Collection<Item> raw = lodestone.getNearbyEntitiesByType(Item.class,5, 5,5);
        List<Item> itemsforming = raw.stream().filter(item -> item.getPersistentDataContainer().has(ALREADY_FORMING)).toList();
        return itemsforming.isEmpty();
    }
}
