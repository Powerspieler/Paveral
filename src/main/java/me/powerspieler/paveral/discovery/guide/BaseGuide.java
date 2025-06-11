package me.powerspieler.paveral.discovery.guide;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.discovery.DiscoveryBook;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Lectern;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseGuide extends DiscoveryBook implements Listener {
    static final NamespacedKey GUIDE_ENTRIES = new NamespacedKey(Paveral.getPlugin(), "guide_entries");

    public BaseGuide() {
        super("guide_book", "You", "Paveral Guide", BookMeta.Generation.ORIGINAL, new ArrayList<>(), false);
    }

    @Override
    public ItemStack build() {
        ItemStack guide = super.build();
        ItemMeta meta = guide.getItemMeta();
        meta.getPersistentDataContainer().set(GUIDE_ENTRIES, Constant.STRING_LIST_DATA_TYPE, new ArrayList<>());
        guide.setItemMeta(meta);
        return guide;
    }

    public static final NamespacedKey recipeKey = new NamespacedKey(Paveral.getPlugin(), "guide_book");
    public static ShapedRecipe registerRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new BaseGuide().build());
        recipe.shape(" F ","PBI"," E ");
        recipe.setIngredient('F', Material.FEATHER);
        recipe.setIngredient('P', Material.PAPER);
        recipe.setIngredient('B', Material.BOOK);
        recipe.setIngredient('I', Material.GLOW_INK_SAC);
        recipe.setIngredient('E', Material.ECHO_SHARD);
        return recipe;
    }

    // Always set pages when opening the book, to ensure the book is up to date

    @EventHandler
    private void onBookOpen(PlayerInteractEvent event){
        if(event.getItem() != null && event.getItem().getType() == Material.WRITTEN_BOOK && event.getItem().getItemMeta() instanceof BookMeta bookMeta){
            if(bookMeta.getPersistentDataContainer().has(Constant.DISCOVERY) && bookMeta.getPersistentDataContainer().get(Constant.DISCOVERY, PersistentDataType.STRING).equals("guide_book")){
                bookMeta.pages(generateGuideContent(event.getItem()));
                event.getItem().setItemMeta(bookMeta);
                event.getPlayer().openBook(event.getItem());
            }
        }

        if(event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.LECTERN
                && event.getClickedBlock().getBlockData() instanceof org.bukkit.block.data.type.Lectern lecternData
                && lecternData.hasBook()
                && event.getClickedBlock().getState() instanceof Lectern lectern) {
            ItemStack book = lectern.getInventory().getContents()[0];
            if(book != null && book.getType() == Material.WRITTEN_BOOK && book.getItemMeta() instanceof BookMeta bookMeta){
                if(bookMeta.getPersistentDataContainer().has(Constant.DISCOVERY) && bookMeta.getPersistentDataContainer().get(Constant.DISCOVERY, PersistentDataType.STRING).equals("guide_book")){
                    bookMeta.pages(generateGuideContent(book));
                    book.setItemMeta(bookMeta);
                    lectern.getInventory().setContents(new ItemStack[]{book});
                }
            }
        }

    }

    private final List<String> guideOrder = List.of(
            "Forming", "Dis", "Forge",
            "Enhanced", "Bonk","LightningRod",
            "Lightstaff","BedrockBreaker",
            "MusicCore","MusicPianoSword","MusicStringBlade","MusicPickaxe","MusicAxe","MusicShovel","MusicHoe",
            "CreeperDefuser","Chunkloader","Wrench","Worldalterer");
    private String convertToHumanString(String entry){
        return switch (entry) {
            case "Forming" -> "The Forming Altar";
            case "Dis" -> "Disassembling";
            case "Forge" -> "The Forge";
            case "Enhanced" -> "Enhanced Books";
            case "Bonk" -> "Bonk";
            case "LightningRod" -> "Lightning Rod";
            case "Lightstaff" -> "Lightstaff";
            case "BedrockBreaker" -> "Bedrock Breaker";
            case "MusicCore" -> "Music Core";
            case "MusicPianoSword" -> "Rhythms Awakening";
            case "MusicStringBlade" -> "String Blade";
            case "MusicPickaxe" -> "Resonating Pickaxe";
            case "MusicAxe" -> "Lumberjack's Bass";
            case "MusicShovel" -> "Bardic Inspiration";
            case "MusicHoe" -> "Scythe of Harmony";
            case "CreeperDefuser" -> "Creeper Defuser";
            case "Chunkloader" -> "Chunkloader";
            case "Wrench" -> "Wrench";
            case "Worldalterer" -> "Worldalterer";
            default -> "null";
        };
    }
    private List<Component> generateGuideContent(ItemStack book) {
        List<String> entries = book.getPersistentDataContainer().get(GUIDE_ENTRIES, Constant.STRING_LIST_DATA_TYPE);
        assert entries != null;

        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("Titlepage"));

        if(entries.isEmpty()){
            pages.add(Component.text("Welcome!\n\nThis book will assist you in collecting all recipes added by Paveral.\n\nYou can find various diaries and documents in your world, which can be added to this guide."));
            pages.add(Component.text("You can do this by combining the found literature with this guide via crafting.\nGet started by clicking on the first \"???\" on the next page!\n\n Note: This two introduction pages will be removed once you have added your first diary or document"));
        }

        for (int i = 0; i < Math.ceil((double) guideOrder.size() / 14) ; i++) { // i = page number
            Component buildingComponent = buildPage(i, entries);
            pages.add(buildingComponent);
        }
        return pages;
    }

    private @NotNull Component buildPage(int i, List<String> entries) {
        Component buildingComponent = Component.text("");
        for (int j = 0; j < 14 && i * 14 + j < guideOrder.size(); j++) { // j = entry number on page
            String entry = guideOrder.get(i * 14 + j);
            if (entries.contains(entry)) {
                buildingComponent = buildingComponent.append(Component.text(convertToHumanString(entry) + "\n")
                        .clickEvent(ClickEvent.runCommand("/guide entry " + entry)));
            } else {
                buildingComponent = buildingComponent.append(Component.text("???\n", NamedTextColor.DARK_PURPLE)
                        .clickEvent(ClickEvent.runCommand("/guide hint " + entry)));
            }
        }
        return buildingComponent;
    }

}
