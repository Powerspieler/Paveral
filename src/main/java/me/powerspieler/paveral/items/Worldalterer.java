package me.powerspieler.paveral.items;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.advancements.AwardAdvancements;
import me.powerspieler.paveral.crafting.PaveralIngredient;
import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.crafting.StandardIngredient;
import me.powerspieler.paveral.items.helper.Enchantable;
import me.powerspieler.paveral.util.Constant;
import me.powerspieler.paveral.util.ItemsUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingTransformRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.logging.Level;

public class Worldalterer extends PaveralItem implements Listener, Enchantable {
    private static Component itemName(){
        return Component.text("||", NamedTextColor.LIGHT_PURPLE)
                .decoration(TextDecoration.OBFUSCATED, true).decoration(TextDecoration.ITALIC, true)
                .append(Component.text(" Worldalterer ", NamedTextColor.GOLD)
                        .decoration(TextDecoration.OBFUSCATED, false).decoration(TextDecoration.ITALIC, false))
                .append(Component.text("||", NamedTextColor.LIGHT_PURPLE)
                        .decoration(TextDecoration.OBFUSCATED, true).decoration(TextDecoration.ITALIC, true));
    }

    private static List<Component> lore(){
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Press ", NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false)
                .append(Component.keybind("key.sneak", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))
                .append(Component.text(" + (", NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false))
                .append(Component.keybind("key.attack", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false))
                .append(Component.text(" or ", NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false))
                .append(Component.keybind("key.use", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false))
                .append(Component.text(") to select Positions", NamedTextColor.DARK_AQUA)));
        lore.add(Component.text("Look in the desired direction and press ", NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false)
                .append(Component.keybind("key.attack", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false))
                .append(Component.text(" to set the direction", NamedTextColor.DARK_AQUA)));
        lore.add(Component.text("Press ", NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false)
                .append(Component.keybind("key.use", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, false))
                .append(Component.text(" to commit alteration", NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false)));
        lore.add(Component.text("Does not move entites!", NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text(""));
        lore.add(Component.text("Refill using Echo Shard in Smithing Table", NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false));
        return lore;
    }

    public Worldalterer() {
        super(Material.WARPED_FUNGUS_ON_A_STICK, 6, Constant.ITEMTYPE, "worldalterer", itemName(), lore());
    }

    @Override
    protected ItemStack build() {
        ItemStack item = super.build();
        Damageable itemMeta = (Damageable) item.getItemMeta();
        itemMeta.setDamage(100);
        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    public PaveralRecipe recipe() {
        Set<StandardIngredient> ingredients = new HashSet<>();
        ingredients.add(new PaveralIngredient(Material.JIGSAW, 1, Constant.ITEMTYPE, "amethyst_laser"));
        ingredients.add(new PaveralIngredient(Material.JIGSAW, 1, Constant.ITEMTYPE, "alteration_core"));
        ingredients.add(new PaveralIngredient(Material.JIGSAW, 1, Constant.ITEMTYPE, "echo_container"));
        return new PaveralRecipe(ingredients, this.build());
    }

    // --- Item Logic ---

    // TODO Bea book writing

    Map<UUID, Integer> runnableMap = new HashMap<>();

    // Cooldown before commiting - depends on amount of blocks selected
    private final HashMap<UUID, Long> cooldownuntil = new HashMap<>(); // TODO Einheitlicher Cooldown holy moly

    @EventHandler
    private void onPlayerUse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(player.getPersistentDataContainer().has(Constant.IS_HOLDING, PersistentDataType.STRING) && player.getPersistentDataContainer().get(Constant.IS_HOLDING, PersistentDataType.STRING).equals("worldalterer")){
            event.setCancelled(true);
            if(!(event.getHand() == EquipmentSlot.HAND)){
                return;
            }

            // Only allow players with advancement use this item
            if(AwardAdvancements.isAdvancementUndone(player, "worldalterer")){
                player.sendMessage(Component.text("What is this and how does it work?", NamedTextColor.DARK_AQUA));
                return;
            }

            // Disable until move is finished
            if(cooldownuntil.containsKey(player.getUniqueId()) && System.currentTimeMillis() - cooldownuntil.get(player.getUniqueId()) <= 0){
                return;
            }

            if(!player.isSneaking()){
                if (setDircetion(event, player)) return;
                if (checkDurability(event, player)) return;

                if(player.getPersistentDataContainer().has(Constant.WA_POS1)
                        && player.getPersistentDataContainer().has(Constant.WA_POS2)
                        && player.getPersistentDataContainer().has(Constant.WA_FACING)) {
                    commitMove(player);
                }
            } else {
                // Set Pos
                Block block = event.getClickedBlock();
                if(block != null){
                    int[] pos = new int[]{block.getX(), block.getY(), block.getZ()};
                    setPosition(event.getPlayer(), event.getAction(), pos);
                    player.playSound(Sound.sound(Key.key("block.respawn_anchor.set_spawn"), Sound.Source.AMBIENT, 1f, 1.75f), Sound.Emitter.self());
                }
            }
        }
    }

