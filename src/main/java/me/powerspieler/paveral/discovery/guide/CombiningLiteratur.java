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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static me.powerspieler.paveral.discovery.guide.BaseGuide.GUIDE_ENTRIES;

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

                    if(guide.getItemMeta() instanceof BookMeta bookMeta && Objects.equals(bookMeta.getAuthor(), "You")){
                        bookMeta.setAuthor(player.getName());
                    }

                    ItemStack result = new ItemStack(guide);
                    List<String> entries = result.getPersistentDataContainer().get(GUIDE_ENTRIES, Constant.STRING_LIST_DATA_TYPE);
                    if(entries != null){
                        String addition = ItemHelper.getPaveralNamespacedKey(literature, Constant.DISCOVERY);
                        assert addition != null; // Check for other than Discovery Type
                        entries.add(convertToGuideEntry(addition));

                        ItemMeta itemMeta = result.getItemMeta();
                        itemMeta.getPersistentDataContainer().set(GUIDE_ENTRIES, Constant.STRING_LIST_DATA_TYPE, entries);
                        result.setItemMeta(itemMeta);

                        event.getInventory().setResult(result);
                    }
                }
            }
        }
    }

    private String convertToGuideEntry(String string){
        String result = null;
        switch(string){
            case "altar_book" -> result = "Forming";
            //TODO
        }
        return result;
    }
}
