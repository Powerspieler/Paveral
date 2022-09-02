package me.powerspieler.paveral.forming_altar;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.forming_altar.events.FormingItemOnAltar;
import me.powerspieler.paveral.items.AntiCreeperGrief;
import me.powerspieler.paveral.items.Items;
import me.powerspieler.paveral.items.LightStaff;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.List;

public class FormingListeners implements Listener {

    // FOR FUTURE USE :) // private static final NamespacedKey ITEMTYPE = new NamespacedKey(Paveral.getPlugin(), "itemtype");

    @EventHandler
    public void onIngredientDrop(FormingItemOnAltar event){
        Collection<Item> raw = event.getAltar().getNearbyEntitiesByType(Item.class, 1,1,1);
        List<Item> items = raw.stream().filter(item -> item.getPersistentDataContainer().has(Awake.FORMING_CANDIDATE)).toList();
        // AntiCreeperGrief
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.CREEPER_HEAD) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.FIREWORK_STAR) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.SCULK_SENSOR)){
            List<Item> formingitems = items.stream()
                    .filter(item -> {
                        Material type = item.getItemStack().getType();
                        return (type == Material.CREEPER_HEAD && item.getItemStack().getAmount() == 1) || (type == Material.FIREWORK_STAR && item.getItemStack().getAmount() == 1) || (type == Material.SCULK_SENSOR  && item.getItemStack().getAmount() == 1);
                    }).toList();
            if(formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.CREEPER_HEAD) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.FIREWORK_STAR) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.SCULK_SENSOR)){
                if(isCharged(event.getAltar())){
                    Items acg = new AntiCreeperGrief();
                    formItem(event.getAltar(), formingitems, acg.build());
                }
            }
            return;
        }
        // Lightstaff
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.IRON_INGOT) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.COPPER_INGOT) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.REDSTONE_LAMP) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.WITHER_ROSE)){
            List<Item> formingitems = items.stream()
                    .filter(item -> {
                        Material type = item.getItemStack().getType();
                        return (type == Material.IRON_INGOT && item.getItemStack().getAmount() == 2) || (type == Material.COPPER_INGOT && item.getItemStack().getAmount() == 1) || (type == Material.REDSTONE_LAMP  && item.getItemStack().getAmount() == 1) || (type == Material.WITHER_ROSE  && item.getItemStack().getAmount() == 2);
                    }).toList();
            if(formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.IRON_INGOT) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.COPPER_INGOT) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.REDSTONE_LAMP) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.WITHER_ROSE)){
                if(isCharged(event.getAltar())){
                    Items lightstaff = new LightStaff();
                    formItem(event.getAltar(), formingitems, lightstaff.build());
                }
            }
            // Insert return; here
        }
        // Next Entry HERE!

    }

    private boolean isCharged(Location location){
        Location NW = location.getBlock().getRelative(-2,1,-2).getLocation().add(0.5,0.5,0.5);
        Location NE = location.getBlock().getRelative(2,1,-2).getLocation().add(0.5,0.5,0.5);
        Location SW = location.getBlock().getRelative(-2,1,2).getLocation().add(0.5,0.5,0.5);
        Location SE = location.getBlock().getRelative(2,1,2).getLocation().add(0.5,0.5,0.5);

        if(!(NW.getBlock().getType() == Material.RESPAWN_ANCHOR) || !(NE.getBlock().getType() == Material.RESPAWN_ANCHOR) || !(SW.getBlock().getType() == Material.RESPAWN_ANCHOR) || !(SE.getBlock().getType() == Material.RESPAWN_ANCHOR)){
            return false;
        }

        RespawnAnchor NW_RA = (RespawnAnchor) NW.getBlock().getBlockData();
        RespawnAnchor NE_RA = (RespawnAnchor) NE.getBlock().getBlockData();
        RespawnAnchor SW_RA = (RespawnAnchor) SW.getBlock().getBlockData();
        RespawnAnchor SE_RA = (RespawnAnchor) SE.getBlock().getBlockData();

        if(NW_RA.getCharges() == 0){
            NW.getWorld().spawnParticle(Particle.WAX_OFF, NW, 50, 0.5,0,0.5);
            NW.getWorld().playSound(Sound.sound(Key.key("block.respawn_anchor.deplete"), Sound.Source.AMBIENT, 1f, 1f));
            return false;
        }
        if(NE_RA.getCharges() == 0){
            NE.getWorld().spawnParticle(Particle.WAX_OFF, NE, 50, 0.5,0,0.5);
            NE.getWorld().playSound(Sound.sound(Key.key("block.respawn_anchor.deplete"), Sound.Source.AMBIENT, 1f, 1f));
            return false;
        }
        if(SW_RA.getCharges() == 0){
            SW.getWorld().spawnParticle(Particle.WAX_OFF, SW, 50, 0.5,0,0.5);
            SW.getWorld().playSound(Sound.sound(Key.key("block.respawn_anchor.deplete"), Sound.Source.AMBIENT, 1f, 1f));
            return false;
        }
        if(SE_RA.getCharges() == 0){
            SE.getWorld().spawnParticle(Particle.WAX_OFF, SE, 50, 0.5,0,0.5);
            SE.getWorld().playSound(Sound.sound(Key.key("block.respawn_anchor.deplete"), Sound.Source.AMBIENT, 1f, 1f));
            return false;
        }
        return true;
    }

    private void formItem(Location location, List<Item> formingitems, ItemStack result){
        for(Item formingitem : formingitems){
            formingitem.setVelocity(new Vector(0,0,0));
            formingitem.setCanPlayerPickup(false);
            formingitem.setGravity(false);
            formingitem.setWillAge(false);
        }
        location.getWorld().playSound(Sound.sound(Key.key("entity.wither.spawn"), Sound.Source.AMBIENT, 1f, 1f));
        for(Item formingitem : formingitems){
            formingitem.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, formingitem.getLocation(), 100, 0,0,0,0.3);
        }
        BossBar progress = Bukkit.createBossBar(ChatColor.DARK_PURPLE + "Forming...", BarColor.PURPLE, BarStyle.SOLID);
        Collection<Entity> entities = location.getNearbyEntities(25,25,25);
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
                if(!(isCharged(location))){
                    for(Item formingitem : formingitems){
                        formingitem.setVelocity(new Vector(0 ,0,0));
                        formingitem.setCanPlayerPickup(true);
                        formingitem.setGravity(true);
                        formingitem.setWillAge(true);
                    }
                    location.getWorld().playSound(Sound.sound(Key.key("entity.lightning_bolt.impact"), Sound.Source.AMBIENT, 1f, 0.75f));
                    location.getWorld().playSound(Sound.sound(Key.key("block.respawn_anchor.deplete"), Sound.Source.AMBIENT, 1f, 0.25f));
                    progress.setVisible(false);
                    cancel();
                }
                progress.setProgress((double) process / 120);

                if(process >= 60){
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.GLOW, particleloc.add(-2,2.5,-2), 10, 0,1,0,0);
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.GLOW, particleloc.add(2,2.5,-2), 10, 0,1,0,0);
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.GLOW, particleloc.add(-2,2.5,2), 10, 0,1,0,0);
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.GLOW, particleloc.add(2,2.5,2), 10, 0,1,0,0);
                }
                if(process >= 60 && process <= 80){
                    location.getWorld().playSound(Sound.sound(Key.key("block.beacon.power_select"), Sound.Source.AMBIENT, 1f, 1.5f));
                }
                if(process >= 80 && process <= 95){
                    location.getWorld().playSound(Sound.sound(Key.key("block.beacon.power_select"), Sound.Source.AMBIENT, 1f, 1.55f));
                }
                if(process >= 95 && process <= 105){
                    location.getWorld().playSound(Sound.sound(Key.key("block.beacon.power_select"), Sound.Source.AMBIENT, 1f, 1.65f));
                }
                if(process >= 105 && process <= 110){
                    location.getWorld().playSound(Sound.sound(Key.key("block.beacon.power_select"), Sound.Source.AMBIENT, 1f, 1.8f));
                }
                if(process >= 110){
                    location.getWorld().playSound(Sound.sound(Key.key("block.beacon.power_select"), Sound.Source.AMBIENT, 1f, 2.0f));
                }


                if(process == 120){
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, particleloc.add(0,1,0), 25,0,0,0,1);

                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.REVERSE_PORTAL, particleloc.add(0,1,0), 256,0,0,0,0.3);

                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.GLOW_SQUID_INK, particleloc.add(-2,1,-2), 25,0,0,0,0.1);

                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.GLOW_SQUID_INK, particleloc.add(2,1,-2), 25,0,0,0,0.1);

                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.GLOW_SQUID_INK, particleloc.add(-2,1,2), 25,0,0,0,0.1);

                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.GLOW_SQUID_INK, particleloc.add(2,1,2), 25,0,0,0,0.1);

                    location.getWorld().playSound(Sound.sound(Key.key("entity.firework_rocket.twinkle_far"), Sound.Source.AMBIENT, 1f, 1.0f));
                    location.getWorld().playSound(Sound.sound(Key.key("block.beacon.activate"), Sound.Source.AMBIENT, 1f, 0.75f));
                    location.getWorld().playSound(Sound.sound(Key.key("block.end_gateway.spawn"), Sound.Source.AMBIENT, 1f, 1f));
                    location.getWorld().playSound(Sound.sound(Key.key("block.end_portal.spawn"), Sound.Source.AMBIENT, 1f, 1.13f));

                    for(Item formingitem : formingitems){
                        formingitem.remove();
                    }
                    deplete(location);
                    location.getWorld().dropItemNaturally(location.add(0,1,0), result);
                    progress.setVisible(false);
                    cancel();
                }
                process++;
            }
        }.runTaskTimer(Paveral.getPlugin(),0,1L);
    }

    private void deplete(Location location){
        Location NW = location.getBlock().getRelative(-2,1,-2).getLocation().add(0.5,0.5,0.5);
        Location NE = location.getBlock().getRelative(2,1,-2).getLocation().add(0.5,0.5,0.5);
        Location SW = location.getBlock().getRelative(-2,1,2).getLocation().add(0.5,0.5,0.5);
        Location SE = location.getBlock().getRelative(2,1,2).getLocation().add(0.5,0.5,0.5);
        applyDeplete(NW);
        applyDeplete(NE);
        applyDeplete(SW);
        applyDeplete(SE);
    }

    private void applyDeplete(Location location){
        location.getWorld().playSound(Sound.sound(Key.key("block.respawn_anchor.set_spawn"), Sound.Source.AMBIENT, 1f, 1f));
        Block ra = location.getBlock();
        RespawnAnchor ra_data = (RespawnAnchor) ra.getBlockData();

        int charges = ra_data.getCharges();
        charges--;
        ra_data.setCharges(charges);
        ra.setBlockData(ra_data);


    }
}

