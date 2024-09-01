package me.powerspieler.paveral.items;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.crafting.StandardIngredient;
import me.powerspieler.paveral.items.helper.Dismantable;
import me.powerspieler.paveral.items.helper.ItemHoldingController;
import me.powerspieler.paveral.util.Constant;
import me.powerspieler.paveral.util.ItemsUtil;
import me.powerspieler.paveral.util.MarkerDataStorage;
import net.kyori.adventure.audience.Audience;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LightStaff extends PaveralItem implements Listener, Dismantable {
    private static Component itemName(){
        return Component.text("Lightstaff", NamedTextColor.DARK_PURPLE)
                .decoration(TextDecoration.ITALIC, false);
    }

    private static List<Component> lore(){
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Press ", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false)
                .append(
                        Component.keybind("key.use", NamedTextColor.YELLOW)
                                .decoration(TextDecoration.ITALIC, false))
                .append(Component.text(" to place and remove lightblocks", NamedTextColor.DARK_AQUA)));
        lore.add(Component.text("Press (", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false)
                .append(
                        Component.keybind("key.sneak", NamedTextColor.GREEN)
                                .decoration(TextDecoration.ITALIC, false))
                .append(Component.text(" + ) ", NamedTextColor.DARK_AQUA)
                        .decoration(TextDecoration.ITALIC, false))
                .append(Component.keybind("key.attack", NamedTextColor.YELLOW)
                        .decoration(TextDecoration.ITALIC, false))
                .append(Component.text(" to change lightlevel", NamedTextColor.DARK_AQUA)
                        .decoration(TextDecoration.ITALIC,false)));
        lore.add(Component.text(""));
        lore.add(Component.text("Enchantable with ", NamedTextColor.BLUE)
                .decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Unbreaking",NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false))
                .append(Component.text(" and ", NamedTextColor.BLUE)
                        .decoration(TextDecoration.ITALIC, false))
                .append(Component.text("Mending", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)));
        return lore;
    }

    public LightStaff() {
        super(Material.WARPED_FUNGUS_ON_A_STICK, 4, Constant.ITEMTYPE, "lightstaff", itemName(), lore());
    }

    @Override
    protected ItemStack build() {
        ItemStack item = super.build();
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(LIGHTBLOCKLEVEL, PersistentDataType.INTEGER, 15);
        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    public PaveralRecipe recipe() {
        Set<StandardIngredient> ingredients = new HashSet<>();
        ingredients.add(new StandardIngredient(Material.IRON_INGOT, 2));
        ingredients.add(new StandardIngredient(Material.COPPER_INGOT, 1));
        ingredients.add(new StandardIngredient(Material.REDSTONE_LAMP, 1));
        ingredients.add(new StandardIngredient(Material.WITHER_ROSE, 2));
        return new PaveralRecipe(ingredients, this.build());
    }

    @Override
    public List<ItemStack> parts() {
        List<ItemStack> parts = new ArrayList<>();
        parts.add(new ItemStack(Material.IRON_INGOT, 2));
        parts.add(new ItemStack(Material.COPPER_INGOT));
        parts.add(new ItemStack(Material.REDSTONE_LAMP));
        parts.add(new ItemStack(Material.WITHER_ROSE, 2));
        return parts;
    }

    // --- Item Logic ---

    private static final NamespacedKey LIGHTBLOCKMARKER = new NamespacedKey(Paveral.getPlugin(), "lightblock");
    private static final NamespacedKey LIGHTBLOCKLEVEL = new NamespacedKey(Paveral.getPlugin(), "lightlevel");
    private static boolean particlecooldown;

    // Placement and Removal
    @EventHandler
    private void onPlayerRightclick(PlayerInteractEvent event){
        if(event.hasItem() && ItemHoldingController.checkIsHoldingPaveralItem(event.getPlayer(), keyString)){
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getClickedBlock() != null && event.getItem().getItemMeta().getPersistentDataContainer().get(LIGHTBLOCKLEVEL, PersistentDataType.INTEGER) != null) {
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

    // Display Particle and Message
    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(ItemHoldingController.checkIsHoldingPaveralItem(player, keyString)){
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

            int lightlevel = -1;
            ItemStack mainHand = player.getInventory().getItemInMainHand();
            ItemStack offHand = player.getInventory().getItemInOffHand();
            if (mainHand.hasItemMeta() && mainHand.getItemMeta().getPersistentDataContainer().has(LIGHTBLOCKLEVEL)){
                lightlevel = mainHand.getItemMeta().getPersistentDataContainer().get(LIGHTBLOCKLEVEL, PersistentDataType.INTEGER);
            } else if(offHand.hasItemMeta() && offHand.getItemMeta().getPersistentDataContainer().has(LIGHTBLOCKLEVEL)) {
                lightlevel = player.getInventory().getItemInOffHand().getItemMeta().getPersistentDataContainer().get(LIGHTBLOCKLEVEL, PersistentDataType.INTEGER);
            }
            if(lightlevel != -1){
                showActionbar(player, lightlevel);
            }
        }
    }


    // Change Lightlevel
    @EventHandler
    private void onPlayerLeftClick(PlayerInteractEvent event){
        if(ItemHoldingController.checkIsHoldingPaveralItem(event.getPlayer(), keyString) && event.getAction().isLeftClick() && event.hasItem()){
            ItemStack lightstaff = event.getItem();
            ItemMeta lightstaffmeta = lightstaff.getItemMeta();
            int lightlevel = event.getItem().getItemMeta().getPersistentDataContainer().get(LIGHTBLOCKLEVEL, PersistentDataType.INTEGER);
            if (event.getPlayer().isSneaking()) {
                lightlevel--;
                if (lightlevel < 1) {
                    lightlevel = 15;
                }
            } else if (!event.getPlayer().isSneaking()) {
                lightlevel++;
                if (lightlevel > 15) {
                    lightlevel = 1;
                }
            }
            lightstaffmeta.getPersistentDataContainer().set(LIGHTBLOCKLEVEL, PersistentDataType.INTEGER, lightlevel);
            lightstaff.setItemMeta(lightstaffmeta);
            showActionbar(event.getPlayer(), lightlevel);
            event.setCancelled(true);

        }
    }

    private void showActionbar(Player player, int lightlevel){
        player.sendActionBar(Component.text("[ ", NamedTextColor.GOLD)
                .append(Component.text("Lightlevel: ",NamedTextColor.GREEN))
                .append(Component.text(lightlevel ,NamedTextColor.LIGHT_PURPLE))
                .append(Component.text(" ]",NamedTextColor.GOLD)));

    }


    private void setLightblock(Location location, int lightlevel, ItemStack lightstaff){
        final Audience targets = location.getWorld().filterAudience(member -> member instanceof Player player && player.getLocation().distanceSquared(location) < 25);
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
                targets.playSound(net.kyori.adventure.sound.Sound.sound(Key.key("item.flintandsteel.use"), net.kyori.adventure.sound.Sound.Source.BLOCK, 1f, 1f), Sound.Emitter.self());
                ItemsUtil.applyDamage(lightstaff, 1, 100);

                location.add(0.5, 0.5, 0.5);
                location.getWorld().spawnParticle(Particle.BLOCK_MARKER, location, 1, block.getBlockData());

                MarkerDataStorage.createMarker(block);
                MarkerDataStorage.getMarkerDataContainer(block).set(LIGHTBLOCKMARKER, PersistentDataType.INTEGER, 1);
            }, 1);
        }
    }
}
