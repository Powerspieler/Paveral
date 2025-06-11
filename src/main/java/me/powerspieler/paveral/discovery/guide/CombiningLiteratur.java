package me.powerspieler.paveral.discovery.guide;

import me.powerspieler.paveral.crafting.ItemHelper;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CombiningLiteratur implements Listener {
    @EventHandler
    public void onCombining(PrepareItemCraftEvent event) {
        Set<ItemStack> items = Arrays.stream(event.getInventory().getMatrix()).filter(Objects::nonNull).collect(Collectors.toSet());
        if(items.size() == 2) {
            if(items.stream().anyMatch(item -> ItemHelper.paveralNamespacedKeyEquals(item, Constant.DISCOVERY, "guide_book"))){
                Optional<ItemStack> optionalGuide = items.stream().filter(item -> ItemHelper.paveralNamespacedKeyEquals(item, Constant.DISCOVERY, "guide_book")).findFirst();
                Optional<ItemStack> optionalLiterature = items.stream().filter(item -> !ItemHelper.paveralNamespacedKeyEquals(item, Constant.DISCOVERY, "guide_book")).findFirst();
                if(optionalGuide.isPresent() && optionalLiterature.isPresent()){
                    ItemStack guide = optionalGuide.get();
                    ItemStack literature = optionalLiterature.get();
                    Player player = (Player) event.getViewers().getFirst();
                    switch(ItemHelper.getPaveralNamespacedKey(literature, Constant.DISCOVERY)){
                        case "diary_35" -> {
                            // extract method
                            // change author if author is "You"
                            ItemStack result = new ItemStack(guide);
                            BookMeta meta = (BookMeta) result.getItemMeta();
                            meta.addPages(Component.text("Successful Crafting yay!"));





                            result.setItemMeta(meta);
                            event.getInventory().setResult(result);
                        }

                        case null, default -> {}
                    }
                }
            }
        }
    }

    //private
}
