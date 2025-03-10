package me.powerspieler.paveral.items.musicpack;

import com.destroystokyo.paper.MaterialTags;
import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.crafting.PaveralIngredient;
import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.crafting.StandardIngredient;
import me.powerspieler.paveral.items.CooldownItem;
import me.powerspieler.paveral.items.helper.ItemHoldingController;
import me.powerspieler.paveral.items.helper.TotemDisabler;
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

import java.util.*;

public class StringBlade extends CooldownItem implements Listener {
    public static Set<PaveralRecipe> getAllStringBladeRecipes(){
        Set<PaveralRecipe> recipes = new HashSet<>(16);
        for (Material dye : MaterialTags.DYES.getValues()) {
            recipes.add(new StringBlade(dye, getCustomModelData(dye)).recipe());
        }
        return recipes;
    }

    private static String getCustomModelData(Material material){
        String out = "stringblade/";
        switch (material){
            case BLACK_DYE -> out = out + "black";
            case BLUE_DYE -> out = out + "blue";
            case BROWN_DYE -> out = out + "brown";
            case CYAN_DYE -> out = out + "cyan";
            case GRAY_DYE -> out = out + "gray";
            case GREEN_DYE -> out = out + "green";
            case LIGHT_BLUE_DYE -> out = out + "light_blue";
            case LIGHT_GRAY_DYE -> out = out + "light_gray";
            case LIME_DYE ->out = out + "lime";
            case MAGENTA_DYE -> out = out + "magenta";
            case ORANGE_DYE ->out = out + "orange";
            case PINK_DYE -> out = out + "pink";
            case PURPLE_DYE -> out = out + "purple";
            case RED_DYE -> out = out + "red";
            case WHITE_DYE -> out = out + "white";
            case YELLOW_DYE -> out = out + "yellow";
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
        lore.addAll(TotemDisabler.loreAddition());
        return lore;
    }

    private final Material color;

    public StringBlade(Material color, String customModelDataString) {
        super(Material.NETHERITE_SWORD, customModelDataString, Constant.ITEMTYPE, "string_blade", itemName(color), lore(), 1000);
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

    @EventHandler
    private void onItemUse(PlayerInteractEvent event){
        if(ItemHoldingController.checkIsHoldingPaveralItem(event.getPlayer(), keyString)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Player player = event.getPlayer();
                if(notOnCooldown(player)) {
                    applyCooldown(player, false);
                    shotProjectile(player, player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.FIRE_ASPECT));
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
