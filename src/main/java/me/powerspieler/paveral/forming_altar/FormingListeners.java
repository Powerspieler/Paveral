package me.powerspieler.paveral.forming_altar;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.advancements.AwardAdvancements;
import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.crafting.StandardIngredient;
import me.powerspieler.paveral.discovery.tutorial.DisBook;
import me.powerspieler.paveral.discovery.tutorial.TechBook;
import me.powerspieler.paveral.forming_altar.events.FormItemEvent;
import me.powerspieler.paveral.items.BedrockBreaker;
import me.powerspieler.paveral.items.LightStaff;
import me.powerspieler.paveral.items.enhanced.Channeling;
import me.powerspieler.paveral.items.enhanced.Knockback;
import me.powerspieler.paveral.items.musicpack.LumberjacksBass;
import me.powerspieler.paveral.items.musicpack.PianoSword;
import me.powerspieler.paveral.items.musicpack.ScytheOfHarmony;
import me.powerspieler.paveral.items.musicpack.StringBlade;
import net.kyori.adventure.audience.Audience;
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
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Collectors;

import static me.powerspieler.paveral.crafting.ItemHelper.convertToIngredientSet;
import static me.powerspieler.paveral.forming_altar.AwakeAltar.ALREADY_FORMING;

public class FormingListeners implements Listener {

    // TODO Teste das hier!! Mit anderen Ingredienttypen (!) .equals!! hashCode (?!)
    // TODO jedes item mit recipe versehen

    private Set<PaveralRecipe> getAllAvailableRecipes(){
        Set<PaveralRecipe> recipes = new HashSet<>();
        recipes.add(new DisBook().recipe());
        recipes.add(new TechBook().recipe());

        recipes.add(new BedrockBreaker().recipe());
        recipes.add(new LightStaff().recipe());

        recipes.add(new Knockback().recipe());
        recipes.add(new Channeling().recipe());

        recipes.add(new PianoSword().recipe());
        recipes.add(new StringBlade().recipe());
        // Pickaxe
        recipes.add(new LumberjacksBass().recipe());
        // shovel
        recipes.add(new ScytheOfHarmony().recipe());

        return recipes;
    }

    @EventHandler
    public void onIngredientDrop(FormItemEvent event){
        Set<Item> ingredients = event.getAltar().getNearbyEntitiesByType(Item.class, 1,1,1).stream().filter(item -> item.getPersistentDataContainer().has(AwakeAltar.FORMING_CANDIDATE)).collect(Collectors.toSet());
        Set<StandardIngredient> formCandidate = convertToIngredientSet(ingredients);

        Set<PaveralRecipe> availableRecipes = getAllAvailableRecipes();
        Optional<PaveralRecipe> optionalRecipeMatch = availableRecipes.stream().filter(r -> r.ingredients().equals(formCandidate)).findFirst();

        optionalRecipeMatch.ifPresent(paveralRecipe -> formItem(event.getAltar(), ingredients, paveralRecipe.result()));
    }

    private boolean isCharged(Location location){ // TODO resolve duplicates
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

    private void formItem(Location location, Set<Item> formingitems, ItemStack result){
        if(!isCharged(location)) return;
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
        BossBar progress = Bukkit.createBossBar(ChatColor.DARK_PURPLE + "Forming..." , BarColor.PURPLE, BarStyle.SOLID);
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

