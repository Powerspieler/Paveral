package me.powerspieler.paveral.items;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemHoldingController implements Listener {
    @EventHandler
    public void onItemSwitch(PlayerItemHeldEvent event){
        Player player = event.getPlayer();
        player.sendActionBar(Component.text("")); // Always empty Actionbar
        ItemStack item = event.getPlayer().getInventory().getItem(event.getNewSlot());
        if(item != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE)){
            String itemtype = item.getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING);
            assert itemtype != null;
            player.getPersistentDataContainer().set(Constant.IS_HOLDING, PersistentDataType.STRING, itemtype);
        } else player.getPersistentDataContainer().remove(Constant.IS_HOLDING);
    }

    @EventHandler
    public void onItemSwitchOffhand(PlayerSwapHandItemsEvent event){
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getPluginManager().callEvent(new PlayerItemHeldEvent(event.getPlayer(), event.getPlayer().getInventory().getHeldItemSlot(), event.getPlayer().getInventory().getHeldItemSlot()));
            }
        }.runTaskLater(Paveral.getPlugin(), 1);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        Bukkit.getPluginManager().callEvent(new PlayerItemHeldEvent(event.getPlayer(), event.getPlayer().getInventory().getHeldItemSlot(), event.getPlayer().getInventory().getHeldItemSlot()));
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event){
        if(event.getEntity() instanceof Player player){
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(player.getInventory().getItemInMainHand().equals(event.getItem().getItemStack())){
                        Bukkit.getPluginManager().callEvent(new PlayerItemHeldEvent(player, player.getInventory().getHeldItemSlot(), player.getInventory().getHeldItemSlot()));
                    }
                }
            }.runTaskLater(Paveral.getPlugin(), 1);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getInventory().getHolder() instanceof Player player && event.getSlot() == player.getInventory().getHeldItemSlot()){
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getPluginManager().callEvent(new PlayerItemHeldEvent(player, player.getInventory().getHeldItemSlot(), player.getInventory().getHeldItemSlot()));
                }
            }.runTaskLater(Paveral.getPlugin(), 1);
        }
    }
}
