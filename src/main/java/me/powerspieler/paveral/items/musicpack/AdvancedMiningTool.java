package me.powerspieler.paveral.items.musicpack;

import me.powerspieler.paveral.items.PaveralItem;
import me.powerspieler.paveral.items.helper.Enchantable;
import me.powerspieler.paveral.items.helper.ItemHoldingController;
import me.powerspieler.paveral.util.Constant;
import me.powerspieler.paveral.util.ItemsUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AdvancedMiningTool extends PaveralItem implements Listener, Enchantable {
    public AdvancedMiningTool(Material baseMaterial, String keyString, Component itemName, List<Component> lore) {
        super(baseMaterial, keyString, Constant.ITEMTYPE, keyString, itemName, lore);
    }

    @EventHandler
    protected void onBlockBreak(BlockBreakEvent event){
        if(ItemHoldingController.checkIsHoldingPaveralItem(event.getPlayer(), keyString)){
            BlockFace blockFace = event.getPlayer().getTargetBlockFace(10);
            if(blockFace == null){
                return;
            }

            Player player = event.getPlayer();
            float minBreakSpeed = event.getBlock().getBreakSpeed(player);

            List<Block> blocksAround = this.getAdjacentBlocks(event.getBlock(), blockFace);
            int blocksBroken = 0;
            for (Block adjacentBlock : blocksAround){
                float adjacentBlockBreakSpeed = adjacentBlock.getBreakSpeed(player);
                if(minBreakSpeed <= adjacentBlockBreakSpeed){
                    adjacentBlock.breakNaturally(player.getInventory().getItemInMainHand(), true, true);
                    if(Float.isFinite(adjacentBlockBreakSpeed)){
                        blocksBroken++;
                    }
                }
            }
            if(blocksBroken > 0){
                ItemsUtil.applyDamage(player.getInventory().getItemInMainHand(), blocksBroken, 2031);
            }
        }
    }

    private List<Block> getAdjacentBlocks(Block block, BlockFace face){
        List<Block> blocks = new ArrayList<>();
        switch(face){
            case UP,DOWN -> {
                blocks.add(block.getRelative(BlockFace.NORTH));
                blocks.add(block.getRelative(BlockFace.NORTH_EAST));
                blocks.add(block.getRelative(BlockFace.EAST));
                blocks.add(block.getRelative(BlockFace.SOUTH_EAST));
                blocks.add(block.getRelative(BlockFace.SOUTH));
                blocks.add(block.getRelative(BlockFace.SOUTH_WEST));
                blocks.add(block.getRelative(BlockFace.WEST));
                blocks.add(block.getRelative(BlockFace.NORTH_WEST));
            }
            case NORTH, SOUTH -> {
                Block up = block.getRelative(BlockFace.UP);
                blocks.add(up);
                blocks.add(up.getRelative(BlockFace.WEST));
                blocks.add(up.getRelative(BlockFace.EAST));

                blocks.add(block.getRelative(BlockFace.WEST));
                blocks.add(block.getRelative(BlockFace.EAST));

                Block down = block.getRelative(BlockFace.DOWN);
                blocks.add(down);
                blocks.add(down.getRelative(BlockFace.WEST));
                blocks.add(down.getRelative(BlockFace.EAST));
            }
            case EAST, WEST -> {
                Block up = block.getRelative(BlockFace.UP);
                blocks.add(up);
                blocks.add(up.getRelative(BlockFace.NORTH));
                blocks.add(up.getRelative(BlockFace.SOUTH));

                blocks.add(block.getRelative(BlockFace.NORTH));
                blocks.add(block.getRelative(BlockFace.SOUTH));

                Block down = block.getRelative(BlockFace.DOWN);
                blocks.add(down);
                blocks.add(down.getRelative(BlockFace.NORTH));
                blocks.add(down.getRelative(BlockFace.SOUTH));
            }
        }
        return blocks;
    }

    @Override
    @EventHandler
    public void onEnchantingAttempt(PrepareAnvilEvent event) {
        Set<Enchantment> enchantments = new HashSet<>();
        enchantments.add(Enchantment.UNBREAKING);
        Enchantable.super.onEnchantingAttempt(event, keyString, enchantments);
    }
}