    private void commitMove(Player player) {
        int[] pos1 = player.getPersistentDataContainer().get(Constant.WA_POS1, PersistentDataType.INTEGER_ARRAY);
        int[] pos2 = player.getPersistentDataContainer().get(Constant.WA_POS2, PersistentDataType.INTEGER_ARRAY);
        assert pos1 != null;
        assert pos2 != null;

        // max blocks = 32768
        int blockCount = (Math.abs(pos1[0] - pos2[0]) + 1) * (Math.abs(pos1[1] - pos2[1]) + 1) * (Math.abs(pos1[2] - pos2[2]) + 1);
        if(blockCount > 32768){
            cancelActionbar(player);
            player.sendActionBar(Component.text("Too many blocks selected! Max: 32768", NamedTextColor.RED));
            player.playSound(Sound.sound(Key.key("block.note_block.bass"), Sound.Source.AMBIENT, 1f, 0.2f), Sound.Emitter.self());
            runActionbar(player, 40);
            return;
        }

        // Chunks loaded?
        if(!isChunkLoaded(player, pos1, pos2)){
            return;
        }

        // Check for Illegal Blocks
        for (int x = Math.min(pos1[0], pos2[0]); x <= Math.max(pos1[0], pos2[0]); x++) {
            for (int y = Math.min(pos1[1], pos2[1]); y <= Math.max(pos1[1], pos2[1]); y++) {
                for (int z = Math.min(pos1[2], pos2[2]); z <= Math.max(pos1[2], pos2[2]); z++) {
                    if (Tag.WITHER_IMMUNE.getValues().contains(player.getWorld().getBlockAt(x, y, z).getType())) {
                        cancelActionbar(player);

                        Location illegalloc = new Location(player.getWorld(), x, y, z);
                        BlockDisplay errglow = (BlockDisplay) player.getWorld().spawnEntity(illegalloc, EntityType.BLOCK_DISPLAY);
                        errglow.setBlock(Material.RED_STAINED_GLASS.createBlockData());
                        errglow.setGlowing(true);
                        errglow.setBrightness(new Display.Brightness(15,15));
                        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
                        Team error;
                        if(scoreboard.getTeams().stream().noneMatch(t -> t.getName().equals("WA_ERROR"))){
                            error = scoreboard.registerNewTeam("WA_ERROR");
                        } else {
                            error = scoreboard.getTeam("WA_ERROR");
                        }
                        assert error != null;
                        error.color(NamedTextColor.RED);
                        error.addEntity(errglow);

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                errglow.remove();
                                if(error.getEntries().isEmpty()){
                                    error.unregister();
                                }
                            }
                        }.runTaskLater(Paveral.getPlugin(), 40L);

                        player.sendActionBar(Component.text("Unmovable block at " + x + ", " + y + ", " + z, NamedTextColor.RED));
                        player.playSound(Sound.sound(Key.key("block.note_block.basedrum"), Sound.Source.AMBIENT, 1f, 1f), Sound.Emitter.self());
                        runActionbar(player, 40);
                        return;
                    }
                }
            }
        }

        // Apply Direction
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

        // Check if air - creating area to be moved in
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
        // check for non-air in destination
        for(int x = Math.min(aircheck1[0], aircheck2[0]); x <= Math.max(aircheck1[0], aircheck2[0]); x++){
            for(int y = Math.min(aircheck1[1], aircheck2[1]); y <= Math.max(aircheck1[1], aircheck2[1]); y++){
                for(int z = Math.min(aircheck1[2], aircheck2[2]); z <= Math.max(aircheck1[2], aircheck2[2]); z++){
                    if(!player.getWorld().getBlockAt(x,y,z).isEmpty()){
                        cancelActionbar(player);

                        Location collloc = new Location(player.getWorld(), x, y, z);
                        BlockDisplay collglow = (BlockDisplay) player.getWorld().spawnEntity(collloc, EntityType.BLOCK_DISPLAY);
                        collglow.setBlock(Material.YELLOW_STAINED_GLASS.createBlockData());
                        collglow.setGlowing(true);
                        collglow.setBrightness(new Display.Brightness(15,15));
                        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
                        Team coll;
                        if(scoreboard.getTeams().stream().noneMatch(t -> t.getName().equals("WA_COLL"))){
                            coll = scoreboard.registerNewTeam("WA_COLL");
                        } else {
                            coll = scoreboard.getTeam("WA_COLL");
                        }
                        assert coll != null;
                        coll.color(NamedTextColor.YELLOW);
                        coll.addEntity(collglow);

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                collglow.remove();
                                if(coll.getEntries().isEmpty()){
                                    coll.unregister();
                                }
                            }
                        }.runTaskLater(Paveral.getPlugin(), 40L);

                        player.sendActionBar(Component.text("Collision at " + x + ", " + y + ", " + z, NamedTextColor.YELLOW));
                        player.playSound(Sound.sound(Key.key("entity.allay.hurt"), Sound.Source.AMBIENT, 1f, 1f), Sound.Emitter.self());
                        runActionbar(player, 40);
                        return;
                    }
                }
            }
        }

        // Set a future time for re-enabling / prevent spamming for larger selects: e.g. max: 32768 = 8,192s Cooldown ( / 20 (ticks to s) [/ 100] / 2 * 1000 (-> ms))
        cooldownuntil.put(player.getUniqueId(), Math.max(System.currentTimeMillis() + (blockCount / 4), System.currentTimeMillis() + 150)); // Prevent Spamming,  Still Causes Bug and loss of blocks

        new BukkitRunnable() {
            long duration;
            @Override
            public void run() {
                if(System.currentTimeMillis() - cooldownuntil.get(player.getUniqueId()) >= 0){
                    if(isChunkLoaded(player, pos1, pos2)){
                        moveBlocks(player, pos1, pos2, paste, facing);
                    }
                    cancel();
                }
                duration = cooldownuntil.get(player.getUniqueId()) - System.currentTimeMillis();

                // help -> https://stackoverflow.com/questions/929103/convert-a-number-range-to-another-range-maintaining-ratio
                // NewValue = (((OldValue - OldMin) * (NewMax - NewMin)) / (OldMax - OldMin)) + NewMin
                float pitch = ((float) ((duration - 0) * (20 - 0)) / (Math.max(blockCount / 4, 150) - 0)) / 10; // Math.max like above
                player.playSound(Sound.sound(Key.key("block.beacon.power_select"), Sound.Source.AMBIENT, 0.5f, pitch * -1 + 2), Sound.Emitter.self());
                player.spawnParticle(Particle.PORTAL, getRightSide(player.getEyeLocation(), 0.45).subtract(0, .6, 0), 1);
            }
        }.runTaskTimer(Paveral.getPlugin(), 0, 1);
    }

    private boolean checkDurability(PlayerInteractEvent event, Player player) {
        ItemStack item = event.getItem();
        Damageable itemmeta = (Damageable) item.getItemMeta();
        if(itemmeta.getDamage() == 100){
            cancelActionbar(player);
            player.playSound(Sound.sound(Key.key("block.beacon.deactivate"), Sound.Source.AMBIENT, 1f, 1.75f), Sound.Emitter.self());
            player.sendActionBar(Component.text("No Energy", NamedTextColor.RED));
            runActionbar(player, 40);
            return true;
        }
        itemmeta.setDamage(itemmeta.getDamage() + 1);
        item.setItemMeta(itemmeta);
        return false;
    }

    private boolean setDircetion(PlayerInteractEvent event, Player player) {
        if(event.getAction().isLeftClick()){
            // Change Direction
            String facing = player.getFacing().name();
            if(player.getLocation().getPitch() >= 40){
                facing = "DOWN";
            }
            if(player.getLocation().getPitch() <= -40){
                facing = "UP";
            }
            player.getPersistentDataContainer().set(Constant.WA_FACING, PersistentDataType.STRING, facing);
            player.playSound(Sound.sound(Key.key("item.lodestone_compass.lock"), Sound.Source.AMBIENT, 1f, 1f), Sound.Emitter.self());
            cancelActionbar(player);
            runActionbar(player, 0);
            return true;
        }
        return false;
    }

    private static Location getRightSide(Location location, double distance) {
        float angle = location.getYaw() / 60;
        return location.clone().subtract(new Vector(Math.cos(angle), 0, Math.sin(angle)).normalize().multiply(distance));
    }

    private void moveBlocks(Player player, int[] pos1, int[] pos2, int[] paste, String facing){

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

    private boolean isChunkLoaded(Player player, int[] pos1, int[] pos2){
        Location pos1loc = new Location(player.getWorld(), pos1[0], pos1[1], pos1[2]);
        Location pos2loc = new Location(player.getWorld(), pos2[0], pos2[1], pos2[2]);
        if(pos1loc.isChunkLoaded() && pos2loc.isChunkLoaded()){
            return true;
        }
        cancelActionbar(player);
        player.sendActionBar(Component.text("Selection is too far away! - CANCELLED", NamedTextColor.RED));
        player.playSound(Sound.sound(Key.key("block.note_block.bass"), Sound.Source.AMBIENT, 1f, 0.2f), Sound.Emitter.self());
        runActionbar(player, 40);
        return false;
    }

    @EventHandler
    private void onItemSelect(PlayerItemHeldEvent event){
        cancelActionbar(event.getPlayer());
        ItemStack item = event.getPlayer().getInventory().getItem(event.getNewSlot());
        if(item != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE) && item.getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING).equals("worldalterer")){
            Paveral.getPlugin().getLogger().log(Level.WARNING, event.getPlayer().getName() + " is holding the Worldalterer");
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
            showHoloBlock(player, pos1);
            showHoloBlock(player, pos2);
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

    private void showHoloBlock(Player player, int[] position) {
        if(player.getWorld().isChunkLoaded(position[0] / 16 + 1, position[2] / 16 + 1)){
            Location pos2loc = new Location(player.getWorld(), position[0], position[1], position[2]);
            BlockDisplay pos2glow = (BlockDisplay) player.getWorld().spawnEntity(pos2loc, EntityType.BLOCK_DISPLAY);

            if(pos2loc.getBlock().isSolid()){
                pos2glow.setBlock(pos2loc.getBlock().getBlockData());
            } else pos2glow.setBlock(Material.GLASS.createBlockData());

            pos2glow.setGlowing(true);
            pos2glow.setBrightness(new Display.Brightness(15,15));
            pos2glow.getPersistentDataContainer().set(Constant.WA_GLOWOWNER, PersistentDataType.STRING, String.valueOf(player.getUniqueId()));
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

    //REFILL ON SMITHING TABLE
    private static final NamespacedKey recipeKey = new NamespacedKey(Paveral.getPlugin(), "wa_refill");
    public static SmithingTransformRecipe registerRecipe(){
        return new SmithingTransformRecipe(recipeKey,
                new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK),
                RecipeChoice.empty(),
                new RecipeChoice.MaterialChoice(Material.WARPED_FUNGUS_ON_A_STICK),
                new RecipeChoice.MaterialChoice(Material.ECHO_SHARD));
    }

    @EventHandler
    private void onRefill(PrepareSmithingEvent event){
        if(event.getInventory().getInputTemplate() == null && event.getInventory().getInputMineral() != null && event.getInventory().getInputMineral().getType() == Material.ECHO_SHARD){
            ItemStack base = event.getInventory().getInputEquipment();
            if(base != null && base.hasItemMeta() && base.getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE) && base.getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING).equals(keyString)){
                ItemStack result = new ItemStack(base);
                ItemsUtil.repair(result, 25);
                event.setResult(result);
            }
        }
    }

    @Override
    @EventHandler
    public void onEnchantingAttempt(PrepareAnvilEvent event) {
        Set<Enchantment> enchants = new HashSet<>();
        enchants.add(Enchantment.MENDING);
        enchants.add(Enchantment.UNBREAKING);
        Enchantable.super.onEnchantingAttempt(event, keyString, enchants);
    }
}
