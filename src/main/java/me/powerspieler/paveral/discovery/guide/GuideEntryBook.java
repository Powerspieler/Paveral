package me.powerspieler.paveral.discovery.guide;

import me.powerspieler.paveral.advancements.AwardAdvancements;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.util.List;

public interface GuideEntryBook {
    List<Component> getPages();

    default Component generateAchievementResetPage(String advancement) {
        return Component.text("FORMING ALTAR\n\n")
                .append(Component.text("Klicke hier für mehr Informationen", NamedTextColor.BLUE)
                        .clickEvent(ClickEvent.callback(audience -> {
                            if (audience instanceof Player player) {
                                if(!AwardAdvancements.isAdvancementUndone(player, advancement)){
                                    AwardAdvancements.revokeAdvancement(player, advancement);
                                    player.sendMessage(Component.text("Achievement has been revoked. You are now able to encounter this literature again!"));
                                    player.playSound(Sound.sound(Key.key("ui.toast.out"), Sound.Source.MASTER, 2f, 2f), Sound.Emitter.self());
                                }
                            }
                        })));
    }
}
