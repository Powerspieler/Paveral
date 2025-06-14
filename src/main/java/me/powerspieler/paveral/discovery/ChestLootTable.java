package me.powerspieler.paveral.discovery;

import me.powerspieler.paveral.discovery.diaries.*;
import me.powerspieler.paveral.items.LightStaff;
import me.powerspieler.paveral.items.Wrench;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.ArrayList;
import java.util.List;

public class ChestLootTable implements Listener {

    @EventHandler
    public void onChestGenerate(LootGenerateEvent event){
        String chesttype = event.getLootTable().getKey().asString();
        // Bonk - Book
        if(chesttype.equals("minecraft:chests/woodland_mansion")){
            if(Math.random() < 0.25){
                List<ItemStack> loot = new ArrayList<>(event.getLoot());
                loot.add(new Bonk().build());
                event.setLoot(loot);
            }
        }
        // LightningRod - Book
        if(chesttype.equals("minecraft:chests/underwater_ruin_small")){
            if(Math.random() <= 0.25){
                List<ItemStack> loot = new ArrayList<>(event.getLoot());
                loot.add(new LightningRod().build());
                event.setLoot(loot);
            }
        }
        // Enhancing - Book
        if(chesttype.equals("minecraft:chests/stronghold_library")){
            if(Math.random() <= 0.25){
                List<ItemStack> loot = new ArrayList<>(event.getLoot());
                loot.add(new Enhancing().build());
                event.setLoot(loot);
            }
        }
        // Lightstaff - Item
        if(chesttype.equals("minecraft:chests/stronghold_crossing") || chesttype.equals("minecraft:chests/stronghold_corridor")){
            if(Math.random() <= 0.25){
                List<ItemStack> loot = new ArrayList<>(event.getLoot());
                ItemStack item = new LightStaff().recipe().result();
                Damageable damagemeta = (Damageable) item.getItemMeta();
                damagemeta.setDamage((int) (Math.random() * 100));
                item.setItemMeta(damagemeta);

                loot.add(item);
                event.setLoot(loot);
            }
        }
        // AntiCreeperGrief - Book // Not needed bc Guide
//        if(chesttype.equals("minecraft:chests/jungle_temple")){
//            if(Math.random() <= 0.25){
//                List<ItemStack> loot = new ArrayList<>(event.getLoot());
//                loot.add(new AntiCreeperGrief().build());
//                event.setLoot(loot);
//            }
//        }
        // Wrench - Item
        if(chesttype.equals("minecraft:chests/village/village_toolsmith")){
            if (Math.random() <= 0.25) {
                List<ItemStack> loot = new ArrayList<>(event.getLoot());
                loot.add(new Wrench().recipe().result());
                event.setLoot(loot);
            }
        }
        // Worldalterer - Book // TODO change to portal (structure blocks o.O)
        if(chesttype.equals("minecraft:chests/ancient_city_ice_box")){
            if (Math.random() <= 0.5) {
                List<ItemStack> loot = new ArrayList<>(event.getLoot());
                loot.add(new Worldalterer().build());
                event.setLoot(loot);
            }
        }
    }
}
