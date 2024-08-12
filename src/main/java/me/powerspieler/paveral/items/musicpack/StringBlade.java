package me.powerspieler.paveral.items.musicpack;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.items.ItemHoldingController;
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
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.*;

public class StringBlade implements Listener, Items {

    @Override
    public ItemStack build() {
        ItemStack itemStack = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(Constant.ITEMTYPE, PersistentDataType.STRING, "string_blade");
        itemMeta.setCustomModelData(2);

        itemMeta.itemName(Component.text("String Blade", NamedTextColor.DARK_PURPLE));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Press ", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false)
                .append(Component.keybind("key.mouse.right",NamedTextColor.YELLOW)
                        .decoration(TextDecoration.ITALIC, false))
                .append(Component.text(" to shot a single to note", NamedTextColor.DARK_AQUA)
                        .decoration(TextDecoration.ITALIC, false)));
        itemMeta.lore(lore);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public List<ItemStack> parts() {
        return List.of();
    }

    private final HashMap<UUID, Long> cooldown = new HashMap<>();

    @EventHandler
    public void onItemUse(PlayerInteractEvent event){
        if(ItemHoldingController.checkIsHoldingPaveralItem(event.getPlayer(), "string_blade")) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Player player = event.getPlayer();

                if(!cooldown.containsKey(player.getUniqueId()) || (System.currentTimeMillis() - cooldown.get(player.getUniqueId()) >= 1000)) {
                    cooldown.put(player.getUniqueId(), System.currentTimeMillis());

                    shotProjectile(player);

                    // Cooldown
                    new BukkitRunnable() {
                        final DecimalFormat df = new DecimalFormat("0.000");
                        @Override
                        public void run() {
                            if(ItemHoldingController.checkIsHoldingPaveralItem(player, "string_blade")){
                                double cooldownsec = ((1000.0 - (System.currentTimeMillis() - cooldown.get(player.getUniqueId()))) / 1000.0);
                                player.sendActionBar(Component.text("[ ", NamedTextColor.GOLD)
                                        .append(Component.text(df.format(cooldownsec), NamedTextColor.RED))
                                        .append(Component.text(" ]",NamedTextColor.GOLD)));
                                if(cooldownsec <= 0){
                                    cancel();
                                    player.sendActionBar(Component.empty());
                                }
                            }
                        }
                    }.runTaskTimer(Paveral.getPlugin(), 0, 1);
                }
            }
        }
    }

    private void shotProjectile(Player player){
        Location playerLocation = player.getEyeLocation();
        Vector vector = playerLocation.getDirection();

        vector.normalize();
        vector.multiply(2.0);

        FallingBlock projectile = (FallingBlock) playerLocation.getWorld().spawnEntity(playerLocation, EntityType.FALLING_BLOCK);
        projectile.setGravity(false);
        projectile.setBlockData(Material.WITHER_SKELETON_SKULL.createBlockData());
        projectile.setDropItem(false);
        projectile.setCancelDrop(true);
        projectile.setVelocity(vector);

        Audience audience = player.getWorld().filterAudience(member -> member instanceof Player target && target.getLocation().distanceSquared(player.getLocation()) < 100);
        Random random = new Random();
        audience.playSound(Sound.sound(Key.key("block.note_block.guitar"), Sound.Source.AMBIENT, 1f, random.nextFloat(0.5f, 1.01f)), Sound.Emitter.self());

        new BukkitRunnable() {
            @Override
            public void run() {
                Location projectileLocation = projectile.getLocation();
                projectileLocation.getWorld().spawnParticle(Particle.NOTE, projectileLocation, 1, 0, 0, 0);
                projectileLocation.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, projectileLocation, 1, 0, 0, 0, 0);

                List<Entity> raw = new ArrayList<>(projectileLocation.getNearbyEntities(0.5, 0.5, 0.5));
                if(!raw.isEmpty()){
                    Entity entity = raw.stream().findFirst().get();
                    if (entity instanceof LivingEntity target && target.customName() == null && !target.equals(player)) { //TODO Exclude Tamed
                        if(target.getType() == EntityType.PLAYER){
                            target.damage(3.0, player);
                        } else {
                            target.damage(6.0, player);
                        }
                        projectile.remove();
                        cancel();
                    }
                }

                if (projectile.getTicksLived() >= 20 || isOneComponentZero(projectile.getVelocity())) {
                    projectile.remove();
                    cancel();
                }
            }
        }.runTaskTimer(Paveral.getPlugin(), 0, 1);
    }

    private boolean isOneComponentZero(Vector vector){
        return vector.getX() == 0 || vector.getY() == 0 || vector.getZ() == 0;
    }


}
