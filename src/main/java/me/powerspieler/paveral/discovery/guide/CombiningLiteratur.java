package me.powerspieler.paveral.discovery.guide;

import me.powerspieler.paveral.crafting.ItemHelper;
import me.powerspieler.paveral.util.Constant;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
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
                        if(addition == null){
                            return;
                        }

                        entries.addAll(convertToGuideEntries(addition));

                        ItemMeta itemMeta = result.getItemMeta();
                        itemMeta.getPersistentDataContainer().set(GUIDE_ENTRIES, Constant.STRING_LIST_DATA_TYPE, entries);
                        result.setItemMeta(itemMeta);

                        event.getInventory().setResult(result);
                    }
                }
            }
        }
    }

    private Set<String> convertToGuideEntries(String string){
        Set<String> result = new HashSet<>();
        switch(string){
            case "altar_book" -> result.add("Forming");
            case "disassemble_paper" -> result.add("Dis"); // in guide only how to craft disbook // achievement reset
            //case "soos" -> result.addAll(List.of("Forge", "CreeperDefuser", "Chunkloader", "Wrench")); // paper via Again? achievemnt // reset
            case "diary_84" -> result.add("Enhanced");
            case "diary_34" -> result.add("Bonk");
            case "diary_17" -> result.add("LightningRod");
            // TODO Lightstaff Disass. Paper geben als Tutorial zum adden. result.add("Lightstaff")
            case "bedrock_breaker" -> result.add("BedrockBreaker");
            // TODO MusicCore // paper mit First_Forming Achievement // achievment reset wenn mehr benötigt
            //case "s" -> result.add("MusicCore");
            // TODO All MusicCore Items // paper with MuiscCore_Achievment // reset
            //case "soos" -> result.addAll(List.of("MusicPianoSword","MusicStringBlade","MusicPickaxe","MusicAxe","MusicShovel","MusicHoe"));
            //TODO  Worldalterer // book in acient city ("A book about .... . Looks like this book still isn't finished")
            // TODO case "soos" -> result.add("Worldalterer")

        }
        return result;
    }
}
