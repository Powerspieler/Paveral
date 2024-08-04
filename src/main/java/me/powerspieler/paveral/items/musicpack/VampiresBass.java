package me.powerspieler.paveral.items.musicpack;

import com.destroystokyo.paper.MaterialSetTag;
import io.papermc.paper.event.block.BlockBreakProgressUpdateEvent;
import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.items.ItemHoldingController;
import me.powerspieler.paveral.items.Items;
import me.powerspieler.paveral.util.Constant;
import me.powerspieler.paveral.util.ItemsUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class VampiresBass implements Listener, Items {

    private final AttributeModifier largeTreeModifier = new AttributeModifier(new NamespacedKey(Paveral.getPlugin(), "largeTreeModifier"), -0.985, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.MAINHAND);
    private final AttributeModifier mediumTreeModifier = new AttributeModifier(new NamespacedKey(Paveral.getPlugin(), "mediumTreeModifier"), -0.95, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.MAINHAND);
    private final AttributeModifier smallTreeModifier = new AttributeModifier(new NamespacedKey(Paveral.getPlugin(), "smallTreeModifier"), -0.90, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.MAINHAND);


    @Override
    public ItemStack build() {
        ItemStack item = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.itemName(Component.text("Vampires Bass", NamedTextColor.DARK_PURPLE));
        itemMeta.getPersistentDataContainer().set(Constant.ITEMTYPE, PersistentDataType.STRING, "vampires_bass");
        itemMeta.setCustomModelData(1);

        itemMeta.addAttributeModifier(Attribute.PLAYER_BLOCK_BREAK_SPEED, smallTreeModifier);

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("LORE")); //TODO LORE
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
        if (ItemHoldingController.checkIsHoldingPaveralItem(event.getPlayer(), "vampires_bass")) {
            event.setCancelled(true);
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() instanceof Damageable itemMeta && itemMeta.getDamage() == 2031){
                return;
            }
            Material logType = event.getBlock().getType();

            if(logType == Material.MUSHROOM_STEM){
                breakMushroom(event.getBlock(), event.getPlayer());
            } else if(MaterialSetTag.CRIMSON_STEMS.isTagged(logType) || MaterialSetTag.WARPED_STEMS.isTagged(logType)){
                breakNetherTree(event.getBlock(), event.getPlayer());
            } else if(MaterialSetTag.LOGS.isTagged(logType)){
                breakTreeWithActualLeaves(event.getBlock(), event.getPlayer());
            } else {
                event.setCancelled(false);
            }
        }
    }


    /* ------------------------
    * --- break stem recursivly
    * --- decide red - / - brown
    * --- break 3x3 top + break every 3x3 side - / - expand recursive on x and z, break every brown mushroom block
    * ------------------------
     */
    private void breakMushroom(Block block, Player player){
        Block treeTopEntry = breakMushroomStem(block, player);
        switch(treeTopEntry.getType()){
            case BROWN_MUSHROOM_BLOCK -> breakBrownMushroomTop(treeTopEntry, player);
            case RED_MUSHROOM_BLOCK -> breakRedMushroomTop(treeTopEntry, player);
        }
    }

    private Block breakMushroomStem(Block block, Player player){
        if(block.getType() == Material.MUSHROOM_STEM){
            destroyBlock(block, player.getInventory().getItemInMainHand());
            return breakMushroomStem(block.getRelative(BlockFace.UP), player);
        }
        return block;
    }

    private void breakBrownMushroomTop(Block block, Player player){
        if(block.getType() == Material.BROWN_MUSHROOM_BLOCK){
            destroyBlock(block, player.getInventory().getItemInMainHand());
            for(BlockFace face : blockFaceListFour){
                breakBrownMushroomTop(block.getRelative(face), player);
            }
        }
    }

    private void breakRedMushroomTop(Block block, Player player){
        ItemStack tool = player.getInventory().getItemInMainHand();
        if(block.getType() == Material.RED_MUSHROOM_BLOCK){
            destroyBlock(block, tool);
        }
        for(BlockFace face : blockFaceListSameEight){
            if(block.getRelative(face).getType() == Material.RED_MUSHROOM_BLOCK){
                destroyBlock(block.getRelative(face), tool);
            }
        }
        for(BlockFace face : blockFaceListFour){
            breakRedMushroomSide(block, tool, BlockFace.NORTH);
            breakRedMushroomSide(block, tool, BlockFace.EAST);
            breakRedMushroomSide(block, tool, BlockFace.SOUTH);
            breakRedMushroomSide(block, tool, BlockFace.WEST);

        }
    }

    private void breakRedMushroomSide(Block block, ItemStack tool, BlockFace face){
        Block tempCenter = block.getRelative(BlockFace.DOWN).getRelative(face).getRelative(face);
        BlockFace orthogonalFace = BlockFace.NORTH;
        switch (face){
            case NORTH -> orthogonalFace = BlockFace.EAST;
            case EAST -> orthogonalFace = BlockFace.SOUTH;
            case SOUTH -> orthogonalFace = BlockFace.WEST;
            case WEST -> orthogonalFace = BlockFace.NORTH;
        }

        for (int i = 0; i < 3; i++) {
            if(tempCenter.getType() == Material.RED_MUSHROOM_BLOCK){
                destroyBlock(tempCenter, tool);
            }
            if(tempCenter.getRelative(orthogonalFace).getType() == Material.RED_MUSHROOM_BLOCK){
                destroyBlock(tempCenter.getRelative(orthogonalFace), tool);
            }

            if(tempCenter.getRelative(orthogonalFace.getOppositeFace()).getType() == Material.RED_MUSHROOM_BLOCK){
                destroyBlock(tempCenter.getRelative(orthogonalFace.getOppositeFace()), tool);
            }

            tempCenter = tempCenter.getRelative(BlockFace.DOWN);
        }
    }

    /*
    * ------------------------
    * --- onBlockBreak the best center for 3x3 will be calculated. Has no effect on 1x1 trees.
    * --- Center will then break (recursive) to the top in 3x3.
    * --- If theres a wartblock on top of the stem, it will break all neighbors (recursive) (distance on x and z to the center must be less or equal to 3;
    * --- y must be greater or equal to the initalBlockBreak (CenterCorrected))
    * --- Shroomlights are treated like wartBlocks.
    * ------------------------
     */
    private void breakNetherTree(Block block, Player player){
        Block correctedCenter = correctCenter(block);
        Block wartEntry = breakMainStemRecursive(correctedCenter, correctedCenter.getType(), player.getInventory().getItemInMainHand());
        if(wartEntry == null || !MaterialSetTag.WART_BLOCKS.isTagged(wartEntry.getType())) return;
        breakWartTreetop(wartEntry, wartEntry.getType(), wartEntry.getX(), wartEntry.getZ(), correctedCenter.getY());
    }

    private Block correctCenter(Block block){
        Material material = block.getType();
        int currentHighest = calculateAmountOfSameNeighbors(block);
        Block candidate = block;
        for(BlockFace face : blockFaceListSameEight){
            if(block.getRelative(face).getType() != material) continue;
            int neighbors = calculateAmountOfSameNeighbors(block.getRelative(face));
            if(currentHighest < neighbors){
                currentHighest = neighbors;
                candidate = block.getRelative(face);
            }
        }
        return candidate;
    }

    private final List<BlockFace> blockFaceListFour = List.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
    private int calculateAmountOfSameNeighbors(Block block){
        Material material = block.getType();
        int out = 0;
        for(BlockFace face : blockFaceListFour){
            if(block.getRelative(face).getType() == material){
                out++;
            }
        }
        return out;
    }



    private final List<BlockFace> blockFaceListSameEight = List.of(BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST);
    private final List<BlockFace> blockFaceListWeirdEight = List.of(BlockFace.NORTH_NORTH_EAST, BlockFace.EAST_NORTH_EAST, BlockFace.EAST_SOUTH_EAST, BlockFace.SOUTH_SOUTH_EAST, BlockFace.SOUTH_SOUTH_WEST, BlockFace.WEST_SOUTH_WEST, BlockFace.WEST_NORTH_WEST, BlockFace.NORTH_NORTH_WEST);
    private Block breakMainStemRecursive(Block block, Material stemType, ItemStack tool){
        if(block.getType() == stemType){
            if (destroyBlock(block, tool)) return null;
            for(BlockFace face : blockFaceListSameEight){ // This is for 3x3 Stems. CenterCorrected Block broken still determines wartEntry.
                if(block.getRelative(face).getType() == stemType){
                    if (destroyBlock(block.getRelative(face), tool)) return null;
                }
            }
            return breakMainStemRecursive(block.getRelative(BlockFace.UP), stemType, tool);
        }
        return block;
    }

    private static boolean destroyBlock(Block block, ItemStack tool) {
        block.breakNaturally(true);
        ItemsUtil.applyDamage(tool, 1, 2031);
        return tool.getItemMeta() instanceof Damageable itemMeta && itemMeta.getDamage() == 2031;
    }


    private void breakWartTreetop(Block block, Material material, int entryX, int entryZ, int lowestY){
        if((block.getType() == material || block.getType() == Material.SHROOMLIGHT)
                && Math.abs(block.getX() - entryX) <= 3 && Math.abs(block.getZ() - entryZ) <= 3 && block.getY() >= lowestY){
            block.breakNaturally(true);
            for(BlockFace face : blockFaceListSameEight){
                breakWartTreetop(block.getRelative(face), material, entryX, entryZ, lowestY); // Surround Entry
                breakWartTreetop(block.getRelative(BlockFace.DOWN), material, entryX, entryZ, lowestY); // Entry's Y - 1
                breakWartTreetop(block.getRelative(BlockFace.DOWN).getRelative(face), material, entryX, entryZ, lowestY); // Surround Y - 1
            }
        }
    }

    /*
    * ------------------------
    * --- Logs check all 8 Blocks around them on same Y and all 9 Blocks on Y + 1 (Origin Block breaks; this is recursive) Returns List of Leaves found.
    * --- Wait for every leavesBlock to update to distance 7
    * --- Then Leaves of List destroy itself with their neighbors(Distance of 7 + notPersistant; 6 Directions) (Recursive)
    * ------------------------
     */
    private void breakTreeWithActualLeaves(Block logBlock, Player player) {
        Material logBlockType = logBlock.getType();
        Material leavesType = convertToLeavesVariant(logBlockType);

        Set<Block> leavesEntryList = breakLogRecursive(logBlock, logBlockType, leavesType, player.getInventory().getItemInMainHand());
        leavesEntryList.forEach(Block::tick); //Distance 1 -> 3

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Block leavesBlock : leavesEntryList) {
                    breakLeavesRecursive(leavesBlock, leavesType);
                }
            }
        }.runTaskLater(Paveral.getPlugin(), 6L);
    }


    private Set<Block> breakLogRecursive(Block block, Material logType, Material leavesType, ItemStack item) {
        Set<Block> leavesBlocks = new HashSet<>();
        if (block.getType() == logType) {
            block.breakNaturally(true);
            ItemsUtil.applyDamage(item, 1, 2031);
            if(item.getItemMeta() instanceof Damageable itemMeta && itemMeta.getDamage() == 2031){
                return leavesBlocks;
            }

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
                        leavesBlocks.addAll(breakLogRecursive(block.getRelative(x, y, z), logType, leavesType, item));
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
            // TODO Azalea ??
            case MANGROVE_LOG, MANGROVE_WOOD, STRIPPED_MANGROVE_LOG, STRIPPED_MANGROVE_WOOD ->
                    leavesType = Material.MANGROVE_LEAVES;
            case CHERRY_LOG, CHERRY_WOOD, STRIPPED_CHERRY_LOG, STRIPPED_CHERRY_WOOD ->
                    leavesType = Material.CHERRY_LEAVES;
        }
        return leavesType;
    }

    private final List<BlockFace> blockFaceListSix = List.of(BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST);
    private void breakLeavesRecursive(Block block, Material material) {
        if (block.getType() == material && block.getBlockData() instanceof Leaves leavesBlockData && !leavesBlockData.isPersistent() && leavesBlockData.getDistance() == 7) {
            block.breakNaturally(false);
            for (BlockFace face : blockFaceListSix) {
                breakLeavesRecursive(block.getRelative(face), material);
            }
        }
    }


    // Chance Mining Speed based on Tree
    @EventHandler
    public void onBlockBreaking(BlockBreakProgressUpdateEvent event){
        if(event.getEntity() instanceof Player player && ItemHoldingController.checkIsHoldingPaveralItem(player, "vampires_bass") && (MaterialSetTag.LOGS.isTagged(event.getBlock().getType()) || event.getBlock().getType() == Material.MUSHROOM_STEM)){
            Material logType = event.getBlock().getType();
            if(event.getBlock().getRelative(BlockFace.NORTH).getType() == logType || event.getBlock().getRelative(BlockFace.EAST).getType() == logType
                    || event.getBlock().getRelative(BlockFace.SOUTH).getType() == logType || event.getBlock().getRelative(BlockFace.WEST).getType() == logType){
                ItemMeta toolMeta = player.getInventory().getItemInMainHand().getItemMeta();
                toolMeta.removeAttributeModifier(Attribute.PLAYER_BLOCK_BREAK_SPEED);

                if(MaterialSetTag.CRIMSON_STEMS.isTagged(logType) || MaterialSetTag.WARPED_STEMS.isTagged(logType)){
                    toolMeta.addAttributeModifier(Attribute.PLAYER_BLOCK_BREAK_SPEED, mediumTreeModifier);
                } else {
                    toolMeta.addAttributeModifier(Attribute.PLAYER_BLOCK_BREAK_SPEED, largeTreeModifier);
                }
                player.getInventory().getItemInMainHand().setItemMeta(toolMeta);
            } else {
                ItemMeta toolMeta = player.getInventory().getItemInMainHand().getItemMeta();
                toolMeta.removeAttributeModifier(Attribute.PLAYER_BLOCK_BREAK_SPEED);
                toolMeta.addAttributeModifier(Attribute.PLAYER_BLOCK_BREAK_SPEED, smallTreeModifier);
                player.getInventory().getItemInMainHand().setItemMeta(toolMeta);
            }
        }
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
