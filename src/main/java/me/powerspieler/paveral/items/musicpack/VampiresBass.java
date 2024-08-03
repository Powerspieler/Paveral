package me.powerspieler.paveral.items.musicpack;

import com.destroystokyo.paper.MaterialSetTag;
import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.items.ItemHoldingController;
import me.powerspieler.paveral.items.Items;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.logging.Level;

public class VampiresBass implements Listener, Items {
    @Override
    public ItemStack build() {
        ItemStack item = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.itemName(Component.text("Vampires Bass", NamedTextColor.DARK_PURPLE));
        itemMeta.getPersistentDataContainer().set(Constant.ITEMTYPE, PersistentDataType.STRING, "vampires_bass");
        itemMeta.setCustomModelData(1);

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("LORE"));
        lore.add(Component.text("Enchantable with ", NamedTextColor.BLUE)
                .decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Unbreaking", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)
                )
                .append(Component.text(" and ", NamedTextColor.BLUE)
                        .decoration(TextDecoration.ITALIC, false)
                )
                .append(Component.text("Mending", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)));
        itemMeta.lore(lore);

        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    public List<ItemStack> parts() {
        return List.of();
    }


    @EventHandler
    public void onTreeBreak(BlockBreakEvent event) {
        long oldTime = System.currentTimeMillis();

        if (ItemHoldingController.checkIsHoldingPaveralItem(event.getPlayer(), "vampires_bass") && MaterialSetTag.LOGS.isTagged(event.getBlock().getType())) {
            event.setCancelled(true);
            Block logBlock = event.getBlock();
            Material logBlockType = logBlock.getType();
            Material leavesType = convertToLeavesVariant(logBlockType);

            Set<Block> leavesEntryList = breakLogRecursive(logBlock, logBlockType, leavesType);
            leavesEntryList.forEach(Block::tick); //Distance 1 -> 3

            Set<Block> leavesSet = new HashSet<>();
            for (Block leavesBlock : leavesEntryList) {
                leavesSet.addAll(gatherLeavesRecursive(leavesBlock, leavesType, leavesSet));
            }
            Paveral.getPlugin().getLogger().log(Level.WARNING, "Soos");
            new BukkitRunnable() {
                @Override
                public void run() {
                    leavesSet.forEach(Block::randomTick);


                        cancel();
                        Bukkit.broadcast(Component.text("Finished! " + (System.currentTimeMillis()-oldTime), NamedTextColor.GREEN));

                }
            }.runTaskTimer(Paveral.getPlugin(), 7L, 1L);
        }
    }


    private Set<Block> breakLogRecursive(Block block, Material logType, Material leavesType) {
        Set<Block> leavesBlocks = new HashSet<>();
        if (block.getType() == logType) {
            block.breakNaturally(true);

            for (int x = -1; x <= 1; x++) {
                for (int y = 0; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        if (x == 0 && y == 0 && z == 0) {
                            continue;
                        }
                        if (block.getRelative(x, y, z).getType() == leavesType) {
                            leavesBlocks.add(block.getRelative(x, y, z));
                            block.getRelative(x, y, z).tick();
                        }
                        leavesBlocks.addAll(breakLogRecursive(block.getRelative(x, y, z), logType, leavesType));
                    }
                }
            }
        }
        return leavesBlocks;
    }

    private Material convertToLeavesVariant(Material logType) {
        Material leavesType = null;
        switch (logType) {
            case OAK_LOG, OAK_WOOD, STRIPPED_OAK_LOG, STRIPPED_OAK_WOOD -> leavesType = Material.OAK_LEAVES;
            case SPRUCE_LOG, SPRUCE_WOOD, STRIPPED_SPRUCE_LOG, STRIPPED_SPRUCE_WOOD ->
                    leavesType = Material.SPRUCE_LEAVES;
            case BIRCH_LOG, BIRCH_WOOD, STRIPPED_BIRCH_LOG, STRIPPED_BIRCH_WOOD -> leavesType = Material.BIRCH_LEAVES;
            case JUNGLE_LOG, JUNGLE_WOOD, STRIPPED_JUNGLE_LOG, STRIPPED_JUNGLE_WOOD ->
                    leavesType = Material.JUNGLE_LEAVES;
            case ACACIA_LOG, ACACIA_WOOD, STRIPPED_ACACIA_LOG, STRIPPED_ACACIA_WOOD ->
                    leavesType = Material.ACACIA_LEAVES;
            case DARK_OAK_LOG, DARK_OAK_WOOD, STRIPPED_DARK_OAK_LOG, STRIPPED_DARK_OAK_WOOD ->
                    leavesType = Material.DARK_OAK_LEAVES;
            // Azalea ??
            case MANGROVE_LOG, MANGROVE_WOOD, STRIPPED_MANGROVE_LOG, STRIPPED_MANGROVE_WOOD ->
                    leavesType = Material.MANGROVE_LEAVES;
            case CHERRY_LOG, CHERRY_WOOD, STRIPPED_CHERRY_LOG, STRIPPED_CHERRY_WOOD ->
                    leavesType = Material.CHERRY_LEAVES;
            // NETHER
            // HUGE MUSHROOMS
        }
        return leavesType;
    }

    private final List<BlockFace> blockFaceList = List.of(BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST);

    private Set<Block> gatherLeavesRecursive(Block block, Material material, Set<Block> leavesSet) {
        if (block.getBlockData() instanceof Leaves test) {
            Bukkit.broadcast(Component.text("Persistent: " + test.isPersistent() + ", Distance: " + test.getDistance()));
        }
        if (block.getType() == material && !leavesSet.contains(block) && block.getBlockData() instanceof Leaves leavesBlockData && !leavesBlockData.isPersistent() /*&& leavesBlockData.getDistance() == 7*/) {
            leavesSet.add(block);
            for (BlockFace face : blockFaceList) {
                leavesSet.addAll(gatherLeavesRecursive(block.getRelative(face), material, leavesSet));
            }
        }
        return leavesSet;
    }

    @EventHandler
    public void onEnchantingAttempt(PrepareAnvilEvent event) {
        if (event.getInventory().getFirstItem() != null && event.getResult() != null && event.getInventory().getFirstItem().getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE)) {
            if (Objects.equals(event.getInventory().getFirstItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "vampires_bass")) {
                ItemStack result = event.getResult();
                ItemMeta resultmeta = result.getItemMeta();
                if (event.getResult().containsEnchantment(Enchantment.EFFICIENCY)) {
                    resultmeta.removeEnchant(Enchantment.EFFICIENCY);
                }
                if (event.getResult().containsEnchantment(Enchantment.SILK_TOUCH)) {
                    resultmeta.removeEnchant(Enchantment.SILK_TOUCH);
                }
                if (event.getResult().containsEnchantment(Enchantment.FORTUNE)) {
                    resultmeta.removeEnchant(Enchantment.FORTUNE);
                }
                if (event.getResult().containsEnchantment(Enchantment.SHARPNESS)) {
                    resultmeta.removeEnchant(Enchantment.SHARPNESS);
                }
                if (event.getResult().containsEnchantment(Enchantment.SMITE)) {
                    resultmeta.removeEnchant(Enchantment.SMITE);
                }
                if (event.getResult().containsEnchantment(Enchantment.BANE_OF_ARTHROPODS)) {
                    resultmeta.removeEnchant(Enchantment.BANE_OF_ARTHROPODS);
                }
                result.setItemMeta(resultmeta);
                event.setResult(result);

                if (event.getInventory().getSecondItem() != null && event.getInventory().getSecondItem().getType() == Material.ENCHANTED_BOOK) {
                    if (event.getInventory().getFirstItem().getEnchantments().equals(event.getResult().getEnchantments())) {
                        event.setResult(new ItemStack(Material.AIR));
                    }
                }
            }
        }
    }
}
