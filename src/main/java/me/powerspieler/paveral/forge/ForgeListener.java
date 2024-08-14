package me.powerspieler.paveral.forge;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.advancements.AwardAdvancements;
import me.powerspieler.paveral.crafting.ItemHelper;
import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.crafting.StandardIngredient;
import me.powerspieler.paveral.forge.events.ForgeItemEvent;
import me.powerspieler.paveral.items.AntiCreeperGrief;
import me.powerspieler.paveral.items.Chunkloader;
import me.powerspieler.paveral.items.Worldalterer;
import me.powerspieler.paveral.items.Wrench;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.format.NamedTextColor;
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

import java.util.*;
import java.util.stream.Collectors;

import static me.powerspieler.paveral.crafting.ItemHelper.convertToIngredientSet;
import static me.powerspieler.paveral.forge.AwakeForge.ALREADY_FORGING;
import static me.powerspieler.paveral.forming_altar.AwakeAltar.ALREADY_FORMING;

public class ForgeListener implements Listener {

    private Set<PaveralRecipe> getAllAvailableRecipes(){
        Set<PaveralRecipe> recipes = new HashSet<>();
        recipes.add(new AntiCreeperGrief().recipe());
        recipes.add(new Chunkloader().recipe());
        recipes.add(new Wrench().recipe());
        recipes.add(new Worldalterer().recipe());

        return recipes;
    }

    @EventHandler
    public void onIngredientDrop(ForgeItemEvent event){
        Set<Item> ingredients = event.getForge().getNearbyEntitiesByType(Item.class, 1,1,1).stream().filter(item -> item.getPersistentDataContainer().has(AwakeForge.FORGING_CANDIDATE)).collect(Collectors.toSet());
        Set<StandardIngredient> forgeCandidate = convertToIngredientSet(ingredients);

        Set<PaveralRecipe> availableRecipes = getAllAvailableRecipes();
        Optional<PaveralRecipe> optionalRecipeMatch = availableRecipes.stream().filter(r -> r.ingredients().equals(forgeCandidate)).findFirst();

        optionalRecipeMatch.ifPresent(paveralRecipe -> forgeItem(event.getForge(), ingredients, paveralRecipe.result()));
    }

    private static boolean isFueled(Location location){
        final Audience targets = location.getWorld().filterAudience(member -> member instanceof Player player && player.getLocation().distanceSquared(location) < 625);
        Block center = location.getBlock();
        if(!(center.getRelative(3, 2, 0).getType() == Material.LAVA_CAULDRON)){
            location.add(3, 2, 0);
            location.getWorld().spawnParticle(Particle.FLAME, location, 50, 0,0,0, 0.05);
            targets.playSound(Sound.sound(Key.key("block.lava.extinguish"), Sound.Source.AMBIENT, 1f, 0.33f), Sound.Emitter.self());
            return false;
        }
        if(!(center.getRelative(-3, 2, 0).getType() == Material.LAVA_CAULDRON)){
            location.add(-3, 2, 0);
            location.getWorld().spawnParticle(Particle.FLAME, location, 50, 0,0,0, 0.05);
            targets.playSound(Sound.sound(Key.key("block.lava.extinguish"), Sound.Source.AMBIENT, 1f, 0.33f), Sound.Emitter.self());
            return false;
        }
        if(!(center.getRelative(0, 2, 3).getType() == Material.LAVA_CAULDRON)){
            location.add(0, 2, 3);
            location.getWorld().spawnParticle(Particle.FLAME, location, 50, 0,0,0, 0.05);
            targets.playSound(Sound.sound(Key.key("block.lava.extinguish"), Sound.Source.AMBIENT, 1f, 0.33f), Sound.Emitter.self());
            return false;
        }
        if(!(center.getRelative(0, 2, -3).getType() == Material.LAVA_CAULDRON)){
            location.add(0, 2, -3);
            location.getWorld().spawnParticle(Particle.FLAME, location, 50, 0,0,0, 0.05);
            targets.playSound(Sound.sound(Key.key("block.lava.extinguish"), Sound.Source.AMBIENT, 1f, 0.33f), Sound.Emitter.self());
            return false;
        }
        return true;
    }

