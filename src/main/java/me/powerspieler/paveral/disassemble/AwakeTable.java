package me.powerspieler.paveral.disassemble;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.disassemble.events.DisassembleItemEvent;
import org.bukkit.*;
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

public class AwakeTable implements Listener {

    private static HashSet<Material> itemlist;
    public static final NamespacedKey DISASSEMBLE_CANDIDATE = new NamespacedKey(Paveral.getPlugin(), "disassemble_candidate");
    public static final NamespacedKey ALREADY_DISASSEMBLING = new NamespacedKey(Paveral.getPlugin(), "already_disassembling");

    public AwakeTable(){
        itemlist = new HashSet<>(5);
        itemlist.add(Material.WARPED_FUNGUS_ON_A_STICK);
        itemlist.add(Material.JIGSAW);
        itemlist.add(Material.PETRIFIED_OAK_SLAB);
        itemlist.add(Material.ENCHANTED_BOOK);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        Item itementity = event.getItemDrop();
        if(itemlist.contains(itementity.getItemStack().getType())){
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(itementity.isOnGround()){
                        Location layingon = itementity.getLocation();
                        layingon.add(0,-1,0);
                        if(layingon.getBlock().getType() == Material.SMITHING_TABLE){
                            if(onValidTableNS(layingon) || onValidTableWE(layingon)){
                                if(isTableAvailable(itementity.getLocation())){
                                    itementity.getPersistentDataContainer().set(DISASSEMBLE_CANDIDATE, PersistentDataType.INTEGER, 1);
                                    Bukkit.getPluginManager().callEvent(new DisassembleItemEvent(itementity, itementity.getLocation().getBlock().getLocation().add(0.5, -0.5, 0.5)));
                                }
                            }
                        }
                        cancel();
                    }
                }
            }.runTaskTimer(Paveral.getPlugin(), 0L, 5L);
        }
    }

    private boolean onValidTableNS(Location smith){
        Block center = smith.getBlock();
        int y;
        int z;

        for(y = 0; y <= 3; y++){
            for(z = -1; z <= 1; z++){
                if(y <= 2 && Math.abs(z) == 1){
                    if(!(Tag.WALLS.getValues().contains(center.getRelative(0, y, z).getType()))){
                        return false;
                    }
                }
                if(y == 3 && Math.abs(z) == 1){
                    if(!(Tag.STAIRS.getValues().contains(center.getRelative(0, y, z).getType()))){
                        return false;
                    }
                }
                if(y == 2 && z == 0){
                    if(!(center.getRelative(0, y, z).getType() == Material.END_ROD)){
                        return false;
                    }
                }
                if(y == 3 && z == 0){
                    if(!(center.getRelative(0, y, z).getType() == Material.IRON_BARS)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean onValidTableWE(Location smith){
        Block center = smith.getBlock();
        int x;
        int y;


        for(y = 0; y <= 3; y++){
            for(x = -1; x <= 1; x++){
                if(y <= 2 && Math.abs(x) == 1){
                    if(!(Tag.WALLS.getValues().contains(center.getRelative(x, y, 0).getType()))){
                        return false;
                    }
                }
                if(y == 3 && Math.abs(x) == 1){
                    if(!(Tag.STAIRS.getValues().contains(center.getRelative(x, y, 0).getType()))){
                        return false;
                    }
                }
                if(y == 2 && x == 0){
                    if(!(center.getRelative(x, y, 0).getType() == Material.END_ROD)){
                        return false;
                    }
                }
                if(y == 3 && x == 0){
                    if(!(center.getRelative(x, y, 0).getType() == Material.IRON_BARS)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isTableAvailable(Location smith){
        List<Item> raw = new ArrayList<>(smith.getNearbyEntitiesByType(Item.class, 5, 5 ,5));
        List<Item> itemsdis = raw.stream().filter(item -> item.getPersistentDataContainer().has(ALREADY_DISASSEMBLING)).toList();
        return itemsdis.isEmpty();
    }
}
