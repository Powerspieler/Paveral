package me.powerspieler.paveral.discovery.guide;

import me.powerspieler.paveral.crafting.ItemHelper;
import me.powerspieler.paveral.util.Constant;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.*;
import java.util.stream.Collectors;

import static me.powerspieler.paveral.discovery.guide.BaseGuide.GUIDE_ENTRIES;

public class CombiningLiteratur implements Listener {
    @EventHandler
    public void onCombining(PrepareItemCraftEvent event) {
        List<ItemStack> items = Arrays.stream(event.getInventory().getMatrix()).filter(Objects::nonNull).toList();
        if(items.size() == 2) {
            if(items.stream().anyMatch(item -> ItemHelper.paveralNamespacedKeyEquals(item, Constant.DISCOVERY, "guide_book"))){
                Optional<ItemStack> optionalGuide = items.stream().filter(item -> ItemHelper.paveralNamespacedKeyEquals(item, Constant.DISCOVERY, "guide_book")).findFirst();
                Optional<ItemStack> optionalLiterature = items.stream().filter(item -> !ItemHelper.paveralNamespacedKeyEquals(item, Constant.DISCOVERY, "guide_book")).findFirst();
                if(optionalGuide.isPresent() && optionalLiterature.isPresent()){
                    ItemStack guide = optionalGuide.get();
                    ItemStack literature = optionalLiterature.get();
                    Player player = (Player) event.getViewers().getFirst();

                    ItemStack result = new ItemStack(guide);
                    List<String> entries = result.getPersistentDataContainer().get(GUIDE_ENTRIES, Constant.STRING_LIST_DATA_TYPE);
                    if(entries != null){
                        String addition = ItemHelper.getPaveralNamespacedKey(literature, Constant.DISCOVERY);
                        if(addition == null){
                            return;
                        }

                        int entriesAmount = entries.size();
                        for ( String entry : convertToGuideEntries(addition)) {
                            if(!entries.contains(entry)){
                                entries.add(entry);
                            }
                        }
                        if(entries.size() == entriesAmount){
                            return;
                        }

                        BookMeta itemMeta = (BookMeta) result.getItemMeta();
                        itemMeta.getPersistentDataContainer().set(GUIDE_ENTRIES, Constant.STRING_LIST_DATA_TYPE, entries);
                        if(Objects.equals(itemMeta.getAuthor(), "You")) itemMeta.setAuthor(player.getName());
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
            //case "altar_book" -> result.add("Forming"); // Unlocked by default.
            case "disassemble_paper" -> result.add("Dis"); // wetpaper // in guide only how to craft disbook // achievement reset
            case "forge_paper" -> result.addAll(List.of("Forge", "CreeperDefuser", "Chunkloader", "Wrench")); // paper via Again? achievement // reset
            case "diary_84" -> result.add("Enhanced");
            case "diary_34" -> result.add("Bonk");
            case "diary_17" -> result.add("LightningRod");
            case "lightstaff_paper" -> result.add("Lightstaff"); // via disass.
            case "bedrock_breaker" -> result.add("BedrockBreaker"); // achievement reset
            case "musiccore_paper" -> result.add("MusicCore"); // paper mit First_Forming Achievement // achievement reset
            case "musiccore_items_paper" -> result.addAll(List.of("MusicPianoSword","MusicStringBlade","MusicPickaxe","MusicAxe","MusicShovel","MusicHoe")); // paper with MuiscCore_Achievment // reset
            case "worldalterer" -> result.add("Worldalterer"); // book in acient city ("A book about .... . Looks like this book still isn't finished")
        }
        return result;
    }
}
