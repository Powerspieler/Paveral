package me.powerspieler.paveral.items;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.util.Constant;
import me.powerspieler.paveral.util.ItemsUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Marker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class LightStaff implements Listener,Items {
    
    // Lightstaff ItemStack
    public ItemStack build(){
        ItemStack lightstaff = new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK);
        ItemMeta lightstaffmeta = lightstaff.getItemMeta();
        lightstaffmeta.getPersistentDataContainer().set(Constant.ITEMTYPE, PersistentDataType.STRING, "lightstaff");
        lightstaffmeta.getPersistentDataContainer().set(LIGHTBLOCKLEVEL, PersistentDataType.INTEGER, 15);
        lightstaffmeta.setCustomModelData(4);

        lightstaffmeta.displayName(Component.text("Lightstaff", NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Press ")
                .decoration(TextDecoration.ITALIC, false)
                .append(
                        Component.keybind("key.use", NamedTextColor.YELLOW)
                                .decoration(TextDecoration.ITALIC, false)
                )
                .append(Component.text(" to place and remove lightblocks")));
        lore.add(Component.text("Press (")
                .decoration(TextDecoration.ITALIC, false)
                .append(
                        Component.keybind("key.sneak", NamedTextColor.GREEN)
                                .decoration(TextDecoration.ITALIC, false)
                )
                .append(Component.text(" + ) ")
                        .decoration(TextDecoration.ITALIC, false))
                .append(Component.keybind("key.attack", NamedTextColor.YELLOW)
                        .decoration(TextDecoration.ITALIC, false)
                        )
                .append(Component.text(" to change lightlevel")
                        .decoration(TextDecoration.ITALIC,false)));
        lore.add(Component.text(""));
        lore.add(Component.text("Enchantable with ")
                .decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Unbreaking",NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)
                )
                .append(Component.text(" and ")
                        .decoration(TextDecoration.ITALIC, false)
                )
                .append(Component.text("Mending", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)));
        lightstaffmeta.lore(lore);

        lightstaff.setItemMeta(lightstaffmeta);
        return lightstaff;
    }

    @Override
    public List<ItemStack> parts() {
        List<ItemStack> parts = new ArrayList<>();
        ItemStack ironingot = new ItemStack(Material.IRON_INGOT, 2);
        ItemStack copperingot = new ItemStack(Material.COPPER_INGOT);
        ItemStack redstonelamp = new ItemStack(Material.REDSTONE_LAMP);
        ItemStack witherrose = new ItemStack(Material.WITHER_ROSE, 2);
        parts.add(ironingot);
        parts.add(copperingot);
        parts.add(redstonelamp);
        parts.add(witherrose);
        return parts;
    }

    private static final NamespacedKey LIGHTBLOCKMARKER = new NamespacedKey(Paveral.getPlugin(), "lightblock");
    private static final NamespacedKey LIGHTBLOCKLEVEL = new NamespacedKey(Paveral.getPlugin(), "lightlevel");
    private static boolean particlecooldown;

   // Placement and Removal
    @EventHandler
    public void onPlayerRightclick(PlayerInteractEvent event){
        if(event.getItem() != null && event.getItem().hasItemMeta() && event.getItem().getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE)){
            if(Objects.equals(event.getItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "lightstaff")){
                if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
                    if(event.getClickedBlock() != null && event.getItem().getItemMeta().getPersistentDataContainer().get(LIGHTBLOCKLEVEL, PersistentDataType.INTEGER) != null){
                        Location location = event.getClickedBlock().getLocation();
                        int lightlevel = event.getItem().getItemMeta().getPersistentDataContainer().get(LIGHTBLOCKLEVEL, PersistentDataType.INTEGER);
                        switch (event.getBlockFace()) {
                            case UP -> location.add(0, 1, 0);
                            case DOWN -> location.add(0, -1, 0);
                            case NORTH -> location.add(0, 0, -1);
                            case SOUTH -> location.add(0, 0, 1);
                            case WEST -> location.add(-1, 0, 0);
                            case EAST -> location.add(1, 0, 0);
                        }
                        setLightblock(location, lightlevel, event.getItem());
                    }
                }
            }
        }
    }

    // Display Particle and Message
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        ItemStack MainHand = player.getInventory().getItemInMainHand();
        ItemStack OffHand = player.getInventory().getItemInOffHand();
        if((MainHand.hasItemMeta() && MainHand.getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE) && Objects.equals(MainHand.getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "lightstaff")) || OffHand.hasItemMeta() && (OffHand.getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE) && Objects.equals(OffHand.getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "lightstaff"))){
            if(!particlecooldown) {
                particlecooldown = true;
                Bukkit.getScheduler().scheduleSyncDelayedTask(Paveral.getPlugin(), () -> particlecooldown = false, 80);
                List<Entity> entityList = player.getNearbyEntities(16D,16D,16D);
                for(Entity entity : entityList){
                    if(entity instanceof Marker marker){
                        if(marker.getPersistentDataContainer().has(LIGHTBLOCKMARKER, PersistentDataType.INTEGER)){
                            if(marker.getLocation().getBlock().getType() == Material.LIGHT) {
                                Location particlelocation = marker.getLocation();
                                marker.getWorld().spawnParticle(Particle.BLOCK_MARKER, particlelocation, 1, marker.getLocation().getBlock().getBlockData());

                            } else
                                marker.remove();
                        }
                    }
                }
            }

            int lightlevel = 0;
            if(player.getInventory().getItemInOffHand().hasItemMeta()) {
                lightlevel = player.getInventory().getItemInOffHand().getItemMeta().getPersistentDataContainer().get(LIGHTBLOCKLEVEL, PersistentDataType.INTEGER);
            } else if (player.getInventory().getItemInMainHand().hasItemMeta()){
                lightlevel = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(LIGHTBLOCKLEVEL, PersistentDataType.INTEGER);
            }
            showActionbar(player, lightlevel);
        }
    }


    // Change Lightlevel
    @EventHandler
    public void onPlayerLeftClick(PlayerInteractEvent event){
        if(event.getAction().isLeftClick() && event.hasItem()){
            ItemStack lightstaff = event.getItem();
            if(lightstaff.hasItemMeta() && lightstaff.getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE)){
                ItemMeta lightstaffmeta = lightstaff.getItemMeta();
                if(event.getItem().getItemMeta().getPersistentDataContainer().has(LIGHTBLOCKLEVEL)){
                    int lightlevel = event.getItem().getItemMeta().getPersistentDataContainer().get(LIGHTBLOCKLEVEL, PersistentDataType.INTEGER);
                    if(event.getPlayer().isSneaking()){
                        lightlevel--;
                        if(lightlevel < 1){
                            lightlevel = 15;
                        }
                    }
                    else if (!event.getPlayer().isSneaking()) {
                        lightlevel++;
                        if(lightlevel > 15){
                            lightlevel = 1;
                        }
                    }
                    lightstaffmeta.getPersistentDataContainer().set(LIGHTBLOCKLEVEL, PersistentDataType.INTEGER, lightlevel);
                    lightstaff.add().setItemMeta(lightstaffmeta);
                    showActionbar(event.getPlayer(), lightlevel);
                    event.setCancelled(true);
                }
            }
        }
    }

    private void showActionbar(Player player, int lightlevel){
        player.sendActionBar(Component.text("[ ", NamedTextColor.GOLD)
                .append(Component.text("Lightlevel: ",NamedTextColor.GREEN))
                .append(Component.text(lightlevel ,NamedTextColor.LIGHT_PURPLE))
                .append(Component.text(" ]",NamedTextColor.GOLD)));

    }


    private void setLightblock(Location location, int lightlevel, ItemStack lightstaff){
        Damageable itemdamage = (Damageable) lightstaff.getItemMeta();
        if(location.getBlock().getType() == Material.LIGHT){

            Location particlelocation = location.add(0.5,0.5,0.5);
            location.getWorld().spawnParticle(Particle.BLOCK_MARKER, particlelocation, 1, location.getBlock().getBlockData());

            location.getBlock().setType(Material.AIR);

            location.getWorld().spawnParticle(Particle.BLOCK_MARKER, particlelocation, 1, Material.BARRIER.createBlockData());

        } else if((location.getBlock().getType() == Material.AIR || location.getBlock().getType() == Material.CAVE_AIR) && itemdamage.getDamage() < 100) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Paveral.getPlugin(), () -> {

                Block block = location.getBlock();
                block.setType(Material.LIGHT);

                Levelled light = (Levelled) block.getBlockData();
                light.setLevel(lightlevel);
                block.setBlockData(light);
                location.getWorld().playSound(Sound.sound(Key.key("item.flintandsteel.use"), Sound.Source.BLOCK, 1f, 1f));
                ItemsUtil.applyDamage(lightstaff, 1);

                location.add(0.5, 0.5, 0.5);
                location.getWorld().spawnParticle(Particle.BLOCK_MARKER, location, 1, block.getBlockData());

                location.getWorld().spawn(location, Marker.class, marker -> marker.getPersistentDataContainer().set(LIGHTBLOCKMARKER, PersistentDataType.INTEGER, 1));
            }, 1);
        }
    }
}
