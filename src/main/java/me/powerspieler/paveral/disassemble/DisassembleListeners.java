package me.powerspieler.paveral.disassemble;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.advancements.AwardAdvancements;
import me.powerspieler.paveral.disassemble.events.DisassembleItemEvent;
import me.powerspieler.paveral.items.*;
import me.powerspieler.paveral.items.enchanced.Channeling;
import me.powerspieler.paveral.items.enchanced.Knockback;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static me.powerspieler.paveral.disassemble.AwakeTable.ALREADY_DISASSEMBLING;

public class DisassembleListeners implements Listener {

    @EventHandler
    public void onToolDrop(DisassembleItemEvent event){
        List<Item> raw = new ArrayList<>(event.getTable().getNearbyEntitiesByType(Item.class, 1,1,1));
        List<Item> items = raw.stream().filter(item -> item.getPersistentDataContainer().has(AwakeTable.DISASSEMBLE_CANDIDATE)).toList();
        // Handle Jigsaws
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.JIGSAW)){
            Optional<Item> first_match = items.stream()
                    .filter(item -> (item.getItemStack().getType() == Material.JIGSAW && item.getItemStack().getAmount() == 1)).findFirst();
            if(first_match.isPresent()){
                String itemtype = first_match.get().getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING);
                if(itemtype != null){
                    if(itemtype.equals("anticreepergrief")){
                        Items item = new AntiCreeperGrief();
                        disassembleItem(event.getTable(), first_match.get(), item.parts());
                    }
                }
            }
        }
        // Handle Enchanted_Book (Enhanced)
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.ENCHANTED_BOOK)){
            Optional<Item> first_match = items.stream()
                    .filter(item -> (item.getItemStack().getType() == Material.ENCHANTED_BOOK && item.getItemStack().getAmount() == 1)).findFirst();
            if(first_match.isPresent()){
                String itemtype = first_match.get().getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING);
                if(itemtype != null){
                    if(itemtype.equals("enhanced_channeling")){
                        Items item = new Channeling();
                        disassembleItem(event.getTable(), first_match.get(), item.parts());
                    }
                    if(itemtype.equals("enhanced_knockback")){
                        Items item = new Knockback();
                        disassembleItem(event.getTable(), first_match.get(), item.parts());
                    }
                }
            }
        }
        // Handle Warped_Fungus_On_A_Stick
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.WARPED_FUNGUS_ON_A_STICK)){
            Optional<Item> first_match = items.stream()
                    .filter(item -> (item.getItemStack().getType() == Material.WARPED_FUNGUS_ON_A_STICK && item.getItemStack().getAmount() == 1)).findFirst();
            if(first_match.isPresent()){
                String itemtype = first_match.get().getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING);
                if(itemtype != null){
                    if(itemtype.equals("bedrock_breaker")){
                        Items item = new BedrockBreaker();
                        disassembleItem(event.getTable(), first_match.get(), item.parts());
                    }
                    if(itemtype.equals("lightning_rod")){
                        Items item = new LightningRod();
                        disassembleItem(event.getTable(), first_match.get(), item.parts());
                    }
                    if(itemtype.equals("lightstaff")){
                        Items item = new LightStaff();
                        disassembleItem(event.getTable(), first_match.get(), item.parts());
                    }
                    if(itemtype.equals("wrench")){
                        Items item = new Wrench();
                        disassembleItem(event.getTable(), first_match.get(), item.parts());
                    }
                }
            }
        }
        // Handle Petrified_Oak_Slab (Chunkloader)
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.PETRIFIED_OAK_SLAB)){
            Optional<Item> first_match = items.stream()
                    .filter(item -> (item.getItemStack().getType() == Material.PETRIFIED_OAK_SLAB && item.getItemStack().getAmount() == 1)).findFirst();
            if(first_match.isPresent()){
                String itemtype = first_match.get().getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING);
                if(itemtype != null){
                    if(itemtype.equals("chunkloader")){
                        Items item = new Chunkloader();
                        disassembleItem(event.getTable(), first_match.get(), item.parts());
                    }
                }
            }
        }
    }

    private void disassembleItem(Location location, Item item, List<ItemStack> parts){
        final Audience targets = location.getWorld().filterAudience(member -> member instanceof Player player && player.getLocation().distanceSquared(location) < 625);
        item.setVelocity(new Vector(0,0,0));
        item.setCanPlayerPickup(false);
        item.setGravity(false);
        item.setWillAge(false);
        item.getWorld().spawnParticle(Particle.ENCHANT,item.getLocation(), 100, 0,0,0, 0.2);
        item.getPersistentDataContainer().set(ALREADY_DISASSEMBLING, PersistentDataType.INTEGER, 1);

        targets.playSound(Sound.sound(Key.key("entity.evoker.prepare_attack"), Sound.Source.AMBIENT, 1f, 1f), Sound.Emitter.self());
        BossBar progress = Bukkit.createBossBar(ChatColor.DARK_PURPLE + "Disassembling...", BarColor.PURPLE, BarStyle.SOLID);
        List<Entity> entities = new ArrayList<>(location.getNearbyEntities(25,25,25));
        for(Entity entity : entities){
            if(entity instanceof Player player){
                progress.addPlayer(player);
                progress.setVisible(true);
                if(AwardAdvancements.isAdvancementUndone(player, "first_dis")){
                    AwardAdvancements.grantAdvancement(player, "first_dis");
                }
            }
        }

        new BukkitRunnable() {
            int process = 0;
            Location particleloc;
            @Override
            public void run() {
                progress.setProgress((double) process / 200);

                if(process == 25){
                    item.getWorld().spawnParticle(Particle.GLOW, item.getLocation(), 100, 0,0,0, 1);
                    targets.playSound(Sound.sound(Key.key("block.portal.ambient"), Sound.Source.AMBIENT, 1f, 1.75f), Sound.Emitter.self());
                }

                if(process >= 40 && process <= 80){
                    particleloc = new Location(item.getLocation().getWorld(), location.getX(), location.getY(), location.getZ());
                    location.getWorld().spawnParticle(Particle.REVERSE_PORTAL, particleloc.add(0,1.5,0), 10, 0,0,0, 0.1);
                }
                if(process >= 40 && process <= 60){
                    targets.playSound(Sound.sound(Key.key("block.beacon.power_select"), Sound.Source.AMBIENT, 1f, 1.50f), Sound.Emitter.self());
                }
                if(process >= 60 && process <= 75){
                    targets.playSound(Sound.sound(Key.key("block.beacon.power_select"), Sound.Source.AMBIENT, 1f, 1.55f), Sound.Emitter.self());
                }
                if(process >= 75 && process <= 85){
                    targets.playSound(Sound.sound(Key.key("block.beacon.power_select"), Sound.Source.AMBIENT, 1f, 1.65f), Sound.Emitter.self());
                }
                if(process >= 85 && process <= 90){
                    targets.playSound(Sound.sound(Key.key("block.beacon.power_select"), Sound.Source.AMBIENT, 1f, 1.80f), Sound.Emitter.self());
                }
                if(process == 90){
                    targets.playSound(Sound.sound(Key.key("block.beacon.power_select"), Sound.Source.AMBIENT, 1f, 2.00f), Sound.Emitter.self());
                }

                if(process >= 100 && process <= 120){
                    particleloc = new Location(item.getLocation().getWorld(), location.getX(), location.getY(), location.getZ());
                    location.getWorld().spawnParticle(Particle.END_ROD, particleloc.add(0,1,0), 5, 0,0.25,0, 0);
                }
                if(process == 100){
                    targets.playSound(Sound.sound(Key.key("item.trident.thunder"), Sound.Source.AMBIENT, 1f, 1f), Sound.Emitter.self());
                }

                if(process == 135){
                    targets.playSound(Sound.sound(Key.key("entity.zombie.attack_iron_door"), Sound.Source.AMBIENT, 1f, 1f), Sound.Emitter.self());
                    item.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, item.getLocation(), 50, 0,0.25,0, 0.1);
                }

                if(process == 168){
                    targets.playSound(Sound.sound(Key.key("block.anvil.destroy"), Sound.Source.AMBIENT, 1f, 0.5f), Sound.Emitter.self());
                }

                if(process >= 200){
                    /* // Advancements moved to other sources - Keeping old code
                    if(item.getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING) != null && Objects.equals(item.getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "lightstaff")){
                        List<Entity> entities = new ArrayList<>(location.getNearbyEntities(15,15,15));
                        for(Entity entity : entities){
                            if(entity instanceof Player player){
                                if(AwardAdvancements.isAdvancementUndone(player, "dis_lightstaff")){
                                    AwardAdvancements.grantAdvancement(player, "dis_lightstaff");
                                }
                            }
                        }
                    }
                     */

                    item.remove();

                    for(ItemStack part : parts){
                        location.getWorld().dropItem(location, part);
                    }

                    progress.setVisible(false);
                    cancel();
                }
                process++;
            }
        }.runTaskTimer(Paveral.getPlugin(), 0,1L);
    }
}
