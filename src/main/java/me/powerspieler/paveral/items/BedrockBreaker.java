package me.powerspieler.paveral.items;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.crafting.StandardIngredient;
import me.powerspieler.paveral.util.Constant;
import me.powerspieler.paveral.util.ItemsUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BedrockBreaker extends PaveralItem implements Listener, Dismantable, Enchantable {
    private static Component itemName(){
        return Component.text("Bedrock Breaker", NamedTextColor.DARK_PURPLE)
                .decoration(TextDecoration.ITALIC, false);
    }

    private static List<Component> lore(){
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Press ", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false)
                .append(Component.keybind("key.mouse.right", NamedTextColor.YELLOW)
                        .decoration(TextDecoration.ITALIC, false))
                .append(Component.text(" to break bedrock or", NamedTextColor.DARK_AQUA)
                        .decoration(TextDecoration.ITALIC, false)));
        lore.add(Component.text("repair using ancient debris", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text(""));
        lore.add(Component.text("Only enchantable with ", NamedTextColor.BLUE)
                .decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Unbreaking", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)));
        return lore;
    }

    public BedrockBreaker() {
        super(Material.WARPED_FUNGUS_ON_A_STICK, 5, Constant.ITEMTYPE, "bedrock_breaker", itemName(), lore());
    }

    @Override
    public PaveralRecipe recipe() {
        Set<StandardIngredient> ingredients = new HashSet<>();
        ingredients.add(new StandardIngredient(Material.OBSIDIAN, 1));
        ingredients.add(new StandardIngredient(Material.PISTON, 2));
        ingredients.add(new StandardIngredient(Material.TNT, 2));
        ingredients.add(new StandardIngredient(Material.LEVER, 1));
        ingredients.add(new StandardIngredient(Material.OAK_TRAPDOOR, 1));
        ingredients.add(new StandardIngredient(Material.ANCIENT_DEBRIS, 4));
        return new PaveralRecipe(ingredients, this.build());
    }

    @Override
    public List<ItemStack> parts() {
        List<ItemStack> parts = new ArrayList<>();
        parts.add(new ItemStack(Material.OBSIDIAN));
        parts.add(new ItemStack(Material.PISTON, 2));
        parts.add(new ItemStack(Material.TNT, 2));
        parts.add(new ItemStack(Material.LEVER));
        parts.add(new ItemStack(Material.OAK_TRAPDOOR));
        return parts;
    }

    @EventHandler
    @Override
    public void onEnchantingAttempt(PrepareAnvilEvent event) {
        Set<Enchantment> enchants = new HashSet<>();
        enchants.add(Enchantment.MENDING);
        Enchantable.super.onEnchantingAttempt(event, keyString, enchants);
    }

    // --- Item Logic ---

    @EventHandler
    private void onPlayerRightClick(PlayerInteractEvent event) {
        if (ItemHoldingController.checkIsHoldingPaveralItem(event.getPlayer(), keyString)) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Damageable itemdamage = (Damageable) event.getItem().getItemMeta();
                        Block block = event.getClickedBlock();
                        if (block != null && block.getType() == Material.BEDROCK && itemdamage.getDamage() < 100) {
                            final Audience targets = block.getWorld().filterAudience(member -> member instanceof Player player && player.getLocation().distanceSquared(block.getLocation()) < 25);
                            block.setType(Material.AIR);
                            block.getWorld().spawnParticle(Particle.ASH, block.getLocation().add(0.5, 0.5, 0.5), 500, 0.25, 0.25, 0.25);
                            targets.playSound(Sound.sound(Key.key("entity.elder_guardian.death"), Sound.Source.AMBIENT, 1f, 0.75f), Sound.Emitter.self());
                            targets.playSound(Sound.sound(Key.key("entity.wither.break_block"), Sound.Source.AMBIENT, 1f, 0.25f), Sound.Emitter.self());
                            ItemsUtil.applyDamage(event.getItem(), 2, 100);
                        } else if (block != null && block.getType() == Material.ANCIENT_DEBRIS) {
                            final Audience targets = block.getWorld().filterAudience(member -> member instanceof Player player && player.getLocation().distanceSquared(block.getLocation()) < 25);
                            block.setType(Material.AIR);
                            block.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, block.getLocation().add(0.5, 0.5, 0.5), 100, 0.25, 0.25, 0.25, new Particle.DustTransition(Color.BLACK, Color.WHITE, 2));
                            targets.stopSound(Sound.sound(Key.key("entity.ender_dragon.death"), Sound.Source.AMBIENT, 1f, 2f));
                            targets.playSound(Sound.sound(Key.key("block.ancient_debris.break"), Sound.Source.BLOCK, 1f, 1f), Sound.Emitter.self());
                            targets.playSound(Sound.sound(Key.key("ui.stonecutter.take_result"), Sound.Source.AMBIENT, 1f, 0.25f), Sound.Emitter.self());
                            targets.playSound(Sound.sound(Key.key("entity.wither.ambient"), Sound.Source.AMBIENT, 1f, 0.5f), Sound.Emitter.self());
                            targets.playSound(Sound.sound(Key.key("entity.ender_dragon.death"), Sound.Source.AMBIENT, 1f, 2f), Sound.Emitter.self());
                            ItemsUtil.repair(event.getItem(), 25);
                        }
                    }
                }.runTaskLater(Paveral.getPlugin(), 1);
            }
        }
    }
}