    private void forgeItem(Location location, Set<Item> forgeitems, ItemStack result){ // TODO resolve duplicates
        if(!isFueled(location)) return;
        final Audience targets = location.getWorld().filterAudience(member -> member instanceof Player player && player.getLocation().distanceSquared(location) < 625);
        for(Item item : forgeitems){
            item.setVelocity(new Vector(0,0,0));
            item.setCanPlayerPickup(false);
            item.setGravity(false);
            item.setWillAge(false);
            item.getWorld().spawnParticle(Particle.ENCHANT, item.getLocation(), 100, 0,0,0,0.3);
            item.getPersistentDataContainer().set(ALREADY_FORGING, PersistentDataType.INTEGER, 1);
        }
        targets.playSound(Sound.sound(Key.key("block.anvil.use"), Sound.Source.AMBIENT, 1f, 0.5f), Sound.Emitter.self());
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
                            targets.playSound(Sound.sound(Key.key("block.ender_chest.open"), Sound.Source.AMBIENT, 1f, 2f), Sound.Emitter.self());
                            particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                            location.getWorld().spawnParticle(Particle.ASH, particleloc.add(3, 3, 0), 100, 0.5,0.5,0.5, 0.1);
                            progress.setVisible(false);
                            cancel();
                        } else {
                            location.getBlock().getRelative(3,2,0).setType(Material.CAULDRON);
                            targets.playSound(Sound.sound(Key.key("block.ender_chest.close"), Sound.Source.AMBIENT, 1f, 1.75f), Sound.Emitter.self());
                        }
                    }
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.FLAME, particleloc.add(3, 0.25, 0), 2, 0,0.25,0, 0.015);
                }
                if(process >= 80 && process <= 100){
                    if(process == 80){
                        targets.playSound(Sound.sound(Key.key("entity.ghast.shoot"), Sound.Source.AMBIENT, 1f, 0.75f), Sound.Emitter.self());
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
                            targets.playSound(Sound.sound(Key.key("block.ender_chest.open"), Sound.Source.AMBIENT, 1f, 2f), Sound.Emitter.self());
                            particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                            location.getWorld().spawnParticle(Particle.ASH, particleloc.add(0, 3, -3), 100, 0.5,0.5,0.5, 0.1);
                            progress.setVisible(false);
                            cancel();
                        } else {
                            location.getBlock().getRelative(0,2,-3).setType(Material.CAULDRON);
                            targets.playSound(Sound.sound(Key.key("block.ender_chest.close"), Sound.Source.AMBIENT, 1f, 1.75f), Sound.Emitter.self());
                        }
                    }
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.FLAME, particleloc.add(0, 0.25, -3), 2, 0,0.25,0, 0.015);
                }
                if(process >= 120 && process <= 140){
                    if(process == 120){
                        targets.playSound(Sound.sound(Key.key("entity.ghast.shoot"), Sound.Source.AMBIENT, 1f, 0.75f), Sound.Emitter.self());
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
                            targets.playSound(Sound.sound(Key.key("block.ender_chest.open"), Sound.Source.AMBIENT, 1f, 2f), Sound.Emitter.self());
                            particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                            location.getWorld().spawnParticle(Particle.ASH, particleloc.add(0, 3, 3), 100, 0.5,0.5,0.5, 0.1);
                            progress.setVisible(false);
                            cancel();
                        } else {
                            location.getBlock().getRelative(0,2,3).setType(Material.CAULDRON);
                            targets.playSound(Sound.sound(Key.key("block.ender_chest.close"), Sound.Source.AMBIENT, 1f, 1.75f), Sound.Emitter.self());
                        }
                    }
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.FLAME, particleloc.add(0, 0.25, 3), 2, 0,0.25,0, 0.015);
                }
                if(process >= 160 && process <= 180){
                    if(process == 160){
                        targets.playSound(Sound.sound(Key.key("entity.ghast.shoot"), Sound.Source.AMBIENT, 1f, 0.75f), Sound.Emitter.self());
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
                            targets.playSound(Sound.sound(Key.key("block.ender_chest.open"), Sound.Source.AMBIENT, 1f, 2f), Sound.Emitter.self());
                            particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                            location.getWorld().spawnParticle(Particle.ASH, particleloc.add(-3, 3, 0), 100, 0.5,0.5,0.5, 0.1);
                            progress.setVisible(false);
                            cancel();
                        } else {
                            location.getBlock().getRelative(-3,2,0).setType(Material.CAULDRON);
                            targets.playSound(Sound.sound(Key.key("block.ender_chest.close"), Sound.Source.AMBIENT, 1f, 1.75f), Sound.Emitter.self());
                        }
                    }
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.FLAME, particleloc.add(-3, 0.25, 0), 2, 0,0.25,0, 0.015);
                }
                if(process >= 200 && process <= 220){
                    if(process == 200){
                        targets.playSound(Sound.sound(Key.key("entity.ghast.shoot"), Sound.Source.AMBIENT, 1f, 0.75f), Sound.Emitter.self());
                    }
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.FLAME, particleloc.add(-1.5, -0.4, 0), 2, 0.75,0,0, 0);
                }

                if(process == 220){
                    targets.playSound(Sound.sound(Key.key("block.end_portal.spawn"), Sound.Source.AMBIENT, 1f, 1.4f), Sound.Emitter.self());
                }
                if(process >= 220 && process <= 280){
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.GLOW, particleloc.add(0,0.6,0), 4, 0,0,0, 0.1);

                }

                if(process >= 220 && process <= 240){
                    if(process % 2 == 0){
                        targets.playSound(Sound.sound(Key.key("entity.illusioner.prepare_blindness"), Sound.Source.AMBIENT, 1f, 1.5f), Sound.Emitter.self());
                    }
                }
                if(process >= 240 && process <= 255){
                    if(process % 2 == 0){
                        targets.playSound(Sound.sound(Key.key("entity.illusioner.prepare_blindness"), Sound.Source.AMBIENT, 1f, 1.55f), Sound.Emitter.self());
                    }

                }
                if(process >= 255 && process <= 265){
                    if(process % 2 == 0){
                        targets.playSound(Sound.sound(Key.key("entity.illusioner.prepare_blindness"), Sound.Source.AMBIENT, 1f, 1.65f), Sound.Emitter.self());
                    }

                }
                if(process >= 265 && process <= 270){
                    if(process % 2 == 0){
                        targets.playSound(Sound.sound(Key.key("entity.illusioner.prepare_blindness"), Sound.Source.AMBIENT, 1f, 1.8f), Sound.Emitter.self());
                    }

                }
                if(process >= 270 && process <= 280){
                    if(process % 2 == 0){
                        targets.playSound(Sound.sound(Key.key("entity.illusioner.prepare_blindness"), Sound.Source.AMBIENT, 1f, 2.0f), Sound.Emitter.self());
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

                    targets.playSound(Sound.sound(Key.key("entity.firework_rocket.twinkle_far"), Sound.Source.AMBIENT, 1f, 1.0f), Sound.Emitter.self());
                    targets.playSound(Sound.sound(Key.key("block.beacon.activate"), Sound.Source.AMBIENT, 1f, 0.75f), Sound.Emitter.self());
                    targets.playSound(Sound.sound(Key.key("block.end_gateway.spawn"), Sound.Source.AMBIENT, 1f, 1f), Sound.Emitter.self());
                    targets.playSound(Sound.sound(Key.key("entity.wither.death"), Sound.Source.AMBIENT, 1f, 1.25f), Sound.Emitter.self());

                    // Advancement
                    if(ItemHelper.paveralNamespacedKeyEquals(result, Constant.ITEMTYPE, "worldalterer")){
                        Optional<Item> optionalItem = forgeitems.stream().findFirst();
                        if(optionalItem.isPresent()){
                            UUID uuid = optionalItem.get().getThrower();
                            if(uuid != null){
                                Player player = (Player) Bukkit.getOfflinePlayer(uuid);
                                if(AwardAdvancements.isAdvancementUndone(player, "worldalterer")){
                                    AwardAdvancements.grantAdvancement(player, "worldalterer");
                                }
                            }
                        }
                    }

                    for(Item item : forgeitems){
                        item.remove();
                    }
                    location.getWorld().dropItem(location.add(0,1,0), result).setVelocity(new Vector(0,0.2,0));
                    progress.setVisible(false);
                    cancel();
                }
                process++;
            }
        }.runTaskTimer(Paveral.getPlugin(), 0, 1L);
    }
}
