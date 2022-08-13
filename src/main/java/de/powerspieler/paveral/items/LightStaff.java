package de.powerspieler.paveral.items;

import de.powerspieler.paveral.Paveral;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class LightStaff implements Listener {

    private static NamespacedKey lightstaffkey = new NamespacedKey(Paveral.getPlugin(), "lightstaff");
    private static NamespacedKey lightblockmarker = new NamespacedKey(Paveral.getPlugin(), "lightblock");
    private static NamespacedKey lightblocklevel = new NamespacedKey(Paveral.getPlugin(), "lightlevel");
    private static boolean particlecooldown;

    @EventHandler
    public void onPlayerRightclick(PlayerInteractEvent event){
        if(event.hasItem()){
            if(event.getItem().getItemMeta().getPersistentDataContainer().has(lightstaffkey, PersistentDataType.INTEGER)){
                if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
                    Location location = event.getClickedBlock().getLocation();
                    int lightlevel = event.getItem().getItemMeta().getPersistentDataContainer().get(lightblocklevel, PersistentDataType.INTEGER);
                    switch(event.getBlockFace()){
                        case UP:
                            location.add(0, 1, 0);
                            break;

                        case DOWN:
                            location.add(0, -1, 0);
                            break;

                        case NORTH:
                            location.add(0, 0, -1);
                            break;

                        case SOUTH:
                            location.add(0, 0, 1);
                            break;

                        case WEST:
                            location.add(-1, 0, 0);
                            break;

                        case EAST:
                            location.add(1, 0, 0);
                            break;
                    }
                    setLightblock(location, lightlevel);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        ItemStack MainHand = player.getInventory().getItemInMainHand();
        ItemStack OffHand = player.getInventory().getItemInOffHand();
        if(MainHand.hasItemMeta() && MainHand.getItemMeta().getPersistentDataContainer().has(lightstaffkey, PersistentDataType.INTEGER) || OffHand.hasItemMeta() && OffHand.getItemMeta().getPersistentDataContainer().has(lightstaffkey, PersistentDataType.INTEGER)){
            if(!particlecooldown) {
                particlecooldown = true;
                Bukkit.getScheduler().scheduleSyncDelayedTask(Paveral.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        particlecooldown = false;
                    }
                }, 80);
                List<Entity> entityList = player.getNearbyEntities(16D,16D,16D);
                for(Entity entity : entityList){
                    if(entity instanceof Marker marker){
                        if(marker.getPersistentDataContainer().has(lightblockmarker, PersistentDataType.INTEGER)){
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
                lightlevel = player.getInventory().getItemInOffHand().getItemMeta().getPersistentDataContainer().get(lightblocklevel, PersistentDataType.INTEGER);
            } else if (player.getInventory().getItemInMainHand().hasItemMeta()){
                lightlevel = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(lightblocklevel, PersistentDataType.INTEGER);
            }
            showActionbar(player, lightlevel);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        if(event.getItemDrop().getItemStack().hasItemMeta() && event.getItemDrop().getItemStack().getItemMeta().getPersistentDataContainer().has(lightstaffkey, PersistentDataType.INTEGER)){
            ItemStack lightstaff = event.getItemDrop().getItemStack();
            ItemMeta lightstaffmeta = lightstaff.getItemMeta();

            int lightlevel = event.getItemDrop().getItemStack().getItemMeta().getPersistentDataContainer().get(lightblocklevel, PersistentDataType.INTEGER);
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
            lightstaffmeta.getPersistentDataContainer().set(lightblocklevel, PersistentDataType.INTEGER, lightlevel);
            lightstaff.add().setItemMeta(lightstaffmeta);
            showActionbar(event.getPlayer(), lightlevel);
            event.setCancelled(true);
        }
    }

    public void showActionbar(Player player, int lightlevel){
        player.sendActionBar(Component.text("[ ", NamedTextColor.GOLD)
                .append(Component.text("Lightlevel: ",NamedTextColor.GREEN))
                .append(Component.text(lightlevel ,NamedTextColor.LIGHT_PURPLE))
                .append(Component.text(" ]",NamedTextColor.GOLD)));

    }


    public void setLightblock(Location location, int lightlevel){
        if(location.getBlock().getType() == Material.LIGHT){
            location.getBlock().setType(Material.AIR);
            Location particlelocation = location.add(0.5,0.5,0.5);
            location.getWorld().spawnParticle(Particle.BLOCK_MARKER, particlelocation, 1, Material.BARRIER.createBlockData());
        } else if(location.getBlock().getType() == Material.AIR) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Paveral.getPlugin(), new Runnable() {

                @Override
                public void run() {

                    Block block = location.getBlock();
                    block.setType(Material.LIGHT);

                    Levelled light = (Levelled) block.getBlockData();
                    light.setLevel(lightlevel);
                    block.setBlockData(light);

                    Location particlelocation = location;
                    particlelocation.add(0.5, 0.5, 0.5);
                    location.getWorld().spawnParticle(Particle.BLOCK_MARKER, particlelocation, 1, block.getBlockData());

                    location.getWorld().spawn(location, Marker.class, marker -> {
                        marker.getPersistentDataContainer().set(lightblockmarker, PersistentDataType.INTEGER, 1);
                    });
                }
            }, 1);
        }
    }
}
