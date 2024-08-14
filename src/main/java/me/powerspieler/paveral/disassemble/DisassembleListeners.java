package me.powerspieler.paveral.disassemble;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.advancements.AwardAdvancements;
import me.powerspieler.paveral.crafting.ItemHelper;
import me.powerspieler.paveral.disassemble.events.DisassembleItemEvent;
import me.powerspieler.paveral.items.Dismantable;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static me.powerspieler.paveral.disassemble.AwakeTable.ALREADY_DISASSEMBLING;

public class DisassembleListeners implements Listener {

    private String convertToCamelCase(String s) {
        String out = null;
        switch(s){
            case "anti_creeper_grief" -> out = "AntiCreeperGrief";
            case "bedrock_breaker" -> out = "BedrockBreaker";
            case "enhanced_channeling" -> out = "Channeling";
            case "chunkloader" -> out = "ChunkLoader";
            case "enhanced_knockback" -> out = "Knockback";
            case "lightstaff" -> out = "LightStaff";
            case "lightning_rod" -> out = "LightningRod";
            case "wrench" -> out = "Wrench";
        }
        return out;
    }


    private List<ItemStack> getPartsOfItem(Item item) {
        String itemName = ItemHelper.getPaveralNamespacedKey(item.getItemStack(), Constant.ITEMTYPE);
        if (itemName == null) return null;

        String fileName = convertToCamelCase(itemName);
        if (fileName == null) return null;

        String className = "me.powerspieler.paveral." + fileName;
        Class<?> act = null;
        try {
            act = Class.forName(className);
        } catch (ClassNotFoundException ignored) {
            className = "me.powerspieler.paveral.enhanced" + fileName;
            try {
                act = Class.forName(className);
            } catch (ClassNotFoundException ignored2) {
                Paveral.getPlugin().getLogger().log(Level.SEVERE, "ClassNotFoundException while getting parts of item on disassemble table"); // Should not be reached.
            }
        }
        if (act == null) return null;

        List<ItemStack> parts = null;
        try {
            Dismantable partItem = (Dismantable) act.getConstructor().newInstance();
            parts = partItem.parts();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException |
                 ClassCastException e) {
            Paveral.getPlugin().getLogger().log(Level.SEVERE, (Supplier<String>) e);
        }

        return parts;
    }


    @EventHandler
    public void onToolDrop(DisassembleItemEvent event){
        Set<Item> ingredients = event.getTable().getNearbyEntitiesByType(Item.class, 1,1,1).stream().filter(item -> item.getPersistentDataContainer().has(AwakeTable.DISASSEMBLE_CANDIDATE)).collect(Collectors.toSet());
        Optional<Item> disCandidate = ingredients.stream().findFirst();
        if(disCandidate.isPresent() && disCandidate.get().getItemStack().getAmount() == 1){
            List<ItemStack> parts = getPartsOfItem(disCandidate.get());

            if(parts != null){
                disassembleItem(event.getTable(), disCandidate.get(), parts);
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
        BossBar progress = Bukkit.createBossBar(NamedTextColor.DARK_PURPLE + "Disassembling...", BarColor.PURPLE, BarStyle.SOLID);
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
