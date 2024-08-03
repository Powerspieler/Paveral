package me.powerspieler.paveral.items;

import com.destroystokyo.paper.event.entity.PhantomPreSpawnEvent;
import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.advancements.AwardAdvancements;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class LightningRod implements Listener, Items {
    public LightningRod() {
    }

    @Override
    public ItemStack build() {
        ItemStack lr = new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK);
        ItemMeta lrmeta = lr.getItemMeta();
        lrmeta.getPersistentDataContainer().set(Constant.ITEMTYPE, PersistentDataType.STRING, "lightning_rod");
        lrmeta.setCustomModelData(1);
        lrmeta.setUnbreakable(true);
        lrmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        lrmeta.itemName(Component.text("Lightning Rod", NamedTextColor.DARK_PURPLE)
                .decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Press ", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false)
                .append(Component.keybind("key.mouse.right",NamedTextColor.YELLOW)
                        .decoration(TextDecoration.ITALIC, false))
                .append(Component.text(" to cast lightning", NamedTextColor.DARK_AQUA)
                        .decoration(TextDecoration.ITALIC, false)));
        lore.add(Component.text("Kills phantoms instantly", NamedTextColor.RED)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Kill three phantoms at once to", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("prevent them spawning for five minutes", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lrmeta.lore(lore);

        lr.setItemMeta(lrmeta);
        return lr;
    }

    @Override
    public List<ItemStack> parts() {
        List<ItemStack> parts = new ArrayList<>();
        ItemStack trident = new ItemStack(Material.TRIDENT);
        Damageable tridentmeta = (Damageable) trident.getItemMeta();
        tridentmeta.setDamage(250 - (int) (Math.random() * 50));
        trident.setItemMeta(tridentmeta);
        ItemStack book = new ItemStack(Material.BOOK);
        parts.add(trident);
        parts.add(book);
        return parts;
    }

    private final HashMap<UUID, Long> cooldown = new HashMap<>();
    private final HashMap<UUID, Long> paralyzer = new HashMap<>();

    @EventHandler
    public void onPlayerRightclick(PlayerInteractEvent event){
        if(ItemHoldingController.checkIsHoldingPaveralItem(event.getPlayer(), "lightning_rod")){
            if (event.getAction().isRightClick()) {
                Player player = event.getPlayer();
                if (!cooldown.containsKey(player.getUniqueId()) || (System.currentTimeMillis() - cooldown.get(player.getUniqueId())) >= 1500) {
                    cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                    final Audience targets = player.getWorld().filterAudience(member -> member instanceof Player playermember && playermember.getLocation().distanceSquared(player.getLocation()) < 2500);
                    targets.playSound(Sound.sound(Key.key("entity.lightning_bolt.thunder"), Sound.Source.MASTER, 1f, 1.8f), Sound.Emitter.self());
                    targets.playSound(Sound.sound(Key.key("item.trident.thunder"), Sound.Source.MASTER, 1f, 1f), Sound.Emitter.self());

                    Location loc = player.getEyeLocation();
                    Vector direction = loc.getDirection().multiply(0.5);
                    for (int i = 10; i > 0; i--) {
                        if (loc.getBlock().isSolid()) {
                            return;
                        }
                        loc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc, 1, 0, 0, 0, 0);
                        loc.add(direction);
                    }
                    new BukkitRunnable() {
                        int joints = 20;
                        int kills = 0;

                        @Override
                        public void run() {
                            direction.setX(direction.getX() + ((Math.random() - Math.random()) / 7));
                            direction.setY(direction.getY() + ((Math.random() - Math.random()) / 7));
                            direction.setZ(direction.getZ() + ((Math.random() - Math.random()) / 7));

                            for (int i = 20; i > 0; i--) {
                                if (loc.getBlock().isSolid()) {
                                    cancel();
                                    break;
                                }
                                List<Entity> raw = new ArrayList<>(loc.getNearbyEntities(3, 3, 3));
                                for (Entity entity : raw) {
                                    if (entity.customName() == null) {
                                        if (entity instanceof Phantom phantom) {
                                            kills++;
                                            phantom.setHealth(0);
                                            phantom.getLocation().getWorld().strikeLightningEffect(phantom.getLocation());
                                            phantom.getLocation().getWorld().spawnParticle(Particle.FLASH, phantom.getLocation(), 5, 0, 0, 0, 1, null, true);
                                            phantom.getLocation().getWorld().spawnParticle(Particle.ENCHANT, phantom.getLocation().add(0, 1.5, 0), 1000, 0, 0, 0, 10);
                                            phantom.remove();
                                        } else if (entity instanceof Monster monster) {
                                            monster.setFireTicks(100);
                                            double health = monster.getHealth() - 0.5;
                                            if (health < 0) {
                                                health = 0;
                                            }
                                            monster.setHealth(health);
                                        }
                                    }
                                }
                                loc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc, 1, 0, 0, 0, 0, null, true);
                                loc.add(direction);

                            }
                            joints--;
                            if (joints <= 0) {
                                cancel();
                            }
                            if (isCancelled()) {
                                if (kills >= 3) {
                                    paralyzer.put(player.getUniqueId(), System.currentTimeMillis());
                                    if (AwardAdvancements.isAdvancementUndone(player, "lightning_rod_phantom")) {
                                        AwardAdvancements.grantAdvancement(player, "lightning_rod_phantom");
                                    }
                                    player.playSound(Sound.sound(Key.key("block.conduit.activate"), Sound.Source.AMBIENT, 1f, 0f), Sound.Emitter.self());
                                    player.playSound(Sound.sound(Key.key("block.conduit.ambient"), Sound.Source.AMBIENT, 1f, 0f), Sound.Emitter.self());
                                    player.getWorld().spawnParticle(Particle.NAUTILUS, player.getLocation(), 1, 0, 0, 0, 1);
                                }
                            }
                        }
                    }.runTaskTimer(Paveral.getPlugin(), 0, 1);
                } else {
                    player.playSound(Sound.sound(Key.key("block.note_block.basedrum"), Sound.Source.AMBIENT, 1f, 0f), Sound.Emitter.self());
                    showActionbar(player);

                }
            }
        }
    }

    @EventHandler
    public void onLightningRodMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(ItemHoldingController.checkIsHoldingPaveralItem(player, "lightning_rod")){
            showActionbar(player);
        }
    }

    private void showActionbar(Player player){
        if(cooldown.containsKey(player.getUniqueId())){
            double cooldownsec = ((1500.0 - (System.currentTimeMillis() - cooldown.get(player.getUniqueId()))) / 1000.0);
            if(cooldownsec > 0){
                player.sendActionBar(Component.text("[ ", NamedTextColor.GOLD)
                        .append(Component.text("" + cooldownsec, NamedTextColor.RED))
                        .append(Component.text(" ]",NamedTextColor.GOLD)));

            } else {
                if(paralyzer.containsKey(player.getUniqueId()) && System.currentTimeMillis() - paralyzer.get(player.getUniqueId()) <= 300000){
                    long raw = (300000 - (System.currentTimeMillis() - paralyzer.get(player.getUniqueId())));
                    if(raw > 60000){
                        int temp = (int) (raw / 1000);
                        int seconds = temp % 60;
                        int minutes = (temp - seconds) / 60;

                        player.sendActionBar(Component.text("[ ", NamedTextColor.GOLD)
                                .append(Component.text("Phantom Protection", NamedTextColor.GREEN))
                                .append(Component.text(" ]",NamedTextColor.GOLD))
                                .append(Component.text(" - ", NamedTextColor.GRAY))
                                .append(Component.text("" + minutes + "min " + seconds + "s",NamedTextColor.LIGHT_PURPLE)));

                    } else {
                        double seconds = raw / 1000.0;
                        player.sendActionBar(Component.text("[ ", NamedTextColor.GOLD)
                                .append(Component.text("Phantom Protection", NamedTextColor.GREEN))
                                .append(Component.text(" ]",NamedTextColor.GOLD))
                                .append(Component.text(" - ", NamedTextColor.GRAY))
                                .append(Component.text("" + seconds + "s",NamedTextColor.LIGHT_PURPLE)));


                    }
                } else {
                    player.sendActionBar(Component.text("[ ", NamedTextColor.GOLD)
                            .append(Component.text("Ready", NamedTextColor.YELLOW))
                            .append(Component.text(" ]",NamedTextColor.GOLD)));
                }
            }
        } else {
            player.sendActionBar(Component.text("[ ", NamedTextColor.GOLD)
                    .append(Component.text("Ready", NamedTextColor.YELLOW))
                    .append(Component.text(" ]",NamedTextColor.GOLD)));
        }
    }

    @EventHandler
    public void onPhantomSpawn(PhantomPreSpawnEvent event){
        if(event.getSpawningEntity() instanceof Player player){
            if(paralyzer.containsKey(player.getUniqueId())){
                if(System.currentTimeMillis() - paralyzer.get(player.getUniqueId()) <= 300000){
                    event.setCancelled(true);
                }
            }
        }
    }
}
