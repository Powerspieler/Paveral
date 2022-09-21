package me.powerspieler.paveral.forming_altar;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.discovery.Discovery;
import me.powerspieler.paveral.discovery.tutorial.DisBook;
import me.powerspieler.paveral.forming_altar.events.FormItemEvent;
import me.powerspieler.paveral.items.*;
import me.powerspieler.paveral.items.enchanced.Channeling;
import me.powerspieler.paveral.items.enchanced.Knockback;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static me.powerspieler.paveral.forming_altar.AwakeAltar.ALREADY_FORMING;

public class FormingListeners implements Listener {

    @EventHandler
    public void onIngredientDrop(FormItemEvent event){
        List<Item> raw = new ArrayList<>(event.getAltar().getNearbyEntitiesByType(Item.class, 1,1,1));
        List<Item> items = raw.stream().filter(item -> item.getPersistentDataContainer().has(AwakeAltar.FORMING_CANDIDATE)).toList();
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
            return;
        }
        // Bedrock Breaker
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.OBSIDIAN) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.PISTON) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.TNT) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.LEVER) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.OAK_TRAPDOOR) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.ANCIENT_DEBRIS)){
            List<Item> formingitems = items.stream()
                    .filter(item -> {
                        Material type = item.getItemStack().getType();
                        return (type == Material.OBSIDIAN && item.getItemStack().getAmount() == 1) || (type == Material.PISTON && item.getItemStack().getAmount() == 2) || (type == Material.TNT  && item.getItemStack().getAmount() == 2) || (type == Material.LEVER  && item.getItemStack().getAmount() == 1) || (type == Material.OAK_TRAPDOOR  && item.getItemStack().getAmount() == 1) || (type == Material.ANCIENT_DEBRIS  && item.getItemStack().getAmount() == 4);
                    }).toList();
            if(formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.OBSIDIAN) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.PISTON) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.TNT) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.LEVER) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.OAK_TRAPDOOR) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.ANCIENT_DEBRIS)){
                if(isCharged(event.getAltar())){
                    Items bb = new BedrockBreaker();
                    formItem(event.getAltar(), formingitems, bb.build());
                }
            }
            return;
        }
        // Disassemble Tutorial Book
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.WRITTEN_BOOK) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.NETHERITE_SCRAP)){
            List<Item> formingitems = items.stream()
                    .filter(item -> {
                        Material type = item.getItemStack().getType();
                        return (type == Material.WRITTEN_BOOK && item.getItemStack().getItemMeta().getPersistentDataContainer().has(Constant.DISCOVERY, PersistentDataType.STRING) && Objects.equals(item.getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.DISCOVERY, PersistentDataType.STRING), "altar_book")) || (type == Material.NETHERITE_SCRAP && item.getItemStack().getAmount() == 1);
                    }).toList();
            if(formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.WRITTEN_BOOK) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.NETHERITE_SCRAP)){
                if(isCharged(event.getAltar())){
                    Discovery dis_book = new DisBook();
                    formItem(event.getAltar(), formingitems, dis_book.build());
                }
            }
            return;
        }
        // Wrench
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.IRON_INGOT)){
            List<Item> formingitems = items.stream()
                    .filter(item -> {
                        Material type = item.getItemStack().getType();
                        return type == Material.IRON_INGOT && item.getItemStack().getAmount() == 4;
                    }).toList();
            if(formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.IRON_INGOT)){
                if(isCharged(event.getAltar())){
                    Items wrench = new Wrench();
                    formItem(event.getAltar(), formingitems, wrench.build());
                }
            }
            return;
        }
        // Chunkloader
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.NETHER_STAR) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.LODESTONE) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.OBSIDIAN) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.ENCHANTING_TABLE)){
            List<Item> formingitems = items.stream()
                    .filter(item -> {
                        Material type = item.getItemStack().getType();
                        return (type == Material.NETHER_STAR && item.getItemStack().getAmount() == 3) || (type == Material.LODESTONE && item.getItemStack().getAmount() == 1) || (type == Material.OBSIDIAN  && item.getItemStack().getAmount() == 2) || (type == Material.ENCHANTING_TABLE  && item.getItemStack().getAmount() == 1);
                    }).toList();
            if(formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.NETHER_STAR) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.LODESTONE) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.OBSIDIAN) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.ENCHANTING_TABLE)){
                if(isCharged(event.getAltar())){
                    Items cl = new Chunkloader();
                    formItem(event.getAltar(), formingitems, cl.build());
                }
            }
            return;
        }
        // Next Entry here!

        // Enchaned Enchantment
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.ENCHANTED_BOOK) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.NETHERITE_SCRAP)){
            List<Item> formingitems = new ArrayList<>(items.stream()
                    .filter(item -> {
                        Material type = item.getItemStack().getType();
                        return (type == Material.ENCHANTED_BOOK) || (type == Material.NETHERITE_SCRAP && item.getItemStack().getAmount() == 1);
                    }).toList());
            Optional<Item> ench_raw = formingitems.stream().filter(item -> item.getItemStack().getType() == Material.ENCHANTED_BOOK).findFirst();
            if(ench_raw.isPresent()){
                EnchantmentStorageMeta ench = (EnchantmentStorageMeta) ench_raw.get().getItemStack().getItemMeta();
                // Knockback 5
                if(ench.hasStoredEnchant(Enchantment.KNOCKBACK) && ench.getStoredEnchantLevel(Enchantment.KNOCKBACK) == 2){
                    formingitems.add(ench_raw.get());
                    if(isCharged(event.getAltar())){
                        Items enh_kb = new Knockback();
                        formItem(event.getAltar(), formingitems, enh_kb.build());
                    }
                }
                // Channeling 10
                if(ench.hasStoredEnchant(Enchantment.CHANNELING) && ench.getStoredEnchantLevel(Enchantment.CHANNELING) == 1){
                    formingitems.add(ench_raw.get());
                    if(isCharged(event.getAltar())){
                        Items enh_ch = new Channeling();
                        formItem(event.getAltar(), formingitems, enh_ch.build());
                    }
                }
                // Next Enchantment Entry HERE
            }
        }
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
            formingitem.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, formingitem.getLocation(), 100, 0,0,0,0.3);
            formingitem.getPersistentDataContainer().set(ALREADY_FORMING, PersistentDataType.INTEGER, 1);
        }
        location.getWorld().playSound(Sound.sound(Key.key("entity.wither.spawn"), Sound.Source.AMBIENT, 1f, 1f));
        BossBar progress = Bukkit.createBossBar(ChatColor.DARK_PURPLE + "Forming...", BarColor.PURPLE, BarStyle.SOLID);
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
                if(!(isCharged(location))){
                    for(Item formingitem : formingitems){
                        formingitem.setVelocity(new Vector(0 ,0,0));
                        formingitem.setCanPlayerPickup(true);
                        formingitem.setGravity(true);
                        formingitem.setWillAge(true);
                        formingitem.getPersistentDataContainer().remove(ALREADY_FORMING);
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

