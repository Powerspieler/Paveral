package me.powerspieler.paveral.forge;


import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.forge.events.ForgeItemEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AwakeForge implements Listener {
    private static HashSet<Material> itemlist;
    public static final NamespacedKey FORGING_CANDIDATE = new NamespacedKey(Paveral.getPlugin(), "forging_candidate");
    public static final NamespacedKey ALREADY_FORGING = new NamespacedKey(Paveral.getPlugin(), "already_forging");

    public AwakeForge(){
        itemlist = new HashSet<>(10);
        // Wrench (1)
        itemlist.add(Material.IRON_INGOT);
        // Chunkloader (4)
        itemlist.add(Material.NETHER_STAR);
        itemlist.add(Material.LODESTONE);
        itemlist.add(Material.ENCHANTING_TABLE);
        itemlist.add(Material.OBSIDIAN);
        // CreeperItem (3)
        itemlist.add(Material.CREEPER_HEAD);
        itemlist.add(Material.FIREWORK_STAR);
        itemlist.add(Material.SCULK_SENSOR);
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
                        if(layingon.getBlock().getType() == Material.ANVIL){
                            if(onValidForge(layingon)){
                                if(isForgeAvailable(itementity.getLocation())){
                                    itementity.getPersistentDataContainer().set(FORGING_CANDIDATE, PersistentDataType.INTEGER, 1);
                                    Bukkit.getPluginManager().callEvent(new ForgeItemEvent(itementity, itementity.getLocation().getBlock().getLocation().add(0.5,-0.5,0.5)));
                                }
                            }
                        }
                        cancel();
                    }
                }
            }.runTaskTimer(Paveral.getPlugin(), 0L, 5L);
        }
    }

    private boolean onValidForge(Location anvil){
        anvil.add(0,-1,0);
        Block center = anvil.getBlock();
        int x;
        int y;
        int z;

        for(y = 0; y <= 3; y++){
            for(x = -1; x <= 1; x++){
                for(z = -1; z <= 1; z++){
                    if(Math.abs(x * 3) == 3 && Math.abs(z) == 0 || Math.abs(x) == 0 && Math.abs(z * 3) == 3){
                        if(y == 0){
                            if(!(center.getRelative(x * 3, y, z * 3).getType() == Material.POLISHED_BLACKSTONE_BRICKS)){
                                return false;
                            }
                        }
                        if(y == 1){
                            if(!(center.getRelative(x * 3, y, z * 3).getType() == Material.POLISHED_BLACKSTONE_BRICK_WALL)){
                                return false;
                            }
                        }
                        if(y == 2){
                            if(!(center.getRelative(x * 3, y, z * 3).getType() == Material.HOPPER)){
                                return false;
                            }
                        }
                        if(y == 3){
                            if(!(center.getRelative(x * 3, y, z * 3).getType() == Material.LAVA_CAULDRON || center.getRelative(x * 3, y, z * 3).getType() == Material.CAULDRON)){
                                return false;
                            }
                        }
                    }
                }
            }
        }

        for(x = -2; x <= 2; x++){
            for(z = -2; z <= 2; z++){
                if((Math.abs(x) == 2 && Math.abs(z) == 1) || (Math.abs(x) == 1 && Math.abs(z) == 2)){
                    if(!(center.getRelative(x,0,z).getType() == Material.COBBLED_DEEPSLATE_SLAB)){
                        return false;
                    }
                }
                if(Math.abs(x) == 1 && Math.abs(z) == 1){
                    if(!(center.getRelative(x,0,z).getType() == Material.COBBLED_DEEPSLATE)){
                        return false;
                    }
                }
                if((Math.abs(x) == 2 && Math.abs(z) == 0) || (Math.abs(x) == 0 && Math.abs(z) == 2)){
                    if(!(center.getRelative(x,0,z).getType() == Material.POLISHED_BASALT)){
                        return false;
                    }
                }
                if((Math.abs(x) == 1 && Math.abs(z) == 0) || (Math.abs(x) == 0 && Math.abs(z) == 1)){
                    if(!(center.getRelative(x,0,z).getType() == Material.POLISHED_BASALT)){
                        return false;
                    }
                }
                if(Math.abs(x) == 0 && Math.abs(z) == 0){
                    if(!(center.getRelative(x,0,z).getType() == Material.LODESTONE)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isForgeAvailable(Location lodestone){
        List<Item> raw = new ArrayList<>(lodestone.getNearbyEntitiesByType(Item.class,5, 5,5));
        List<Item> itemsforming = raw.stream().filter(item -> item.getPersistentDataContainer().has(ALREADY_FORGING)).toList();
        return itemsforming.isEmpty();
    }
}
