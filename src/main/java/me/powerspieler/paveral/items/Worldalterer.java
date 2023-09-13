package me.powerspieler.paveral.items;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class Worldalterer implements Listener,Items {
    Map<UUID, Integer> runnableMap = new HashMap<>();

    @Override
    public ItemStack build() {
        ItemStack item = new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.getPersistentDataContainer().set(Constant.ITEMTYPE, PersistentDataType.STRING, "worldalterer");
        itemmeta.setCustomModelData(6);

        item.setItemMeta(itemmeta);
        return item;
    }

    @Override
    public List<ItemStack> parts() {
        return null;
    }

    // TODO Only allow players with crafting advance use this tool
    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(player.getPersistentDataContainer().has(Constant.IS_HOLDING, PersistentDataType.STRING) && player.getPersistentDataContainer().get(Constant.IS_HOLDING, PersistentDataType.STRING).equals("worldalterer")){
            event.setCancelled(true);
            if(!(event.getHand() == EquipmentSlot.HAND)){
                return;
            }

            if(!player.isSneaking()){
                // Change Mode
                if(event.getAction().isLeftClick()){
                    String facing = player.getFacing().name();
                    if(player.getLocation().getPitch() >= 40){
                        facing = "DOWN";
                    }
                    if(player.getLocation().getPitch() <= -40){
                        facing = "UP";
                    }
                    player.getPersistentDataContainer().set(Constant.WA_FACING, PersistentDataType.STRING, facing);
                    cancelActionbar(player);
                    runActionbar(player, 0);
                    return;
                }



                if(player.getPersistentDataContainer().has(Constant.WA_POS1) && player.getPersistentDataContainer().has(Constant.WA_POS2) && player.getPersistentDataContainer().has(Constant.WA_FACING)) {
                    int[] pos1 = player.getPersistentDataContainer().get(Constant.WA_POS1, PersistentDataType.INTEGER_ARRAY);
                    int[] pos2 = player.getPersistentDataContainer().get(Constant.WA_POS2, PersistentDataType.INTEGER_ARRAY);
                    assert pos1 != null;
                    assert pos2 != null;


                    for (int x = Math.min(pos1[0], pos2[0]); x <= Math.max(pos1[0], pos2[0]); x++) {
                        for (int y = Math.min(pos1[1], pos2[1]); y <= Math.max(pos1[1], pos2[1]); y++) {
                            for (int z = Math.min(pos1[2], pos2[2]); z <= Math.max(pos1[2], pos2[2]); z++) {
                                if (Tag.WITHER_IMMUNE.getValues().contains(player.getWorld().getBlockAt(x, y, z).getType())) {
                                    cancelActionbar(player);
                                    player.sendActionBar(Component.text("Unmovable block at " + x + ", " + y + ", " + z, NamedTextColor.RED));
                                    player.playSound(Sound.sound(Key.key("block.note_block.basedrum"), Sound.Source.AMBIENT, 1f, 1f), Sound.Emitter.self());
                                    runActionbar(player, 40);
                                    return;
                                }
                            }
                        }
                    }

                    String facing = player.getPersistentDataContainer().get(Constant.WA_FACING, PersistentDataType.STRING);

                    int[] paste = new int[3];
                    for (int i = 0; i < 3; i++) {
                        paste[i] = Math.min(pos1[i], pos2[i]);
                    }

                    switch (facing) {
                        case "UP" -> paste[1]++;
                        case "DOWN" -> paste[1]--;
                        case "NORTH" -> paste[2]--;
                        case "SOUTH" -> paste[2]++;
                        case "WEST" -> paste[0]--;
                        case "EAST" -> paste[0]++;
                        default -> {
                            return;
                        }

                    }
                    // Check if air
                    int rx = Math.abs(pos1[0] - pos2[0]) + 1;
                    int ry = Math.abs(pos1[1] - pos2[1]) + 1;
                    int rz = Math.abs(pos1[2] - pos2[2]) + 1;

                    int[] aircheck1 = new int[]{pos1[0], pos1[1], pos1[2]};
                    int[] aircheck2 = new int[]{pos2[0], pos2[1], pos2[2]};

                    switch (facing) {
                        case "UP" -> {
                            aircheck1[1] = Math.min(pos1[1], pos2[1]) + ry;
                            aircheck2[1] = Math.max(pos1[1], pos2[1]) + 1;
                        }
                        case "DOWN" -> {
                            aircheck1[1] = Math.min(pos1[1], pos2[1]) - 1;
                            aircheck2[1] = Math.max(pos1[1], pos2[1]) - ry;
                        }
                        case "NORTH" -> {
                            aircheck1[2] = Math.min(pos1[2], pos2[2]) - 1;
                            aircheck2[2] = Math.max(pos1[2], pos2[2]) - rz;
                        }
                        case "SOUTH" -> {
                            aircheck1[2] = Math.min(pos1[2], pos2[2]) + rz;
                            aircheck2[2] = Math.max(pos1[2], pos2[2]) + 1;
                        }
                        case "WEST" -> {
                            aircheck1[0] = Math.min(pos1[0], pos2[0]) - 1;
                            aircheck2[0] = Math.max(pos1[0], pos2[0]) - rx;
                        }
                        case "EAST" -> {
                            aircheck1[0] = Math.min(pos1[0], pos2[0]) + rx;
                            aircheck2[0] = Math.max(pos1[0], pos2[0]) + 1;
                        }
                    }
                    for(int x = Math.min(aircheck1[0], aircheck2[0]); x <= Math.max(aircheck1[0], aircheck2[0]); x++){
                        for(int y = Math.min(aircheck1[1], aircheck2[1]); y <= Math.max(aircheck1[1], aircheck2[1]); y++){
                            for(int z = Math.min(aircheck1[2], aircheck2[2]); z <= Math.max(aircheck1[2], aircheck2[2]); z++){
                                if(!player.getWorld().getBlockAt(x,y,z).isEmpty()){
                                    cancelActionbar(player);
                                    player.sendActionBar(Component.text("Collision at " + x + ", " + y + ", " + z, NamedTextColor.GREEN));
                                    player.playSound(Sound.sound(Key.key("entity.allay.hurt"), Sound.Source.AMBIENT, 1f, 1f), Sound.Emitter.self());
                                    runActionbar(player, 40);
                                    return;
                                }
                            }
                        }
                    }




                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "clone " +
                            pos1[0] + " " + pos1[1] + " " + pos1[2] + " " +
                            pos2[0] + " " + pos2[1] + " " + pos2[2] + " " +
                            paste[0] + " " + paste[1] + " " + paste[2] +
                            " replace move");

                    player.playSound(Sound.sound(Key.key("block.end_portal_frame.fill"), Sound.Source.AMBIENT, 1f, 1.75f), Sound.Emitter.self());



                    // Move SelectionBlocks
                    switch (facing) {
                        case "UP" -> {
                            pos1[1]++;
                            pos2[1]++;
                        }
                        case "DOWN" -> {
                            pos1[1]--;
                            pos2[1]--;
                        }
                        case "NORTH" -> {
                            pos1[2]--;
                            pos2[2]--;
                        }
                        case "SOUTH" -> {
                            pos1[2]++;
                            pos2[2]++;
                        }
                        case "WEST" -> {
                            pos1[0]--;
                            pos2[0]--;
                        }
                        case "EAST" -> {
                            pos1[0]++;
                            pos2[0]++;
                        }
                    }
                    setPosition(player, Action.LEFT_CLICK_BLOCK, pos1);
                    setPosition(player, Action.RIGHT_CLICK_BLOCK, pos2);


                }
            } else {
                // Set Pos
                Block block = event.getClickedBlock();
                if(block != null){
                    int[] pos = new int[]{block.getX(), block.getY(), block.getZ()};
                    setPosition(event.getPlayer(), event.getAction(), pos);
                }
            }
        }
    }

    @EventHandler
    public void onItemSelect(PlayerItemHeldEvent event){
        cancelActionbar(event.getPlayer());
        ItemStack item = event.getPlayer().getInventory().getItem(event.getNewSlot());
        if(item != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE) && item.getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING).equals("worldalterer")){
            Bukkit.getLogger().log(Level.WARNING, event.getPlayer().getName() + " is holding the Worldalterer");
            runActionbar(event.getPlayer(), 0);
        }
    }
    private void setPosition(Player player,Action action, int[] pos){
        if(action.isLeftClick()){
            player.getPersistentDataContainer().set(Constant.WA_POS1, PersistentDataType.INTEGER_ARRAY, pos);
        } else player.getPersistentDataContainer().set(Constant.WA_POS2, PersistentDataType.INTEGER_ARRAY, pos);

        cancelActionbar(player);
        player.sendActionBar(Component.text("Position at ", NamedTextColor.GREEN)
                .append(Component.text("[", NamedTextColor.GOLD))
                .append(Component.text(pos[0], NamedTextColor.LIGHT_PURPLE))
                .append(Component.text(", ", NamedTextColor.GRAY))
                .append(Component.text(pos[1], NamedTextColor.LIGHT_PURPLE))
                .append(Component.text(", ", NamedTextColor.GRAY))
                .append(Component.text(pos[2], NamedTextColor.LIGHT_PURPLE))
                .append(Component.text("]", NamedTextColor.GOLD)));
        runActionbar(player, 15);
    }

    private void runActionbar(Player player, Integer delay){
        if(!runnableMap.containsKey(player.getUniqueId())) {
            int[] pos1 = new int[]{0,0,0};
            int[] pos2 = new int[]{0,0,0};
            if(player.getPersistentDataContainer().has(Constant.WA_POS1)) {
                pos1 = player.getPersistentDataContainer().get(Constant.WA_POS1, PersistentDataType.INTEGER_ARRAY);
                assert pos1 != null;
            }
            if(player.getPersistentDataContainer().has(Constant.WA_POS2)){
                pos2 = player.getPersistentDataContainer().get(Constant.WA_POS2, PersistentDataType.INTEGER_ARRAY);
                assert pos2 != null;
            }
            if(player.getWorld().isChunkLoaded(pos1[0] / 16 + 1, pos1[2] / 16 + 1)){
                Location pos1loc = new Location(player.getWorld(), pos1[0], pos1[1], pos1[2]);
                BlockDisplay pos1glow = (BlockDisplay) player.getWorld().spawnEntity(pos1loc, EntityType.BLOCK_DISPLAY);
                pos1glow.setBlock(pos1loc.getBlock().getBlockData());
                pos1glow.setGlowing(true);
                pos1glow.setBrightness(new Display.Brightness(15,15));
                pos1glow.getPersistentDataContainer().set(Constant.WA_GLOWOWNER, PersistentDataType.STRING, String.valueOf(player.getUniqueId()));
            }
            if(player.getWorld().isChunkLoaded(pos2[0] / 16 + 1, pos2[2] / 16 + 1)){
                Location pos2loc = new Location(player.getWorld(), pos2[0], pos2[1], pos2[2]);
                BlockDisplay pos2glow = (BlockDisplay) player.getWorld().spawnEntity(pos2loc, EntityType.BLOCK_DISPLAY);
                pos2glow.setBlock(pos2loc.getBlock().getBlockData());
                pos2glow.setGlowing(true);
                pos2glow.setBrightness(new Display.Brightness(15,15));
                pos2glow.getPersistentDataContainer().set(Constant.WA_GLOWOWNER, PersistentDataType.STRING, String.valueOf(player.getUniqueId()));
            }
            int[] finalPos = pos1;
            int[] finalPos1 = pos2;
            int blockCount = (Math.abs(finalPos[0] - finalPos1[0]) + 1) * (Math.abs(finalPos[1] - finalPos1[1]) + 1) * (Math.abs(finalPos[2] - finalPos1[2]) + 1);
            String facing = player.getPersistentDataContainer().get(Constant.WA_FACING, PersistentDataType.STRING);
            if(!player.getPersistentDataContainer().has(Constant.WA_FACING)){
                facing = "Undefined Direction";
            }
            String finalFacing = facing;
            assert finalFacing != null;
            int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Paveral.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    player.sendActionBar(Component.text("[", NamedTextColor.GOLD)
                            .append(Component.text(finalFacing.charAt(0) + finalFacing.substring(1).toLowerCase(), NamedTextColor.GREEN))
                            .append(Component.text("] [", NamedTextColor.GOLD))
                            .append(Component.text(finalPos[0], NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(", ", NamedTextColor.GRAY))
                            .append(Component.text(finalPos[1], NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(", ", NamedTextColor.GRAY))
                            .append(Component.text(finalPos[2], NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text("] [", NamedTextColor.GOLD))
                            .append(Component.text(finalPos1[0], NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(", ", NamedTextColor.GRAY))
                            .append(Component.text(finalPos1[1], NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(", ", NamedTextColor.GRAY))
                            .append(Component.text(finalPos1[2], NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text("] ", NamedTextColor.GOLD))
                            .append(Component.text("Total: ", NamedTextColor.GREEN))
                            .append(Component.text(blockCount, NamedTextColor.YELLOW)));
                }
            }, delay, 40);
            runnableMap.put(player.getUniqueId(), taskID);
        }
    }

    private void cancelActionbar(Player player){
        if(runnableMap.containsKey(player.getUniqueId())){
            Bukkit.getScheduler().cancelTask(runnableMap.get(player.getUniqueId()));
            runnableMap.remove(player.getUniqueId());
            player.getWorld().getEntities().stream().filter(e ->
                    e.getPersistentDataContainer().has(Constant.WA_GLOWOWNER) &&
                    e.getPersistentDataContainer().get(Constant.WA_GLOWOWNER, PersistentDataType.STRING).equals(String.valueOf(player.getUniqueId())))
                    .forEach(Entity::remove);
        }
    }
}
