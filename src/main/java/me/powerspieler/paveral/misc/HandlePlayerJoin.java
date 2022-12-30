package me.powerspieler.paveral.misc;

import me.powerspieler.paveral.advancements.AwardAdvancements;
import me.powerspieler.paveral.util.RecipeLoader;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

import java.util.logging.Level;

public class HandlePlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        // Check and provide Resourcepack
        if(!player.hasResourcePack()){
            player.setResourcePack("https://github.com/Powerspieler/Paveral-Resourcepack/releases/download/v.1.1/Paveral-Resourcepack_v1.1.zip", "de9a6add6fd7b555ca9ff653f21580e3fc358c14", false, Component.text("Custom items have been added to this server and therefore require custom textures!", NamedTextColor.GOLD));
        }
        // Altarbook Reminder Message
        if(player.hasDiscoveredRecipe(RecipeLoader.altarbookrecipekey) && AwardAdvancements.isAdvancementUndone(player, "craft_tutorial_book")){
            player.sendMessage(Component.text("You still haven't crafted the tutorial book! Check your recipe book!" , NamedTextColor.DARK_PURPLE));
        }
    }

    @EventHandler
    public void onResourcepackFailure(PlayerResourcePackStatusEvent event){
        /* if(event.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED){
            event.getPlayer().sendMessage(Component.text("You decline the Resourcepack!\n", NamedTextColor.DARK_RED)
                    .append(Component.text("Most custom items will appear as warped funguns on a stick but still work the same way they should\n", NamedTextColor.GRAY))
                    .append(Component.text("If you wish to remove the loading screen download the resourcepack yourself ", NamedTextColor.GOLD)
                            .append(Component.text("here", NamedTextColor.BLUE)
                                    .decoration(TextDecoration.UNDERLINED, true)
                                    .hoverEvent(Component.text("Download Resourcepack"))
                                    .clickEvent(ClickEvent.openUrl("https://github.com/Powerspieler/Paveral-Resourcepack/releases/download/v1.0/Paveral-Resourcepack_v1.0.zip")))));
        }*/
        if(event.getStatus() == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD){
            event.getPlayer().sendMessage(Component.text("Resourcepack Download Failed! Contact the plugin author", NamedTextColor.RED));
            Bukkit.getLogger().log(Level.WARNING, "Request to download resourcepack failed!");
        }
    }
}
