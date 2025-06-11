package me.powerspieler.paveral.discovery.guide;

import me.powerspieler.paveral.crafting.ItemHelper;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.view.LecternView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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

    private boolean isAllowedToAccess(ItemStack book, String type, String pageName){
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        TextComponent secondpage = (TextComponent) bookMeta.page(2);
        List<Component> index = secondpage.content().startsWith("Welcome!") ? bookMeta.page(4).children() : bookMeta.page(2).children(); // TODO HERE
        return true;
    }


    final List<String> guideOrder = List.of("Altar", "Dis", "Forge","Enhanced","Bonk","LightningRod","Lightstaff","BedrockBreaker","MusicCore","MusicPianoSword","MusicStringBlade","MusicPickaxe","MusicAxe","MusicShovel","MusicHoe","CreeperDefuser","Chunkloader","Wrench","Worldalterer");

    int compare(String o1, String o2){
        return guideOrder.indexOf(o1) - guideOrder.indexOf(o2);
    }
}
