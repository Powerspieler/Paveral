package me.powerspieler.paveral.advancements;

import me.powerspieler.paveral.util.Constant;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;
import java.util.logging.Level;

public class AwardAdvancements implements Listener {
    @EventHandler
    public void onCraftTutorialBook(CraftItemEvent event){
        if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Constant.DISCOVERY, PersistentDataType.STRING) != null && event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Constant.DISCOVERY, PersistentDataType.STRING).equals("altar_book")){
            Player player = (Player) event.getWhoClicked();
            if(!isAdvancementDone(player, "craft_tutorial_book")){
                grantAdvancement(player, "craft_tutorial_book");
            }
        }
    }
    @EventHandler
    public void onDiaryFind(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(!isAdvancementDone(player , "find_diary")){
            if(event.getCurrentItem() != null && event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(Constant.IS_DIARY)){
                grantAdvancement(player, "find_diary");
            }
        }
    }


    private static void grantAdvancement(Player player, String key){
        NamespacedKey advkey = new NamespacedKey("paveral", key);
        Advancement adv = Bukkit.getAdvancement(advkey);
        if(adv != null){
            Collection<String> remCrits = player.getAdvancementProgress(adv).getRemainingCriteria();
            for(String crit : remCrits){
                player.getAdvancementProgress(adv).awardCriteria(crit);
            }
        } else Bukkit.getLogger().log(Level.WARNING,"Failed to grant Advancement! Advancement: \"" + advkey.getKey() + "\" does not exist");
    }

    private static boolean isAdvancementDone(Player player, String key) {
        NamespacedKey advkey = new NamespacedKey("paveral", key);
        Advancement adv = Bukkit.getAdvancement(advkey);
        if (adv != null) {
            return player.getAdvancementProgress(adv).isDone();
        } else {
            Bukkit.getLogger().log(Level.WARNING, "Failed to check advancement! Advancement: \"" + advkey.getKey() + "\" does not exist");
        }
        return false;
    }
}
