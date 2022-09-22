package me.powerspieler.paveral.forge;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.forge.events.ForgeItemEvent;
import me.powerspieler.paveral.items.AntiCreeperGrief;
import me.powerspieler.paveral.items.Chunkloader;
import me.powerspieler.paveral.items.Items;
import me.powerspieler.paveral.items.Wrench;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.*;
import org.bukkit.block.Block;
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

import static me.powerspieler.paveral.forge.AwakeForge.ALREADY_FORGING;
import static me.powerspieler.paveral.forming_altar.AwakeAltar.ALREADY_FORMING;

public class ForgeListener implements Listener {

    @EventHandler
    public void onIngredientDrop(ForgeItemEvent event){
        List<Item> raw = new ArrayList<>(event.getForge().getNearbyEntitiesByType(Item.class, 1,1,1));
        List<Item> items = raw.stream().filter(item -> item.getPersistentDataContainer().has(AwakeForge.FORGING_CANDIDATE)).toList();
        //AntiCreeperGrief
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.CREEPER_HEAD) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.FIREWORK_STAR) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.SCULK_SENSOR)){
            List<Item> formingitems = items.stream()
                    .filter(item -> {
                        Material type = item.getItemStack().getType();
                        return (type == Material.CREEPER_HEAD && item.getItemStack().getAmount() == 1) || (type == Material.FIREWORK_STAR && item.getItemStack().getAmount() == 1) || (type == Material.SCULK_SENSOR  && item.getItemStack().getAmount() == 1);
                    }).toList();
            if(formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.CREEPER_HEAD) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.FIREWORK_STAR) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.SCULK_SENSOR)){
                if(isFueled(event.getForge())){
                    Items acg = new AntiCreeperGrief();
                    forgeItem(event.getForge(), formingitems, acg.build());
                }
            }
            return;
        }
        //Chunkloader
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.NETHER_STAR) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.OBSIDIAN) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.ENCHANTING_TABLE) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.LODESTONE)){
            List<Item> formingitems = items.stream()
                    .filter(item -> {
                        Material type = item.getItemStack().getType();
                        return (type == Material.NETHER_STAR && item.getItemStack().getAmount() == 3) || (type == Material.OBSIDIAN && item.getItemStack().getAmount() == 2) || (type == Material.ENCHANTING_TABLE  && item.getItemStack().getAmount() == 1) || (type == Material.LODESTONE  && item.getItemStack().getAmount() == 1);
                    }).toList();
            if(formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.NETHER_STAR) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.OBSIDIAN) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.ENCHANTING_TABLE) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.LODESTONE)){
                if(isFueled(event.getForge())){
                    Items acg = new Chunkloader();
                    forgeItem(event.getForge(), formingitems, acg.build());
                }
            }
            return;
        }
        //Wrench
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.IRON_INGOT)){
            List<Item> formingitems = items.stream()
                    .filter(item -> {
                        Material type = item.getItemStack().getType();
                        return (type == Material.IRON_INGOT && item.getItemStack().getAmount() == 4);
                    }).toList();
            if(formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.IRON_INGOT)){
                if(isFueled(event.getForge())){
                    Items acg = new Wrench();
                    forgeItem(event.getForge(), formingitems, acg.build());
                }
            }
            // INSERT RETURN HERE!
        }
    }

    private static boolean isFueled(Location location){
        Block center = location.getBlock();
        if(!(center.getRelative(3, 2, 0).getType() == Material.LAVA_CAULDRON)){
            location.add(3, 2, 0);
            location.getWorld().spawnParticle(Particle.FLAME, location, 50, 0,0,0, 0.05);
            location.getWorld().playSound(Sound.sound(Key.key("block.lava.extinguish"), Sound.Source.AMBIENT, 1f, 0.33f));
            return false;
        }
        if(!(center.getRelative(-3, 2, 0).getType() == Material.LAVA_CAULDRON)){
            location.add(-3, 2, 0);
            location.getWorld().spawnParticle(Particle.FLAME, location, 50, 0,0,0, 0.05);
            location.getWorld().playSound(Sound.sound(Key.key("block.lava.extinguish"), Sound.Source.AMBIENT, 1f, 0.33f));
            return false;
        }
        if(!(center.getRelative(0, 2, 3).getType() == Material.LAVA_CAULDRON)){
            location.add(0, 2, 3);
            location.getWorld().spawnParticle(Particle.FLAME, location, 50, 0,0,0, 0.05);
            location.getWorld().playSound(Sound.sound(Key.key("block.lava.extinguish"), Sound.Source.AMBIENT, 1f, 0.33f));
            return false;
        }
        if(!(center.getRelative(0, 2, -3).getType() == Material.LAVA_CAULDRON)){
            location.add(0, 2, -3);
            location.getWorld().spawnParticle(Particle.FLAME, location, 50, 0,0,0, 0.05);
            location.getWorld().playSound(Sound.sound(Key.key("block.lava.extinguish"), Sound.Source.AMBIENT, 1f, 0.33f));
            return false;
        }
        return true;
    }

    private void forgeItem(Location location, List<Item> forgeitems, ItemStack result){
        for(Item item : forgeitems){
            item.setVelocity(new Vector(0,0,0));
            item.setCanPlayerPickup(false);
            item.setGravity(false);
            item.setWillAge(false);
            item.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, item.getLocation(), 100, 0,0,0,0.3);
            item.getPersistentDataContainer().set(ALREADY_FORGING, PersistentDataType.INTEGER, 1);
        }
        location.getWorld().playSound(Sound.sound(Key.key("block.anvil.use"), Sound.Source.AMBIENT, 1f, 0.5f));
        BossBar progress = Bukkit.createBossBar(ChatColor.DARK_PURPLE + "Forging...", BarColor.PURPLE, BarStyle.SOLID);
        List<Entity> entities = new ArrayList<>(location.getNearbyEntities(25,25,25));
        for(Entity entity : entities){
            if(entity instanceof Player player){
                progress.addPlayer(player);
                progress.setVisible(true);
            }
        }

        new BukkitRunnable() {
            int process = 0;
            Location particleloc;
            @Override
            public void run() {
                progress.setProgress((double) process / 300);
                // East (Pos X)
                if(process >= 60 && process <= 80){
                    if(process == 60){
                        if(location.getBlock().getRelative(3,2,0).getType() != Material.LAVA_CAULDRON){
                            for(Item item : forgeitems){
                                item.setCanPlayerPickup(true);
                                item.setGravity(true);
                                item.setWillAge(true);
                                item.getPersistentDataContainer().remove(ALREADY_FORMING);
                            }
                            location.getWorld().playSound(Sound.sound(Key.key("block.ender_chest.open"), Sound.Source.AMBIENT, 1f, 2f));
                            particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                            location.getWorld().spawnParticle(Particle.ASH, particleloc.add(3, 3, 0), 100, 0.5,0.5,0.5, 0.1);
                            progress.setVisible(false);
                            cancel();
                        } else {
                            location.getBlock().getRelative(3,2,0).setType(Material.CAULDRON);
                            location.getWorld().playSound(Sound.sound(Key.key("block.ender_chest.close"), Sound.Source.AMBIENT, 1f, 1.75f));
                        }
                    }
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.FLAME, particleloc.add(3, 0.25, 0), 2, 0,0.25,0, 0.015);
                }
                if(process >= 80 && process <= 100){
                    if(process == 80){
                        location.getWorld().playSound(Sound.sound(Key.key("entity.ghast.shoot"), Sound.Source.AMBIENT, 1f, 0.75f));
                    }
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.FLAME, particleloc.add(1.5, -0.4, 0), 2, 0.75,0,0, 0);
                }
                // North (Neg Z)
                if(process >= 100 && process <= 120){
                    if(process == 100){
                        if(location.getBlock().getRelative(0,2,-3).getType() != Material.LAVA_CAULDRON){
                            for(Item item : forgeitems){
                                item.setCanPlayerPickup(true);
                                item.setGravity(true);
                                item.setWillAge(true);
                                item.getPersistentDataContainer().remove(ALREADY_FORMING);
                            }
                            location.getWorld().playSound(Sound.sound(Key.key("block.ender_chest.open"), Sound.Source.AMBIENT, 1f, 2f));
                            particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                            location.getWorld().spawnParticle(Particle.ASH, particleloc.add(0, 3, -3), 100, 0.5,0.5,0.5, 0.1);
                            progress.setVisible(false);
                            cancel();
                        } else {
                            location.getBlock().getRelative(0,2,-3).setType(Material.CAULDRON);
                            location.getWorld().playSound(Sound.sound(Key.key("block.ender_chest.close"), Sound.Source.AMBIENT, 1f, 1.75f));
                        }
                    }
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.FLAME, particleloc.add(0, 0.25, -3), 2, 0,0.25,0, 0.015);
                }
                if(process >= 120 && process <= 140){
                    if(process == 120){
                        location.getWorld().playSound(Sound.sound(Key.key("entity.ghast.shoot"), Sound.Source.AMBIENT, 1f, 0.75f));
                    }
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.FLAME, particleloc.add(0, -0.4, -1.5), 2, 0,0,0.75, 0);
                }

                // South (Pos Z)
                if(process >= 140 && process <= 160){
                    if(process == 140){
                        if(location.getBlock().getRelative(0,2,3).getType() != Material.LAVA_CAULDRON){
                            for(Item item : forgeitems){
                                item.setCanPlayerPickup(true);
                                item.setGravity(true);
                                item.setWillAge(true);
                                item.getPersistentDataContainer().remove(ALREADY_FORMING);
                            }
                            location.getWorld().playSound(Sound.sound(Key.key("block.ender_chest.open"), Sound.Source.AMBIENT, 1f, 2f));
                            particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                            location.getWorld().spawnParticle(Particle.ASH, particleloc.add(0, 3, 3), 100, 0.5,0.5,0.5, 0.1);
                            progress.setVisible(false);
                            cancel();
                        } else {
                            location.getBlock().getRelative(0,2,3).setType(Material.CAULDRON);
                            location.getWorld().playSound(Sound.sound(Key.key("block.ender_chest.close"), Sound.Source.AMBIENT, 1f, 1.75f));
                        }
                    }
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.FLAME, particleloc.add(0, 0.25, 3), 2, 0,0.25,0, 0.015);
                }
                if(process >= 160 && process <= 180){
                    if(process == 160){
                        location.getWorld().playSound(Sound.sound(Key.key("entity.ghast.shoot"), Sound.Source.AMBIENT, 1f, 0.75f));
                    }
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.FLAME, particleloc.add(0, -0.4, 1.5), 2, 0,0,0.75, 0);
                }

                // West (Neg X)
                if(process >= 180 && process <= 200){
                    if(process == 180){
                        if(location.getBlock().getRelative(-3,2,0).getType() != Material.LAVA_CAULDRON){
                            for(Item item : forgeitems){
                                item.setCanPlayerPickup(true);
                                item.setGravity(true);
                                item.setWillAge(true);
                                item.getPersistentDataContainer().remove(ALREADY_FORMING);
                            }
                            location.getWorld().playSound(Sound.sound(Key.key("block.ender_chest.open"), Sound.Source.AMBIENT, 1f, 2f));
                            particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                            location.getWorld().spawnParticle(Particle.ASH, particleloc.add(-3, 3, 0), 100, 0.5,0.5,0.5, 0.1);
                            progress.setVisible(false);
                            cancel();
                        } else {
                            location.getBlock().getRelative(-3,2,0).setType(Material.CAULDRON);
                            location.getWorld().playSound(Sound.sound(Key.key("block.ender_chest.close"), Sound.Source.AMBIENT, 1f, 1.75f));
                        }
                    }
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.FLAME, particleloc.add(-3, 0.25, 0), 2, 0,0.25,0, 0.015);
                }
                if(process >= 200 && process <= 220){
                    if(process == 200){
                        location.getWorld().playSound(Sound.sound(Key.key("entity.ghast.shoot"), Sound.Source.AMBIENT, 1f, 0.75f));
                    }
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.FLAME, particleloc.add(-1.5, -0.4, 0), 2, 0.75,0,0, 0);
                }

                if(process == 220){
                    location.getWorld().playSound(Sound.sound(Key.key("block.end_portal.spawn"), Sound.Source.AMBIENT, 1f, 1.4f));
                }
                if(process >= 220 && process <= 280){
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.GLOW, particleloc.add(0,0.6,0), 4, 0,0,0, 0.1);

                }

                if(process >= 220 && process <= 240){
                    if(process % 2 == 0){
                        location.getWorld().playSound(Sound.sound(Key.key("entity.illusioner.prepare_blindness"), Sound.Source.AMBIENT, 1f, 1.5f));
                    }
                }
                if(process >= 240 && process <= 255){
                    if(process % 2 == 0){
                        location.getWorld().playSound(Sound.sound(Key.key("entity.illusioner.prepare_blindness"), Sound.Source.AMBIENT, 1f, 1.55f));
                    }

                }
                if(process >= 255 && process <= 265){
                    if(process % 2 == 0){
                        location.getWorld().playSound(Sound.sound(Key.key("entity.illusioner.prepare_blindness"), Sound.Source.AMBIENT, 1f, 1.65f));
                    }

                }
                if(process >= 265 && process <= 270){
                    if(process % 2 == 0){
                        location.getWorld().playSound(Sound.sound(Key.key("entity.illusioner.prepare_blindness"), Sound.Source.AMBIENT, 1f, 1.8f));
                    }

                }
                if(process >= 270 && process <= 280){
                    if(process % 2 == 0){
                        location.getWorld().playSound(Sound.sound(Key.key("entity.illusioner.prepare_blindness"), Sound.Source.AMBIENT, 1f, 2.0f));
                    }
                }

                if(process == 290){
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.SONIC_BOOM, particleloc.add(0,0.75,0), 1, 0,0,0, 1);
                }

                if(process >= 300){
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, particleloc.add(0,1,0), 100,0,0,0,0.1);

                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.REVERSE_PORTAL, particleloc.add(0,1,0), 256,0,0,0,0.3);

                    location.getWorld().playSound(Sound.sound(Key.key("entity.firework_rocket.twinkle_far"), Sound.Source.AMBIENT, 1f, 1.0f));
                    location.getWorld().playSound(Sound.sound(Key.key("block.beacon.activate"), Sound.Source.AMBIENT, 1f, 0.75f));
                    location.getWorld().playSound(Sound.sound(Key.key("block.end_gateway.spawn"), Sound.Source.AMBIENT, 1f, 1f));
                    location.getWorld().playSound(Sound.sound(Key.key("entity.wither.death"), Sound.Source.AMBIENT, 1f, 1.25f));

                    for(Item item : forgeitems){
                        item.remove();
                    }
                    location.getWorld().dropItemNaturally(location.add(0,1,0), result);
                    progress.setVisible(false);
                    cancel();
                }
                process++;
            }
        }.runTaskTimer(Paveral.getPlugin(), 0, 1L);
    }
}
