package me.powerspieler.paveral.forming_altar;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.advancements.AwardAdvancements;
import me.powerspieler.paveral.discovery.Discovery;
import me.powerspieler.paveral.discovery.tutorial.DisBook;
import me.powerspieler.paveral.discovery.tutorial.TechBook;
import me.powerspieler.paveral.forming_altar.events.FormItemEvent;
import me.powerspieler.paveral.items.BedrockBreaker;
import me.powerspieler.paveral.items.Items;
import me.powerspieler.paveral.items.LightStaff;
import me.powerspieler.paveral.items.enchanced.Channeling;
import me.powerspieler.paveral.items.enchanced.Knockback;
import me.powerspieler.paveral.items.musicpack.PianoSword;
import me.powerspieler.paveral.items.musicpack.ScytheOfHarmony;
import me.powerspieler.paveral.items.musicpack.StringBlade;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.audience.Audience;
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
                        return (type == Material.WRITTEN_BOOK && item.getItemStack().getAmount() == 1 && item.getItemStack().getItemMeta().getPersistentDataContainer().has(Constant.DISCOVERY, PersistentDataType.STRING) && Objects.equals(item.getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.DISCOVERY, PersistentDataType.STRING), "altar_book")) || (type == Material.NETHERITE_SCRAP && item.getItemStack().getAmount() == 1);
                    }).toList();
            if(formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.WRITTEN_BOOK) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.NETHERITE_SCRAP)){
                if(isCharged(event.getAltar())){
                    Discovery dis_book = new DisBook();
                    formItem(event.getAltar(), formingitems, dis_book.build());
                }
            }
            // No Return; Allow next Tutorial Book to be checked
        }
        // Forge Tutorial Book
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.WRITTEN_BOOK) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.NETHERITE_SCRAP)){
            List<Item> formingitems = items.stream()
                    .filter(item -> {
                        Material type = item.getItemStack().getType();
                        return (type == Material.WRITTEN_BOOK && item.getItemStack().getAmount() == 1 && item.getItemStack().getItemMeta().getPersistentDataContainer().has(Constant.DISCOVERY, PersistentDataType.STRING) && Objects.equals(item.getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.DISCOVERY, PersistentDataType.STRING), "dis_book")) || (type == Material.NETHERITE_SCRAP && item.getItemStack().getAmount() == 1);
                    }).toList();
            if(formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.WRITTEN_BOOK) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.NETHERITE_SCRAP)){
                if(isCharged(event.getAltar())){
                    Discovery tech_book = new TechBook();
                    formItem(event.getAltar(), formingitems, tech_book.build());
                }
            }
            return;
        }
        // Piano Sword / Rhytms Awakening
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.JIGSAW) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.NETHERITE_SWORD) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.BLACKSTONE) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.QUARTZ)){
            List<Item> formingitems = items.stream()
                    .filter(item -> {
                        Material type = item.getItemStack().getType();
                        return (type == Material.JIGSAW && item.getItemStack().getAmount() == 1 && item.getItemStack().getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE, PersistentDataType.STRING) && Objects.equals(item.getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "music_core")) || (type == Material.NETHERITE_SWORD && item.getItemStack().getAmount() == 1 ) || (type == Material.BLACKSTONE && item.getItemStack().getAmount() == 8) || (type == Material.QUARTZ && item.getItemStack().getAmount() == 16);
                    }).toList();
            if(formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.JIGSAW) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.NETHERITE_SWORD) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.BLACKSTONE) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.QUARTZ)){
                if(isCharged(event.getAltar())){
                    formItem(event.getAltar(), formingitems, new PianoSword().build());
                }
            }
            return;
        }
        // String Blade
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.JIGSAW) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.NETHERITE_SWORD) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.ORANGE_DYE)){
            List<Item> formingitems = items.stream()
                    .filter(item -> {
                        Material type = item.getItemStack().getType();
                        return (type == Material.JIGSAW && item.getItemStack().getAmount() == 1 && item.getItemStack().getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE, PersistentDataType.STRING) && Objects.equals(item.getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "music_core")) || (type == Material.NETHERITE_SWORD && item.getItemStack().getAmount() == 1 ) || (type == Material.ORANGE_DYE && item.getItemStack().getAmount() == 1);
                    }).toList();
            if(formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.JIGSAW) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.NETHERITE_SWORD) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.ORANGE_DYE)){
                if(isCharged(event.getAltar())){
                    formItem(event.getAltar(), formingitems, new StringBlade().build());
                }
            }
            return;
        }
        // Scythe_of_Harmony
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.JIGSAW) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.NETHERITE_HOE)){
            List<Item> formingitems = items.stream()
                    .filter(item -> {
                        Material type = item.getItemStack().getType();
                        return (type == Material.JIGSAW && item.getItemStack().getAmount() == 1 && item.getItemStack().getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE, PersistentDataType.STRING) && Objects.equals(item.getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "music_core")) || (type == Material.NETHERITE_HOE && item.getItemStack().getAmount() == 1 );
                    }).toList();
            if(formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.JIGSAW) && formingitems.stream().anyMatch(item -> item.getItemStack().getType() == Material.NETHERITE_HOE)){
                if(isCharged(event.getAltar())){
                    formItem(event.getAltar(), formingitems, new ScytheOfHarmony().build());
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
        final Audience targets = location.getWorld().filterAudience(member -> member instanceof Player player && player.getLocation().distanceSquared(location) < 625);
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
            targets.playSound(Sound.sound(Key.key("block.respawn_anchor.deplete"), Sound.Source.AMBIENT, 1f, 1f), Sound.Emitter.self());
            return false;
        }
        if(NE_RA.getCharges() == 0){
            NE.getWorld().spawnParticle(Particle.WAX_OFF, NE, 50, 0.5,0,0.5);
            targets.playSound(Sound.sound(Key.key("block.respawn_anchor.deplete"), Sound.Source.AMBIENT, 1f, 1f), Sound.Emitter.self());
            return false;
        }
        if(SW_RA.getCharges() == 0){
            SW.getWorld().spawnParticle(Particle.WAX_OFF, SW, 50, 0.5,0,0.5);
            targets.playSound(Sound.sound(Key.key("block.respawn_anchor.deplete"), Sound.Source.AMBIENT, 1f, 1f), Sound.Emitter.self());
            return false;
        }
        if(SE_RA.getCharges() == 0){
            SE.getWorld().spawnParticle(Particle.WAX_OFF, SE, 50, 0.5,0,0.5);
            targets.playSound(Sound.sound(Key.key("block.respawn_anchor.deplete"), Sound.Source.AMBIENT, 1f, 1f), Sound.Emitter.self());
            return false;
        }
        return true;
    }

    private void formItem(Location location, List<Item> formingitems, ItemStack result){
        final Audience targets = location.getWorld().filterAudience(member -> member instanceof Player player && player.getLocation().distanceSquared(location) < 625);
        for(Item formingitem : formingitems){
            formingitem.setVelocity(new Vector(0,0,0));
            formingitem.setCanPlayerPickup(false);
            formingitem.setGravity(false);
            formingitem.setWillAge(false);
            formingitem.getWorld().spawnParticle(Particle.ENCHANT, formingitem.getLocation(), 100, 0,0,0,0.3);
            formingitem.getPersistentDataContainer().set(ALREADY_FORMING, PersistentDataType.INTEGER, 1);
        }
        targets.playSound(Sound.sound(Key.key("entity.wither.spawn"), Sound.Source.AMBIENT, 1f, 1f), Sound.Emitter.self());
        BossBar progress = Bukkit.createBossBar(ChatColor.DARK_PURPLE + "Forming...", BarColor.PURPLE, BarStyle.SOLID);
        List<Entity> entities = new ArrayList<>(location.getNearbyEntities(25,25,25));
        for(Entity entity : entities){
            if(entity instanceof Player player){
                progress.addPlayer(player);
                progress.setVisible(true);
                if(AwardAdvancements.isAdvancementUndone(player, "first_forming")){
                    AwardAdvancements.grantAdvancement(player, "first_forming");
                }
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
                    targets.playSound(Sound.sound(Key.key("entity.lightning_bolt.impact"), Sound.Source.AMBIENT, 1f, 0.75f), Sound.Emitter.self());
                    targets.playSound(Sound.sound(Key.key("block.respawn_anchor.deplete"), Sound.Source.AMBIENT, 1f, 0.25f), Sound.Emitter.self());
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
                    targets.playSound(Sound.sound(Key.key("block.beacon.power_select"), Sound.Source.AMBIENT, 1f, 1.5f), Sound.Emitter.self());
                }
                if(process >= 80 && process <= 95){
                    targets.playSound(Sound.sound(Key.key("block.beacon.power_select"), Sound.Source.AMBIENT, 1f, 1.55f), Sound.Emitter.self());
                }
                if(process >= 95 && process <= 105){
                    targets.playSound(Sound.sound(Key.key("block.beacon.power_select"), Sound.Source.AMBIENT, 1f, 1.65f), Sound.Emitter.self());
                }
                if(process >= 105 && process <= 110){
                    targets.playSound(Sound.sound(Key.key("block.beacon.power_select"), Sound.Source.AMBIENT, 1f, 1.8f), Sound.Emitter.self());
                }
                if(process >= 110){
                    targets.playSound(Sound.sound(Key.key("block.beacon.power_select"), Sound.Source.AMBIENT, 1f, 2.0f), Sound.Emitter.self());
                }


                if(process == 120){
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.FIREWORK, particleloc.add(0,1,0), 25,0,0,0,1);

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

                    targets.playSound(Sound.sound(Key.key("entity.firework_rocket.twinkle_far"), Sound.Source.AMBIENT, 1f, 1.0f), Sound.Emitter.self());
                    targets.playSound(Sound.sound(Key.key("block.beacon.activate"), Sound.Source.AMBIENT, 1f, 0.75f), Sound.Emitter.self());
                    targets.playSound(Sound.sound(Key.key("block.end_gateway.spawn"), Sound.Source.AMBIENT, 1f, 1f), Sound.Emitter.self());
                    targets.playSound(Sound.sound(Key.key("block.end_portal.spawn"), Sound.Source.AMBIENT, 1f, 1.13f), Sound.Emitter.self());

                    for(Item formingitem : formingitems){
                        formingitem.remove();
                    }
                    deplete(location);
                    location.getWorld().dropItem(location.add(0,1,0), result).setVelocity(new Vector(0,0.2,0));
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
        final Audience targets = location.getWorld().filterAudience(member -> member instanceof Player player && player.getLocation().distanceSquared(location) < 625);
        targets.playSound(Sound.sound(Key.key("block.respawn_anchor.set_spawn"), Sound.Source.AMBIENT, 1f, 1f), Sound.Emitter.self());
        Block ra = location.getBlock();
        RespawnAnchor ra_data = (RespawnAnchor) ra.getBlockData();

        int charges = ra_data.getCharges();
        charges--;
        ra_data.setCharges(charges);
        ra.setBlockData(ra_data);


    }
}

