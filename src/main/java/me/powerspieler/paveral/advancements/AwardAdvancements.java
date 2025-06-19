package me.powerspieler.paveral.advancements;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.crafting.ItemHelper;
import me.powerspieler.paveral.discovery.guide.BaseGuide;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;
import java.util.Objects;
import java.util.logging.Level;

public class AwardAdvancements implements Listener {
    // Award Recipe for Guide Book
    @EventHandler
    public void onRootAdvGrant(PlayerAdvancementDoneEvent event){
        NamespacedKey root = new NamespacedKey("paveral", "root");
        if(event.getAdvancement().getKey().equals(root)){
            Player player = event.getPlayer();
            player.discoverRecipe(BaseGuide.recipeKey);
            player.sendMessage(Component.text("You have unlocked the Paveral Guide! Check your recipe book inside a crafting table!" , NamedTextColor.DARK_PURPLE));
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
                    if(player != null && ItemHelper.hasPaveralNamespacedKey(player.getInventory().getItemInMainHand(), Constant.ITEMTYPE) && Objects.equals(player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "bonk")){
                        checkAndGrandAdvancement(player, "flying_pig");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event){
        gotNewItem(event.getItem().getItemStack(), event.getEntity());
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent event){
        gotNewItem(event.getCurrentItem(), event.getWhoClicked());
    }

    @EventHandler
    public void onItemCraft(CraftItemEvent event){
        gotNewItem(event.getCurrentItem(), event.getWhoClicked());
    }

    private void gotNewItem(ItemStack item, Entity entity){
        if(ItemHelper.hasPaveralNamespacedKey(item, Constant.ITEMTYPE) && entity instanceof Player player){
            String itemType = item.getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING);
            checkAndGrandAdvancement(player, itemType);
        }

        if(ItemHelper.hasPaveralNamespacedKey(item, Constant.DISCOVERY) && entity instanceof Player player){
            String discoveryType = item.getPersistentDataContainer().get(Constant.DISCOVERY, PersistentDataType.STRING);
            switch (Objects.requireNonNull(discoveryType)){
                case "bedrock_breaker" -> checkAndGrandAdvancement(player, "sleep_with_cat");
                case "guide_book" -> checkAndGrandAdvancement(player, "craft_guide_book");
                case "tech_book" -> checkAndGrandAdvancement(player, "craft_tutorial_book_forge");
                case "worldalterer" -> {return;}
                default -> checkAndGrandAdvancement(player, discoveryType);
            }
        }

        if(ItemHelper.hasPaveralNamespacedKey(item, Constant.IS_DIARY) && entity instanceof Player player){
            checkAndGrandAdvancement(player, "find_diary");
        }
    }


    private static void checkAndGrandAdvancement(Player player, String key){
        if(existsAdvancement(key) && isAdvancementUndone(player, key)){
            grantAdvancement(player, key);
        }
    }

    private static boolean existsAdvancement(String key){
        return Bukkit.getAdvancement(new NamespacedKey("paveral", key)) != null;
    }

    public static void grantAdvancement(Player player, String key){
        NamespacedKey advkey = new NamespacedKey("paveral", key);
        Advancement adv = Bukkit.getAdvancement(advkey);
        if(adv != null){
            Collection<String> remCrits = player.getAdvancementProgress(adv).getRemainingCriteria();
            for(String crit : remCrits){
                player.getAdvancementProgress(adv).awardCriteria(crit);
            }
        } else Paveral.getPlugin().getLogger().log(Level.WARNING,"Failed to grant Advancement! Advancement: \"" + advkey.getKey() + "\" does not exist");
    }

    public static void revokeAdvancement(Player player, String key){
        NamespacedKey advkey = new NamespacedKey("paveral", key);
        Advancement adv = Bukkit.getAdvancement(advkey);
        if(adv != null){
            Collection<String> awardedCriteria = player.getAdvancementProgress(adv).getAwardedCriteria();
            for(String crit : awardedCriteria){
                player.getAdvancementProgress(adv).revokeCriteria(crit);
            }
        } else Paveral.getPlugin().getLogger().log(Level.WARNING,"Failed to revoke Advancement! Advancement: \"" + advkey.getKey() + "\" does not exist");
    }

    public static boolean isAdvancementUndone(Player player, String key) {
        NamespacedKey advkey = new NamespacedKey("paveral", key);
        Advancement adv = Bukkit.getAdvancement(advkey);
        if (adv != null) {
            return !player.getAdvancementProgress(adv).isDone();
        } else {
            Paveral.getPlugin().getLogger().log(Level.WARNING, "Failed to check advancement! Advancement: \"" + advkey.getKey() + "\" does not exist");
        }
        return true;
    }
}
