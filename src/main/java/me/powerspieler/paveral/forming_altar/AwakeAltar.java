package me.powerspieler.paveral.forming_altar;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.forming_altar.events.FormItemEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AwakeAltar implements Listener {

    private static HashSet<Material> itemlist;
    public static final NamespacedKey FORMING_CANDIDATE = new NamespacedKey(Paveral.getPlugin(), "forming_candidate");
    public static final NamespacedKey ALREADY_FORMING = new NamespacedKey(Paveral.getPlugin(), "already_forming");

    public AwakeAltar(){
        itemlist = new HashSet<>(25);
        // Enhancing (2)
        itemlist.add(Material.ENCHANTED_BOOK);
        itemlist.add(Material.NETHERITE_SCRAP);
        // Lightstaff (4) - Total: 6
        itemlist.add(Material.IRON_INGOT);
        itemlist.add(Material.COPPER_INGOT);
        itemlist.add(Material.REDSTONE_LAMP);
        itemlist.add(Material.WITHER_ROSE);
        // Bedrock Breaker (6) - Total: 12
        itemlist.add(Material.OBSIDIAN);
        itemlist.add(Material.PISTON);
        itemlist.add(Material.TNT);
        itemlist.add(Material.LEVER);
        itemlist.add(Material.OAK_TRAPDOOR);
        itemlist.add(Material.ANCIENT_DEBRIS);
        // Disassemble Tutorial Book (1) - Total: 13
        itemlist.add(Material.WRITTEN_BOOK);
        // Chunkloader (3) - Total: 16
        itemlist.add(Material.ENCHANTING_TABLE);
        itemlist.add(Material.LODESTONE);
        itemlist.add(Material.NETHER_STAR);
        // Piano Sword (4) - Total: 20
        itemlist.add(Material.JIGSAW);
        itemlist.add(Material.NETHERITE_SWORD);
        itemlist.add(Material.QUARTZ);
        itemlist.add(Material.BLACKSTONE);
        // String Blade (1) - Total: 21
        itemlist.add(Material.ORANGE_DYE);
        // Music Tools (4) - Total: 25
        itemlist.add(Material.NETHERITE_PICKAXE);
        itemlist.add(Material.NETHERITE_AXE);
        itemlist.add(Material.NETHERITE_SHOVEL);
        itemlist.add(Material.NETHERITE_HOE);
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
                                    Bukkit.getPluginManager().callEvent(new FormItemEvent(itementity, itementity.getLocation().getBlock().getLocation().add(0.5,-0.5,0.5)));
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
        final Audience targets = lodestone.getWorld().filterAudience(member -> member instanceof Player player && player.getLocation().distanceSquared(lodestone) < 625);
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
                    if(!(Tag.SLABS.getValues().contains(center.getRelative(x,0,z).getType()))){
                        return false;
                    }
                }
                if((Math.abs(x) == 2 && Math.abs(z) == 0) || (Math.abs(x) == 0 && Math.abs(z) == 2)){
                    if(!(center.getRelative(x,0,z).getType() == Material.PURPUR_PILLAR)){
                        return false;
                    }
                }
                if((Math.abs(x) == 1 && Math.abs(z) == 0) || (Math.abs(x) == 0 && Math.abs(z) == 1) || (Math.abs(x) == 0 && Math.abs(z) == 0)){
                    if(!(center.getRelative(x,0,z).getType() == Material.PURPUR_PILLAR)){
                        return false;
                    }
                }
                if(Math.abs(x) == 1 && Math.abs(z) == 1){
                    if(!(center.getRelative(x,0,z).getType() == Material.CUT_COPPER)){
                        if(!(center.getRelative(x,0,z).getType() == Material.AIR)){
                            center.getWorld().spawnParticle(Particle.WAX_ON, center.getLocation().add(x + 0.5,1,z + 0.5), 50, 0.25,0,0.25, 1);
                            targets.playSound(Sound.sound(Key.key("item.axe.scrape"), Sound.Source.BLOCK, 1f, 1f), Sound.Emitter.self());
                        }
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isAltarAvailable(Location lodestone){
        List<Item> raw = new ArrayList<>(lodestone.getNearbyEntitiesByType(Item.class,2, 2,2));
        List<Item> itemsforming = raw.stream().filter(item -> item.getPersistentDataContainer().has(ALREADY_FORMING)).toList();
        return itemsforming.isEmpty();
    }
}
