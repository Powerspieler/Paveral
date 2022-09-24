package me.powerspieler.paveral.discovery;

import me.powerspieler.paveral.advancements.AwardAdvancements;
import me.powerspieler.paveral.items.Items;
import me.powerspieler.paveral.items.LightStaff;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FishingLootTable implements Listener {
    @EventHandler
    public void onFishing(PlayerFishEvent event){
        if(event.getState() == PlayerFishEvent.State.CAUGHT_FISH){
            if(event.getCaught() instanceof Item item){
                if(!hasLightstaff(event.getPlayer())){
                    if(Math.random() <= 0.1){
                        Items lightstaff = new LightStaff();
                        ItemStack lsStack = lightstaff.build();

                        Damageable damagemeta = (Damageable) lsStack.getItemMeta();
                        damagemeta.setDamage((int) (100 - (Math.random() / 8) * 100));
                        lsStack.setItemMeta(damagemeta);
                        item.setItemStack(lsStack);
                    }
                }

                if(AwardAdvancements.isAdvancementUndone(event.getPlayer(), "fishing")){
                    if(Math.random() <= 0.05){
                        AwardAdvancements.grantAdvancement(event.getPlayer(), "fishing");
                        ItemStack wetPaper = new ItemStack(Material.PAPER);
                        ItemMeta wetPaperMeta = wetPaper.getItemMeta();
                        List<Component> lore = new ArrayList<>();
                        lore.add(Component.text("Have you ever wondered if you could")
                                .decoration(TextDecoration.ITALIC, false));
                        lore.add(Component.text("enhance the documentation book on the altar?")
                                .decoration(TextDecoration.ITALIC, false));
                        wetPaperMeta.lore(lore);
                        wetPaper.setItemMeta(wetPaperMeta);

                        item.setItemStack(wetPaper);

                    }
                }
            }
        }
    }

    private boolean hasLightstaff(final Player player){
        final PlayerInventory inv = player.getInventory();
        final ItemStack[] contents = inv.getContents();
        for (final ItemStack stack : contents) {
            if (stack != null && stack.hasItemMeta() && stack.getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE) && Objects.equals(stack.getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "lightstaff")) {
                return true;
            }
        }
        return false;
    }
}
