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
                Discovery book = new Bonk();
                loot.add(book.build());
                event.setLoot(loot);
            }
        }
        // LightningRod - Book
        if(chesttype.equals("minecraft:chests/underwater_ruin_small")){
            if(Math.random() <= 0.25){
                List<ItemStack> loot = new ArrayList<>(event.getLoot());
                Discovery book = new LightningRod();
                loot.add(book.build());
                event.setLoot(loot);
            }
        }
        // Enhancing - Book
        if(chesttype.equals("minecraft:chests/stronghold_library")){
            if(Math.random() <= 0.25){
                List<ItemStack> loot = new ArrayList<>(event.getLoot());
                Discovery book = new Enhancing();
                loot.add(book.build());
                event.setLoot(loot);
            }
        }
        // Lightstaff - Item
        if(chesttype.equals("minecraft:chests/stronghold_crossing") || chesttype.equals("minecraft:chests/stronghold_corridor")){
            if(Math.random() <= 0.25){
                List<ItemStack> loot = new ArrayList<>(event.getLoot());
                Items lightstaff = new LightStaff();

                ItemStack item = lightstaff.build();
                Damageable damagemeta = (Damageable) item.getItemMeta();
                damagemeta.setDamage((int) (Math.random() * 100));
                item.setItemMeta(damagemeta);

                loot.add(item);
                event.setLoot(loot);
            }
        }
        // AntiCreeperGrief - Book
        if(chesttype.equals("minecraft:chests/jungle_temple")){
            if(Math.random() <= 0.25){
                List<ItemStack> loot = new ArrayList<>(event.getLoot());
                Discovery book = new AntiCreeperGrief();
                loot.add(book.build());
                event.setLoot(loot);
            }
        }
        // Wrench - Item
        if(chesttype.equals("minecraft:chests/village/village_toolsmith")){
            if (Math.random() <= 0.25) {
                List<ItemStack> loot = new ArrayList<>(event.getLoot());
                Items item = new Wrench();
                loot.add(item.build());
                event.setLoot(loot);
            }
        }
        // Worldalterer - Book
        if(chesttype.equals("minecraft:chests/ancient_city_ice_box")){
            if (Math.random() <= 0.5) {
                List<ItemStack> loot = new ArrayList<>(event.getLoot());
                Discovery book = new Worldalterer();
                loot.add(book.build());
                event.setLoot(loot);
            }
        }
    }
}
