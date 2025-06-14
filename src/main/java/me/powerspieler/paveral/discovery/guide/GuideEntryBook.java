package me.powerspieler.paveral.discovery.guide;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.advancements.AwardAdvancements;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.logging.Level;

public interface GuideEntryBook {
    List<Component> getPages();

    default Component generateAchievementResetPage(String advancement_key) {
        Advancement advancement = Bukkit.getAdvancement(new NamespacedKey("paveral", advancement_key));
        if (advancement == null) {
            Paveral.getPlugin().getLogger().log(Level.SEVERE, "Could not find advancement " + advancement_key);
            return Component.empty();
        }
        Component revokeComp = Component.text("\n\nClick here to revoke ", NamedTextColor.DARK_RED).append(advancement.displayName());
        return Component.text("This piece of literature is bound to the advancement ")
                .append(advancement.displayName())
                .append(Component.text(" and can only be found when it's uncompleted.\nYou may want to revoke it yourself, if you need this piece again"))
                .append(revokeComp)
                        .clickEvent(ClickEvent.callback(audience -> {
                            if (audience instanceof Player player) {
                                if(!AwardAdvancements.isAdvancementUndone(player, advancement_key)){
                                    AwardAdvancements.revokeAdvancement(player, advancement_key);
                                    player.sendMessage(Component.text("Advancement ")
                                            .append(advancement.displayName())
                                            .append(Component.text(" has been revoked. You are now able to encounter this literature again!")));
                                    player.playSound(Sound.sound(Key.key("ui.toast.out"), Sound.Source.MASTER, 2f, 2f), Sound.Emitter.self());
                                }
                            }
                        }));
    }
}
