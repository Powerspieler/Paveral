package me.powerspieler.paveral.misc;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.advancements.AwardAdvancements;
import me.powerspieler.paveral.util.RecipeLoader;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

public class HandlePlayerJoin implements Listener {
    @EventHandler
    public void onPJRecipeReminder(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(player.hasDiscoveredRecipe(RecipeLoader.altarbookrecipekey) && AwardAdvancements.isAdvancementUndone(player, "craft_tutorial_book")){
            player.sendMessage(Component.text("You still haven't crafted the tutorial book! Check your recipe book!" , NamedTextColor.DARK_PURPLE));
        }
    }

    @EventHandler
    public void onPJChangelog(PlayerJoinEvent event){
        Player player = event.getPlayer();
        NamespacedKey msg = new NamespacedKey(Paveral.getPlugin(), "show_chlog");
        if(!player.getPersistentDataContainer().has(msg)){
            player.getPersistentDataContainer().set(msg, PersistentDataType.INTEGER, 3);
        }
        if(player.getPersistentDataContainer().get(msg, PersistentDataType.INTEGER) != null && player.getPersistentDataContainer().get(msg, PersistentDataType.INTEGER) > 0){
            player.sendMessage(Component.text("\"Paveralicious Additions - Datapack\" has been ported to a Java-Plugin \"Paveral\" due to performance reasons!", NamedTextColor.GOLD));
            player.sendMessage(Component.text("Every item / tool crafted so far will NOT work any longer and need to be recrafted! Some tweaks have also been made:", NamedTextColor.DARK_PURPLE));
            player.sendMessage(Component.text("+ Added a new multistructure to craft tech-related items\n* Layout of Formingaltar has changed; recraft the tutorial books!\n* Advancements have been reset\n* Some recipes have changed\n* Behaviour of Lightning Rod has been tweaked\n* Many many more minor tweaks\n- Removed Golden Crook (Sheepfreeze)", NamedTextColor.YELLOW));

            int msg_remain = player.getPersistentDataContainer().get(msg, PersistentDataType.INTEGER);
            msg_remain --;
            player.getPersistentDataContainer().set(msg, PersistentDataType.INTEGER, msg_remain);
        }
    }
}
