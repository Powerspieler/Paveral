package me.powerspieler.paveral.items;

import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

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
}
