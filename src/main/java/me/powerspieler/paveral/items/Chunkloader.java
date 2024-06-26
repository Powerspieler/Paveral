package me.powerspieler.paveral.items;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Slab;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class Chunkloader implements Listener, Items {
    @Override
    public ItemStack build() {
        ItemStack item = new ItemStack(Material.PETRIFIED_OAK_SLAB);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.getPersistentDataContainer().set(Constant.ITEMTYPE, PersistentDataType.STRING, "chunkloader");

        itemmeta.displayName(Component.text("Chunkloader", NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Loads 3x3 Chunks", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC,false));
        lore.add(Component.text("Does not support random ticks!",NamedTextColor.RED)
                .decoration(TextDecoration.ITALIC,false));
        itemmeta.lore(lore);

        item.setItemMeta(itemmeta);
        return item;
    }

    @Override
    public List<ItemStack> parts() {
        List<ItemStack> parts = new ArrayList<>();
        ItemStack netherstar = new ItemStack(Material.NETHER_STAR, 3);
        ItemStack lodestone = new ItemStack(Material.LODESTONE);
        ItemStack obsidian = new ItemStack(Material.OBSIDIAN, 2);
        ItemStack enchtable = new ItemStack(Material.ENCHANTING_TABLE);
        parts.add(netherstar);
        parts.add(lodestone);
        parts.add(obsidian);
        parts.add(enchtable);
        return parts;
    }

    private static final NamespacedKey CHUNKLOADS = new NamespacedKey(Paveral.getPlugin(), "chunkloads");

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        if(event.getBlock().getType() == Material.PETRIFIED_OAK_SLAB){
            Slab slab = (Slab) event.getBlock().getBlockData();
            if(event.getBlockReplacedState().getType() == Material.PETRIFIED_OAK_SLAB || slab.getType() == Slab.Type.TOP){
                event.setCancelled(true);
            } else {
                for(int x = -1; x <= 1; x++){
                    for(int z = -1; z <= 1; z++){
                        Chunk chunk = event.getBlock().getRelative(x * 16,0,z * 16).getChunk();
                        if(!chunk.getPersistentDataContainer().has(CHUNKLOADS)){
                            chunk.getPersistentDataContainer().set(CHUNKLOADS, PersistentDataType.INTEGER, 0);
                        }
                        int value = chunk.getPersistentDataContainer().get(CHUNKLOADS, PersistentDataType.INTEGER) + 1;
                        if(value < 0){value = 0;}
                        chunk.getPersistentDataContainer().set(CHUNKLOADS, PersistentDataType.INTEGER, value);
                        if(chunk.getPersistentDataContainer().get(CHUNKLOADS, PersistentDataType.INTEGER) > 0){
                            chunk.setForceLoaded(true);
                        }
                    }
                }
                event.getBlock().getWorld().spawnParticle(Particle.REVERSE_PORTAL, event.getBlock().getLocation().add(0.5,0.55,0.5), 100, 0,0,0, 0.5);
                Location location = event.getBlock().getLocation().add(0.5,0.5,0.5);
                final Audience targets = location.getWorld().filterAudience(member -> member instanceof Player player && player.getLocation().distanceSquared(location) < 100);
                targets.playSound(Sound.sound(Key.key("block.beacon.activate"), Sound.Source.BLOCK, 1f, 0.5f), Sound.Emitter.self());
                targets.playSound(Sound.sound(Key.key("entity.ghast.scream"), Sound.Source.BLOCK, 1f, 0.5f), Sound.Emitter.self());
                targets.playSound(Sound.sound(Key.key("block.stone.place"), Sound.Source.BLOCK, 1f, 0.5f), Sound.Emitter.self());
                showActionbar(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if(event.getBlock().getType() == Material.PETRIFIED_OAK_SLAB){
            if(event.getPlayer().getGameMode() == GameMode.CREATIVE){
                event.setDropItems(false);
            }
            for(int x = -1; x <= 1; x++){
                for(int z = -1; z <= 1; z++){
                    Chunk chunk = event.getBlock().getRelative(x * 16,0,z * 16).getChunk();
                    if(!chunk.getPersistentDataContainer().has(CHUNKLOADS)){
                        chunk.getPersistentDataContainer().set(CHUNKLOADS, PersistentDataType.INTEGER, 0);
                    }
                    int value = chunk.getPersistentDataContainer().get(CHUNKLOADS, PersistentDataType.INTEGER) - 1;
                    if(value <= 0){
                        chunk.getPersistentDataContainer().remove(CHUNKLOADS);
                        chunk.setForceLoaded(false);
                    } else {
                        chunk.getPersistentDataContainer().set(CHUNKLOADS, PersistentDataType.INTEGER, value);
                    }
                }
            }
            event.getBlock().getWorld().spawnParticle(Particle.PORTAL, event.getBlock().getLocation().add(0.5,0.55,0.5), 100, 0,0,0, 0.5);
            Location location = event.getBlock().getLocation().add(0.5,0.5,0.5);
            final Audience targets = location.getWorld().filterAudience(member -> member instanceof Player player && player.getLocation().distanceSquared(location) < 100);
            targets.playSound(Sound.sound(Key.key("block.beacon.deactivate"), Sound.Source.BLOCK, 1f, 0.5f), Sound.Emitter.self());
            showActionbar(event.getPlayer());
        }
    }

    @EventHandler
    public void onChunkloaderMove(PlayerMoveEvent event){
        if(ItemHoldingController.checkIsHoldingPaveralItem(event.getPlayer(), "chunkloader")){
            showActionbar(event.getPlayer());
        }
    }

    @EventHandler
    public void onBlockItemDrop(BlockDropItemEvent event){

        if(event.getBlockState().getType() == Material.PETRIFIED_OAK_SLAB){
            Items chunkloader = new Chunkloader();
            Location location = event.getBlock().getLocation();
            location.getWorld().dropItem(location.add(0.5,0.5,0.5),chunkloader.build());
            event.setCancelled(true);
        }
    }

    private static void showActionbar(Player player){
        int chunks = player.getWorld().getForceLoadedChunks().size();
        if(player.getChunk().isForceLoaded()){
            player.sendActionBar(Component.text("[ ",NamedTextColor.GOLD)
                    .append(Component.text("Chunk loaded", NamedTextColor.GREEN))
                    .append(Component.text(" ]",NamedTextColor.GOLD))
                    .append(Component.text(" - ",NamedTextColor.GRAY))
                    .append(Component.text("Total: ",NamedTextColor.BLUE))
                    .append(Component.text("" + chunks, NamedTextColor.YELLOW)));

        }else{
            player.sendActionBar(Component.text("[ ",NamedTextColor.GOLD)
                    .append(Component.text("Chunk not loaded", NamedTextColor.RED))
                    .append(Component.text(" ]",NamedTextColor.GOLD))
                    .append(Component.text(" - ",NamedTextColor.GRAY))
                    .append(Component.text("Total: ",NamedTextColor.BLUE))
                    .append(Component.text("" + chunks)));
        }

    }

    // Prevent Chunkloader Breaking
    @EventHandler
    public void onPistonMove(BlockPistonExtendEvent event){
        if(event.getBlocks().stream().anyMatch(block -> block.getType() == Material.PETRIFIED_OAK_SLAB)){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPistonMove(BlockPistonRetractEvent event){
        if(event.getBlocks().stream().anyMatch(block -> block.getType() == Material.PETRIFIED_OAK_SLAB)){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onBlockExplosion(BlockExplodeEvent event){
        if(event.blockList().stream().anyMatch(block -> block.getType() == Material.PETRIFIED_OAK_SLAB)){
            List<Block> cl = event.blockList().stream().filter(block -> block.getType() == Material.PETRIFIED_OAK_SLAB).toList();
            event.blockList().removeAll(cl);
        }
    }
    @EventHandler
    public void onEntityExplosion(EntityExplodeEvent event){
        if(event.blockList().stream().anyMatch(block -> block.getType() == Material.PETRIFIED_OAK_SLAB)){
            List<Block> cl = event.blockList().stream().filter(block -> block.getType() == Material.PETRIFIED_OAK_SLAB).toList();
            event.blockList().removeAll(cl);
        }
    }
}
