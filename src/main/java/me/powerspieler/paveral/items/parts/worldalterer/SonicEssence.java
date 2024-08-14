package me.powerspieler.paveral.items.parts.worldalterer;

import me.powerspieler.paveral.advancements.AwardAdvancements;
import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.items.PaveralItem;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class SonicEssence extends PaveralItem implements Listener {
    private static Component itemName(){
        return Component.text("Sonic Essence", NamedTextColor.GOLD) // Name by Raphilius
                .decoration(TextDecoration.ITALIC, false);
    }

    public SonicEssence() {
        super(Material.JIGSAW, 2, Constant.ITEMTYPE, "sonic_essence", itemName(), null);
    }

    @Override
    protected ItemStack build() {
        return super.build();
    }

    @Override
    public PaveralRecipe recipe() {
        return null;
    }

    // --- Item Logic ---

    @EventHandler
    private void onWardenSonic(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player player && event.getCause() == EntityDamageEvent.DamageCause.SONIC_BOOM){
            ItemStack bottle = player.getInventory().getItemInMainHand();
            if(bottle.getType() == Material.GLASS_BOTTLE){
                bottle.setAmount(bottle.getAmount() - 1);

                if(AwardAdvancements.isAdvancementUndone(player, keyString)){
                    AwardAdvancements.grantAdvancement(player, keyString);
                }

                HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(new SonicEssence().build());
                if(!leftover.isEmpty()){
                    for(ItemStack item : leftover.values()){
                        player.getWorld().dropItemNaturally(player.getLocation(), item);
                    }
                }
            }
        }
    }

}
