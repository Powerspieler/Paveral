package me.powerspieler.paveral.items.helper;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemHoldingController implements Listener {
    @EventHandler
    public void onItemSwitch(PlayerItemHeldEvent event){
        Player player = event.getPlayer();

        new ActionbarStatus(player, "", 1L){ // Always empty Actionbar
            @Override
            public void message() {
                player.sendActionBar(Component.empty());
            }
        }.displayMessageOnce();

        ItemStack item = player.getInventory().getItem(event.getNewSlot());
//        ItemStack offHandItem = player.getInventory().getItemInOffHand();
        if(item != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE)){
            String itemtype = item.getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING);
            assert itemtype != null;
            player.getPersistentDataContainer().set(Constant.IS_HOLDING, PersistentDataType.STRING, itemtype);
//        } else if(item == null && offHandItem.hasItemMeta() && offHandItem.getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE)) { // Removes Offhand completely; issues when using
//            String itemtype = offHandItem.getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING);         // leftclick. e.g. LumberjacksBass Breaking/Break Block
//            assert itemtype != null;
//            player.getPersistentDataContainer().set(Constant.IS_HOLDING, PersistentDataType.STRING, itemtype);
            Bukkit.getPluginManager().callEvent(new ItemHoldingControllerEvent(player, itemtype));
        } else {
            player.getPersistentDataContainer().remove(Constant.IS_HOLDING);
            Bukkit.getPluginManager().callEvent(new ItemHoldingControllerEvent(player, ""));
        }
    }

    @EventHandler
    public void onItemHoldingControllerEvent(ItemHoldingControllerEvent event){
        ActionbarStatus.recoverDisplayMessage(event.getPlayer().getUniqueId(), event.getItemType());
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
    public void onEntityInteraction(PlayerInteractAtEntityEvent event){ // Allay, Armorstand
        new BukkitRunnable() { // CANT BE EXECUTED INSTANT, SMALL DELAY IS NESSESSARY TO GET CORRECT ITEM IN SLOT
            @Override
            public void run() {
                Bukkit.getPluginManager().callEvent(new PlayerItemHeldEvent(event.getPlayer(), event.getPlayer().getInventory().getHeldItemSlot(), event.getPlayer().getInventory().getHeldItemSlot()));
            }
        }.runTask(Paveral.getPlugin());
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
        if(event.getInventory().getHolder() instanceof Player player){
            if(event.getSlot() < 0) return; // Pressing Q on Item in Inventory calls this event with -999

            ItemStack clickedItem = player.getInventory().getItem(event.getSlot());
            if(event.getSlot() == player.getInventory().getHeldItemSlot() || event.getSlot() == 40 || (clickedItem != null && clickedItem.getPersistentDataContainer().has(Constant.ITEMTYPE))){ // 40 = Offhand
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.getPluginManager().callEvent(new PlayerItemHeldEvent(player, player.getInventory().getHeldItemSlot(), player.getInventory().getHeldItemSlot()));
                    }
                }.runTaskLater(Paveral.getPlugin(), 1);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        if(event.getPlayer() instanceof Player player){
            Bukkit.getPluginManager().callEvent(new PlayerItemHeldEvent(player, player.getInventory().getHeldItemSlot(), player.getInventory().getHeldItemSlot()));
        }
    }

    public static boolean checkIsHoldingPaveralItem(Player player, String itemtype){
        return player.getPersistentDataContainer().has(Constant.IS_HOLDING, PersistentDataType.STRING)
                && player.getPersistentDataContainer().get(Constant.IS_HOLDING, PersistentDataType.STRING).equals(itemtype);
    }
}
