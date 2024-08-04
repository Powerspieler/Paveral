package me.powerspieler.paveral.items;

import me.powerspieler.paveral.Paveral;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BedrockBreaker implements Listener,Items {
    
    @Override
    public ItemStack build() {
        ItemStack bedrockbreaker = new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK);
        ItemMeta bedrockbreakermeta = bedrockbreaker.getItemMeta();
        bedrockbreakermeta.getPersistentDataContainer().set(Constant.ITEMTYPE, PersistentDataType.STRING, "bedrock_breaker");
        bedrockbreakermeta.setCustomModelData(5);

        bedrockbreakermeta.itemName(Component.text("Bedrock Breaker", NamedTextColor.DARK_PURPLE)
                .decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Press ", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false)
                .append(Component.keybind("key.mouse.right", NamedTextColor.YELLOW)
                        .decoration(TextDecoration.ITALIC, false)
                )
                .append(Component.text(" to break bedrock or", NamedTextColor.DARK_AQUA)
                        .decoration(TextDecoration.ITALIC, false)));
        lore.add(Component.text("repair using ancient debris", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text(""));
        lore.add(Component.text("Only enchantable with ", NamedTextColor.BLUE)
                .decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Unbreaking", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)));
        bedrockbreakermeta.lore(lore);

        bedrockbreaker.setItemMeta(bedrockbreakermeta);
        return bedrockbreaker;
    }

    @Override
    public List<ItemStack> parts() {
        List<ItemStack> parts = new ArrayList<>();
        ItemStack obsidian = new ItemStack(Material.OBSIDIAN);
        ItemStack piston = new ItemStack(Material.PISTON, 2);
        ItemStack tnt = new ItemStack(Material.TNT, 2);
        ItemStack lever = new ItemStack(Material.LEVER);
        ItemStack oaktrap = new ItemStack(Material.OAK_TRAPDOOR);
        // Skipped 4 Ancient Debris
        parts.add(obsidian);
        parts.add(piston);
        parts.add(tnt);
        parts.add(lever);
        parts.add(oaktrap);
        return parts;
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {
        if (ItemHoldingController.checkIsHoldingPaveralItem(event.getPlayer(), "bedrock_breaker")) {
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
    @EventHandler
    public void onMendingAttempt(PrepareAnvilEvent event) {
        if (event.getInventory().getFirstItem() != null && event.getResult() != null && event.getInventory().getFirstItem().getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE)) {
            if (Objects.equals(event.getInventory().getFirstItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "bedrock_breaker")) {
                ItemStack result = event.getResult();
                ItemMeta resultmeta = result.getItemMeta();
                if (event.getResult().containsEnchantment(Enchantment.MENDING)) {
                    resultmeta.removeEnchant(Enchantment.MENDING);
                }
                result.setItemMeta(resultmeta);
                event.setResult(result);

                if (event.getInventory().getSecondItem() != null && event.getInventory().getSecondItem().getType() == Material.ENCHANTED_BOOK) {
                    if(event.getInventory().getFirstItem().getEnchantments().equals(event.getResult().getEnchantments())){
                        event.setResult(new ItemStack(Material.AIR));
                    }
                }
            }
        }
    }
}
