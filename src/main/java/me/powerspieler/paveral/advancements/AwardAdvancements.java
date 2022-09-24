package me.powerspieler.paveral.advancements;

import me.powerspieler.paveral.util.Constant;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;
import java.util.Objects;
import java.util.logging.Level;

public class AwardAdvancements implements Listener {
    // craft Tutorial Book
    @EventHandler
    public void onCraftTutorialBook(CraftItemEvent event){
        if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Constant.DISCOVERY, PersistentDataType.STRING) != null && Objects.equals(event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Constant.DISCOVERY, PersistentDataType.STRING), "altar_book")){
            Player player = (Player) event.getWhoClicked();
            if(isAdvancementUndone(player, "craft_tutorial_book")){
                grantAdvancement(player, "craft_tutorial_book");
            }
        }
    }
    // find Diary
    @EventHandler
    public void onDiaryFind(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(isAdvancementUndone(player , "find_diary")){
            if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(Constant.IS_DIARY)){
                grantAdvancement(player, "find_diary");
            }
        }
    }
    // Forge Tutorial Book
    @EventHandler
    public void onForgeTutorialBookPickup(EntityPickupItemEvent event){
        if(event.getItem().getItemStack().hasItemMeta() && event.getItem().getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.DISCOVERY, PersistentDataType.STRING) != null && Objects.equals(event.getItem().getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.DISCOVERY, PersistentDataType.STRING), "tech_book")){
            if(event.getEntity() instanceof Player player){
                if(isAdvancementUndone(player, "craft_tutorial_book_forge")){
                    grantAdvancement(player, "craft_tutorial_book_forge");
                }
            }
        }
    }
    @EventHandler
    public void onForgeTutorialBookClick(InventoryClickEvent event){
        if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Constant.DISCOVERY, PersistentDataType.STRING) != null && Objects.equals(event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Constant.DISCOVERY, PersistentDataType.STRING), "tech_book")){
            if(event.getWhoClicked() instanceof Player player){
                if(isAdvancementUndone(player, "craft_tutorial_book_forge")){
                    grantAdvancement(player, "craft_tutorial_book_forge");
                }
            }
        }
    }
    // Chunkloader
    @EventHandler
    public void onChunkloaderPickup(EntityPickupItemEvent event){
        if(event.getItem().getItemStack().hasItemMeta() && event.getItem().getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING) != null && Objects.equals(event.getItem().getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "chunkloader")){
            if(event.getEntity() instanceof Player player){
                if(isAdvancementUndone(player, "chunkloader")){
                    grantAdvancement(player, "chunkloader");
                }
            }
        }
    }
    @EventHandler
    public void onChunkloaderClick(InventoryClickEvent event){
        if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING) != null && Objects.equals(event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "chunkloader")){
            if(event.getWhoClicked() instanceof Player player){
                if(isAdvancementUndone(player, "chunkloader")){
                    grantAdvancement(player, "chunkloader");
                }
            }
        }
    }
    // Anti Creeper Grief
    @EventHandler
    public void onACGPickup(EntityPickupItemEvent event){
        if(event.getItem().getItemStack().hasItemMeta() && event.getItem().getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING) != null && Objects.equals(event.getItem().getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "anticreepergrief")){
            if(event.getEntity() instanceof Player player){
                if(isAdvancementUndone(player, "anti_creeper_grief")){
                    grantAdvancement(player, "anti_creeper_grief");
                }
            }
        }
    }
    @EventHandler
    public void onACGClick(InventoryClickEvent event){
        if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING) != null && Objects.equals(event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "anticreepergrief")){
            if(event.getWhoClicked() instanceof Player player){
                if(isAdvancementUndone(player, "anti_creeper_grief")){
                    grantAdvancement(player, "anti_creeper_grief");
                }
            }
        }
    }
    // Bedrock Breaker DIARY
    @EventHandler
    public void onBBDiaryPickup(EntityPickupItemEvent event){
        if(event.getItem().getItemStack().hasItemMeta() && event.getItem().getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.DISCOVERY, PersistentDataType.STRING) != null && Objects.equals(event.getItem().getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.DISCOVERY, PersistentDataType.STRING), "bedrock_breaker")){
            if(event.getEntity() instanceof Player player){
                if(isAdvancementUndone(player, "sleep_with_cat")){
                    grantAdvancement(player, "sleep_with_cat");
                }
            }
        }
    }
    @EventHandler
    public void onBBDiaryClick(InventoryClickEvent event){
        if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Constant.DISCOVERY, PersistentDataType.STRING) != null && Objects.equals(event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Constant.DISCOVERY, PersistentDataType.STRING), "bedrock_breaker")){
            if(event.getWhoClicked() instanceof Player player){
                if(isAdvancementUndone(player, "sleep_with_cat")){
                    grantAdvancement(player, "sleep_with_cat");
                }
            }
        }
    }
    // Bonk
    @EventHandler
    public void onBonkPickup(EntityPickupItemEvent event){
        if(event.getItem().getItemStack().hasItemMeta() && event.getItem().getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING) != null && Objects.equals(event.getItem().getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "bonk")){
            if(event.getEntity() instanceof Player player){
                if(isAdvancementUndone(player, "bonk")){
                    grantAdvancement(player, "bonk");
                }
            }
        }
    }
    @EventHandler
    public void onBonkClick(InventoryClickEvent event){
        if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING) != null && Objects.equals(event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "bonk")){
            if(event.getWhoClicked() instanceof Player player){
                if(isAdvancementUndone(player, "bonk")){
                    grantAdvancement(player, "bonk");
                }
            }
        }
    }
    // Bedrock Breaker
    @EventHandler
    public void onBBPickup(EntityPickupItemEvent event){
        if(event.getItem().getItemStack().hasItemMeta() && event.getItem().getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING) != null && Objects.equals(event.getItem().getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "bedrock_breaker")){
            if(event.getEntity() instanceof Player player){
                if(isAdvancementUndone(player, "bedrock_breaker")){
                    grantAdvancement(player, "bedrock_breaker");
                }
            }
        }
    }
    @EventHandler
    public void onBBClick(InventoryClickEvent event){
        if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING) != null && Objects.equals(event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "bedrock_breaker")){
            if(event.getWhoClicked() instanceof Player player){
                if(isAdvancementUndone(player, "bedrock_breaker")){
                    grantAdvancement(player, "bedrock_breaker");
                }
            }
        }
    }
    // Lightning Rod
    @EventHandler
    public void onLightningRodPickup(EntityPickupItemEvent event){
        if(event.getItem().getItemStack().hasItemMeta() && event.getItem().getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING) != null && Objects.equals(event.getItem().getItemStack().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "lightning_rod")){
            if(event.getEntity() instanceof Player player){
                if(isAdvancementUndone(player, "lightning_rod")){
                    grantAdvancement(player, "lightning_rod");
                }
            }
        }
    }
    @EventHandler
    public void onLightningRodClick(InventoryClickEvent event){
        if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING) != null && Objects.equals(event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "lightning_rod")){
            if(event.getWhoClicked() instanceof Player player){
                if(isAdvancementUndone(player, "lightning_rod")){
                    grantAdvancement(player, "lightning_rod");
                }
            }
        }
    }
    // Flying Pig
    @EventHandler
    public void onPigBonk(EntityDeathEvent event){
        if(event.getEntity() instanceof Pig pig){
            if(pig.getLastDamageCause() != null){
                EntityDamageEvent.DamageCause deathcause = pig.getLastDamageCause().getCause();
                if(deathcause == EntityDamageEvent.DamageCause.FALL){
                    Player player = pig.getKiller();
                    if(player != null && player.getInventory().getItemInMainHand().hasItemMeta() && player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING) != null && Objects.equals(player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "bonk")){
                        if(isAdvancementUndone(player, "flying_pig")){
                            grantAdvancement(player, "flying_pig");
                        }
                    }
                }
            }
        }
    }






    public static void grantAdvancement(Player player, String key){
        NamespacedKey advkey = new NamespacedKey("paveral", key);
        Advancement adv = Bukkit.getAdvancement(advkey);
        if(adv != null){
            Collection<String> remCrits = player.getAdvancementProgress(adv).getRemainingCriteria();
            for(String crit : remCrits){
                player.getAdvancementProgress(adv).awardCriteria(crit);
            }
        } else Bukkit.getLogger().log(Level.WARNING,"Failed to grant Advancement! Advancement: \"" + advkey.getKey() + "\" does not exist");
    }

    public static boolean isAdvancementUndone(Player player, String key) {
        NamespacedKey advkey = new NamespacedKey("paveral", key);
        Advancement adv = Bukkit.getAdvancement(advkey);
        if (adv != null) {
            return !player.getAdvancementProgress(adv).isDone();
        } else {
            Bukkit.getLogger().log(Level.WARNING, "Failed to check advancement! Advancement: \"" + advkey.getKey() + "\" does not exist");
        }
        return true;
    }
}
