package me.powerspieler.paveral.discovery.guide;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.crafting.ItemHelper;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.view.LecternView;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;

import static me.powerspieler.paveral.discovery.guide.BaseGuide.GUIDE_ENTRIES;

public class GuideCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(sender instanceof Player player && args.length == 2) {
            ItemStack guideBook = null;
            if(ItemHelper.paveralNamespacedKeyEquals(player.getInventory().getItemInMainHand(), Constant.DISCOVERY, "guide_book")) {
                guideBook = player.getInventory().getItemInMainHand();
            } else if (ItemHelper.paveralNamespacedKeyEquals(player.getInventory().getItemInOffHand(), Constant.DISCOVERY, "guide_book")) {
                guideBook = player.getInventory().getItemInOffHand();
            } else if(isInsideGuideLectern(player)){
                LecternView lectern = (LecternView) player.getOpenInventory();
                guideBook = lectern.getTopInventory().getBook();
            }
            if(guideBook != null){
                BookMeta bookMeta = (BookMeta) guideBook.getItemMeta();
                if(bookMeta != null && (args[0].equals("hint") || args[0].equals("entry"))){
                    if(args[0].equals("entry") && !isAllowedToAccess(guideBook, args[1])) return true;

                    String className = "me.powerspieler.paveral.discovery.guide.";
                    if(args[0].equals("entry")){
                        className += "entries.";
                    } else {
                        className += "hints.";
                    }
                    className += args[1];

                    Class<?> act = null;
                    try {
                        act = Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        Paveral.getPlugin().getLogger().log(Level.SEVERE, "ClassNotFoundException while accessing guide entry: ", e);
                    }
                    if(act == null) return true;

                    try {
                        GuideBookEntry entry = (GuideBookEntry) act.getConstructor().newInstance();
                        List<Component> pages = entry.getPages();

                        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
                        BookMeta meta = (BookMeta) book.getItemMeta();
                        meta.setAuthor("author");
                        meta.setTitle("title");
                        meta.setGeneration(BookMeta.Generation.ORIGINAL);
                        for(Component page : pages){
                            meta.addPages(page);
                        }
                        book.setItemMeta(meta);
                        player.openBook(book);
                    } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                             NoSuchMethodException | ClassCastException e) {
                        Paveral.getPlugin().getLogger().log(Level.SEVERE, "Exception while getting pages for guide entry: ", e);
                    }
                }
            }
        }
        return true;
    }

    private boolean isInsideGuideLectern(Player player){
        if (player.getOpenInventory() instanceof LecternView lecternView) {
            return ItemHelper.paveralNamespacedKeyEquals(lecternView.getTopInventory().getBook(), Constant.DISCOVERY, "guide_book");
        }
        return false;
    }

    private boolean isAllowedToAccess(ItemStack book, String entry){
        List<String> entries = book.getPersistentDataContainer().get(GUIDE_ENTRIES, Constant.STRING_LIST_DATA_TYPE);
        return entries != null && entries.contains(entry);
    }
}
