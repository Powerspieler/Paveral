package me.powerspieler.paveral.discovery.guide;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.discovery.DiscoveryBook;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
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
        super("guide_book", "You", "Paveral Guide", BookMeta.Generation.ORIGINAL, new ArrayList<>(), false, false);
    }

    @Override
    public ItemStack build() {
        ItemStack guide = super.build();
        ItemMeta meta = guide.getItemMeta();
        meta.getPersistentDataContainer().set(GUIDE_ENTRIES, Constant.STRING_LIST_DATA_TYPE, List.of("Forming"));
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
        if(event.getAction().isRightClick() && event.getItem() != null && event.getItem().getType() == Material.WRITTEN_BOOK && event.getItem().getItemMeta() instanceof BookMeta bookMeta){
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
        pages.add(generateTitlePage());

        if(entries.size() == 1){
            pages.add(Component.text("Welcome!\n\nThis book will assist you in ")
                    .append(Component.text("collecting", NamedTextColor.GOLD))
                    .append(Component.text(" all recipes added by "))
                    .append(Component.text("Paveral", NamedTextColor.GOLD))
                    .append(Component.text(".\n\nYou can find various "))
                    .append(Component.text("diaries", NamedTextColor.GOLD))
                    .append(Component.text(" and "))
                    .append(Component.text("documents", NamedTextColor.GOLD))
                    .append(Component.text(" in your world, which can be added to "))
                    .append(Component.text("this guide", NamedTextColor.GOLD))
                    .append(Component.text(".")));
            pages.add(Component.text("You can do this by ")
                    .append(Component.text("combining", NamedTextColor.GOLD))
                    .append(Component.text(" the found piece of literature with this guide via "))
                    .append(Component.text("crafting", NamedTextColor.GOLD))
                    .append(Component.text(".\nGet started by clicking on the "))
                    .append(Component.text("first entry", NamedTextColor.GOLD))
                    .append(Component.text(" on the next page!\n\nNote: This two introduction pages will be "))
                    .append(Component.text("removed", NamedTextColor.GOLD))
                    .append(Component.text(" once you have added your first diary or document.")));
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
            NamedTextColor color = j % 2 == 0 ? NamedTextColor.GRAY : NamedTextColor.DARK_GRAY;
            if (entries.contains(entry)) {
                buildingComponent = buildingComponent.append(Component.text(convertToHumanString(entry) + "\n", color)
                        .clickEvent(ClickEvent.runCommand("/guide entry " + entry)));
            } else {
                buildingComponent = buildingComponent.append(Component.text("???\n", NamedTextColor.DARK_PURPLE)
                        .clickEvent(ClickEvent.runCommand("/guide hint " + entry)));
            }
        }
        return buildingComponent;
    }

    private Component generateTitlePage(){
        TextColor color = TextColor.color(240, 240,240);
        Component text = Component.text("eralGuide", color);
        int[] pavList = {1,2,3,4,6,8,9,11,13,14,15,16,18,20,21};
        int[] guideList = {2,4,5,6,7,9,10,11,12,13,14,16,17,18,19,21,22,23};

        int paveral = pavList[(int) Math.floor(Math.random() * pavList.length)];
        int[] trimmedGuideList = Arrays.stream(guideList).filter(num -> num > paveral).toArray();
        int guide = trimmedGuideList[(int) (Math.floor(Math.random() * trimmedGuideList.length))];

        for (int i = 1; i < 24; i++) {
            if(i == paveral){
                text = text.append(Component.text("Paveral", NamedTextColor.DARK_GREEN).append(Component.text("Guide", color)));
            } else if(i == guide){
                text = text.append(Component.text("Paveral", color).append(Component.text("Guide", NamedTextColor.LIGHT_PURPLE)));
            } else {
                text = text.append(Component.text("PaveralGuide", color));
            }
        }
        return text.append(Component.text("Pav", color));

    }

}
