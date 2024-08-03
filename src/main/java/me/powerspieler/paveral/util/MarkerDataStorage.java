package me.powerspieler.paveral.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Marker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class MarkerDataStorage implements Listener {
    public static PersistentDataContainer getMarkerDataContainer(Block block){
        return getMarker(block).getPersistentDataContainer();
    }

    public static boolean hasMarker(Block block){
        Location location = block.getLocation().toCenterLocation();
        return !location.getNearbyEntitiesByType(Marker.class, 0.01).isEmpty();
    }

    private static Marker getMarker(Block block){
        if(hasMarker(block)){
            Location location = block.getLocation().toCenterLocation();
            Optional<Marker> optionalMarker = location.getNearbyEntitiesByType(Marker.class, 0.01).stream().findFirst();
            if(optionalMarker.isPresent()){
                return optionalMarker.get();
            }
        }
        return null;
    }

    public static void createMarker(Block block){
        if(!hasMarker(block)){
            Location location = block.getLocation().toCenterLocation();
            location.getWorld().spawnEntity(location, EntityType.MARKER);
        }
    }

    public static void removeMarker(Block block){
        if(hasMarker(block)){
            getMarker(block).remove();
        }
    }

    // Removal of Block
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        removeMarker(event.getBlock());
    }
    @EventHandler
    public void onBlockFade(BlockFadeEvent event){ // Farmland dehydrating
        removeMarker(event.getBlock());
    }
    @EventHandler
    public void onBlockBurn(BlockBurnEvent event){
        removeMarker(event.getBlock());
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){ //NOTE: Currently all Markers are placed via PlayerInteractEvent. This may cause some trouble in the future. Used for Block being placed into lightblock
        removeMarker(event.getBlock());
    }
    @EventHandler
    public void onPistonMove(BlockPistonExtendEvent event){
        event.getBlocks().forEach(MarkerDataStorage::removeMarker);
    }
    @EventHandler
    public void onPistonMove(BlockPistonRetractEvent event){
        event.getBlocks().forEach(MarkerDataStorage::removeMarker);
    }
    @EventHandler
    public void onBlockExplosion(BlockExplodeEvent event){
        event.blockList().forEach(MarkerDataStorage::removeMarker);
    }
    @EventHandler
    public void onEntityExplosion(EntityExplodeEvent event){
        event.blockList().forEach(MarkerDataStorage::removeMarker);
    }
}
