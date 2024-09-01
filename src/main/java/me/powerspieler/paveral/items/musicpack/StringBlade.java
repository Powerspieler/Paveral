package me.powerspieler.paveral.items.musicpack;

import com.destroystokyo.paper.MaterialTags;
import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.crafting.PaveralIngredient;
import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.crafting.StandardIngredient;
import me.powerspieler.paveral.items.PaveralItem;
import me.powerspieler.paveral.items.helper.ItemHoldingController;
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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.*;

public class StringBlade extends PaveralItem implements Listener {
    public static Set<PaveralRecipe> getAllStringBladeRecipes(){
        Set<PaveralRecipe> recipes = new HashSet<>(16);
        for (Material dye : MaterialTags.DYES.getValues()) {
            recipes.add(new StringBlade(dye, getCustomModelData(dye)).recipe());
        }
        return recipes;
    }

    private static int getCustomModelData(Material material){
        int out = 0;
        switch (material){
            case BLACK_DYE -> out = 2;
            case BLUE_DYE -> out = 3;
            case BROWN_DYE -> out = 4;
            case CYAN_DYE -> out = 5;
            case GRAY_DYE -> out = 6;
            case GREEN_DYE -> out = 7;
            case LIGHT_BLUE_DYE -> out = 8;
            case LIGHT_GRAY_DYE -> out = 9;
            case LIME_DYE -> out = 10;
            case MAGENTA_DYE -> out = 11;
            case ORANGE_DYE -> out = 12;
            case PINK_DYE -> out = 13;
            case PURPLE_DYE -> out = 14;
            case RED_DYE -> out = 15;
            case WHITE_DYE -> out = 16;
            case YELLOW_DYE -> out = 17;
        }
        return out;
    }

    private static Component itemName(Material color){
        String colorString = color.translationKey().split("\\.")[2].split("_")[0];

        return Component.text("String Blade (", NamedTextColor.DARK_PURPLE)
                .append(Component.translatable("color.minecraft." + colorString, NamedTextColor.DARK_PURPLE))
                .append(Component.text(")", NamedTextColor.DARK_PURPLE));
    }

    private static List<Component> lore(){
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Press ", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false)
                .append(Component.keybind("key.mouse.right",NamedTextColor.YELLOW)
                        .decoration(TextDecoration.ITALIC, false))
                .append(Component.text(" to shot a single to note", NamedTextColor.DARK_AQUA)
                        .decoration(TextDecoration.ITALIC, false)));
        return lore;
    }

    private final Material color;

    public StringBlade(Material color, int customModelData) {
        super(Material.NETHERITE_SWORD, customModelData, Constant.ITEMTYPE, "string_blade", itemName(color), lore());
        this.color = color;
    }

    @Override
    public PaveralRecipe recipe() {
        Set<StandardIngredient> ingredients = new HashSet<>();
        ingredients.add(new StandardIngredient(Material.NETHERITE_SWORD, 1));
        ingredients.add(new PaveralIngredient(Material.JIGSAW, 1, Constant.ITEMTYPE, "music_core"));
        ingredients.add(new StandardIngredient(color, 1));
        return new PaveralRecipe(ingredients, this.build());
    }

    // --- Item Logic ---

    private final HashMap<UUID, Long> cooldown = new HashMap<>();

    @EventHandler
    private void onItemUse(PlayerInteractEvent event){
        if(ItemHoldingController.checkIsHoldingPaveralItem(event.getPlayer(), keyString)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Player player = event.getPlayer();

                if(!cooldown.containsKey(player.getUniqueId()) || (System.currentTimeMillis() - cooldown.get(player.getUniqueId()) >= 1000)) {
                    cooldown.put(player.getUniqueId(), System.currentTimeMillis());

                    shotProjectile(player, player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.FIRE_ASPECT));

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

    private void shotProjectile(Player player, boolean fire){
        Location playerLocation = player.getEyeLocation();
        org.bukkit.util.Vector vector = playerLocation.getDirection();

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
                    if (entity instanceof LivingEntity target && target.customName() == null && !target.equals(player)) {
                        if(!(target instanceof Tameable tameable && tameable.isTamed())) {
                            if (fire) {
                                target.setFireTicks(100);
                            }

                            if (target.getType() == EntityType.PLAYER) {
                                target.damage(3.0, player);
                            } else {
                                target.damage(6.0, player);
                            }
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
