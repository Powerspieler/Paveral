package me.powerspieler.paveral.items.musicpack;

import com.destroystokyo.paper.MaterialSetTag;
import io.papermc.paper.event.block.BlockBreakProgressUpdateEvent;
import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.crafting.PaveralIngredient;
import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.crafting.StandardIngredient;
import me.powerspieler.paveral.items.PaveralItem;
import me.powerspieler.paveral.items.helper.Enchantable;
import me.powerspieler.paveral.items.helper.ItemHoldingController;
import me.powerspieler.paveral.items.helper.TotemDisabler;
import me.powerspieler.paveral.util.Constant;
import me.powerspieler.paveral.util.ItemsUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
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
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LumberjacksBass extends PaveralItem implements Listener, Enchantable {
    private static Component itemName(){
        return Component.text("Lumberjack's Bass", NamedTextColor.DARK_PURPLE);
    }

    private static List<Component> lore(){
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("The power of this axe shatters through the whole tree", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("In addition, leaves decay instantaneously", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text(""));
        lore.add(Component.text("Includes: Every type of log, crimson and warped stem", NamedTextColor.DARK_GREEN)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("            and huge mushrooms", NamedTextColor.DARK_GREEN)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Enchantable with ", NamedTextColor.BLUE)
                .decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Unbreaking", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false))
                .append(Component.text(" and ", NamedTextColor.BLUE)
                        .decoration(TextDecoration.ITALIC, false))
                .append(Component.text("Mending", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)));
        lore.addAll(TotemDisabler.loreAddition());
        return lore;
    }


    public LumberjacksBass() {
        super(Material.NETHERITE_AXE, "vampires_bass", Constant.ITEMTYPE, "lumberjacks_bass", itemName(), lore());
    }

    private final AttributeModifier largeTreeModifier = new AttributeModifier(new NamespacedKey(Paveral.getPlugin(), "largeTreeModifier"), -0.935, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.MAINHAND);
    private final AttributeModifier mediumTreeModifier = new AttributeModifier(new NamespacedKey(Paveral.getPlugin(), "mediumTreeModifier"), -0.90, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.MAINHAND);
    private final AttributeModifier smallTreeModifier = new AttributeModifier(new NamespacedKey(Paveral.getPlugin(), "smallTreeModifier"), -0.85, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.MAINHAND);

    @Override
    protected ItemStack build() {
        ItemStack item = super.build();
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addAttributeModifier(Attribute.BLOCK_BREAK_SPEED, smallTreeModifier);
        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    public PaveralRecipe recipe() {
        Set<StandardIngredient> ingredients = new HashSet<>();
        ingredients.add(new PaveralIngredient(Material.JIGSAW, 1, Constant.ITEMTYPE, "music_core"));
        ingredients.add(new StandardIngredient(Material.NETHERITE_AXE, 1));
        return new PaveralRecipe(ingredients, this.build());
    }

    // --- Item Logic ---
    @EventHandler
    private void onTreeStrip(PlayerInteractEvent event) {
        if (ItemHoldingController.checkIsHoldingPaveralItem(event.getPlayer(), keyString)) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null) {
                event.setCancelled(true);
                if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() instanceof Damageable itemMeta && itemMeta.getDamage() == 2031){
                    return;
                }
                Material logType = event.getClickedBlock().getType();

                if(MaterialSetTag.CRIMSON_STEMS.isTagged(logType) || MaterialSetTag.WARPED_STEMS.isTagged(logType)){
                    breakNetherTree(event.getClickedBlock(), event.getPlayer(), true);
                } else if(MaterialSetTag.LOGS.isTagged(logType)){
                    breakTreeWithActualLeaves(event.getClickedBlock(), event.getPlayer(), true);
                } else {
                    event.setCancelled(false);
                }
            }
        }
    }

    @EventHandler
    private void onTreeBreak(BlockBreakEvent event) {
        if (ItemHoldingController.checkIsHoldingPaveralItem(event.getPlayer(), keyString)) {
            event.setCancelled(true);
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() instanceof Damageable itemMeta && itemMeta.getDamage() == 2031){
                return;
            }
            Material logType = event.getBlock().getType();

            if(logType == Material.MUSHROOM_STEM){
                breakMushroom(event.getBlock(), event.getPlayer());
            } else if(MaterialSetTag.CRIMSON_STEMS.isTagged(logType) || MaterialSetTag.WARPED_STEMS.isTagged(logType)){
                breakNetherTree(event.getBlock(), event.getPlayer(), false);
            } else if(MaterialSetTag.LOGS.isTagged(logType)){
                breakTreeWithActualLeaves(event.getBlock(), event.getPlayer(), false);
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
            destroyBlock(block, player.getInventory().getItemInMainHand(), false);
            return breakMushroomStem(block.getRelative(BlockFace.UP), player);
        }
        return block;
    }

    private void breakBrownMushroomTop(Block block, Player player){
        if(block.getType() == Material.BROWN_MUSHROOM_BLOCK){
            destroyBlock(block, player.getInventory().getItemInMainHand(), false);
            for(BlockFace face : blockFaceListFour){
                breakBrownMushroomTop(block.getRelative(face), player);
            }
        }
    }

    private void breakRedMushroomTop(Block block, Player player){
        ItemStack tool = player.getInventory().getItemInMainHand();
        if(block.getType() == Material.RED_MUSHROOM_BLOCK){
            destroyBlock(block, tool, false);
        }
        for(BlockFace face : blockFaceListSameEight){
            if(block.getRelative(face).getType() == Material.RED_MUSHROOM_BLOCK){
                destroyBlock(block.getRelative(face), tool, false);
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
                destroyBlock(tempCenter, tool, false);
            }
            if(tempCenter.getRelative(orthogonalFace).getType() == Material.RED_MUSHROOM_BLOCK){
                destroyBlock(tempCenter.getRelative(orthogonalFace), tool, false);
            }

            if(tempCenter.getRelative(orthogonalFace.getOppositeFace()).getType() == Material.RED_MUSHROOM_BLOCK){
                destroyBlock(tempCenter.getRelative(orthogonalFace.getOppositeFace()), tool, false);
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
    private void breakNetherTree(Block block, Player player, boolean stripMode){
        Block correctedCenter = correctCenter(block);
        Set<Material> blocksToBreak = stripMode ? Set.of(correctedCenter.getType()) : convertToListWithAllTypes(correctedCenter.getType());
        Block wartEntry = breakMainStemRecursive(correctedCenter, blocksToBreak, player.getInventory().getItemInMainHand(), stripMode);
        if(stripMode) return;
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
    private Block breakMainStemRecursive(Block block, Set<Material> blocksToBreak, ItemStack tool, boolean stripMode) {
        if(blocksToBreak.contains(block.getType())){
            if (destroyBlock(block, tool, stripMode)) return null;
            for(BlockFace face : blockFaceListSameEight){ // This is for 3x3 Stems. CenterCorrected Block broken still determines wartEntry.
                if(blocksToBreak.contains(block.getRelative(face).getType())){
                    if (destroyBlock(block.getRelative(face), tool, stripMode)) return null;
                }
            }
            return breakMainStemRecursive(block.getRelative(BlockFace.UP), blocksToBreak, tool, stripMode);
        }
        return block;
    }

    private boolean destroyBlock(Block block, ItemStack tool, boolean stripMode) {
        if(stripMode) {
            Material stripped = convertToStripped(block.getType());
            if(stripped == null) return false;
            block.setType(stripped);
        } else {
            block.breakNaturally(true);
        }
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
    private void breakTreeWithActualLeaves(Block logBlock, Player player, boolean stripMode) {
        Material logBlockType = logBlock.getType();
        Set<Material> leavesType = convertToLeavesVariant(logBlockType);
        Set<Material> logBlockTypeSet = stripMode ?  Set.of(logBlockType) : convertToListWithAllTypes(logBlockType);

        Set<Block> leavesEntryList = breakLogRecursive(logBlock, logBlockTypeSet, leavesType, player.getInventory().getItemInMainHand(), logBlock.getX(), logBlock.getZ(), stripMode);
        if (stripMode) {
            return;
        }
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


    private Set<Block> breakLogRecursive(Block block, Set<Material> blocksToBreak, Set<Material> leavesTypes, ItemStack item, int entryX, int entryZ, boolean stripMode) {
        Set<Block> leavesBlocks = new HashSet<>();
        if (blocksToBreak.contains(block.getType())) {
            if(stripMode){
                Material stripped = convertToStripped(block.getType());
                if(stripped == null){
                    return leavesBlocks;
                } else {
                    block.setType(stripped);
                }
            } else {
                block.breakNaturally(true);
            }
            ItemsUtil.applyDamage(item, 1, 2031);
            if((item.getItemMeta() instanceof Damageable itemMeta && itemMeta.getDamage() == 2031)
                    || !(Math.abs(block.getX() - entryX) <= 4 && Math.abs(block.getZ() - entryZ) <= 4)){
                return leavesBlocks;
            }

            for (int x = -1; x <= 1; x++) {
                for (int y = 0; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        if (x == 0 && y == 0 && z == 0) {
                            continue;
                        }
                        if (leavesTypes.contains(block.getRelative(x, y, z).getType())) {
                            leavesBlocks.add(block.getRelative(x, y, z));
                            block.getRelative(x, y, z).tick();
                        }
                        leavesBlocks.addAll(breakLogRecursive(block.getRelative(x, y, z), blocksToBreak, leavesTypes, item, entryX, entryZ, stripMode));
                    }
                }
            }
        }
        return leavesBlocks;
    }

    private Set<Material> convertToLeavesVariant(Material logType) {
        Set<Material> leavesType = new HashSet<>(); // Set bc of AzealaTrees also using Oak as log
        switch (logType) {
            case OAK_LOG, OAK_WOOD, STRIPPED_OAK_LOG, STRIPPED_OAK_WOOD -> leavesType.addAll(List.of(Material.OAK_LEAVES, Material.AZALEA_LEAVES, Material.FLOWERING_AZALEA_LEAVES));
            case SPRUCE_LOG, SPRUCE_WOOD, STRIPPED_SPRUCE_LOG, STRIPPED_SPRUCE_WOOD -> leavesType.add(Material.SPRUCE_LEAVES);
            case BIRCH_LOG, BIRCH_WOOD, STRIPPED_BIRCH_LOG, STRIPPED_BIRCH_WOOD -> leavesType.add(Material.BIRCH_LEAVES);
            case JUNGLE_LOG, JUNGLE_WOOD, STRIPPED_JUNGLE_LOG, STRIPPED_JUNGLE_WOOD -> leavesType.add(Material.JUNGLE_LEAVES);
            case ACACIA_LOG, ACACIA_WOOD, STRIPPED_ACACIA_LOG, STRIPPED_ACACIA_WOOD -> leavesType.add(Material.ACACIA_LEAVES);
            case DARK_OAK_LOG, DARK_OAK_WOOD, STRIPPED_DARK_OAK_LOG, STRIPPED_DARK_OAK_WOOD -> leavesType.add(Material.DARK_OAK_LEAVES);
            case MANGROVE_LOG, MANGROVE_WOOD, STRIPPED_MANGROVE_LOG, STRIPPED_MANGROVE_WOOD -> leavesType.add(Material.MANGROVE_LEAVES);
            case CHERRY_LOG, CHERRY_WOOD, STRIPPED_CHERRY_LOG, STRIPPED_CHERRY_WOOD -> leavesType.add(Material.CHERRY_LEAVES);
            case PALE_OAK_LOG, PALE_OAK_WOOD, STRIPPED_PALE_OAK_LOG, STRIPPED_PALE_OAK_WOOD -> leavesType.add(Material.PALE_OAK_LEAVES);
        }
        return leavesType;
    }

    private Set<Material> convertToListWithAllTypes(Material logType) {
        Set<Material> logs = new HashSet<>();
        switch (logType) {
            case OAK_LOG, OAK_WOOD, STRIPPED_OAK_LOG, STRIPPED_OAK_WOOD -> logs.addAll(List.of(Material.OAK_LOG ,Material.STRIPPED_OAK_LOG, Material.OAK_WOOD, Material.STRIPPED_OAK_WOOD));
            case SPRUCE_LOG, SPRUCE_WOOD, STRIPPED_SPRUCE_LOG, STRIPPED_SPRUCE_WOOD -> logs.addAll(List.of(Material.SPRUCE_LOG, Material.STRIPPED_SPRUCE_LOG, Material.SPRUCE_WOOD, Material.STRIPPED_SPRUCE_WOOD));
            case BIRCH_LOG, BIRCH_WOOD, STRIPPED_BIRCH_LOG, STRIPPED_BIRCH_WOOD -> logs.addAll(List.of(Material.BIRCH_LOG, Material.STRIPPED_BIRCH_LOG, Material.BIRCH_WOOD, Material.STRIPPED_BIRCH_WOOD));
            case JUNGLE_LOG, JUNGLE_WOOD, STRIPPED_JUNGLE_LOG, STRIPPED_JUNGLE_WOOD -> logs.addAll(List.of(Material.JUNGLE_LOG, Material.STRIPPED_JUNGLE_LOG, Material.JUNGLE_WOOD, Material.STRIPPED_JUNGLE_WOOD));
            case ACACIA_LOG, ACACIA_WOOD, STRIPPED_ACACIA_LOG, STRIPPED_ACACIA_WOOD -> logs.addAll(List.of(Material.ACACIA_LOG, Material.STRIPPED_ACACIA_LOG, Material.ACACIA_WOOD, Material.STRIPPED_ACACIA_WOOD));
            case DARK_OAK_LOG, DARK_OAK_WOOD, STRIPPED_DARK_OAK_LOG, STRIPPED_DARK_OAK_WOOD -> logs.addAll(List.of(Material.DARK_OAK_LOG, Material.STRIPPED_DARK_OAK_LOG, Material.DARK_OAK_WOOD, Material.STRIPPED_DARK_OAK_WOOD));
            case MANGROVE_LOG, MANGROVE_WOOD, STRIPPED_MANGROVE_LOG, STRIPPED_MANGROVE_WOOD -> logs.addAll(List.of(Material.MANGROVE_LOG, Material.STRIPPED_MANGROVE_LOG, Material.MANGROVE_WOOD, Material.STRIPPED_MANGROVE_WOOD));
            case CHERRY_LOG, CHERRY_WOOD, STRIPPED_CHERRY_LOG, STRIPPED_CHERRY_WOOD -> logs.addAll(List.of(Material.CHERRY_LOG, Material.STRIPPED_CHERRY_LOG, Material.CHERRY_WOOD, Material.STRIPPED_CHERRY_WOOD));
            case PALE_OAK_LOG, PALE_OAK_WOOD, STRIPPED_PALE_OAK_LOG, STRIPPED_PALE_OAK_WOOD -> logs.addAll(List.of(Material.PALE_OAK_LOG, Material.STRIPPED_PALE_OAK_LOG, Material.PALE_OAK_WOOD, Material.STRIPPED_PALE_OAK_WOOD));
            case CRIMSON_STEM, STRIPPED_CRIMSON_STEM, CRIMSON_HYPHAE, STRIPPED_CRIMSON_HYPHAE -> logs.addAll(List.of(Material.CRIMSON_STEM, Material.STRIPPED_CRIMSON_STEM, Material.CRIMSON_HYPHAE, Material.STRIPPED_CRIMSON_HYPHAE));
            case WARPED_STEM, STRIPPED_WARPED_STEM, WARPED_HYPHAE, STRIPPED_WARPED_HYPHAE -> logs.addAll(List.of(Material.WARPED_STEM, Material.STRIPPED_WARPED_STEM, Material.WARPED_HYPHAE, Material.STRIPPED_WARPED_HYPHAE));
        }
        return logs;
    }

    private Material convertToStripped(Material log){
        switch (log) {
            case OAK_LOG -> { return Material.STRIPPED_OAK_LOG; }
            case OAK_WOOD -> { return Material.STRIPPED_OAK_WOOD; }
            case SPRUCE_LOG -> { return Material.STRIPPED_SPRUCE_LOG; }
            case SPRUCE_WOOD -> { return Material.STRIPPED_SPRUCE_WOOD; }
            case BIRCH_LOG -> { return Material.STRIPPED_BIRCH_LOG; }
            case BIRCH_WOOD -> { return Material.STRIPPED_BIRCH_WOOD; }
            case JUNGLE_LOG -> { return Material.STRIPPED_JUNGLE_LOG; }
            case JUNGLE_WOOD -> { return Material.STRIPPED_JUNGLE_WOOD; }
            case ACACIA_LOG -> { return Material.STRIPPED_ACACIA_LOG; }
            case ACACIA_WOOD -> { return Material.STRIPPED_ACACIA_WOOD; }
            case DARK_OAK_LOG -> { return Material.STRIPPED_DARK_OAK_LOG; }
            case DARK_OAK_WOOD -> { return Material.STRIPPED_DARK_OAK_WOOD; }
            case MANGROVE_LOG -> { return Material.STRIPPED_MANGROVE_LOG; }
            case MANGROVE_WOOD -> { return Material.STRIPPED_MANGROVE_WOOD; }
            case CHERRY_LOG -> { return Material.STRIPPED_CHERRY_LOG; }
            case CHERRY_WOOD -> { return Material.STRIPPED_CHERRY_WOOD; }
            case PALE_OAK_LOG -> { return Material.STRIPPED_PALE_OAK_LOG; }
            case PALE_OAK_WOOD -> { return Material.STRIPPED_PALE_OAK_WOOD; }
            case CRIMSON_STEM -> { return Material.STRIPPED_CRIMSON_STEM; }
            case CRIMSON_HYPHAE -> { return Material.STRIPPED_CRIMSON_HYPHAE; }
            case WARPED_STEM -> { return Material.STRIPPED_WARPED_STEM; }
            case WARPED_HYPHAE -> { return Material.STRIPPED_WARPED_HYPHAE; }
        }
        return null;
    }

    private final List<BlockFace> blockFaceListSix = List.of(BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST);
    private void breakLeavesRecursive(Block block, Set<Material> material) {
        if (material.contains(block.getType()) && block.getBlockData() instanceof Leaves leavesBlockData && !leavesBlockData.isPersistent() && leavesBlockData.getDistance() == 7) {
            block.breakNaturally(false);
            for (BlockFace face : blockFaceListSix) {
                breakLeavesRecursive(block.getRelative(face), material);
            }
        }
    }


    // Chance Mining Speed based on Tree
    @EventHandler
    private void onBlockBreaking(BlockBreakProgressUpdateEvent event){
        if(event.getEntity() instanceof Player player && ItemHoldingController.checkIsHoldingPaveralItem(player, keyString) && (MaterialSetTag.LOGS.isTagged(event.getBlock().getType()) || event.getBlock().getType() == Material.MUSHROOM_STEM)){
            Material logType = event.getBlock().getType();
            if(event.getBlock().getRelative(BlockFace.NORTH).getType() == logType || event.getBlock().getRelative(BlockFace.EAST).getType() == logType
                    || event.getBlock().getRelative(BlockFace.SOUTH).getType() == logType || event.getBlock().getRelative(BlockFace.WEST).getType() == logType){
                ItemMeta toolMeta = player.getInventory().getItemInMainHand().getItemMeta();
                toolMeta.removeAttributeModifier(Attribute.BLOCK_BREAK_SPEED);

                if(MaterialSetTag.CRIMSON_STEMS.isTagged(logType) || MaterialSetTag.WARPED_STEMS.isTagged(logType)){
                    toolMeta.addAttributeModifier(Attribute.BLOCK_BREAK_SPEED, mediumTreeModifier);
                } else {
                    toolMeta.addAttributeModifier(Attribute.BLOCK_BREAK_SPEED, largeTreeModifier);
                }
                player.getInventory().getItemInMainHand().setItemMeta(toolMeta);
            } else {
                ItemMeta toolMeta = player.getInventory().getItemInMainHand().getItemMeta();
                toolMeta.removeAttributeModifier(Attribute.BLOCK_BREAK_SPEED);
                toolMeta.addAttributeModifier(Attribute.BLOCK_BREAK_SPEED, smallTreeModifier);
                player.getInventory().getItemInMainHand().setItemMeta(toolMeta);
            }
        }
    }

    @Override
    @EventHandler
    public void onEnchantingAttempt(PrepareAnvilEvent event) {
        Set<Enchantment> enchants = new HashSet<>();
        enchants.add(Enchantment.EFFICIENCY);
        enchants.add(Enchantment.SILK_TOUCH);
        enchants.add(Enchantment.FORTUNE);
        enchants.add(Enchantment.SHARPNESS);
        enchants.add(Enchantment.SMITE);
        enchants.add(Enchantment.BANE_OF_ARTHROPODS);
        Enchantable.super.onEnchantingAttempt(event, keyString, enchants);
    }
}
